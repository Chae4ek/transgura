package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
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
    final Sprite sprite = player.getComponent(Sprite.class);
    final Position pos = player.getComponent(Position.class);

    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_RIGHT)) {
      sprite.flipX = false;
      pos.x += PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_LEFT)) {
      sprite.flipX = true;
      pos.x -= PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_UP)) {
      pos.y += PlayerSettings.SPEED * Game.getDeltaTime();
    }
    if (Gdx.input.isKeyPressed(PlayerSettings.PLAYER_DOWN)) {
      pos.y -= PlayerSettings.SPEED * Game.getDeltaTime();
    }
  }
}
