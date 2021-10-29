package chae4ek.transgura.ecs.system;

public abstract class System {

  private boolean isEnabled;

  protected System(final boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  /** @return true if the system is enabled */
  public final boolean isEnabled() {
    return isEnabled;
  }

  /** Enable the system */
  public final void enable() {
    isEnabled = true;
  }

  /** Disable the system */
  public final void disable() {
    isEnabled = false;
  }

  /** Update the system logic every frame */
  public void update(final float deltaTime) {}

  /** Update the system logic fixed frame number */
  public void fixedUpdate() {}
}
