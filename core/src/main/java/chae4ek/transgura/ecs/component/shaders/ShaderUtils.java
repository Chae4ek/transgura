package chae4ek.transgura.ecs.component.shaders;

public class ShaderUtils {

  public static final String vertex =
      make(
          "attribute vec4 a_position;",
          "attribute vec2 a_texCoord0;",
          "uniform mat4 u_projTrans;",
          "varying vec2 v_texCoords;",
          "void main() {",
          "  v_texCoords = a_texCoord0;",
          "  gl_Position = u_projTrans * a_position;",
          "}");

  public static final String fragmentHeader =
      make(
          "#ifdef GL_ES",
          "#define LOWP lowp",
          "precision lowp float;",
          "#else",
          "#define LOWP",
          "#endif",
          "varying vec2 v_texCoords;",
          "uniform sampler2D u_texture;");

  public static String make(final String... strings) {
    return String.join(System.lineSeparator(), strings);
  }
}
