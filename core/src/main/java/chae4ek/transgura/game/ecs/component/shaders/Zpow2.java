package chae4ek.transgura.game.ecs.component.shaders;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.game.ecs.component.Shader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Zpow2 extends Shader {

  public Zpow2(final int zOrder) {
    super(
        zOrder,
        "attribute vec4 a_position;\n"
            + "attribute vec2 a_texCoord0;\n"
            + "uniform mat4 u_projTrans;\n"
            + "varying vec2 v_texCoords;\n"
            + "void main() {\n"
            + "  v_texCoords = a_texCoord0;\n"
            + "  gl_Position = u_projTrans * a_position;\n"
            + "}\n",
        "#ifdef GL_ES\n"
            + "#define LOWP lowp\n"
            + "precision lowp float;\n"
            + "#else\n"
            + "#define LOWP\n"
            + "#endif\n"
            + "uniform vec2 u_camCoords;\n"
            + "varying vec2 v_texCoords;\n"
            + "uniform sampler2D u_texture;\n"
            + "#define mul(z1, z2) vec2(z1.x * z2.x - z1.y * z2.y, z1.x * z2.y + z1.y * z2.x)\n"
            + "vec2 rot(in vec2 st, in float angle) {\n"
            + "  float c = cos(angle), s = sin(angle);\n"
            + "  return mat2(c, -s, s, c) * st;\n"
            + "}"
            + "void main() {\n"
            + "  vec2 st = v_texCoords - 0.5 - u_camCoords;\n"
            + "  st *= 1.5;\n"
            + "  st = rot(st, -45.0);\n"
            + "  vec4 color = texture2D(u_texture, mul(st, st) + 0.5 + u_camCoords);\n"
            + "  gl_FragColor = color;\n"
            + "}\n");
  }

  @Override
  public void setUp(final ShaderProgram shader) {
    shader.setUniformf(
        "u_camCoords",
        Game.getScene().camera.position.x / Gdx.graphics.getWidth(),
        Game.getScene().camera.position.y / Gdx.graphics.getHeight());
  }
}
