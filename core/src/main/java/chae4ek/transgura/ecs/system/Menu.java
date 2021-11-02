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
  public void update() {
    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Game.setScene(null);
  }

  /** @deprecated only for testing */
  @Deprecated(forRemoval = true)
  @Override
  public void fixedUpdate() {
    if (Gdx.input.isKeyPressed(Keys.ENTER)) Game.setScene(null);
  }
}