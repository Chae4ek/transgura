package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.system.collision.CollisionProcessor;
import chae4ek.transgura.ecs.system.collision.CollisionSubscriber;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import chae4ek.transgura.ecs.util.input.Button;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

public class PlayerController extends System implements CollisionSubscriber {

  private static final float jumpStep = 0.06f;
  private float jumpForce;
  private boolean allowJump;
  private int touchedGrounds;
  private boolean dash;

  public PlayerController() {
    scene.systemManager.collisionListener.addCollisionSubscriber(this);
  }

  @Override
  public boolean isUpdateEnabled() {
    return true;
  }

  @Override
  public boolean isFixedUpdateEnabled() {
    return true;
  }

  @Override
  public void update() {
    final boolean rightMouse = InputProcessor.isButtonJustDownNow(Button.RIGHT);
    if (rightMouse) dash = true;
  }

  @Override
  public void fixedUpdate() {
    for (final Entity parent : getParentEntities()) fixedUpdate(parent);
  }

  private void fixedUpdate(final Entity player) {
    final AnimatedSprite animation = player.getComponent(AnimatedSprite.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    final boolean right = InputProcessor.isKeyDown(PlayerSettings.PLAYER_RIGHT);
    final boolean left = InputProcessor.isKeyDown(PlayerSettings.PLAYER_LEFT);
    final boolean up = InputProcessor.isKeyDown(PlayerSettings.PLAYER_UP);
    final boolean down = InputProcessor.isKeyDown(PlayerSettings.PLAYER_DOWN);

    final float playerSpeed;
    if (dash) {
      dash = false;
      final Vector2 vel = body.getLinearVelocity();
      vel.y = jumpForce = 0f;
      body.setLinearVelocity(vel);

      playerSpeed = (PlayerSettings.DASH_FORCE + PlayerSettings.SPEED);
    } else playerSpeed = PlayerSettings.SPEED;

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

    if (allowJump && up && !down) {
      isRunning = true;
      allowJump = false;
      jumpForce = PlayerSettings.JUMP_FORCE;
      body.applyLinearImpulse(0f, jumpForce, 0f, 0f, true);
    } else {
      if (!up && jumpForce > jumpStep * 5f && body.getLinearVelocity().y > 0f) {
        jumpForce = jumpStep * 5f;
      }
    }

    if (!up && down) {
      isRunning = true;
      body.applyLinearImpulse(0f, -playerSpeed, 0f, 0f, true);
    }

    if (body.getLinearVelocity().y == 0f) jumpForce = -jumpStep;
    else jumpForce -= jumpStep;

    body.applyLinearImpulse(0f, jumpForce, 0f, 0f, true);

    animation.setAnimation(isRunning ? PlayerSettings.run : PlayerSettings.idle);
  }

  @Override
  public void onDestroy() {
    scene.systemManager.collisionListener.removeCollisionSubscriber(this);
  }

  @Override
  public void beginContact(final Contact contact) {
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "GROUND")) {
      ++touchedGrounds;
      allowJump = touchedGrounds > 0;
    }
  }

  @Override
  public void endContact(final Contact contact) {
    if (CollisionProcessor.isFixturesCollision(contact, "PLAYER_BOTTOM", "GROUND")) {
      --touchedGrounds;
      allowJump = touchedGrounds > 0;
    }
  }
}
