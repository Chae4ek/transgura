package chae4ek.transgura.game;

import chae4ek.transgura.ecs.EntityManager;
import chae4ek.transgura.ecs.RenderManager;
import chae4ek.transgura.ecs.SystemManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public abstract class Scene {

  private static final ExtendViewport viewport =
      new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

  public final EntityManager entityManager = new EntityManager();
  public final SystemManager systemManager = new SystemManager();
  public final RenderManager renderManager = new RenderManager(viewport);

  float sceneLifetimeInSec;

  /**
   * Don't override this constructor cause it makes problems with scene changing. Use {@link
   * #create} instead
   */
  public Scene() {}

  /** Create and start this scene */
  public abstract void create();

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
