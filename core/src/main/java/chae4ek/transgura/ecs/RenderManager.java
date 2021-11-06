package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.RenderUtils;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class RenderManager {

  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  private final ExtendViewport viewport;
  private final Map<Entity, Set<RenderComponent>> renderComponents = new ConcurrentHashMap<>();

  public RenderManager(final ExtendViewport viewport) {
    this.viewport = viewport;
  }

  /** Add a render component to this render manager */
  void addRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    renderComponents.compute(
        parentEntity,
        (parent, components) -> {
          if (components == null) {
            /*
             it's not a thread-safe set cause the render isn't parallel;
             the render is invoked after update/fixedUpdate;
             all operations with the set of render components are in the thread-safe methods like this
            */
            components = new HashSet<>(GameSettings.AVG_RENDER_COMPONENTS_PER_ENTITY);
          }
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
    if (renderComponents.computeIfPresent(
            parentEntity,
            (parent, components) -> {
              if (!components.remove(renderComponent)) {
                gameAlert.warn(
                    GameErrorType.RENDER_COMPONENT_DOES_NOT_EXIST,
                    "parentEntity: " + parentEntity + ", renderComponents: " + renderComponents);
              }
              return components;
            })
        == null) {
      gameAlert.warn(
          GameErrorType.ENTITY_HAS_NOT_RENDER_COMPONENT, "parentEntity: " + parentEntity);
    }
  }

  /** Render all render components */
  public void renderAll() {
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    final Matrix4 projection = viewport.getCamera().combined;
    final Collection<SpriteBatch> spriteBatches = RenderUtils.getSpriteBatches();

    for (final SpriteBatch spriteBatch : spriteBatches) {
      spriteBatch.setProjectionMatrix(projection);
      spriteBatch.begin();
    }

    for (final Set<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled) renderComponent.draw();
      }
    }

    for (final SpriteBatch spriteBatch : spriteBatches) {
      spriteBatch.end();
    }
  }
}
