package chae4ek.engine.ecs;

import box2dLight.RayHandler;
import chae4ek.engine.util.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ObjectSet;
import java.util.NavigableMap;
import java.util.TreeMap;

public class RenderManager {

  /** Sprite batch for drawing */
  public static final SpriteBatch spriteBatch =
      new SpriteBatch(
          GameSettings.defaultSpriteBatchSize,
          new ShaderProgram(GameSettings.defaultVertexShader, GameSettings.defaultFragmentShader));

  /** Sprite batch for shaders or post-processing */
  public static final SpriteBatch shaderBatch =
      new SpriteBatch(
          GameSettings.defaultSpriteBatchSize,
          new ShaderProgram(GameSettings.defaultVertexShader, GameSettings.defaultFragmentShader));

  public static final Matrix4 SHADER_MATRIX_IDENTITY = new Matrix4();
  public static final Matrix4 PROJECTION_MATRIX = new Matrix4();
  public static final Matrix4 LIGHTS_MATRIX = new Matrix4();

  static {
    shaderBatch.setProjectionMatrix(SHADER_MATRIX_IDENTITY);
  }

  private static FrameBuffer frontFrameBuffer =
      new FrameBuffer(
          Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
  private static FrameBuffer backFrameBuffer =
      new FrameBuffer(
          Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);

  private final NavigableMap<Integer, ObjectSet<RenderComponent>> renderComponents =
      new TreeMap<>();

  public static void setNewFrameBuffer(final int width, final int height) {
    if (frontFrameBuffer.getWidth() == width && frontFrameBuffer.getHeight() == height) return;
    if (width == 0 || height == 0) return;
    frontFrameBuffer.dispose();
    frontFrameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false, false);
    Game.getScene().rayHandler.resizeFBO(width >> 2, height >> 2);
  }

  public static Texture getBackFrameBufferTexture() {
    return backFrameBuffer.getColorBufferTexture();
  }

  public static void switchFrontAndBackFrameBuffers() {
    if (backFrameBuffer.getWidth() == frontFrameBuffer.getWidth()
        && backFrameBuffer.getHeight() == frontFrameBuffer.getHeight()) {
      final FrameBuffer temp = backFrameBuffer;
      backFrameBuffer = frontFrameBuffer;
      frontFrameBuffer = temp;
    } else {
      backFrameBuffer.dispose();
      backFrameBuffer = frontFrameBuffer;
      frontFrameBuffer =
          new FrameBuffer(
              Format.RGBA8888,
              frontFrameBuffer.getWidth(),
              frontFrameBuffer.getHeight(),
              false,
              false);
    }
    backFrameBuffer.end();
    frontFrameBuffer.begin();
    Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  protected void disposeStatic() {
    frontFrameBuffer.dispose();
    backFrameBuffer.dispose();
    spriteBatch.dispose();
    shaderBatch.dispose();
  }

  /**
   * Change the priority for rendering
   *
   * <p>Note: oldZOrder != newZOrder && oldZOrder == renderComponent.zOrder
   */
  final void changeZOrder(
      final RenderComponent renderComponent, final int oldZOrder, final int newZOrder) {
    renderComponents.computeIfPresent(
        oldZOrder,
        (z, rcomps) -> {
          rcomps.remove(renderComponent);
          return rcomps.isEmpty() ? null : rcomps;
        });
    renderComponents.compute(
        newZOrder,
        (z, rcomps) -> {
          if (rcomps == null) rcomps = new ObjectSet<>();
          rcomps.add(renderComponent);
          return rcomps;
        });
  }

  /**
   * Add a render component to this render manager
   *
   * <p>Note: the renderComponent should NOT exist in the {@link #renderComponents}
   */
  final void addRenderComponent(final RenderComponent renderComponent) {
    renderComponents.compute(
        renderComponent.getZOrder(),
        (z, rcomps) -> {
          if (rcomps == null) rcomps = new ObjectSet<>();
          rcomps.add(renderComponent);
          return rcomps;
        });
  }

  /**
   * Remove the render component of this render manager
   *
   * <p>Note: the renderComponent SHOULD exist in the {@link #renderComponents}
   */
  final void removeRenderComponent(final RenderComponent renderComponent) {
    renderComponents.computeIfPresent(
        renderComponent.getZOrder(),
        (z, rcomps) -> {
          rcomps.remove(renderComponent);
          return rcomps.isEmpty() ? null : rcomps;
        });
  }

  /** Render all render components */
  protected void renderAll() {
    final OrthographicCamera camera = Game.getScene().camera;
    camera.update();
    PROJECTION_MATRIX.set(camera.combined).scl(GameSettings.renderScale);
    LIGHTS_MATRIX.set(camera.combined).scl(GameSettings.PPM);
    spriteBatch.setProjectionMatrix(PROJECTION_MATRIX);

    frontFrameBuffer.begin();
    Gdx.gl.glClearColor(0.4156f, 0.8235f, 0.9137f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();

    boolean isUILayout = false;
    for (final ObjectSet<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled()) {
          if (GameSettings.renderLights
              && !isUILayout
              && renderComponent.getZOrder() >= GameSettings.zOrderForUIRendering) {
            isUILayout = true;
            endDraw();
            updateLights(camera);
            // TODO: render lights to FBO
            frontFrameBuffer.begin();
            Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            spriteBatch.begin();
          }
          renderComponent.draw();
        }
      }
    }

    endDraw();
    if (GameSettings.renderLights && !isUILayout) updateLights(camera);
  }

  private static void endDraw() {
    spriteBatch.end();
    frontFrameBuffer.end();

    shaderBatch.begin();
    shaderBatch.draw(frontFrameBuffer.getColorBufferTexture(), -1f, 1f, 2f, -2f);
    shaderBatch.end();
  }

  private static void updateLights(final OrthographicCamera camera) {
    final RayHandler rayHandler = Game.getScene().rayHandler;
    rayHandler.setCombinedMatrix(
        LIGHTS_MATRIX,
        camera.position.x * GameSettings.reversePPM,
        camera.position.y * GameSettings.reversePPM,
        camera.viewportWidth * camera.zoom,
        camera.viewportHeight * camera.zoom);
    rayHandler.updateAndRender();
  }
}
