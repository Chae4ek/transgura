package chae4ek.transgura.ecs.component;

public abstract class Component {

  private boolean isEnabled;

  protected Component(final boolean isEnabled) {
    this.isEnabled = isEnabled;
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

  /** @return the unique type of the component */
  public abstract ComponentType getType();
}
