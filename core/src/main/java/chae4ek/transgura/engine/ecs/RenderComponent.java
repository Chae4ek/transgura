package chae4ek.transgura.engine.ecs;

public abstract class RenderComponent extends Component {

  /** The priority for rendering */
  private int zOrder;

  public RenderComponent(final int zOrder) {
    this.zOrder = zOrder;
  }

  public RenderComponent() {}

  /**
   * @return the priority for rendering
   */
  public final int getZOrder() {
    return zOrder;
  }

  /** Set the priority for rendering */
  public final void setZOrder(final int zOrder) {
    scene.renderManager.changeZOrder(this, this.zOrder, zOrder);
    this.zOrder = zOrder;
  }

  /**
   * Draw this render component. This method should not invoke begin()/end() methods in sprite batch
   */
  public abstract void draw();

  @Override
  final boolean bind(final Entity parentEntity) {
    if (super.bind(parentEntity)) {
      scene.renderManager.addRenderComponent(this);
      return true;
    }
    return false;
  }

  @Override
  final void destroyThis() {
    super.destroyThis();
    scene.renderManager.removeRenderComponent(this);
  }

  @Override
  public final boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public final int hashCode() {
    return java.lang.System.identityHashCode(this);
  }
}
