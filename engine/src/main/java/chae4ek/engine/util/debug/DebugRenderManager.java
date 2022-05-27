package chae4ek.engine.util.debug;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.RenderManager;
import chae4ek.engine.util.GameSettings;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public final class DebugRenderManager extends RenderManager {

  private static final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
  private static final Matrix4 debugMatrix = new Matrix4();

  @Override
  protected void disposeStatic() {
    super.disposeStatic();
    debugRenderer.dispose();
  }

  @Override
  protected void renderAll() {
    super.renderAll();
    debugRenderer.render(
        Game.getScene().b2dWorld,
        debugMatrix.set(Game.getScene().camera.combined).scl(GameSettings.PPM));
  }
}
