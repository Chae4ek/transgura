package chae4ek.transgura;

import chae4ek.transgura.game.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class Main {
  public static void main(final String[] args) {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

    config.setTitle("Transgura");
    config.setWindowedMode(848, 477);
    config.useVsync(false);
    config.setForegroundFPS(500);

    new Lwjgl3Application(new Game(), config);
  }
}
