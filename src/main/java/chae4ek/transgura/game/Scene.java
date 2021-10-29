package chae4ek.transgura.game;

import chae4ek.transgura.ecs.entity.EntityManager;
import chae4ek.transgura.ecs.system.SystemManager;
import chae4ek.transgura.render.RenderManager;

public abstract class Scene {

  protected final SystemManager systemManager = new SystemManager();
  protected final EntityManager entityManager = new EntityManager();
  protected final RenderManager renderManager = new RenderManager();

  /** Close the scene and clean the resources */
  public final void close() {
    renderManager.close();
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
    renderManager.resize(width, height);
  }
}
