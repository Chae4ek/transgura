package chae4ek.transgura.game.scenes;

import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.util.GameSettings;
import com.badlogic.gdx.Gdx;
import java.io.DataInputStream;
import java.io.IOException;

public class SaveLoad extends Scene {

  public SaveLoad() {
    try (final DataInputStream in =
        new DataInputStream(
            Gdx.files.internal("saves/test_world").read(GameSettings.worldBufferSize))) {
      loadWorld(in);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
