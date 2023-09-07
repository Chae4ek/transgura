package chae4ek.engine.ecs;

import com.badlogic.gdx.utils.ObjectSet;

public final class EntityManager {

  private final ObjectSet<Entity> entities = new ObjectSet<>();

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
}
