package chae4ek.engine.ecs;

import chae4ek.engine.util.GameSettings;
import chae4ek.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public final class Game implements ApplicationListener {

  private static final GameAlert gameAlert = new GameAlert(Game.class);

  static boolean sceneChanging; // to prevent (new)scene inside (Runnable)nextScene
  static Scene scene;
  private static Runnable nextScene;

  private static float time;
  private static float deltaTime;

  public Game() {
    nextScene = GameSettings.mainScene;
  }

  /**
   * @return the time between the current frame and the previous one in seconds
   */
  public static float getDeltaTime() {
    return deltaTime;
  }

  /**
   * @return the current scene
   */
  public static Scene getScene() {
    return scene;
  }

  /**
   * Schedule to set a new scene and exit the current scene immediately. If the sceneCreator is null
   * the game will close
   *
   * @param sceneCreator a new scene creator
   * @throws SceneExit it is necessary to exit whatever. Don't catch it!
   */
  public static void setScene(final Runnable sceneCreator) throws SceneExit {
    nextScene = sceneCreator;
    if (sceneCreator == null) Gdx.app.exit();
    throw new SceneExit(); // fast exit whatever
  }

  @Override
  public void create() {
    InputProcessor.init();
    sceneChanging = true;
    nextScene.run();
    if (sceneChanging) gameAlert.error("Scene should create inside Game.setScene() method");
  }

  @Override
  public void dispose() {
    scene.softDispose();
    scene.disposeStatic();
    GameSettings.resourceManager.dispose();
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

    try {
      scene.update(fixedUpdateCount);
    } catch (final SceneExit exit) {
      if (nextScene != null) {
        InputProcessor.postUpdate();

        GameSettings.resourceManager.unloadSceneResources();

        scene.softDispose();
        sceneChanging = true;
        nextScene.run();
        if (sceneChanging) gameAlert.error("Scene should create inside Game.setScene() method");

        gameAlert.debug("Scene {} is loaded", scene);
      }
      return;
    }
    InputProcessor.postUpdate();
  }

  @Override
  public void pause() {}

  @Override
  public void resume() {}

  @Override
  public void resize(final int width, final int height) {
    if (scene != null) {
      scene.camera.viewportWidth = width;
      scene.camera.viewportHeight = height;
      RenderManager.setNewFrameBuffer(width, height);
    }
  }

  /** Using to exit a scene */
  private static final class SceneExit extends RuntimeException {
    private SceneExit() {}
  }
}
