package chae4ek.transgura.engine.ecs;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class EntityManager {

  private final Set<Entity> entities = new HashSet<>();

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

  byte[] serialize() {
    return WorldSerializer.serialize(entities);
  }

  void deserialize(final byte[] data) throws IOException {
    // TODO: destroying is unsafe (may delete an entity)
    for (final Entity entity : entities) entity.destroy();
    entities.clear();
    entities.addAll(WorldSerializer.deserialize(data));
  }
}
