package chae4ek.transgura.game.ecs.system;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.InputProcessor;
import chae4ek.transgura.engine.ecs.System;
import chae4ek.transgura.game.ecs.component.AnimatedSprites;
import chae4ek.transgura.game.ecs.entity.Player;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerGodModController extends System {

  public PlayerGodModController() {
    super(false);
  }

  @Override
  public void update() {
    final boolean godMod = InputProcessor.isKeyJustDownNow(Player.GOD_MOD);
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
    final AnimatedSprites animation = player.getComponent(AnimatedSprites.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    final boolean right = InputProcessor.isKeyDown(Player.PLAYER_RIGHT);
    final boolean left = InputProcessor.isKeyDown(Player.PLAYER_LEFT);
    final boolean up = InputProcessor.isKeyDown(Player.PLAYER_UP);
    final boolean down = InputProcessor.isKeyDown(Player.PLAYER_DOWN);

    if (right && !left) {
      animation.flipX = false;
      body.applyLinearImpulse(Player.SPEED, 0f, 0f, 0f, true);
    }
    if (!right && left) {
      animation.flipX = true;
      body.applyLinearImpulse(-Player.SPEED, 0f, 0f, 0f, true);
    }

    if (up && !down) {
      body.applyLinearImpulse(0f, Player.SPEED, 0f, 0f, true);
    }
    if (!up && down) {
      body.applyLinearImpulse(0f, -Player.SPEED, 0f, 0f, true);
    }
  }
}
