package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Scene;

public abstract class System extends MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(System.class);

  private Scene scene;

  protected System(final boolean isEnabled) {
    super(isEnabled);
  }

  @Override
  void bind(final Entity parentEntity) {
    super.bind(parentEntity);
    scene = parentEntity.scene;
    scene.systemManager.addSystem(parentEntity, this);
  }

  @Override
  void destroyThis() {
    for (final Entity entity : getParentEntitiesOrigin()) {
      if (scene != entity.scene) {
        gameAlert.warn(
            GameErrorType.SYSTEM_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE,
            "system scene: " + scene + ", entity: " + entity);
      }
      scene.systemManager.removeSystem(entity, this);
    }
  }

  /** @return the scene of this system */
  public final Scene getScene() {
    return scene;
  }

  /** Update the system logic every frame */
  public void update(final float deltaTime) {}

  /** Update the system logic fixed frame number */
  public void fixedUpdate() {}
}
