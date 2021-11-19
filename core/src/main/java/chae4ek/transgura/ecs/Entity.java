package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.DeferredEvent;
import chae4ek.transgura.ecs.util.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.GameSettings;
import chae4ek.transgura.game.Scene;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {

  private static final transient GameAlert gameAlert = new GameAlert(Entity.class);

  /** The scene where this entity was created */
  public final Scene scene;

  private final Map<Class<? extends MultipleComponent>, MultipleComponent> components;

  @DeferredEvent
  public Entity() {
    scene = Game.getScene();
    components = new HashMap<>(GameSettings.AVG_COMPONENTS_PER_ENTITY);
    scene.systemManager.addDeferredEvent(() -> scene.entityManager.addEntity(this));
  }

  /** Remove a component */
  @NonConcurrent
  final void removeComponent(final MultipleComponent component) {
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
  public final void addComponent(final MultipleComponent component) {
    scene.systemManager.addDeferredEvent(
        () -> {
          component.bind(this);
          final MultipleComponent old = components.put(component.getClass(), component);
          if (old != null) {
            gameAlert.warn(
                GameErrorType.COMPONENT_ALREADY_EXISTS, "old: " + old + ", new: " + component);
            if (!old.getParentEntitiesOrigin().remove(this)) {
              gameAlert.warn(
                  GameErrorType.COMPONENT_HAS_NOT_PARENT_ENTITY,
                  "entity: " + this + ", component: " + old);
            }
          }
        });
  }

  /**
   * Get a component
   *
   * @param componentClass the class of the component to get
   * @return the component or null if it doesn't exist
   */
  public final <T extends MultipleComponent> T getComponent(final Class<T> componentClass) {
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
  public final boolean contains(final Class<? extends MultipleComponent> componentClass) {
    return components.containsKey(componentClass);
  }

  /**
   * Destroy this entity and all associated with it components on its scene. This method adds
   * deferred event, so this entity will destroy after the current loop
   */
  @DeferredEvent
  public final void destroy() {
    scene.systemManager.addDeferredEvent(this::destroyThis);
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
        .append("], components: ")
        .append(components)
        .toString();
  }

  /** Each instance of an entity is unique */
  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return super.hashCode();
  }
}
