package chae4ek.transgura.engine.util.debug;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/** You can change these values at runtime if you know something more */
public class GameSettings {

  public static final boolean isDebugBox2DRendererOn = true;
  /** Whether to throw an exception at the WARN log level */
  public static final boolean isWARNThrow = false;
  /** Whether to log a stack trace at the WARN log level */
  public static final boolean isWARNStackTrace = false;

  /** Pixels Per Meter */
  public static final float PPM = 32f;

  public static final float fixedDeltaTime = 1f / 60f;
  public static final float timeStepForPhysics = 1f / 60f;

  public static final int defaultSpriteBatchSize = 1000;
  public static final String defaultVertexShader =
      "attribute vec4 a_position;\n"
          + "attribute vec4 a_color;\n"
          + "attribute vec2 a_texCoord0;\n"
          + "uniform mat4 u_projTrans;\n"
          + "varying vec4 v_color;\n"
          + "varying vec2 v_texCoords;\n"
          + "void main() {\n"
          + "  v_color = a_color;\n"
          + "  v_texCoords = a_texCoord0;\n"
          + "  gl_Position = u_projTrans * a_position;\n"
          + "}\n";
  public static final String defaultFragmentShader =
      "#ifdef GL_ES\n"
          + "#define LOWP lowp\n"
          + "precision lowp float;\n"
          + "#else\n"
          + "#define LOWP\n"
          + "#endif\n"
          + "varying LOWP vec4 v_color;\n"
          + "varying vec2 v_texCoords;\n"
          + "uniform sampler2D u_texture;\n"
          + "void main() {\n"
          + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
          + "}\n";

  public static ShaderProgram postProcessingShader =
      new ShaderProgram(
          defaultVertexShader,
          "#ifdef GL_ES\n"
              + "#define LOWP lowp\n"
              + "precision lowp float;\n"
              + "#else\n"
              + "#define LOWP\n"
              + "#endif\n"
              + "varying vec2 v_texCoords;\n"
              + "uniform sampler2D u_texture;\n"
              + "uniform float u_time;\n"
              + "void main() {\n"
              + "  vec2 st = v_texCoords;\n"
              // + "  st.x += sin(u_time + st.y * 30.0) * 0.01;\n"
              + "  gl_FragColor = texture2D(u_texture, st);\n"
              + "}\n");
  // TODO: move this out
  // postProcessingShader.setUniformf("u_time", Game.getScene().getSceneLifetimeInSec());
  public static Runnable postProcessingSetup = () -> {};
}