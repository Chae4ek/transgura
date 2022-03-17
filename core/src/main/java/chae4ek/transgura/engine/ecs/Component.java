package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.debug.CallOnce;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable;

public abstract class Component implements HierarchicallySerializable {

  private static final GameAlert gameAlert = new GameAlert(Component.class);

  private transient Entity parent;
  private boolean isEnabled = true;
  private transient boolean isDestroyed;

  public Component() {}

  public Component(final boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /**
   * @return true if this component is destroyed or destroying
   */
  public final boolean isDestroyed() {
    return isDestroyed;
  }

  /**
   * Bind this component to its parent entity
   *
   * <p>Note: the parentEntity should NOT destroyed
   *
   * @return true if the component was successfully bound
   */
  @CallOnce
  boolean bind(final Entity parentEntity) {
    if (parent != null) {
      gameAlert.warn(
          "The component {} wasn't bound cause it already has a parent entity {}. The new parent is {}",
          this,
          parent,
          parentEntity);
      return false;
    }
    parent = parentEntity;
    return true;
  }

  /**
   * @return the parent entity of this component
   */
  public final Entity getParent() {
    if (parent == null) gameAlert.warn("The component {} does not belong to an entity yet", this);
    return parent;
  }

  /** Destroy this component and all associated with it resources on its scene */
  @CallOnce
  public final void destroy() {
    if (isDestroyed) {
      gameAlert.warn("The component {} is already destroyed", this);
      return;
    }
    isDestroyed = true;
    onDestroy();
    destroyThis();
  }

  /** Invokes before actually destroying, but {@link #isDestroyed()} returns true now */
  @CallOnce
  protected void onDestroy() {}

  /** The actual destroying of this component */
  @CallOnce
  void destroyThis() {
    if (parent == null) {
      gameAlert.warn("You destroy the component {} without its parent", this);
      return;
    }
    parent.removeComponent(this);
  }

  /**
   * @return true if this component is enabled
   */
  public final boolean isEnabled() {
    if (isDestroyed) {
      gameAlert.warn(
          "The component {} is already destroyed. You cannot check if this is enabled", this);
      return false;
    }
    return isEnabled;
  }

  /** Whether to enable this component */
  public final void setEnabled(final boolean isEnabled) {
    if (isDestroyed) {
      gameAlert.warn(
          "The component {} is already destroyed. You cannot set the enable option", this);
      return;
    }
    if (this.isEnabled == isEnabled) {
      if (isEnabled) gameAlert.warn("The component {} is already enabled", this);
      else gameAlert.warn("The component {} is already disabled", this);
      return;
    }
    this.isEnabled = isEnabled;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o || o != null && getClass() == o.getClass();
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public void serialize(final DefaultSerializer serializer) throws Exception {
    serializer.write(this);
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    deserializer.readTo(this);
  }
}
