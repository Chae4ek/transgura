package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
    frontFrameBuffer.dispose();
    frontFrameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false, false);
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
    Game.getScene().camera.update();
    PROJECTION_MATRIX.set(Game.getScene().camera.combined).scl(GameSettings.renderScale);
    spriteBatch.setProjectionMatrix(PROJECTION_MATRIX);

    frontFrameBuffer.begin();
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();

    for (final ObjectSet<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled()) renderComponent.draw();
      }
    }

    spriteBatch.end();
    frontFrameBuffer.end();

    shaderBatch.begin();
    shaderBatch.draw(frontFrameBuffer.getColorBufferTexture(), -1f, 1f, 2f, -2f);
    shaderBatch.end();
  }
}
