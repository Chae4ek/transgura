package chae4ek.transgura.engine.ecs;

import chae4ek.transgura.engine.util.annotations.CallOnce;
import chae4ek.transgura.engine.util.exceptions.GameAlert;

public abstract class Component {

  private static final GameAlert gameAlert = new GameAlert(Component.class);

  /** The scene where this component is created */
  public final Scene scene = Game.scene;

  private Entity parent;
  private boolean isEnabled = true;
  private boolean isDestroyed;

  public Component() {}

  public Component(final boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /** @return true if this component is destroyed (invalid) */
  public final boolean isDestroyed() {
    return isDestroyed;
  }

  /**
   * Bind this component to its parent entity. Checks that the scene of this component is equal to
   * the parent's one
   *
   * <p>Note: the parentEntity SHOULD exist
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
    if (scene != parentEntity.scene) {
      gameAlert.warn(
          "The component's scene {} is not equal to the entity's scene {}",
          scene,
          parentEntity.scene);
      return false;
    }
    parent = parentEntity;
    return true;
  }

  /** @return the parent entity of this component */
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

  /** Invoke before actually destroying */
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

  /** @return true if this component is enabled */
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
}