package chae4ek.transgura.game.scenes;

import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import java.io.DataInputStream;
import java.io.IOException;

public class SceneLoader extends Scene {

  private static final GameAlert gameAlert = new GameAlert(SceneLoader.class);

  public SceneLoader(final String filePath) {
    try (final DataInputStream in = new DataInputStream(Gdx.files.internal(filePath).read(8192))) {
      loadWorld(in);
    } catch (final IOException e) {
      gameAlert.error("Scene Loader error", e);
    }
  }
}
