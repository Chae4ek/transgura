package chae4ek.transgura.engine.ecs;

import static chae4ek.transgura.engine.util.GameSettings.timeStepForPhysics;

import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.engine.util.collision.CollisionListener;
import chae4ek.transgura.engine.util.debug.DebugRenderManager;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Scene {

  private static final GameAlert gameAlert = new GameAlert(Scene.class);

  public final OrthographicCamera camera;

  public final World world;
  public final CollisionListener collisionListener;

  final EntityManager entityManager;
  final SystemManager systemManager;
  final RenderManager renderManager;

  private final long sceneStartTime;
  private float sceneLifetimeInSec;

  /** Create and start this scene */
  public Scene() {
    if (!Game.sceneChanging) {
      gameAlert.error("Scene can create only once inside Game.setScene() method");
    }
    Game.sceneChanging = false;
    Game.scene = this;

    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    world = new World(Vector2.Zero, true);
    collisionListener = new CollisionListener();
    world.setContactListener(collisionListener);
    entityManager = new EntityManager();
    systemManager = new SystemManager();
    renderManager =
        GameSettings.isBox2DDebugRendererOn ? new DebugRenderManager() : new RenderManager();

    sceneStartTime = java.lang.System.nanoTime();
  }

  /**
   * The lifetime of this scene in seconds. When a scene creates and starts this method returns 0
   *
   * @return the lifetime of this scene in seconds
   */
  public final float getSceneLifetimeInSec() {
    return sceneLifetimeInSec;
  }

  final void dispose() {
    world.dispose();
  }

  final void updateAll(final int fixedUpdateCount) {
    sceneLifetimeInSec = 1e-9f * (java.lang.System.nanoTime() - sceneStartTime);

    world.step(timeStepForPhysics, 6, 2);
    systemManager.updateAndFixedUpdate(fixedUpdateCount);

    renderManager.renderAll();
  }
}
