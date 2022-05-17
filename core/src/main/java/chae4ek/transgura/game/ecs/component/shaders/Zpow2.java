package chae4ek.transgura.game.ecs.component.shaders;

import chae4ek.transgura.engine.ecs.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Zpow2 extends Shader {

  private static final String fragment =
      ShaderUtils.make(
          ShaderUtils.fragmentHeader,
          "uniform vec2 u_camCoords;",
          "#define mul(z1, z2) vec2(z1.x * z2.x - z1.y * z2.y, z1.x * z2.y + z1.y * z2.x)",
          "vec2 rot(in vec2 st, in float angle) {",
          "  float c = cos(angle), s = sin(angle);",
          "  return mat2(c, -s, s, c) * st;",
          "}",
          "void main() {",
          "  vec2 st = v_texCoords - 0.5 - u_camCoords;",
          "  st *= 1.5;",
          "  st = rot(st, -45.0);",
          "  vec4 color = texture2D(u_texture, mul(st, st) + 0.5 + u_camCoords);",
          "  gl_FragColor = color;",
          "}");

  public Zpow2(final int zOrder) {
    super(zOrder, ShaderUtils.vertex, fragment);
  }

  @Override
  public void setUp(final ShaderProgram shader) {
    shader.setUniformf(
        "u_camCoords",
        Game.getScene().camera.position.x / Gdx.graphics.getWidth(),
        Game.getScene().camera.position.y / Gdx.graphics.getHeight());
  }
}
