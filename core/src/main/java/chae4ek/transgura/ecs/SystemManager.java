package chae4ek.transgura.ecs;

import chae4ek.transgura.ecs.system.collision.CollisionListener;
import chae4ek.transgura.ecs.util.annotations.NonConcurrent;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.game.GameSettings;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
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

  public final World world = new World(Vector2.Zero, true);
  public final CollisionListener collisionListener = new CollisionListener();

  private final Map<Entity, Set<System>> entitySystems = new HashMap<>();
  private final Map<System, Integer> systems = new HashMap<>();
  /** Non-final for fast clear only */
  private Queue<Runnable> deferredEvents = new ConcurrentLinkedQueue<>();

  private ForkJoinTask<?>[] tasksToUpdate = new ForkJoinTask<?>[12];

  public SystemManager() {
    world.setContactListener(collisionListener);
  }

  /** Add a deferred event that will run after all updates in a non-parallel context */
  void addDeferredEvent(final Runnable event) {
    deferredEvents.add(event);
  }

  /** Add a system to this system manager */
  @NonConcurrent
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
  @NonConcurrent
  void removeAllSystemsIfPresent(final Entity parentEntity) {
    final Set<System> systems = entitySystems.remove(parentEntity);
    if (systems != null)
      for (final System system : systems) {
        this.systems.compute(
            system,
            (s, i) -> {
              // if it isn't present it's a bug:
              @SuppressWarnings("ConstantConditions")
              int i0 = i;
              return i0 == 1 ? null : --i0;
            });
        system.toDestroy = true;
        system.onDestroy();
        system.hasDestroyed = true;
      }
  }

  /** Remove the system of this system manager */
  @NonConcurrent
  void removeSystem(final Entity parentEntity, final System system) {
    if (entitySystems.computeIfPresent(
            parentEntity,
            (parent, systems) -> {
              if (systems.remove(system)) {
                this.systems.compute(
                    system,
                    (s, i) -> {
                      // if it isn't present it's a bug:
                      @SuppressWarnings("ConstantConditions")
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
  @Deprecated(forRemoval = true)
  public ForkJoinPool getPool() {
    return pool;
  }

  /**
   * Invoke update() and fixedUpdate() in all enabled systems.
   *
   * <p>This method guarantees that any entity/component/system enabling/disabling/etc are parallel
   * and thread-safe, but their updates are unordered.
   *
   * <p>Well, you can be sure that any game object will be created/destroyed after update, but
   * enable/disable/checks/etc are invoked during the update. Anyway you can call any method at any
   * time in your system script or defer it using {@link #addDeferredEvent}
   */
  public void updateAndFixedUpdate(int fixedUpdateCount) {
    runDeferredEvents();
    world.step(GameSettings.timeStepForPhysics, 6, 2);

    final Set<System> allSystems = systems.keySet();
    // simple update:
    int taskCount = 0, extraCapacityNeeded = 0;
    for (final System system : allSystems) {
      if (system.isEnabled) {
        if (system.isUpdateEnabled()) {
          if (taskCount == tasksToUpdate.length) setSizeOfTasksToUpdate((int) (taskCount * 1.75f));
          tasksToUpdate[taskCount++] = pool.submit(system::update);
        }
        if (system.isFixedUpdateEnabled()) ++extraCapacityNeeded;
      }
    }
    // simple update and first fixed update are invoked together to accelerate:
    if (fixedUpdateCount > 0) {
      --fixedUpdateCount;
      if (taskCount + extraCapacityNeeded > tasksToUpdate.length) {
        setSizeOfTasksToUpdate(taskCount + extraCapacityNeeded);
      }
      for (final System system : allSystems) {
        if (system.isEnabled && system.isFixedUpdateEnabled()) {
          if (taskCount == tasksToUpdate.length) setSizeOfTasksToUpdate((int) (taskCount * 1.75f));
          tasksToUpdate[taskCount++] = pool.submit(system::fixedUpdate);
        }
      }
    }
    while (taskCount > 0) tasksToUpdate[--taskCount].join();
    runDeferredEvents();

    // other fixed updates:
    for (; fixedUpdateCount > 0; --fixedUpdateCount) {
      for (final System system : allSystems) {
        if (system.isEnabled && system.isFixedUpdateEnabled()) {
          if (taskCount == tasksToUpdate.length) setSizeOfTasksToUpdate((int) (taskCount * 1.75f));
          tasksToUpdate[taskCount++] = pool.submit(system::fixedUpdate);
        }
      }
      while (taskCount > 0) tasksToUpdate[--taskCount].join();
      runDeferredEvents();
    }
  }

  /** Run all deferred events */
  private void runDeferredEvents() {
    if (!deferredEvents.isEmpty()) {
      for (final Runnable event : deferredEvents) event.run();
      deferredEvents = new ConcurrentLinkedQueue<>();
    }
  }

  private void setSizeOfTasksToUpdate(final int newSize) {
    final ForkJoinTask<?>[] tasks = tasksToUpdate;
    tasksToUpdate = new ForkJoinTask<?>[newSize];
    java.lang.System.arraycopy(tasks, 0, tasksToUpdate, 0, tasks.length);
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
