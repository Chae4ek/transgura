package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import chae4ek.transgura.ecs.util.annotations.NonConcurrent;
import chae4ek.transgura.ecs.util.structures.SetGuard;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.GameSettings;
import chae4ek.transgura.game.Scene;
import java.util.HashSet;
import java.util.Set;

/**
 * Entities can contain only one instance of a component implementation, but at the same time the
 * component can be contained into several entities.
 *
 * <p>That means you can add a component to several entities, but you cannot add one instance of a
 * component implementation to one entity multiple times
 */
public abstract class MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(MultipleComponent.class);

  /** The scene where this component was created */
  public final Scene scene;
  /** The entities which contain this component */
  private final SetGuard<Entity> parentEntities;

  private final Set<Entity> parentEntitiesOrigin;

  volatile boolean toDestroy;
  boolean hasDestroyed;

  private volatile boolean isEnabled;

  public MultipleComponent() {
    this(true);
  }

  public MultipleComponent(final boolean isEnabled) {
    scene = Game.getScene();
    parentEntitiesOrigin = new HashSet<>(GameSettings.AVG_PARENTS_PER_COMPONENT);
    parentEntities = new SetGuard<>(parentEntitiesOrigin);
    this.isEnabled = isEnabled;
  }

  /** Bind a component to its parent entity */
  @NonConcurrent
  void bind(final Entity parentEntity) {
    parentEntitiesOrigin.add(parentEntity);
  }

  /** @return the parent entities origin set of this component */
  final Set<Entity> getParentEntitiesOrigin() {
    return parentEntitiesOrigin;
  }

  /** Destroy this component */
  @NonConcurrent
  void destroyThis() {
    // it's implemented in an overriden method to optimize this cycle
    // for (final Entity parent : parentEntitiesOrigin) parent.removeComponent(this);
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

  /** @return the parent entities set of this component */
  public final SetGuard<Entity> getParentEntities() {
    return parentEntities;
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
    final StringBuilder sb =
        new StringBuilder()
            .append("scene: [")
            .append(scene.getClass().getName())
            .append("], class: [")
            .append(getClass().getName())
            .append("], isEnabled: [")
            .append(isEnabled)
            .append("], parentEntities: [ ");
    // no entity is used instead of classes to exclude recursive calls:
    for (final Entity entity : parentEntitiesOrigin) sb.append(entity.getClass()).append(' ');
    return sb.append(']').toString();
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
