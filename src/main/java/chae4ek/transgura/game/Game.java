package chae4ek.transgura.game;

import chae4ek.transgura.game.scenes.MainMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public final class Game extends ApplicationAdapter {

  public static final float fixedDeltaTime = 1f / 25f;
  private static float time;

  private static Scene scene;

  /** @return the current scene */
  public static Scene getScene() {
    return scene;
  }

  /**
   * Set a new scene and exit the current scene immediately. If the scene is null the game will
   * close. Should invoke only in update methods!
   *
   * @param scene a new scene
   * @throws SceneExit it is necessary to exit whatever. Don't try to catch it. It doesn't work with
   *     multithreading
   */
  public static void setScene(final Scene scene) throws SceneExit {
    if (Game.scene != null) Game.scene.dispose();
    Game.scene = scene;
    if (scene == null) Gdx.app.exit();
    throw new SceneExit(); // fast exit whatever
  }

  @Override
  public void create() {
    scene = new MainMenu();
  }

  @Override
  public void dispose() {
    if (scene != null) {
      scene.dispose();
      scene = null;
    }
  }

  @Override
  public void render() {
    final float deltaTime = Gdx.graphics.getDeltaTime();
    time += deltaTime;

    long updateCount = 0;
    if (time >= fixedDeltaTime) {
      updateCount = (int) (time / fixedDeltaTime);
      time -= updateCount * fixedDeltaTime;
    }

    try {
      for (; updateCount > 0; --updateCount) scene.fixedUpdate();
      scene.update(deltaTime);
    } catch (final SceneExit exit) {
      return;
    }

    scene.render();
  }

  @Override
  public void resize(final int width, final int height) {
    if (scene != null) scene.resize(width, height);
  }

  /** Using to exit a scene */
  private static final class SceneExit extends RuntimeException {
    private SceneExit() {}
  }
}
