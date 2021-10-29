package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.entity.Entity.IEntityFactory;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.HashMap;
import java.util.Map;

public class EntityManager {

  private static final transient GameAlert gameAlert = new GameAlert(EntityManager.class);

  private final Map<Integer, Entity> idToEntity = new HashMap<>();
  private int lastEntityId;

  /**
   * Create a new entity and add it to the manager's memory
   *
   * @return the created entity
   */
  public Entity addNewEntity(final IEntityFactory<?> creator) {
    final Entity entity = creator.create(++lastEntityId);
    idToEntity.put(lastEntityId, entity);
    return entity;
  }

  /**
   * @param id the id of an entity
   * @return the entity with the id or null if it doesn't exist
   */
  public Entity getEntity(final int id) {
    return idToEntity.get(id);
  }

  /**
   * Remove an entity with its id
   *
   * @param id the id of an entity
   * @return the removed entity with the id or null if it doesn't exist
   */
  public Entity removeEntity(final int id) {
    final Entity entity = idToEntity.remove(id);
    if (entity == null) {
      gameAlert.warn(GameErrorType.ENTITY_DOES_NOT_EXIST, "id: " + id);
    }
    return entity;
  }

  /**
   * Remove an entity
   *
   * @param entity the entity to remove
   * @return true if the entity has removed, otherwise false
   */
  public boolean removeEntity(final Entity entity) {
    if (idToEntity.remove(entity.id) == null) {
      gameAlert.warn(GameErrorType.ENTITY_DOES_NOT_EXIST, "entity: " + entity);
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "lastEntityId: [" + lastEntityId + "], idToEntity: " + idToEntity;
  }
}
