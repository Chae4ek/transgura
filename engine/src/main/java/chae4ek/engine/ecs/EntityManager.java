package chae4ek.engine.ecs;

import chae4ek.engine.util.serializers.WorldSerializer;
import com.badlogic.gdx.utils.ObjectSet;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class EntityManager {

  private ObjectSet<Entity> entities = new ObjectSet<>();

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

  Iterable<Entity> entityIterable() {
    return entities;
  }

  void serialize(final DataOutputStream out) {
    WorldSerializer.serialize(out, entities.size);
    for (final Entity entity : entities) WorldSerializer.serialize(out, entity);
  }

  void deserialize(final DataInputStream in) {
    int size = WorldSerializer.deserialize(in);
    final ObjectSet<Entity> entitiesNew = new ObjectSet<>(size);
    for (final Entity entity : entities) {
      entity.destroy();
      if (size > 0) {
        --size;
        entitiesNew.add(WorldSerializer.deserialize(in));
      }
    }
    for (; size > 0; --size) entitiesNew.add(WorldSerializer.deserialize(in));
    entities = entitiesNew;
  }
}
