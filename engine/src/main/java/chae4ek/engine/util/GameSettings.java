package chae4ek.engine.util;

public final class GameSettings {

  /** Max drawable objects without flushing */
  public static final int defaultSpriteBatchSize = GameConfig.defaultSpriteBatchSize;

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

  /** The first scene that will be loaded */
  public static final Runnable mainScene = GameConfig.mainScene;

  public static final ResourceManager resourceManager = GameConfig.resourceManager.get();

  /** Pixels Per Meter */
  public static final float PPM = GameConfig.PPM;

  public static final float reversePPM = 1f / PPM;

  public static final float renderScale = GameConfig.renderScale;
  public static final float reverseRenderScale = 1f / renderScale;

  public static final float fixedDeltaTime = GameConfig.fixedDeltaTime;
  public static final float timeStepForPhysics = GameConfig.timeStepForPhysics;
  public static final int zOrderForUIRendering = GameConfig.zOrderForUIRendering;

  public static boolean renderLights = true;

  public static final boolean isBox2DDebugRendererOn = GameConfig.isBox2DDebugRendererOn;

  /** Whether to throw an exception at the WARN log level */
  public static boolean isWARNThrowOn = GameConfig.isWARNThrowOn;

  /** Whether to log a stack trace at the WARN log level */
  public static boolean isWARNStackTraceOn = GameConfig.isWARNStackTraceOn;
}
