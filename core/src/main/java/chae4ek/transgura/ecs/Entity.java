package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import chae4ek.transgura.ecs.util.annotations.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.GameSettings;
import chae4ek.transgura.game.Scene;
import java.util.HashMap;
import java.util.Map;

public class Entity {

  private static final transient GameAlert gameAlert = new GameAlert(Entity.class);

  /** The scene where this entity was created */
  public final Scene scene;

  private final Map<Class<? extends Component>, Component> components;

  private volatile boolean toDestroy;
  private boolean hasDestroyed;

  @DeferredEvent
  public Entity(final Component... components) {
    scene = Game.getScene();
    this.components = new HashMap<>(GameSettings.AVG_COMPONENTS_PER_ENTITY);
    scene.systemManager.addDeferredEvent(
        () -> {
          if (toDestroy) {
            gameAlert.warn(GameErrorType.ENTITY_WILL_BE_DESTROYED, "entity: " + this);
          }
          scene.entityManager.addEntity(this);
          for (final Component comp : components) addComponentUnsafe(comp);
        });
  }

  /** Remove a component */
  @NonConcurrent
  final void removeComponent(final Component component) {
    if (components.remove(component.getClass()) == null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_EXIST, "component: " + component);
    }
  }

  /**
   * Add a component
   *
   * @param component the unique component
   */
  @DeferredEvent
  public final void addComponent(final Component component) {
    scene.systemManager.addDeferredEvent(() -> addComponentUnsafe(component));
  }

  /**
   * Add components (at least 2)
   *
   * @param components the unique components
   */
  @DeferredEvent
  public final void addComponent(final Component component, final Component... components) {
    scene.systemManager.addDeferredEvent(
        () -> {
          addComponentUnsafe(component);
          for (final Component comp : components) addComponentUnsafe(comp);
        });
  }

  @NonConcurrent
  private void addComponentUnsafe(final Component component) {
    if (component.toDestroy) {
      gameAlert.warn(GameErrorType.COMPONENT_WILL_BE_DESTROYED, "component: " + component);
    }
    component.bind(this);
    final Component old = components.put(component.getClass(), component);
    if (old != null) {
      gameAlert.warn(GameErrorType.COMPONENT_ALREADY_EXISTS, "old: " + old + ", new: " + component);
      if (old.getParent() == null) {
        gameAlert.warn(
            GameErrorType.COMPONENT_HAS_NOT_PARENT_ENTITY,
            "entity: " + this + ", component: " + old);
      }
    }
  }

  /**
   * Get a component
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   */
  public final <T extends Component> T getComponent(final Class<T> componentClass) {
    @SuppressWarnings("unchecked")
    final T component = (T) components.get(componentClass);
    if (component == null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_EXIST, componentClass.getName());
      return null;
    }
    return component;
  }

  /**
   * @param componentClass the class of the component to check
   * @return true if the component exists, else false
   */
  public final boolean contains(final Class<? extends Component> componentClass) {
    return components.containsKey(componentClass);
  }

  /** @return true if this component will be destroyed */
  public final boolean willDestroy() {
    return toDestroy;
  }

  /**
   * Destroy this entity and all associated with it components on its scene. This method adds
   * deferred event, so this entity will destroy after the current loop
   */
  @DeferredEvent
  public final void destroy() {
    toDestroy = true;
    scene.systemManager.addDeferredEvent(
        () -> {
          if (hasDestroyed) {
            gameAlert.warn(GameErrorType.ENTITY_HAS_ALREADY_DESTROYED, "entity: " + this);
            return;
          }
          destroyThis();
          hasDestroyed = true;
        });
  }

  /** Destroy this entity */
  @NonConcurrent
  private void destroyThis() {
    scene.entityManager.removeEntity(this);
    scene.systemManager.removeAllSystemsIfPresent(this);
    scene.renderManager.removeAllRenderComponentsIfPresent(this);
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("class: [")
        .append(getClass().getName())
        .append("], scene: [")
        .append(scene.getClass().getName())
        .append("], toDestroy: [")
        .append(toDestroy)
        .append("], hasDestroyed: [")
        .append(hasDestroyed)
        .append("], components: ")
        .append(components)
        .toString();
  }

  /** Each instance of an entity is unique. This is necessary to avoid thread-safe indexes */
  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }
}
