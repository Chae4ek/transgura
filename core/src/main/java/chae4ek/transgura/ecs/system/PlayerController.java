package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.system.collision.CollisionProcessor;
import chae4ek.transgura.ecs.system.collision.CollisionSubscriber;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerController extends System implements CollisionSubscriber {

  private static final float jumpStep = 0.06f;
  private static final float jumpInertia = 0.24f;
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
    final boolean dashButton = InputProcessor.isButtonJustDownNow(PlayerSettings.PLAYER_DASH);
    if (dashButton) dash = true;

    final boolean godMod = InputProcessor.isKeyJustDownNow(PlayerSettings.GOD_MOD);
    if (godMod) {
      setEnabled(false);
      final Entity parent = getParent();
      parent.getComponent(PlayerGodModController.class).setEnabled(true);
      final Array<Fixture> array =
          parent.getComponent(PhysicalBody.class).getBody().getFixtureList();
      for (final Fixture fixture : array) {
        if (fixture.getUserData() == "PLAYER") {
          fixture.setSensor(true);
          break;
        }
      }
    }
  }

  @Override
  public void fixedUpdate() {
    final Entity player = getParent();
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
      if (!up && jumpForce > jumpInertia && body.getLinearVelocity().y > 0f) {
        jumpForce = jumpInertia;
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
