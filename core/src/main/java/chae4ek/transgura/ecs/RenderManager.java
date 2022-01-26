package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.annotations.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public final class RenderManager {

  /** Sprite batch for drawing */
  public static final SpriteBatch spriteBatch =
      new SpriteBatch(
          GameSettings.defaultSpriteBatchSize,
          new ShaderProgram(GameSettings.defaultVertexShader, GameSettings.defaultFragmentShader));

  private static final Box2DDebugRenderer debugRenderer =
      GameSettings.isDebugOn ? new Box2DDebugRenderer() : null;
  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  private static final Matrix4 SHADER_MATRIX_IDENTITY = new Matrix4();

  private final ExtendViewport viewport;
  private final Map<Entity, Set<RenderComponent>> entityComponents = new HashMap<>();
  private final NavigableMap<Integer, Set<RenderComponent>> renderComponents = new TreeMap<>();

  private FrameBuffer frameBuffer;

  public RenderManager(final ExtendViewport viewport) {
    this.viewport = viewport;
    setNewFrameBuffer(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  public void setNewFrameBuffer(final int width, final int height) {
    frameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false, false);
  }

  /** Change the priority for rendering */
  @NonConcurrent
  void changeZOrder(final RenderComponent renderComponent, final int newZOrder) {
    if (renderComponent.zOrder == newZOrder) {
      gameAlert.warn(
          GameErrorType.RENDER_COMPONENT_Z_ORDER_IS_THE_SAME,
          "render component: " + renderComponent);
      return;
    }
    renderComponents.computeIfPresent(
        renderComponent.zOrder,
        (z, rcomps) -> {
          if (rcomps.remove(renderComponent)) renderComponent.zOrder = newZOrder;
          return rcomps;
        });
    if (renderComponent.zOrder != newZOrder) {
      gameAlert.warn(
          GameErrorType.RENDER_COMPONENT_DOES_NOT_EXIST, "render component: " + renderComponent);
    } else {
      renderComponents.compute(
          newZOrder,
          (z, rcomps) -> {
            if (rcomps == null) rcomps = new HashSet<>();
            if (!rcomps.add(renderComponent)) {
              gameAlert.warn(
                  GameErrorType.RENDER_COMPONENT_HAS_BEEN_REPLACED,
                  "render component: " + renderComponent);
            }
            return rcomps;
          });
    }
  }

  /** Add a render component to this render manager */
  @NonConcurrent
  void addRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    entityComponents.compute(
        parentEntity,
        (parent, components) -> {
          if (components == null) {
            components = new HashSet<>(GameSettings.AVG_RENDER_COMPONENTS_PER_ENTITY);
          }
          if (components.add(renderComponent)) {
            renderComponents.compute(
                renderComponent.zOrder,
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
  @NonConcurrent
  void removeAllRenderComponentsIfPresent(final Entity parentEntity) {
    final Set<RenderComponent> renderComponents = entityComponents.remove(parentEntity);
    if (renderComponents != null)
      for (final RenderComponent renderComponent : renderComponents) {
        this.renderComponents.compute(
            renderComponent.zOrder,
            (z, rcomps) -> {
              assert rcomps != null; // if it isn't present it's a bug
              rcomps.remove(renderComponent);
              return rcomps.isEmpty() ? null : rcomps;
            });
        renderComponent.toDestroy = true;
        renderComponent.onDestroy();
        renderComponent.hasDestroyed = true;
      }
  }

  /** Remove the render component of this render manager */
  @NonConcurrent
  void removeRenderComponent(final Entity parentEntity, final RenderComponent renderComponent) {
    if (entityComponents.computeIfPresent(
            parentEntity,
            (parent, components) -> {
              if (components.remove(renderComponent)) {
                renderComponents.compute(
                    renderComponent.zOrder,
                    (z, rcomps) -> {
                      assert rcomps != null; // if it isn't present it's a bug
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
    Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    final Matrix4 projection = viewport.getCamera().combined;

    spriteBatch.setProjectionMatrix(projection);
    spriteBatch.begin();

    frameBuffer.begin();
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    for (final Set<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled()) renderComponent.draw();
      }
    }
    spriteBatch.flush();

    if (GameSettings.isDebugOn) {
      debugRenderer.render(
          Game.getScene().systemManager.world, new Matrix4(projection).scl(GameSettings.PPM));
    }

    frameBuffer.end();

    // post-processing
    spriteBatch.setShader(GameSettings.postProcessingShader);
    GameSettings.postProcessingSetup.run();

    spriteBatch.setProjectionMatrix(SHADER_MATRIX_IDENTITY);
    spriteBatch.draw(frameBuffer.getColorBufferTexture(), -1f, 1f, 2f, -2f);

    spriteBatch.end();
    spriteBatch.setShader(null);
  }
}
