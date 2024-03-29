package chae4ek.transgura.ecs.system;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.InputProcessor;
import chae4ek.engine.ecs.System;
import chae4ek.transgura.scenes.MainMenu;
import com.badlogic.gdx.Input.Keys;

public class Menu extends System {

  @Override
  public void update() {
    if (InputProcessor.isKeyDown(Keys.ESCAPE)) Game.setScene(null);
    if (InputProcessor.isKeyJustDownNow(Keys.SPACE)) Game.setScene(MainMenu::new);
  }

  /**
   * @deprecated only for testing
   */
  @Deprecated(forRemoval = true)
  @Override
  public void fixedUpdate() {
    if (InputProcessor.isKeyDown(Keys.ENTER)) Game.setScene(null);
  }
}
