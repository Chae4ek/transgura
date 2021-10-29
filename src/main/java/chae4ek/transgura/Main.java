package chae4ek.transgura;

import chae4ek.transgura.game.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public final class Main {
  public static void main(final String[] arg) {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

    config.setTitle("Transgura");
    config.setWindowedMode(800, 480);
    config.useVsync(false);
    config.setForegroundFPS(500);

    new Lwjgl3Application(
        new ApplicationAdapter() {
          private Game game;

          @Override
          public void create() {
            game = new Game();
          }

          @Override
          public void render() {
            game.loop();
          }

          @Override
          public void dispose() {
            game.close();
          }

          @Override
          public void resize(final int width, final int height) {
            game.resize(width, height);
          }
        },
        config);
  }
}
