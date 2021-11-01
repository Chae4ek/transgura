package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SystemManager {

  private static final transient GameAlert gameAlert = new GameAlert(SystemManager.class);

  private final Map<Entity, Set<System>> systems = new HashMap<>();

  /** Add a system to this system manager */
  void addSystem(final Entity parentEntity, final System system) {
    final Set<System> systems = this.systems.computeIfAbsent(parentEntity, id -> new HashSet<>(5));
    if (!systems.add(system)) {
      gameAlert.warn(
          GameErrorType.SYSTEM_HAS_BEEN_REPLACED,
          "parentEntity: " + parentEntity + ", system: " + system);
    }
  }

  /** Remove all systems of this system manager if they present */
  void removeAllSystemsIfPresent(final Entity parentEntity) {
    systems.remove(parentEntity);
  }

  /** Remove the system of this system manager */
  void removeSystem(final Entity parentEntity, final System system) {
    final Set<System> systems = this.systems.get(parentEntity);
    if (systems == null) {
      gameAlert.warn(GameErrorType.ENTITY_HAS_NOT_SYSTEM, "parentEntity: " + parentEntity);
    } else {
      if (!systems.remove(system)) {
        gameAlert.warn(
            GameErrorType.SYSTEM_DOES_NOT_EXIST,
            "parentEntity: " + parentEntity + ", systems: " + systems);
      }
    }
  }

  /**
   * Invoke update(deltaTime) in all enabled systems
   *
   * @param deltaTime delta time per frame
   */
  public void updateAll(final float deltaTime) {
    for (final Set<System> systems : systems.values()) {
      for (final System system : systems) {
        if (system.isEnabled()) system.update(deltaTime);
      }
    }
  }

  /** Invoke fixedUpdate() in all enabled systems */
  public void fixedUpdateAll() {
    for (final Set<System> systems : systems.values()) {
      for (final System system : systems) {
        if (system.isEnabled()) system.fixedUpdate();
      }
    }
  }

  @Override
  public String toString() {
    return "scripts: " + systems;
  }
}
