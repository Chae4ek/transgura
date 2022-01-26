package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import chae4ek.transgura.ecs.util.annotations.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.Scene;

public abstract class Component {

  private static final transient GameAlert gameAlert = new GameAlert(Component.class);

  /** The scene where this component was created */
  public final Scene scene;

  volatile boolean toDestroy;
  boolean hasDestroyed;

  private Entity parent;

  private volatile boolean isEnabled;

  public Component() {
    this(true);
  }

  public Component(final boolean isEnabled) {
    scene = Game.getScene();
    this.isEnabled = isEnabled;
  }

  /** Bind a component to its parent entity */
  @NonConcurrent
  void bind(final Entity parentEntity) {
    if (parent != null) {
      gameAlert.error(
          GameErrorType.COMPONENT_BELONGS_TO_ENTITY,
          "component: " + this + ", new parent entity: " + parentEntity);
    }
    parent = parentEntity;
  }

  /** @return the parent entity of this component */
  public final Entity getParent() {
    if (parent == null) {
      gameAlert.warn(GameErrorType.COMPONENT_DOES_NOT_BELONG_TO_ENTITY, "component: " + this);
    }
    return parent;
  }

  /** Destroy this component */
  @NonConcurrent
  void destroyThis() {
    if (parent == null) {
      gameAlert.warn(GameErrorType.COMPONENT_IS_DESTROYED_WITHOUT_PARENT, "component: " + this);
      return;
    }
    if (scene != parent.scene) {
      gameAlert.warn(
          GameErrorType.COMPONENT_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE,
          "component scene: " + scene + ", parent entity: " + parent);
    }
    parent.removeComponent(this);
  }

  /** Invoke before actually destroying */
  @NonConcurrent
  public void onDestroy() {}

  /** @return true if this component will be destroyed */
  public final boolean willDestroy() {
    return toDestroy;
  }

  /**
   * Destroy this component and all associated with it resources. This method adds deferred event,
   * so this component will destroy after the current loop
   */
  @DeferredEvent
  public final void destroy() {
    toDestroy = true;
    scene.systemManager.addDeferredEvent(
        () -> {
          if (hasDestroyed) {
            gameAlert.warn(GameErrorType.COMPONENT_HAS_ALREADY_DESTROYED, "component: " + this);
            return;
          }
          onDestroy();
          destroyThis();
          hasDestroyed = true;
        });
  }

  /** @return true if this component is enabled */
  public final boolean isEnabled() {
    return isEnabled;
  }

  /** Whether to enable this component */
  @DeferredEvent
  public final void setEnabled(final boolean isEnabled) {
    if (this.isEnabled == isEnabled) {
      if (isEnabled)
        gameAlert.warn(GameErrorType.COMPONENT_IS_ALREADY_ENABLED, "component: " + this);
      else gameAlert.warn(GameErrorType.COMPONENT_IS_ALREADY_DISABLED, "component: " + this);
      return;
    }
    scene.systemManager.addDeferredEvent(() -> this.isEnabled = isEnabled);
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("scene: [")
        .append(scene.getClass().getName())
        .append("], class: [")
        .append(getClass().getName())
        .append("], isEnabled: [")
        .append(isEnabled)
        .append("], toDestroy: [")
        .append(toDestroy)
        .append("], hasDestroyed: [")
        .append(hasDestroyed)
        .append("], parentEntity: [")
        // no entity is used instead of class to exclude recursive calls:
        .append(parent == null ? "null" : parent.getClass())
        .append(']')
        .toString();
  }

  @Override
  public boolean equals(final Object o) {
    return this == o || o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
