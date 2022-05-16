package chae4ek.transgura.game.ecs.component.shaders;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.game.ecs.component.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Vignette extends Shader {

  private static final String fragment =
      ShaderUtils.make(
          ShaderUtils.fragmentHeader,
          "uniform float u_cameraZoom;",
          "void main() {",
          "  vec2 st = v_texCoords - 0.5;",
          "  float vignette = smoothstep(0.6, 0.3, length(st));",
          "  float shift = (1.0 - vignette) * 0.003 / u_cameraZoom;",
          "  vec4 color = texture2D(u_texture, v_texCoords);",
          "  color.r = texture2D(u_texture, v_texCoords + shift).r;",
          "  color.b = texture2D(u_texture, v_texCoords - shift).b;",
          "  color.rgb = mix(color.rgb, color.rgb * vignette, 0.2);",
          "  gl_FragColor = color;",
          "}");

  public Vignette(final int zOrder) {
    super(zOrder, ShaderUtils.vertex, fragment);
  }

  @Override
  public void setUp(final ShaderProgram shader) {
    shader.setUniformf("u_cameraZoom", Game.getScene().camera.zoom);
  }
}
