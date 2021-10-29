package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.component.Component;
import chae4ek.transgura.ecs.component.ComponentType;
import chae4ek.transgura.ecs.component.components.Script;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.HashMap;
import java.util.Map;

public class SystemManager {

  private static final transient GameAlert gameAlert = new GameAlert(SystemManager.class);

  private final Map<Integer, Script> idToScript = new HashMap<>();

  /** Add a system entity script to this system manager */
  public void addSystemScript(final Entity systemEntity) {
    final Component component = systemEntity.componentManager.getComponent(ComponentType.SCRIPT);
    if (component == null) {
      gameAlert.warn(GameErrorType.SYSTEM_ENTITY_HAS_NOT_SCRIPT, systemEntity.toString());
    } else idToScript.put(systemEntity.id, (Script) component);
  }

  /**
   * Remove a system entity script from this system manager
   *
   * @param entityId the id of a system entity
   * @return the removed script with the system entity id or null if it doesn't exist
   */
  public Script removeSystemScript(final int entityId) {
    final Script script = idToScript.remove(entityId);
    if (script == null) {
      gameAlert.warn(GameErrorType.SYSTEM_ENTITY_DOES_NOT_EXIST, "system entity id: " + entityId);
    }
    return script;
  }

  /**
   * Invoke update() in all enabled system scripts
   *
   * @param deltaTime delta time per frame
   */
  public void updateAll(final float deltaTime) {
    for (final Script script : idToScript.values()) {
      if (script.isEnabled() && script.system.isEnabled()) script.system.update(deltaTime);
    }
  }

  /** Invoke fixedUpdate() in all enabled system scripts */
  public void fixedUpdateAll() {
    for (final Script script : idToScript.values()) {
      if (script.isEnabled() && script.system.isEnabled()) script.system.fixedUpdate();
    }
  }

  @Override
  public String toString() {
    return "scripts: " + idToScript;
  }
}
