package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.component.Component;
import chae4ek.transgura.ecs.component.ComponentManager;

/**
 * You should make an entity constructor (or factory) like a {@link IEntityFactory#create} method.
 * To create an entity you must use the {@link EntityManager#addNewEntity}
 */
public abstract class Entity {

  /** The unique identifier of this entity */
  public final int id;
  /** The manager to control the entity's components */
  public final ComponentManager componentManager;

  protected Entity(final int id, final Component... components) {
    this.id = id;
    componentManager = new ComponentManager(components);
  }

  @Override
  public String toString() {
    return String.format(
        "class: [%s], id: [%d], componentManager: [%s]",
        getClass().getName(), id, componentManager);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Entity entity = (Entity) o;
    return id == entity.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  /** Describes an entity's constructor (or factory) implementation */
  @FunctionalInterface
  public interface IEntityFactory<T extends Entity> {
    /**
     * It must return a new entity for each invocation
     *
     * @return a new entity
     */
    T create(final int id);
  }
}
