package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class RenderManager {

  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  // TODO: clean up this
  private static final SpriteBatch defaultSpriteBatch = new SpriteBatch();

  private final ExtendViewport viewport;
  private final Map<Entity, Set<RenderComponent>> renderComponents = new ConcurrentHashMap<>();

  public RenderManager(final ExtendViewport viewport) {
    this.viewport = viewport;
  }

  /** Clean all resources */
  public static void dispose() {
    defaultSpriteBatch.dispose();
  }

  /** Add a render component to this render manager */
  void addRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    renderComponents.compute(
        parentEntity,
        (parent, components) -> {
          if (components == null) components = new HashSet<>(5);
          if (!components.add(renderComponent)) {
            gameAlert.warn(
                GameErrorType.RENDER_COMPONENT_HAS_BEEN_REPLACED,
                "parentEntity: " + parentEntity + ", render component: " + renderComponent);
          }
          return components;
        });
  }

  /** Remove all render components of this render manager if they present */
  void removeAllRenderComponentsIfPresent(final Entity parentEntity) {
    renderComponents.remove(parentEntity);
  }

  /** Remove the render component of this render manager */
  void removeRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    final Set<RenderComponent> renderComponents = this.renderComponents.get(parentEntity);
    if (renderComponents == null) {
      gameAlert.warn(
          GameErrorType.ENTITY_HAS_NOT_RENDER_COMPONENT, "parentEntity: " + parentEntity);
    } else {
      // too late, but it's deferred, so it's ok
      if (!renderComponents.remove(renderComponent)) {
        gameAlert.warn(
            GameErrorType.RENDER_COMPONENT_DOES_NOT_EXIST,
            "parentEntity: " + parentEntity + ", renderComponents: " + renderComponents);
      }
    }
  }

  /** Render all render components */
  public void renderAll() {
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    final Matrix4 projection = viewport.getCamera().combined;
    defaultSpriteBatch.setProjectionMatrix(projection);
    defaultSpriteBatch.begin();

    for (final Set<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled()) renderComponent.draw(projection);
      }
    }

    defaultSpriteBatch.end();
  }
}
