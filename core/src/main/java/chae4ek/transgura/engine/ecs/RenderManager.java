package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.debug.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import java.util.HashSet;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

public class RenderManager {

  /** Sprite batch for drawing */
  public static final SpriteBatch spriteBatch =
      new SpriteBatch(
          GameSettings.defaultSpriteBatchSize,
          new ShaderProgram(GameSettings.defaultVertexShader, GameSettings.defaultFragmentShader));

  protected static final Matrix4 SHADER_MATRIX_IDENTITY = new Matrix4();
  protected static FrameBuffer frameBuffer =
      new FrameBuffer(
          Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);

  protected final NavigableMap<Integer, Set<RenderComponent>> renderComponents = new TreeMap<>();

  protected final Scene scene;

  public RenderManager(final Scene scene) {
    this.scene = scene;
  }

  protected void dispose() {
    frameBuffer.dispose();
    spriteBatch.dispose();
  }

  protected void setNewFrameBuffer(final int width, final int height) {
    if (frameBuffer.getWidth() == width && frameBuffer.getHeight() == height) return;
    frameBuffer.dispose();
    frameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false, false);
  }

  /**
   * Change the priority for rendering
   *
   * <p>Note: oldZOrder != newZOrder && oldZOrder == renderComponent.zOrder
   */
  protected void changeZOrder(
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
          if (rcomps == null) rcomps = new HashSet<>();
          rcomps.add(renderComponent);
          return rcomps;
        });
  }

  /**
   * Add a render component to this render manager
   *
   * <p>Note: the parentEntity should NOT have the renderComponent. The parentEntity may not exist
   */
  protected void addRenderComponent(final RenderComponent renderComponent) {
    renderComponents.compute(
        renderComponent.getZOrder(),
        (z, rcomps) -> {
          if (rcomps == null) rcomps = new HashSet<>();
          rcomps.add(renderComponent);
          return rcomps;
        });
  }

  /**
   * Remove the render component of this render manager
   *
   * <p>Note: the parentEntity SHOULD exist and SHOULD have the renderComponent
   */
  protected void removeRenderComponent(final RenderComponent renderComponent) {
    renderComponents.computeIfPresent(
        renderComponent.getZOrder(),
        (z, rcomps) -> {
          rcomps.remove(renderComponent);
          return rcomps.isEmpty() ? null : rcomps;
        });
  }

  /** Render all render components */
  public void renderAll() {
    scene.camera.update();
    spriteBatch.setProjectionMatrix(scene.camera.combined);

    frameBuffer.begin();
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    spriteBatch.begin();

    for (final Set<RenderComponent> renderComponents : renderComponents.values()) {
      for (final RenderComponent renderComponent : renderComponents) {
        if (renderComponent.isEnabled()) renderComponent.draw();
      }
    }

    spriteBatch.end();
    frameBuffer.end();

    // post-processing
    spriteBatch.setShader(GameSettings.postProcessingShader);
    GameSettings.postProcessingSetup.run();

    spriteBatch.setProjectionMatrix(SHADER_MATRIX_IDENTITY);
    spriteBatch.begin();
    spriteBatch.draw(frameBuffer.getColorBufferTexture(), -1f, 1f, 2f, -2f);
    spriteBatch.end();

    spriteBatch.setShader(null);
  }
}
