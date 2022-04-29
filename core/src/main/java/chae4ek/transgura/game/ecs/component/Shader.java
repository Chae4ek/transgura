package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import java.util.function.Consumer;

public class Shader extends RenderComponent {

  // TODO: serialize
  private final transient ShaderProgram shaderProgram;
  private final transient Consumer<ShaderProgram> setUp;

  public Shader(
      final int zOrder, final ShaderProgram shaderProgram, final Consumer<ShaderProgram> setUp) {
    super(zOrder);
    this.shaderProgram = shaderProgram;
    this.setUp = setUp;
  }

  @Override
  public void draw() {
    RenderManager.spriteBatch.flush();
    RenderManager.switchFrontAndBackFrameBuffers();

    RenderManager.shaderBatch.setShader(shaderProgram);
    RenderManager.shaderBatch.begin();
    setUp.accept(shaderProgram);
    RenderManager.shaderBatch.draw(RenderManager.getBackFrameBufferTexture(), -1f, 1f, 2f, -2f);
    RenderManager.shaderBatch.end();
    RenderManager.shaderBatch.setShader(null);

    RenderManager.spriteBatch.getShader().bind();
  }
}
