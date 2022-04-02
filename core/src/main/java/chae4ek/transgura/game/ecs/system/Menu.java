package chae4ek.transgura.game.ecs.system;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.InputProcessor;
import chae4ek.transgura.engine.ecs.System;
import chae4ek.transgura.game.scenes.SceneLoader;
import com.badlogic.gdx.Input.Keys;

public class Menu extends System {

  @Override
  public void update() {
    if (InputProcessor.isKeyDown(Keys.ESCAPE)) Game.setScene(null);
    if (InputProcessor.isKeyJustDownNow(Keys.SPACE))
      Game.setScene(() -> new SceneLoader("saves/world0"));
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
