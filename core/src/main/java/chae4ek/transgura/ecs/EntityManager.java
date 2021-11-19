package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class EntityManager {

  private static final transient GameAlert gameAlert = new GameAlert(EntityManager.class);

  private final Set<Entity> entities = ConcurrentHashMap.newKeySet();

  /** Add a new entity to the manager's memory */
  @NonConcurrent
  void addEntity(final Entity entity) {
    if (!entities.add(entity)) {
      gameAlert.warn(GameErrorType.ENTITY_HAS_BEEN_REPLACED, "entity: " + entity);
    }
  }

  /** Remove an entity */
  @NonConcurrent
  void removeEntity(final Entity entity) {
    if (!entities.remove(entity)) {
      gameAlert.warn(GameErrorType.ENTITY_DOES_NOT_EXIST, "entity: " + entity);
    }
  }

  @Override
  public String toString() {
    return "entities: " + entities;
  }
}
