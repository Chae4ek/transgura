package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.SetGuard;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.Scene;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This component can be only one per entity, but at the same time it can be contained into several
 * entities. That means you can add a component to several entities, but you cannot add one instance
 * of a component to one entity multiple times
 */
public abstract class MultipleComponent {

  /** The scene where this component was created */
  public final Scene scene;

  private final AtomicBoolean isEnabled = new AtomicBoolean();
  /** The entities which contain this component */
  private final SetGuard<Entity> parentEntities;

  private final Set<Entity> parentEntitiesOrigin;

  protected MultipleComponent(final boolean isEnabled) {
    this.isEnabled.set(isEnabled);
    parentEntitiesOrigin = ConcurrentHashMap.newKeySet(5);
    scene = Game.getScene(); // probably this will delete
    parentEntities = new SetGuard<>(parentEntitiesOrigin);
  }

  /** Bind a component to its parent entity */
  void bind(final Entity parentEntity) {
    parentEntitiesOrigin.add(parentEntity);
  }

  /** @return parent entities origin set */
  final Set<Entity> getParentEntitiesOrigin() {
    return parentEntitiesOrigin;
  }

  /** Destroy this component */
  void destroyThis() {
    // It's implemented in an overriden method to optimize a cycle
    // for (final Entity parent : parentEntitiesOrigin) parent.removeComponent(this);
  }

  /**
   * Destroy this component and all associated with it resources. This method adds deferred event,
   * so this component will destroy after the current loop
   */
  public final void destroy() {
    scene.systemManager.addDeferredEvent(this::destroyThis);
  }

  public final SetGuard<Entity> getParentEntities() {
    return parentEntities;
  }

  /** @return true if the component is enabled */
  public final boolean isEnabled() {
    return isEnabled.get();
  }

  /** Enable the component */
  public final void enable() {
    isEnabled.set(true);
  }

  /** Disable the component */
  public final void disable() {
    isEnabled.set(false);
  }

  @Override
  public String toString() {
    final StringBuilder sb =
        new StringBuilder(70)
            .append("class: [")
            .append(getClass().getName())
            .append("], isEnabled: [")
            .append(isEnabled)
            .append("], parentEntity ids: [ ");
    // don't use the entity instead of classes to exclude recursive calls:
    for (final Entity entity : parentEntitiesOrigin) sb.append(entity.getClass()).append(" ");
    return sb.append("]").toString();
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
