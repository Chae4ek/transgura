package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.ecs.Entity.FSTEntitySerializer;
import chae4ek.transgura.engine.ecs.Scene.FSTSceneSerializer;
import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.nustaq.serialization.FSTConfiguration;

public final class WorldSerializer {

  private static final GameAlert gameAlert = new GameAlert(WorldSerializer.class);

  private static final FSTConfiguration fst = FSTConfiguration.createJsonNoRefConfiguration();

  static {
    fst.registerSerializer(Scene.class, new FSTSceneSerializer(), true);
    fst.registerSerializer(Entity.class, new FSTEntitySerializer(), true);
  }

  public static byte[] serialize(final Object obj) {
    return fst.asByteArray(obj);
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final byte[] data) {
    try {
      return (T) fst.asObject(data);
    } catch (final Exception e) {
      gameAlert.error("Serialization error: {}", e);
      throw new IllegalStateException(e);
    }
  }

  /** Save current scene */
  public static void saveWorld(final String pathWithName) {
    final byte[] data = Game.scene.entityManager.serialize();
    try (final OutputStream out =
        Gdx.files.internal(pathWithName).write(false, GameSettings.worldSizeInBytes)) {
      out.write(data, 0, data.length);
    } catch (final IOException e) {
      gameAlert.error("Error occured while saving the world: {}", e);
    }
  }

  /** Load current scene */
  public static void loadWorld(final String pathWithName) {
    try (final InputStream in =
        Gdx.files.internal(pathWithName).read(GameSettings.worldSizeInBytes)) {
      Game.scene.entityManager.deserialize(in.readAllBytes());
    } catch (final IOException e) {
      gameAlert.error("Error occured while loading the world: {}", e);
    }
  }
}
