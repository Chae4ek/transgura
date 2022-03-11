package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.engine.util.HierarchicallySerializable;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import com.badlogic.gdx.Gdx;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTClazzInfo.FSTFieldInfo;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public final class WorldSerializer {

  private static final GameAlert gameAlert = new GameAlert(WorldSerializer.class);

  private static final FSTConfiguration fst = FSTConfiguration.createJsonNoRefConfiguration();

  static {
    fst.registerSerializer(HierarchicallySerializable.class, new FSTSerializer(), true);
  }

  public static byte[] serialize(final Object obj) {
    return fst.asByteArray(obj);
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final byte[] data) {
    try {
      return (T) fst.asObject(data);
    } catch (final Exception e) {
      gameAlert.error("Serialization error", e);
      throw new IllegalStateException(e);
    }
  }

  /** Save current scene */
  public static void saveWorld(final String pathWithName) {
    final byte[] data = Game.getScene().entityManager.serialize();
    try (final OutputStream out =
        Gdx.files.internal(pathWithName).write(false, GameSettings.worldSizeInBytes)) {
      out.write(data, 0, data.length);
    } catch (final IOException e) {
      gameAlert.error("Error occured while saving the world", e);
    }
  }

  /** Load current scene */
  public static void loadWorld(final String pathWithName) {
    try (final InputStream in =
        Gdx.files.internal(pathWithName).read(GameSettings.worldSizeInBytes)) {
      Game.getScene().entityManager.deserialize(in.readAllBytes());
    } catch (final IOException e) {
      gameAlert.error("Error occured while loading the world", e);
    }
  }

  public static class FSTSerializer extends FSTBasicObjectSerializer {

    @Override
    public void writeObject(
        final FSTObjectOutput out,
        final Object toWrite,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy,
        final int streamPosition)
        throws IOException {
      ((HierarchicallySerializable) toWrite)
          .serialize(() -> out.defaultWriteObject(toWrite, clzInfo));
    }

    @Override
    public void readObject(
        final FSTObjectInput in,
        final Object toRead,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy)
        throws Exception {
      ((HierarchicallySerializable) toRead)
          .deserialize(() -> in.defaultReadObject(referencedBy, clzInfo, toRead));
    }
  }
}
