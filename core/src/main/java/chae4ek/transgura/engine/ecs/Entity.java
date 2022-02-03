package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.annotations.CallOnce;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import java.util.HashMap;
import java.util.Map;

public class Entity {

  private static final GameAlert gameAlert = new GameAlert(Entity.class);

  /** The scene where this entity is created */
  public final Scene scene = Game.scene;

  private Map<Class<? extends Component>, Component> components = new HashMap<>();
  private boolean isDestroyed;

  public Entity(final Component... components) {
    scene.entityManager.addEntity(this);
    for (final Component component : components) addComponent(component);
  }

  /** @return true if this entity is destroyed (invalid) */
  public final boolean isDestroyed() {
    return isDestroyed;
  }

  /**
   * Remove a component
   *
   * <p>Note: the component SHOULD exist
   */
  final void removeComponent(final Component component) {
    if (components != null) components.remove(component.getClass());
  }

  /**
   * Add a component
   *
   * @param component the unique component
   */
  public final void addComponent(final Component component) {
    if (isDestroyed) {
      gameAlert.warn(
          "The entity {} is already destroyed. A component {} wasn't added", this, component);
      return;
    }
    if (component.isDestroyed()) {
      gameAlert.warn("The component {} is already destroyed. It wasn't added", component);
      return;
    }
    if (component.bind(this)) components.put(component.getClass(), component);
  }

  /**
   * Add components
   *
   * @param components the unique components
   */
  public final void addComponent(final Component component, final Component... components) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. Components weren't added", this);
      return;
    }
    addComponent(component);
    for (final Component comp : components) addComponent(comp);
  }

  /**
   * Get a component
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   */
  public final <T extends Component> T getComponent(final Class<T> componentClass) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. You cannot get a component", this);
      return null;
    }
    @SuppressWarnings("unchecked")
    final T component = (T) components.get(componentClass);
    if (component == null) {
      gameAlert.warn(
          "The component {} doesn't belong to this entity {}", componentClass.getName(), this);
    }
    return component;
  }

  /**
   * @param componentClass the class of the component to check
   * @return true if the component exists, else false
   */
  public final boolean contains(final Class<? extends Component> componentClass) {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed. You cannot check a component", this);
      return false;
    }
    return components.containsKey(componentClass);
  }

  /** Destroy this entity and all associated with it components on its scene */
  @CallOnce
  public final void destroy() {
    if (isDestroyed) {
      gameAlert.warn("The entity {} is already destroyed", this);
      return;
    }
    isDestroyed = true;
    onDestroy();
    scene.entityManager.removeEntity(this);

    final Map<Class<? extends Component>, Component> temp = components;
    components = null;
    for (final Component component : temp.values()) component.destroy();
    components = temp;
    components.clear();
  }

  /** Invoke before actually destroying */
  @CallOnce
  protected void onDestroy() {}

  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }
}
