package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.util.DeferredEvent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;

public abstract class RenderComponent extends MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(RenderComponent.class);

  /** The priority for rendering */
  int zOrder;

  public RenderComponent(final int zOrder) {
    this.zOrder = zOrder;
  }

  public RenderComponent() {
    zOrder = 0;
  }

  /** @return the priority for rendering */
  public int getZOrder() {
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
    for (final Entity parent : getParentEntitiesOrigin()) {
      if (scene != parent.scene) {
        gameAlert.warn(
            GameErrorType.RENDER_COMPONENT_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE,
            "render component scene: " + scene + ", parent entity: " + parent);
      }
      scene.renderManager.removeRenderComponent(parent, this);
      parent.removeComponent(this); // it's here to optimize this cycle
    }
    // it's redundant
    // super.destroyThis();
  }

  @Override
  public boolean equals(final Object o) {
    return this == o;
  }

  @Override
  public int hashCode() {
    return java.lang.System.identityHashCode(this);
  }
}
