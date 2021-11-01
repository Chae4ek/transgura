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

  /** Start a scene */
  public abstract void start();

  /** Dispose the scene and clean the resources */
  public final void dispose() {
    renderManager.dispose();
  }

  /** Update this scene */
  public final void update(final float deltaTime) {
    systemManager.updateAll(deltaTime);
  }

  /** Fixed update this scene */
  public final void fixedUpdate() {
    systemManager.fixedUpdateAll();
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
