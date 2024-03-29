package chae4ek.engine.ecs;

public abstract class System extends Component {

  transient boolean wasEnabled = true;

  public System() {}

  public System(final boolean isEnabled) {
    super(isEnabled);
    wasEnabled = isEnabled;
  }

  @Override
  final boolean bind(final Entity parentEntity) {
    if (super.bind(parentEntity)) {
      Game.getScene().systemManager.addSystem(this);
      return true;
    }
    return false;
  }

  @Override
  final void destroyThis() {
    super.destroyThis();
    Game.getScene().systemManager.removeSystem(this);
  }

  /** Update the system logic every frame */
  public void update() {}

  /** Update the system logic fixed frame number */
  public void fixedUpdate() {}

  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return java.lang.System.identityHashCode(this);
  }
}
