package chae4ek.transgura.engine.util;

public class GameConfig {

  /** The first scene that will be loaded */
  public static Runnable mainScene;

  /** Pixels Per Meter */
  public static float PPM = 32f;

  public static float fixedDeltaTime = 1f / 60f;
  public static float timeStepForPhysics = 1f / 60f;

  public static boolean isBox2DDebugRendererOn;
  /** Whether to throw an exception at the WARN log level */
  public static boolean isWARNThrowOn;
  /** Whether to log a stack trace at the WARN log level */
  public static boolean isWARNStackTraceOn;

  /** Max drawable objects without flushing */
  public static int defaultSpriteBatchSize = 1000;
}
