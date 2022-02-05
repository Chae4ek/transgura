package chae4ek.transgura.engine.ecs;

import java.util.HashSet;
import java.util.Set;

public class EntityManager {

  protected final Set<Entity> entities = new HashSet<>();

  /**
   * Add an entity to this enitity manager
   *
   * <p>Note: the entity should NOT exist in the {@link #entities}
   */
  protected void addEntity(final Entity entity) {
    entities.add(entity);
  }

  /**
   * Remove the entity of this entity manager
   *
   * <p>Note: the entity SHOULD exist in the {@link #entities}
   */
  protected void removeEntity(final Entity entity) {
    entities.remove(entity);
  }
}
