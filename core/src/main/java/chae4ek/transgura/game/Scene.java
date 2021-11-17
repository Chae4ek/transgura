package chae4ek.transgura.game;

import chae4ek.transgura.ecs.EntityManager;
import chae4ek.transgura.ecs.RenderManager;
import chae4ek.transgura.ecs.SystemManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public abstract class Scene {

  private static final ExtendViewport viewport =
      new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

  public final EntityManager entityManager = new EntityManager();
  public final SystemManager systemManager = new SystemManager();
  public final RenderManager renderManager = new RenderManager(viewport);
  public final World world = new World(new Vector2(0f, -9.81f), true);

  float sceneLifetimeInSec;

  /**
   * Create and start this scene. Before creating the {@link Game#scene} is set to the previous one
   * or null if this scene is the first
   */
  public Scene() {
    Game.scene = this;
  }

  /**
   * The lifetime of this scene in seconds. When a scene creates and starts this method returns 0
   *
   * @return the lifetime of this scene in seconds
   */
  public final float getSceneLifetimeInSec() {
    return sceneLifetimeInSec;
  }

  /**
   * Update and fixed update this scene
   *
   * @param fixedUpdateCount the count of fixed updates
   */
  public final void updateAndFixedUpdate(final int fixedUpdateCount) {
    systemManager.updateAndFixedUpdate(fixedUpdateCount);
  }

  /** Render this scene */
  public final void render() {
    renderManager.renderAll();
  }

  /** Re-render this scene when resizing the window */
  public final void resize(final int width, final int height) {
    viewport.update(width, height, true);
  }
}
