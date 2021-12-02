package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import chae4ek.transgura.ecs.util.input.Key;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.scenes.MainMenu;

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
    if (InputProcessor.isKeyDown(Key.ESCAPE)) Game.setScene(null);
    if (InputProcessor.isKeyJustDownNow(Key.SPACE)) Game.setScene(MainMenu::new);
  }

  /** @deprecated only for testing */
  @Deprecated(forRemoval = true)
  @Override
  public void fixedUpdate() {
    if (InputProcessor.isKeyDown(Key.ENTER)) Game.setScene(null);
  }
}
