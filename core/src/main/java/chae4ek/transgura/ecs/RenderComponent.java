package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Scene;
import com.badlogic.gdx.math.Matrix4;

public abstract class RenderComponent extends MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(RenderComponent.class);

  private Scene scene;

  protected RenderComponent(final boolean isEnabled) {
    super(isEnabled);
  }

  /** Draw this render component */
  public abstract void draw(final Matrix4 projection);

  @Override
  void bind(final Entity parentEntity) {
    super.bind(parentEntity);
    scene = parentEntity.scene;
    scene.renderManager.addRenderComponent(parentEntity, this);
  }

  @Override
  void destroyThis() {
    for (final Entity entity : getParentEntitiesOrigin()) {
      if (scene != entity.scene) {
        gameAlert.warn(
            GameErrorType.RENDER_COMPONENT_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE,
            "render component scene: " + scene + ", entity: " + entity);
      }
      scene.renderManager.removeRenderComponent(entity, this);
    }
  }
}
