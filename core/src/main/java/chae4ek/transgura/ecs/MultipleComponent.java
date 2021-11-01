package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.NullObjects;
import chae4ek.transgura.ecs.util.SetGuard;
import java.util.HashSet;
import java.util.Set;

/**
 * This component can be only one per entity, but at the same time it can be contained into several
 * entities. That means you can add a component to several entities, but you cannot add one instance
 * of a component to one entity multiple times
 */
public abstract class MultipleComponent {

  /** The entities which contain this component */
  private SetGuard<Entity> parentEntities;

  private Set<Entity> parentEntitiesOrigin;
  private boolean isEnabled;

  protected MultipleComponent(final boolean isEnabled) {
    this.isEnabled = isEnabled;
    parentEntitiesOrigin = new HashSet<>(5);
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

  /** Destroy this component. Invoking before {@link #destroy()} */
  void destroyThis() {}

  /**
   * Destroy this component and all associated with it resources. Don't use that component after
   * this method
   */
  public final void destroy() {
    destroyThis();
    for (final Entity parent : parentEntitiesOrigin) parent.removeComponent(this);
    parentEntitiesOrigin = NullObjects.nullEntitySet;
    parentEntities = NullObjects.nullEntitySetGuard;
  }

  public final SetGuard<Entity> getParentEntities() {
    return parentEntities;
  }

  /** @return true if the component is enabled */
  public final boolean isEnabled() {
    return isEnabled;
  }

  /** Enable the component */
  public final void enable() {
    isEnabled = true;
  }

  /** Disable the component */
  public final void disable() {
    isEnabled = false;
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
    // don't use the entity instead of ids to exclude recursive calls:
    for (final Entity entity : parentEntitiesOrigin) sb.append(entity.id).append(" ");
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
