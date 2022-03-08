package chae4ek.transgura.engine.util;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.Entity.FSTEntitySerializer;
import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.ecs.Scene.FSTSceneSerializer;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import org.nustaq.serialization.FSTConfiguration;

public class Serializer {

  private static final GameAlert gameAlert = new GameAlert(Serializer.class);

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
}
