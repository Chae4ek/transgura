package chae4ek.transgura.engine.util.debug;

import chae4ek.transgura.engine.ecs.RenderManager;
import chae4ek.transgura.engine.ecs.Scene;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class DebugRenderManager extends RenderManager {

  private static final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
  private static final Matrix4 debugMatrix = new Matrix4();

  public DebugRenderManager(final Scene scene) {
    super(scene);
  }

  @Override
  protected void dispose() {
    super.dispose();
    debugRenderer.dispose();
  }

  @Override
  public void renderAll() {
    super.renderAll();
    debugRenderer.render(scene.world, debugMatrix.set(scene.camera.combined).scl(GameSettings.PPM));
  }
}