package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.Serializer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

public class EntityManager {

  private Set<Entity> entities = new HashSet<>();

  /**
   * Add an entity to this enitity manager
   *
   * <p>Note: the entity should NOT exist in the {@link #entities}
   */
  void addEntity(final Entity entity) {
    entities.add(entity);
  }

  /**
   * Remove the entity of this entity manager
   *
   * <p>Note: the entity SHOULD exist in the {@link #entities}
   */
  void removeEntity(final Entity entity) {
    entities.remove(entity);
  }

  // TODO: add specific params for save/load
  public void saveWorld(final OutputStream out) throws IOException {
    final byte[] data = Serializer.serialize(entities);
    out.write(data, 0, data.length);
  }

  public void loadWorld(final InputStream in) throws IOException {
    // TODO: destroying is unsafe
    for (final Entity entity : entities) entity.destroy();
    entities = Serializer.deserialize(in.readAllBytes());
  }
}
