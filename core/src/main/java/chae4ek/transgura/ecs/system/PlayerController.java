package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import chae4ek.transgura.game.Game;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerController extends System {

  @Override
  public boolean isUpdateEnabled() {
    return true;
  }

  @Override
  public boolean isFixedUpdateEnabled() {
    return false;
  }

  @Override
  public void update() {
    for (final Entity parent : getParentEntities()) update(parent);
  }

  private void update(final Entity player) {
    final Sprite sprite = player.getComponent(Sprite.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    if (InputProcessor.isKeyDown(PlayerSettings.PLAYER_RIGHT)) {
      if (!InputProcessor.isKeyDown(PlayerSettings.PLAYER_LEFT)) {
        sprite.flipX = false;
        body.applyLinearImpulse(PlayerSettings.SPEED * Game.getDeltaTime(), 0f, 0f, 0f, true);
      }
    } else if (InputProcessor.isKeyDown(PlayerSettings.PLAYER_LEFT)) {
      sprite.flipX = true;
      body.applyLinearImpulse(-PlayerSettings.SPEED * Game.getDeltaTime(), 0f, 0f, 0f, true);
    }

    if (InputProcessor.isKeyDown(PlayerSettings.PLAYER_UP)) {
      if (!InputProcessor.isKeyDown(PlayerSettings.PLAYER_DOWN))
        body.applyLinearImpulse(0f, PlayerSettings.SPEED * Game.getDeltaTime(), 0f, 0f, true);
    } else if (InputProcessor.isKeyDown(PlayerSettings.PLAYER_DOWN)) {
      body.applyLinearImpulse(0f, -PlayerSettings.SPEED * Game.getDeltaTime(), 0f, 0f, true);
    }
  }
}
