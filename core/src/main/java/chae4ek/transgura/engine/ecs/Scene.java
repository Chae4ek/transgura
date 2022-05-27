package chae4ek.transgura.engine.ecs;

import static chae4ek.transgura.engine.util.GameSettings.timeStepForPhysics;

import box2dLight.RayHandler;
import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.engine.util.collision.CollisionListener;
import chae4ek.transgura.engine.util.debug.DebugRenderManager;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import chae4ek.transgura.engine.util.serializers.WorldSerializer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    b2dWorld.step(timeStepForPhysics, 6, 2);
    systemManager.updateAndOneFixedUpdate(fixedUpdateCount > 0);
    while (--fixedUpdateCount > 0) {
      InputProcessor.postUpdate(); // updating just pressed/released keys
      systemManager.fixedUpdateAll();
    }
    renderManager.renderAll();
  }

  /** Save current scene */
  public void saveWorld(final DataOutputStream out) throws IOException {
    WorldSerializer.cleanSerializedCache();
    entityManager.serialize(out);
    WorldSerializer.cleanSerializedCache();

    out.writeFloat(sceneLifetimeInSec);
  }

  /** Load current scene */
  public void loadWorld(final DataInputStream in) throws IOException {
    WorldSerializer.cleanDeserializedCache();
    entityManager.deserialize(in);
    WorldSerializer.cleanDeserializedCache();

    sceneLifetimeInSec = in.readFloat();
  }

  public void softDispose() {
    rayHandler.dispose();
    b2dWorld.dispose();
  }

  public void disposeStatic() {
    renderManager.disposeStatic();
  }
}
