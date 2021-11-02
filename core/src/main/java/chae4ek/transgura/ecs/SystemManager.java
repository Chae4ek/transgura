package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public final class SystemManager {

  private static final transient GameAlert gameAlert = new GameAlert(SystemManager.class);

  private static final ForkJoinPool pool =
      new ForkJoinPool(
          Runtime.getRuntime().availableProcessors(),
          ForkJoinPool.defaultForkJoinWorkerThreadFactory,
          null,
          true);

  private final Map<Entity, Set<System>> systems = new HashMap<>();
  /** Non-final for fast clear only */
  private Set<Runnable> deferredEvents = ConcurrentHashMap.newKeySet();

  /** Add a deferred event that will run after all updates */
  void addDeferredEvent(final Runnable event) {
    deferredEvents.add(event);
  }

  /** Add a system to this system manager */
  void addSystem(final Entity parentEntity, final System system) {
    systems.compute(
        parentEntity,
        (parent, systems) -> {
          if (systems == null) systems = new HashSet<>(5);
          if (!systems.add(system)) {
            gameAlert.warn(
                GameErrorType.SYSTEM_HAS_BEEN_REPLACED,
                "parentEntity: " + parentEntity + ", system: " + system);
          }
          return systems;
        });
  }

  /** Remove all systems of this system manager if they present */
  void removeAllSystemsIfPresent(final Entity parentEntity) {
    systems.remove(parentEntity);
  }

  /** Remove the system of this system manager */
  void removeSystem(final Entity parentEntity, final System system) {
    if (systems.computeIfPresent(
            parentEntity,
            (parent, systems) -> {
              if (!systems.remove(system)) {
                gameAlert.warn(
                    GameErrorType.SYSTEM_DOES_NOT_EXIST,
                    "parentEntity: " + parentEntity + ", systems: " + systems);
              }
              return systems;
            })
        == null) {
      gameAlert.warn(GameErrorType.ENTITY_HAS_NOT_SYSTEM, "parentEntity: " + parentEntity);
    }
  }

  /**
   * @return the pool to fork/join custom tasks
   * @deprecated it is not safe
   */
  @Deprecated
  public ForkJoinPool getPool() {
    return pool;
  }

  /** Invoke update() and fixedUpdate() in all enabled systems */
  public void updateAndFixedUpdate(int updateCount) {
    // simple update:
    final ArrayList<ForkJoinTask<?>> tasks = new ArrayList<>();
    for (final Set<System> systems : systems.values()) {
      for (final System system : systems) {
        tasks.add(pool.submit(system::update));
      }
    }
    for (final ForkJoinTask<?> task : tasks) task.join();

    // fixed update:
    for (; updateCount > 0; --updateCount) {
      int i = 0;
      for (final Set<System> systems : systems.values()) {
        for (final System system : systems) {
          tasks.set(i++, pool.submit(system::fixedUpdate));
        }
      }
      for (final ForkJoinTask<?> task : tasks) task.join();
    }

    // deferred events:
    for (final Runnable event : deferredEvents) event.run();
    deferredEvents = ConcurrentHashMap.newKeySet(); // fast clear
  }

  @Override
  public String toString() {
    return "systems: " + systems;
  }
}
