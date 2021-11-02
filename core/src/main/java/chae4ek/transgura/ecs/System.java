package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.Scene;

public abstract class System extends MultipleComponent {

  private static final transient GameAlert gameAlert = new GameAlert(System.class);

  protected System(final boolean isEnabled) {
    super(isEnabled);
  }

  @Override
  void bind(final Entity parentEntity) {
    super.bind(parentEntity);
    scene.systemManager.addSystem(parentEntity, this);
  }

  @Override
  void destroyThis() {
    for (final Entity parent : getParentEntitiesOrigin()) {
      if (scene != parent.scene) {
        gameAlert.warn(
            GameErrorType.SYSTEM_SCENE_IS_NOT_EQUAL_TO_ENTITY_SCENE,
            "system scene: " + scene + ", parent entity: " + parent);
      }
      scene.systemManager.removeSystem(parent, this);
      parent.removeComponent(this); // it's here to optimize a cycle
    }
    // It's redundant
    // super.destroyThis();
  }

  /** @return the scene of this system */
  public final Scene getScene() {
    return scene;
  }

  /** Update the system logic every frame */
  public void update() {}

  /** Update the system logic fixed frame number */
  public void fixedUpdate() {}
}
