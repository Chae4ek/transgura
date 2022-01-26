package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerGodModController extends System {

  public PlayerGodModController() {
    super(false);
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
    final boolean godMod = InputProcessor.isKeyJustDownNow(PlayerSettings.GOD_MOD);
    if (godMod) {
      setEnabled(false);
      final Entity parent = getParent();
      parent.getComponent(PlayerController.class).setEnabled(true);
      final Array<Fixture> array =
          parent.getComponent(PhysicalBody.class).getBody().getFixtureList();
      for (final Fixture fixture : array) {
        if (fixture.getUserData() == "PLAYER") {
          fixture.setSensor(false);
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

    if (right && !left) {
      animation.flipX = false;
      body.applyLinearImpulse(PlayerSettings.SPEED, 0f, 0f, 0f, true);
    }
    if (!right && left) {
      animation.flipX = true;
      body.applyLinearImpulse(-PlayerSettings.SPEED, 0f, 0f, 0f, true);
    }

    if (up && !down) {
      body.applyLinearImpulse(0f, PlayerSettings.SPEED, 0f, 0f, true);
    }
    if (!up && down) {
      body.applyLinearImpulse(0f, -PlayerSettings.SPEED, 0f, 0f, true);
    }
  }
}
