package chae4ek.engine.ecs;

import box2dLight.RayHandler;
import chae4ek.engine.util.GameSettings;
import chae4ek.engine.util.collision.CollisionListener;
import chae4ek.engine.util.debug.DebugRenderManager;
import chae4ek.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Scene {

  private static final GameAlert gameAlert = new GameAlert(Scene.class);

  public final OrthographicCamera camera;

  public final World b2dWorld;
  public final RayHandler rayHandler;
  public final CollisionListener collisionListener;

  final EntityManager entityManager;
  final SystemManager systemManager;
  final RenderManager renderManager;

  private float sceneLifetimeInSec;

  /** Create and start this scene */
  public Scene() {
    if (!Game.sceneChanging) {
      gameAlert.error("Scene can create only once inside Game.setScene() method");
    }
    Game.sceneChanging = false;
    Game.scene = this;

    camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    b2dWorld = new World(Vector2.Zero, true);
    rayHandler = new RayHandler(b2dWorld);
    collisionListener = new CollisionListener();
    b2dWorld.setContactListener(collisionListener);
    entityManager = new EntityManager();
    systemManager = new SystemManager();
    renderManager =
        GameSettings.isBox2DDebugRendererOn ? new DebugRenderManager() : new RenderManager();
  }

  public final Iterable<Entity> entityIterable() {
    return entityManager.entityIterable();
  }

  /**
   * The lifetime of this scene in seconds. When a scene creates and starts this method returns 0
   *
   * @return the lifetime of this scene in seconds
   */
  public final float getSceneLifetimeInSec() {
    return sceneLifetimeInSec;
  }

  final void update(int fixedUpdateCount) {
    sceneLifetimeInSec += Game.getDeltaTime();

    // FIXME: timeStepForPhysics depends on fixedDeltaTime. it should not be so
    if (fixedUpdateCount > 0) b2dWorld.step(GameSettings.timeStepForPhysics, 6, 2);
    systemManager.updateAndOneFixedUpdate(fixedUpdateCount > 0);
    while (--fixedUpdateCount > 0) {
      InputProcessor.postUpdate(); // updating just pressed/released keys
      b2dWorld.step(GameSettings.timeStepForPhysics, 6, 2);
      systemManager.fixedUpdateAll();
    }
    renderManager.renderAll();
  }

  public void softDispose() {
    rayHandler.dispose();
    b2dWorld.dispose();
  }

  public void disposeStatic() {
    renderManager.disposeStatic();
  }
}
