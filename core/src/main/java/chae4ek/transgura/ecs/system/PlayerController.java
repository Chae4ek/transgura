package chae4ek.transgura.ecs.system;

import chae4ek.engine.ecs.Entity;
import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.InputProcessor;
import chae4ek.engine.ecs.System;
import chae4ek.engine.util.collision.CollisionSubscriber;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.util.collision.CollisionProcessor;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerController extends System implements CollisionSubscriber {

  private static final float jumpStep = 0.06f;
  private static final float jumpInertia = 0.24f;
  private static final int jumpDelayOffset = 5;
  private float jumpForce;
  private int allowJumpDelay;
  private int touchedGrounds;
  private boolean dash;

  public PlayerController() {
    Game.getScene().collisionListener.addCollisionSubscriber(this);
  }

  @Override
  public void update() {
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
    final Entity player = getParent();
    final AnimatedSprites animation = player.getComponent(AnimatedSprites.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    final boolean right = InputProcessor.isKeyDown(Player.PLAYER_RIGHT);
    final boolean left = InputProcessor.isKeyDown(Player.PLAYER_LEFT);
    final boolean up = InputProcessor.isKeyDown(Player.PLAYER_UP);
    final boolean down = InputProcessor.isKeyDown(Player.PLAYER_DOWN);

    final float playerSpeed;
    if (dash) {
      dash = false;
      playerSpeed = (Player.DASH_FORCE + Player.SPEED);
    } else playerSpeed = Player.SPEED;

    boolean isRunning = false;
    if (right && !left) {
      isRunning = true;
      animation.flipX = false;
      body.applyLinearImpulse(playerSpeed, 0f, 0f, 0f, true);
    }
    if (!right && left) {
      isRunning = true;
      animation.flipX = true;
      body.applyLinearImpulse(-playerSpeed, 0f, 0f, 0f, true);
    }

    boolean onGround = touchedGrounds > 0;
    if (onGround) {
      allowJumpDelay = jumpDelayOffset;
      jumpForce = 0f;
    }

    if (!onGround && allowJumpDelay > 0) --allowJumpDelay;
    if (allowJumpDelay > 0 && up && !down) {
      isRunning = true;
      onGround = false;
      allowJumpDelay = 0;
      jumpForce = Player.JUMP_FORCE;
      body.applyLinearImpulse(0f, jumpForce, 0f, 0f, true);
    } else {
      if (!up && jumpForce > jumpInertia && body.getLinearVelocity().y > 0f) {
        jumpForce = jumpInertia;
      }
    }

    if (!up && down) {
      isRunning = true;
      body.applyLinearImpulse(0f, -playerSpeed, 0f, 0f, true);
    }

    if (jumpForce > 0f && body.getLinearVelocity().y <= 0f) jumpForce = 0f;
    else if (!onGround) jumpForce -= jumpStep;

    if (jumpForce != 0f) body.applyLinearImpulse(0f, jumpForce, 0f, 0f, true);

    final Vector2 vel = body.getLinearVelocity();
    if (onGround && vel.y < 0f) {
      vel.y = 0f;
      body.setLinearVelocity(vel);
    }

    animation.setAnimation(isRunning ? ((Player) getParent()).run : ((Player) getParent()).idle);
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
  }

  @Override
  public void endContact(final Contact contact) {
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "GROUND")) {
      --touchedGrounds;
    }
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    super.deserialize(deserializer);
    Game.getScene().collisionListener.addCollisionSubscriber(this);
  }
}
