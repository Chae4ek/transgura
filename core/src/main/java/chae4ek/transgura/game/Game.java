package chae4ek.transgura.game;

import chae4ek.transgura.ecs.util.ResourceLoader;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.game.scenes.MainMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import java.util.function.Supplier;

public final class Game extends ApplicationAdapter {

  public static final float fixedDeltaTime = 1f / 25f;
  private static final transient GameAlert gameAlert = new GameAlert(Game.class);
  static Scene scene;
  private static Supplier<Scene> nextSceneCreator;
  private static long sceneStartTime;
  private static float time;

  /** @return the current scene */
  public static Scene getScene() {
    return scene;
  }

  /**
   * Schedule to set a new scene and exit the current scene immediately. If the sceneCreator is null
   * the game will close. Should invoke without custom multithreading, only engine!
   *
   * @param sceneCreator a new scene creator
   * @throws SceneExit it is necessary to exit whatever. Don't try to catch it. It doesn't work with
   *     custom multithreading
   */
  public static void setScene(final Supplier<Scene> sceneCreator) throws SceneExit {
    nextSceneCreator = sceneCreator;
    if (sceneCreator == null) Gdx.app.exit();
    throw new SceneExit(); // fast exit whatever
  }

  @Override
  public void create() {
    new MainMenu();
    sceneStartTime = System.nanoTime();
  }

  @Override
  public void dispose() {
    scene = null;
    ResourceLoader.dispose();
  }

  @Override
  public void render() {
    final float deltaTime = Gdx.graphics.getDeltaTime();
    time += deltaTime;

    final int fixedUpdateCount;
    if (time >= fixedDeltaTime) {
      fixedUpdateCount = (int) (time / fixedDeltaTime);
      time -= fixedUpdateCount * fixedDeltaTime;
    } else fixedUpdateCount = 0;

    scene.sceneLifetimeInSec = 1e-9f * (System.nanoTime() - sceneStartTime);
    try {
      scene.updateAndFixedUpdate(fixedUpdateCount);
    } catch (final SceneExit exit) {
      if (nextSceneCreator != null) {
        ResourceLoader.unloadSceneResources();
        nextSceneCreator.get();
        sceneStartTime = System.nanoTime();
        gameAlert.debug("Scene " + scene.getClass().getName() + " is loaded");
      }
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
