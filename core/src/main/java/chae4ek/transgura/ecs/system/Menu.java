package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.System;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.scenes.MainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Menu extends System {

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
    if (Gdx.input.isKeyPressed(Keys.ESCAPE)) Game.setScene(null);
    if (Gdx.input.isKeyPressed(Keys.SPACE)) Game.setScene(MainMenu::new);
  }

  /** @deprecated only for testing */
  @Deprecated(forRemoval = true)
  @Override
  public void fixedUpdate() {
    if (Gdx.input.isKeyPressed(Keys.ENTER)) Game.setScene(null);
  }
}
