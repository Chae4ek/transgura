package chae4ek.transgura.ecs;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.GameSettings;
import com.badlogic.gdx.utils.Array;
import java.util.HashSet;
import java.util.Iterator;
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

  private final Map<Entity, Set<System>> entitySystems = new ConcurrentHashMap<>();
  private final Map<System, Integer> systems = new ConcurrentHashMap<>();
  /** Non-final for fast clear only */
  private Set<Runnable> deferredEvents =
      ConcurrentHashMap.newKeySet(GameSettings.AVG_DEFERRED_EVENTS);

  /** Add a deferred event that will run after all updates */
  void addDeferredEvent(final Runnable event) {
    deferredEvents.add(event);
  }

  /** Add a system to this system manager */
  void addSystem(final Entity parentEntity, final System system) {
    entitySystems.compute(
        parentEntity,
        (parent, systems) -> {
          if (systems == null) systems = new HashSet<>(GameSettings.AVG_SYSTEMS_PER_ENTITY);
          if (systems.add(system)) this.systems.compute(system, (s, i) -> i == null ? 1 : ++i);
          else {
            gameAlert.warn(
                GameErrorType.SYSTEM_HAS_BEEN_REPLACED,
                "parentEntity: " + parentEntity + ", system: " + system);
          }
          return systems;
        });
  }

  /** Remove all systems of this system manager if they present */
  void removeAllSystemsIfPresent(final Entity parentEntity) {
    final Set<System> systems = entitySystems.remove(parentEntity);
    if (systems != null)
      for (final System system : systems) {
        this.systems.compute( // if it isn't present it's a bug
            system,
            (s, i) -> {
              int i0 = i;
              return i0 == 1 ? null : --i0;
            });
      }
  }

  /** Remove the system of this system manager */
  void removeSystem(final Entity parentEntity, final System system) {
    if (entitySystems.computeIfPresent(
            parentEntity,
            (parent, systems) -> {
              if (systems.remove(system)) {
                this.systems.compute( // if it isn't present it's a bug
                    system,
                    (s, i) -> {
                      int i0 = i;
                      return i0 == 1 ? null : --i0;
                    });
              } else {
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

  /**
   * Invoke update() and fixedUpdate() in all enabled systems.
   *
   * <p>This method guarantees that any entity/component/system enabling/disabling/destroying/etc
   * are parallel and thread-safe, but their updates are unordered.
   *
   * <p>Well, you can be sure that any game object will be destroyed after update, but
   * enable/disable/adding/checks/etc are invoked during the update. Anyway you can call any method
   * at any time in your system script
   */
  public void updateAndFixedUpdate(int fixedUpdateCount) {
    // TODO: make final
    final Array<ForkJoinTask<?>> tasks = new Array<>(false, GameSettings.AVG_UPDATE_TASKS);
    final Set<System> allSystems = systems.keySet();
    // simple update:
    int extraCapacityNeeded = 0;
    for (final System system : allSystems) {
      if (system.isEnabled) {
        if (system.isUpdateEnabled()) tasks.add(pool.submit(system::update));
        if (system.isFixedUpdateEnabled()) ++extraCapacityNeeded;
      }
    }
    // simple update and first fixed update are invoked together to accelerate:
    if (fixedUpdateCount > 0) {
      --fixedUpdateCount;
      tasks.ensureCapacity(extraCapacityNeeded);
      for (final System system : allSystems) {
        if (system.isEnabled && system.isFixedUpdateEnabled()) {
          tasks.add(pool.submit(system::fixedUpdate));
        }
      }
    }
    for (final ForkJoinTask<?> task : tasks) task.join();
    runDeferredEvents();

    // other fixed updates:
    for (; fixedUpdateCount > 0; --fixedUpdateCount) {
      int i = 0;
      for (final System system : allSystems) {
        if (system.isEnabled && system.isFixedUpdateEnabled()) {
          if (i < tasks.size) tasks.set(i, pool.submit(system::fixedUpdate));
          else tasks.add(pool.submit(system::fixedUpdate));
          ++i;
        }
      }
      final Iterator<ForkJoinTask<?>> it = tasks.iterator();
      for (; i > 0; --i) it.next().join();
      runDeferredEvents();
    }
  }

  /** Run all deferred events: destroying */
  private void runDeferredEvents() {
    final Iterator<Runnable> it = deferredEvents.iterator();
    if (it.hasNext()) {
      do {
        it.next().run();
      } while (it.hasNext());
      deferredEvents = ConcurrentHashMap.newKeySet(GameSettings.AVG_DEFERRED_EVENTS);
    }
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("systems: [")
        .append(systems)
        .append("], deferredEvents: [")
        .append(deferredEvents)
        .append(']')
        .toString();
  }
}
