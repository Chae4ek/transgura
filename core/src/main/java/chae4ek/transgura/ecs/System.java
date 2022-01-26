package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;

public abstract class System extends Component {

  private static final transient GameAlert gameAlert = new GameAlert(System.class);

  public System() {}

  public System(final boolean isEnabled) {
    super(isEnabled);
  }

  @Override
  void bind(final Entity parentEntity) {
    super.bind(parentEntity);
    scene.systemManager.addSystem(parentEntity, this);
  }

  @Override
  void destroyThis() {
    super.destroyThis();
    scene.systemManager.removeSystem(getParent(), this);
  }

  /** @return true if the system uses {@link #update} */
  public abstract boolean isUpdateEnabled();

  /** @return true if the system uses {@link #fixedUpdate} */
  public abstract boolean isFixedUpdateEnabled();

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
