package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.NullObjects;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.Scene;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

  private static final transient GameAlert gameAlert = new GameAlert(Entity.class);

  private static int lastId;
  /** The unique identifier of this entity on the scene */
  public final int id;
  /** The scene where this entity was created */
  public final Scene scene;

  private Map<Class<? extends MultipleComponent>, MultipleComponent> components;

  protected Entity() {
    id = ++lastId;
    scene = Game.getScene();
    scene.entityManager.addEntity(this);
    components = new HashMap<>(8);
  }

  /** Remove a component */
  final void removeComponent(final MultipleComponent component) {
    if (components.remove(component.getClass()) != null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_EXIST, "component: " + component);
    }
  }

  /**
   * Add a component
   *
   * @param components the unique components
   */
  public final void addComponents(final MultipleComponent... components) {
    for (final MultipleComponent component : components) {
      final MultipleComponent old = this.components.put(component.getClass(), component);
      if (old != null) {
        gameAlert.warn(
            GameErrorType.COMPONENT_ALREADY_EXISTS, "old: " + old + ", new: " + component);
        if (!old.getParentEntitiesOrigin().remove(this)) {
          gameAlert.warn(
              GameErrorType.COMPONENT_HAS_NOT_PARENT_ENTITY,
              "entity: " + this + ", component: " + old);
        }
      }
      component.bind(this);
    }
  }

  /**
   * Get a component
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   */
  public final <T extends MultipleComponent> T getComponent(final Class<T> componentClass) {
    final MultipleComponent component = components.get(componentClass);
    if (component == null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_EXIST, componentClass.getName());
      return null;
    }
    return (T) component;
  }

  /**
   * @param componentClass the class of the component to check
   * @return true if the component exists, else false
   */
  public final boolean contains(final Class<? extends MultipleComponent> componentClass) {
    return components.containsKey(componentClass);
  }

  /**
   * Destroy this entity and all associated with it components on its scene. Don't use that entity
   * after this method
   */
  public final void destroy() {
    scene.entityManager.removeEntity(this);
    scene.systemManager.removeAllSystemsIfPresent(this);
    scene.renderManager.removeAllRenderComponentsIfPresent(this);
    components = NullObjects.nullComponentsMap;
  }

  @Override
  public String toString() {
    return new StringBuilder(400)
        .append("class: [")
        .append(getClass().getName())
        .append("], id: [")
        .append(id)
        .append("], lastId: [")
        .append(lastId)
        .append("], scene: [")
        .append(scene.getClass().getName())
        .append("], components: ")
        .append(components)
        .toString();
  }

  @Override
  public final boolean equals(final Object o) {
    return this == o || o != null && getClass() == o.getClass() && id == ((Entity) o).id;
  }

  @Override
  public final int hashCode() {
    return id;
  }
}
