package chae4ek.transgura;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.util.GameConfig;
import chae4ek.transgura.scenes.MainMenu;
import chae4ek.transgura.util.resources.ResourceLoader;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.MDC;

public final class Main {
  public static void main(final String[] args) {
    MDC.put("time", new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(new Date()));
    run(MainMenu::new);
  }

  public static void run(final Runnable scene) {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

    config.setTitle("Transgura");
    config.setWindowedMode(848, 477);
    config.useVsync(false);
    config.setForegroundFPS(500);

    GameConfig.mainScene = scene;
    GameConfig.resourceManager = ResourceLoader::new;
    GameConfig.isBox2DDebugRendererOn = true;

    new Lwjgl3Application(new Game(), config);
  }
}
