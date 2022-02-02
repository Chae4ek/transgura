package chae4ek.transgura.game;

import chae4ek.transgura.ecs.RenderManager;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import chae4ek.transgura.ecs.util.resources.ResourceLoader;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.game.scenes.MainMenu;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import java.util.function.Supplier;

public final class Game extends ApplicationAdapter {

  private static final transient GameAlert gameAlert = new GameAlert(Game.class);

  static Scene scene;
  private static Supplier<Scene> nextSceneCreator;
  private static long sceneStartTime;

  private static float time;
  private static float deltaTime;

  /** @return delta time between the current frame and the previous one */
  public static float getDeltaTime() {
    return deltaTime;
  }

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
    InputProcessor.init();
    new MainMenu();
    sceneStartTime = System.nanoTime();
  }

  @Override
  public void dispose() {
    RenderManager.dispose();
    scene.systemManager.world.dispose();
    scene = null;
    ResourceLoader.dispose();
  }

  @Override
  public void render() {
    deltaTime = Gdx.graphics.getDeltaTime();
    time += deltaTime;

    final int fixedUpdateCount;
    if (time >= GameSettings.fixedDeltaTime) {
      fixedUpdateCount = (int) (time / GameSettings.fixedDeltaTime);
      time -= fixedUpdateCount * GameSettings.fixedDeltaTime;
    } else fixedUpdateCount = 0;

    scene.sceneLifetimeInSec = 1e-9f * (System.nanoTime() - sceneStartTime);
    try {
      scene.systemManager.updateAndFixedUpdate(fixedUpdateCount);
    } catch (final SceneExit exit) {
      if (nextSceneCreator != null) {
        InputProcessor.postUpdate();
        scene.systemManager.world.dispose();
        ResourceLoader.unloadSceneResources();
        nextSceneCreator.get();
        sceneStartTime = System.nanoTime();
        gameAlert.debug("Scene " + scene.getClass().getName() + " is loaded");
      }
      return;
    }
    InputProcessor.postUpdate();

    scene.renderManager.renderAll();
  }

  @Override
  public void resize(final int width, final int height) {
    if (scene != null) {
      Scene.camera.viewportWidth = width;
      Scene.camera.viewportHeight = height;
      scene.renderManager.setNewFrameBuffer(width, height);
    }
  }

  /** Using to exit a scene */
  private static final class SceneExit extends RuntimeException {
    private SceneExit() {}
  }
}
