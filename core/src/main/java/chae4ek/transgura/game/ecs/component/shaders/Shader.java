package chae4ek.transgura.game.ecs.component.shaders;

import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public abstract class Shader extends RenderComponent {

  private transient ShaderProgram shaderProgram;
  private final String vertexShader;
  private final String fragmentShader;

  public Shader(final int zOrder, final String vertexShader, final String fragmentShader) {
    super(zOrder);
    shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
    this.vertexShader = vertexShader;
    this.fragmentShader = fragmentShader;
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    super.deserialize(deserializer);
    shaderProgram = new ShaderProgram(vertexShader, fragmentShader);
  }

  public abstract void setUp(ShaderProgram shader);

  @Override
  public void draw() {
    RenderManager.spriteBatch.flush();
    RenderManager.switchFrontAndBackFrameBuffers();

    RenderManager.shaderBatch.setShader(shaderProgram);
    RenderManager.shaderBatch.begin();
    setUp(shaderProgram);
    RenderManager.shaderBatch.draw(RenderManager.getBackFrameBufferTexture(), -1f, 1f, 2f, -2f);
    RenderManager.shaderBatch.end();
    RenderManager.shaderBatch.setShader(null);

    RenderManager.spriteBatch.getShader().bind();
  }
}
