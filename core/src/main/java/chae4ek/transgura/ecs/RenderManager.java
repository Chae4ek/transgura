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
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public final class RenderManager {

  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  private final ExtendViewport viewport;
  private final Map<Entity, Set<RenderComponent>> entityComponents = new ConcurrentHashMap<>();
  private final NavigableMap<Integer, Set<RenderComponent>> renderComponents =
      new ConcurrentSkipListMap<>();

  public RenderManager(final ExtendViewport viewport) {
    this.viewport = viewport;
  }

  /** Change the priority for rendering */
  void changeZOrder(final RenderComponent renderComponent, final int newZOrder) {
    renderComponents.computeIfPresent(
        renderComponent.getZOrder(),
        (z, rcomps) -> {
          rcomps.remove(renderComponent);
          return rcomps;
        });
    renderComponent.zOrder = newZOrder; // TODO: fix the add to both sets
    renderComponents.compute(
        newZOrder,
        (z, rcomps) -> {
          if (rcomps == null) rcomps = new HashSet<>();
          rcomps.add(renderComponent);
          return rcomps;
        });
  }

  /** Add a render component to this render manager */
  void addRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    entityComponents.compute(
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
          if (components.add(renderComponent)) {
            renderComponents.compute(
                renderComponent.getZOrder(),
                (z, rcomps) -> {
                  if (rcomps == null) rcomps = new HashSet<>();
                  rcomps.add(renderComponent);
                  return rcomps;
                });
          } else {
            gameAlert.warn(
                GameErrorType.RENDER_COMPONENT_HAS_BEEN_REPLACED,
                "parentEntity: " + parentEntity + ", render component: " + renderComponent);
          }
          return components;
        });
  }

  /** Remove all render components of this render manager if they present */
  void removeAllRenderComponentsIfPresent(final Entity parentEntity) {
    final Set<RenderComponent> renderComponents = entityComponents.remove(parentEntity);
    if (renderComponents != null)
      for (final RenderComponent renderComponent : renderComponents) {
        this.renderComponents.compute( // if it isn't present it's a bug
            renderComponent.getZOrder(),
            (z, rcomps) -> {
              rcomps.remove(renderComponent);
              return rcomps.isEmpty() ? null : rcomps;
            });
      }
  }

  /** Remove the render component of this render manager */
  void removeRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    if (entityComponents.computeIfPresent(
            parentEntity,
            (parent, components) -> {
              if (components.remove(renderComponent)) {
                renderComponents.compute( // if it isn't present it's a bug
                    renderComponent.getZOrder(),
                    (z, rcomps) -> {
                      rcomps.remove(renderComponent);
                      return rcomps.isEmpty() ? null : rcomps;
                    });
              } else {
                gameAlert.warn(
                    GameErrorType.RENDER_COMPONENT_DOES_NOT_EXIST,
                    "parentEntity: " + parentEntity + ", renderComponents: " + entityComponents);
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
