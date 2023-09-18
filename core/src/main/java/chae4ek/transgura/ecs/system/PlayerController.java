package chae4ek.transgura.ecs.system;

import chae4ek.engine.ecs.Entity;
import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.InputProcessor;
import chae4ek.engine.ecs.System;
import chae4ek.engine.util.GameSettings;
import chae4ek.engine.util.collision.CollisionSubscriber;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.component.Particles;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.util.collision.CollisionProcessor;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerController extends System implements CollisionSubscriber {

  public float speedScale = 180;
  public Vector2 maxSpeed = new Vector2(50, 50);
  public float maxJumpHeight = 20;
  public float maxJumpTime = 0.3f;
  public float normalGravityScale = 0.5f;
  public float maxIntermediateHeight = 10;
  public float maxIntermediateTime = 0.15f;
  public float intermediateGravityScale = 0.1f;
  public float fallingGravityScale = 0.5f;

  private Vector2 velocity;
  private float xAxis;
  private boolean isJumpButtonJustPressed;
  private boolean isJumpButtonReleased;
  private final Vector2 appliedVelocity = new Vector2();
  private float jumpTime;
  private boolean isJumping;
  private boolean isOnGround;
  private boolean isTouchingRoof;

  private int pretouchedGrounds;
  private int touchedGrounds;
  private int touchedRoofs;

  private boolean isCoyoteTimeStarted;
  private float coyoteTime;
  private boolean dash;

  public PlayerController() {
    Game.getScene().collisionListener.addCollisionSubscriber(this);
  }

  @Override
  public void update() {
    if (!isJumpButtonJustPressed) {
      isJumpButtonJustPressed = InputProcessor.isKeyJustDownNow(Player.PLAYER_UP);
    }
    isJumpButtonReleased = InputProcessor.isKeyReleased(Player.PLAYER_UP);

    if (isCoyoteTimeStarted && coyoteTime > 0) coyoteTime -= Game.getDeltaTime();

    final boolean dashButton = InputProcessor.isButtonJustDownNow(Player.PLAYER_DASH);
    if (dashButton) dash = true;

    final boolean godMod = InputProcessor.isKeyJustDownNow(Player.GOD_MOD);
    if (godMod) switchToGodMod();
  }

  public void switchToGodMod() {
    setEnabled(false);
    final Entity parent = getParent();
    parent.getComponent(PlayerGodModController.class).setEnabled(true);
    final Array<Fixture> array = parent.getComponent(PhysicalBody.class).getBody().getFixtureList();
    for (final Fixture fixture : array) {
      final EntityData entityData = CollisionProcessor.getEntityData(fixture.getUserData());
      if (entityData != null && "PLAYER".equals(entityData.tag)) {
        fixture.setSensor(true);
      }
    }
  }

  @Override
  public void fixedUpdate() {
    final Player player = (Player) getParent();
    final Particles dashParticles = player.getComponent(Particles.class);
    final AnimatedSprites animation = player.getComponent(AnimatedSprites.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    final boolean right = InputProcessor.isKeyDown(Player.PLAYER_RIGHT);
    final boolean left = InputProcessor.isKeyDown(Player.PLAYER_LEFT);

    isOnGround = touchedGrounds > 0;
    isTouchingRoof = touchedRoofs > 0;
    velocity = body.getLinearVelocity();
    xAxis = (right ? 1 : 0) + (left ? -1 : 0);

    handleInput();

    applyHorizontalVelocity();
    applyVerticalVelocity();
    applyConstraints();

    if (dash) {
      dash = false;
      dashParticles.particleEffect.start();
      // TODO: make it smoother. do interpolation?
      appliedVelocity.x += xAxis * 100f;
    }
    body.setLinearVelocity(appliedVelocity);

    // reset just pressed buttons here in order to process them in fixedUpdate()
    if (pretouchedGrounds == 0 || isOnGround) isJumpButtonJustPressed = false;

    final boolean isRunning = xAxis != 0;
    animation.setAnimation(isRunning ? player.run : player.idle);
    if (xAxis > 0) animation.flipX = false;
    if (xAxis < 0) animation.flipX = true;
  }

  private void handleInput() {
    isCoyoteTimeStarted = true;

    if (isOnGround && !isJumping) {
      appliedVelocity.y = 0; // preventing quick fall off the edge
      coyoteTime = 0.040f; // in seconds
      isCoyoteTimeStarted = false;
    }

    if ((isOnGround || coyoteTime > 0) && !isJumping && isJumpButtonJustPressed) {
      coyoteTime = 0;
      isJumping = true;
      jumpTime = 0;
      appliedVelocity.y = computeVelocity(normalGravityScale, maxJumpHeight, maxJumpTime);
    }

    if (isTouchingRoof) {
      isJumping = false; // preventing floating when player hits the roof
      appliedVelocity.y = 0;
    }

    if (isJumping) {
      jumpTime += GameSettings.fixedDeltaTime;
      if (isJumpButtonReleased || jumpTime > maxJumpTime) {
        isJumping = false;
      }
    }
  }

  private void applyHorizontalVelocity() {
    appliedVelocity.x = velocity.x + xAxis * speedScale * GameSettings.fixedDeltaTime;
  }

  private void applyVerticalVelocity() {
    final float gravityScale = isJumping ? normalGravityScale : fallingGravityScale;

    appliedVelocity.y -=
        computeVelocity(gravityScale, maxJumpHeight, maxJumpTime * maxJumpTime)
            * GameSettings.fixedDeltaTime;

    if (!isJumping) { // when jump is interrupted
      final float smoothVelocity =
          computeVelocity(intermediateGravityScale, maxIntermediateHeight, maxIntermediateTime);
      appliedVelocity.y = Math.min(appliedVelocity.y, smoothVelocity);
    }
  }

  /**
   * velocity := (gravityScale * maxJumpHeight) / (maxJumpTime)
   *
   * <p>acceleration := (gravityScale * maxJumpHeight) / (maxJumpTime^2)
   *
   * @param maxJumpHeight the parabolic jump's height of the highest point
   * @param maxJumpTime the time needed to reach the maxHeight
   */
  private float computeVelocity(
      final float gravityScale, final float maxJumpHeight, final float maxJumpTime) {
    return gravityScale * maxJumpHeight / maxJumpTime;
  }

  private void applyConstraints() {
    appliedVelocity.x = MathUtils.clamp(appliedVelocity.x, -maxSpeed.x, maxSpeed.x);
    appliedVelocity.y = MathUtils.clamp(appliedVelocity.y, -maxSpeed.y, maxSpeed.y);
  }

  @Override
  protected void onDestroy() {
    Game.getScene().collisionListener.removeCollisionSubscriber(this);
  }

  @Override
  public void beginContact(final Contact contact) {
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "GROUND")) {
      ++touchedGrounds;
    }
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_PRE_BOTTOM", "GROUND")) {
      ++pretouchedGrounds;
    }
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_TOP", "GROUND")) {
      ++touchedRoofs;
    }
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "EXIT")) {
      java.lang.System.out.println("EXIT");
    }
  }

  @Override
  public void endContact(final Contact contact) {
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "GROUND")) {
      --touchedGrounds;
    }
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_PRE_BOTTOM", "GROUND")) {
      --pretouchedGrounds;
    }
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_TOP", "GROUND")) {
      --touchedRoofs;
    }
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    super.deserialize(deserializer);
    Game.getScene().collisionListener.addCollisionSubscriber(this);
  }
}
