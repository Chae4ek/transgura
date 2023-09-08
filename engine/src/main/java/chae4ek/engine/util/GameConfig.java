package chae4ek.engine.util;

import chae4ek.engine.util.ResourceManager.NullResourceLoader;
import java.util.function.Supplier;

public final class GameConfig {

  /** The first scene that will be loaded */
  public static Runnable mainScene;

  public static Supplier<ResourceManager> resourceManager = NullResourceLoader::new;

  /** Pixels Per Meter */
  public static float PPM = 32f;

  public static float renderScale = 2f;

  public static float fixedDeltaTime = 1f / 60f;
  public static float timeStepForPhysics = 1f / 60f;
  public static int zOrderForUIRendering = 10000000;

  public static boolean isBox2DDebugRendererOn;

  /** Whether to throw an exception at the WARN log level */
  public static boolean isWARNThrowOn;

  /** Whether to log a stack trace at the WARN log level */
  public static boolean isWARNStackTraceOn;

  /** Max drawable objects without flushing */
  public static int defaultSpriteBatchSize = 1000;
}
