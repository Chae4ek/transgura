package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.serializers.WorldSerializer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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

  void serialize(final DataOutputStream out) {
    WorldSerializer.serialize(out, entities);
  }

  void deserialize(final DataInputStream in) {
    for (final Entity entity : entities) entity.destroy();
    entities.clear();
    entities.addAll(WorldSerializer.deserialize(in));
  }
}
