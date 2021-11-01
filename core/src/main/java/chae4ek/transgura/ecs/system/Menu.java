package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.System;
import chae4ek.transgura.game.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Menu extends System {

  public Menu(final boolean isEnabled) {
    super(isEnabled);
  }

  @Override
  public void update(final float deltaTime) {
    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Game.setScene(null);
  }
}
