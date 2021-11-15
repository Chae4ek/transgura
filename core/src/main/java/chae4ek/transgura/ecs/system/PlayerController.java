package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import chae4ek.transgura.game.Game;
import com.badlogic.gdx.Gdx;

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
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_RIGHT)) {
      player.getComponent(Position.class).x += PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_LEFT)) {
      player.getComponent(Position.class).x -= PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_UP)) {
      player.getComponent(Position.class).y += PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_DOWN)) {
      player.getComponent(Position.class).y -= PlayerSettings.SPEED * Game.getDeltaTime();
    }
  }
}
