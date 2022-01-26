package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import chae4ek.transgura.exceptions.GameAlert;

public abstract class RenderComponent extends Component {

  private static final transient GameAlert gameAlert = new GameAlert(RenderComponent.class);

  /** The priority for rendering */
  int zOrder;

  public RenderComponent(final int zOrder) {
    this.zOrder = zOrder;
  }

  public RenderComponent() {}

  /** @return the priority for rendering */
  public final int getZOrder() {
    return zOrder;
  }

  /** Set the priority for rendering */
  @DeferredEvent
  public final void setZOrder(final int zOrder) {
    scene.systemManager.addDeferredEvent(() -> scene.renderManager.changeZOrder(this, zOrder));
  }

  /**
   * Draw this render component. This method should not invoke begin()/end() methods in sprite batch
   */
  public abstract void draw();

  @Override
  void bind(final Entity parentEntity) {
    super.bind(parentEntity);
    scene.renderManager.addRenderComponent(parentEntity, this);
  }

  @Override
  void destroyThis() {
    super.destroyThis();
    scene.renderManager.removeRenderComponent(getParent(), this);
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
