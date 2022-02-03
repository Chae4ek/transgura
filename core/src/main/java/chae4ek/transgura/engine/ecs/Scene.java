package chae4ek.transgura.engine.ecs;

import static chae4ek.transgura.engine.util.debug.GameSettings.timeStepForPhysics;

import chae4ek.transgura.engine.util.collision.CollisionListener;
import chae4ek.transgura.engine.util.debug.DebugRenderManager;
import chae4ek.transgura.engine.util.debug.GameSettings;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Scene {

  private static final GameAlert gameAlert = new GameAlert(Scene.class);

  public final OrthographicCamera camera =
      new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

  public final World world = new World(Vector2.Zero, true);
  public final CollisionListener collisionListener = new CollisionListener();

  final EntityManager entityManager = new EntityManager();
  final SystemManager systemManager = new SystemManager();
  final RenderManager renderManager =
      GameSettings.isDebugBox2DRendererOn ? new DebugRenderManager(this) : new RenderManager(this);

  private final long sceneStartTime;
  private float sceneLifetimeInSec;

  /** Create and start this scene */
  public Scene() {
    if (!Game.sceneChanging) {
      gameAlert.error("Scene can create only once inside Game.setScene() method");
    }
    Game.sceneChanging = false;
    Game.scene = this;
    world.setContactListener(collisionListener);
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
