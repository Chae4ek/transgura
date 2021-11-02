package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.math.Matrix4;

public abstract class RenderComponent extends MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(RenderComponent.class);

  protected RenderComponent(final boolean isEnabled) {
    super(isEnabled);
  }

  /** Draw this render component */
  public abstract void draw(final Matrix4 projection);

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
      parent.removeComponent(this); // it's here to optimize a cycle
    }
    // It's redundant
    // super.destroyThis();
  }
}