package chae4ek.transgura.engine.ecs;

import com.badlogic.gdx.utils.ObjectSet;

public final class SystemManager {

  // using to change the systems while iterating
  private static Runnable[] deferredEvents = new Runnable[12];
  private final ObjectSet<System> systems = new ObjectSet<>();
  private int eventCount;

  /**
   * Add a system to this system manager
   *
   * <p>Note: the system should NOT exist in the {@link #systems}
   */
  void addSystem(final System system) {
    addDeferredEvent(() -> systems.add(system));
  }

  /**
   * Remove the system of this system manager
   *
   * <p>Note: the system SHOULD exist in the {@link #systems}
   */
  void removeSystem(final System system) {
    addDeferredEvent(() -> systems.remove(system));
  }

  void updateAndOneFixedUpdate(final boolean doFixedUpdate) {
    runDeferredEvents();
    // the fix of immediately enabling
    for (final System system : systems) system.wasEnabled = system.isEnabled();
    for (final System system : systems) {
      if (system.wasEnabled) {
        system.update();
        // the first fixed update is called with the update together to accelerate the cycle
        if (doFixedUpdate && system.isEnabled()) system.fixedUpdate();
      }
    }
  }

  void fixedUpdateAll() {
    runDeferredEvents();
    // for (final System system : systems) system.wasEnabled = system.isEnabled();
    for (final System system : systems) {
      if (system.isEnabled()) system.fixedUpdate();
    }
  }

  private void runDeferredEvents() {
    if (eventCount > 0) {
      for (int i = 0; i < eventCount; ++i) deferredEvents[i].run();
      eventCount = 0;
    }
  }

  private void addDeferredEvent(final Runnable event) {
    if (eventCount == deferredEvents.length) {
      final Runnable[] events = deferredEvents;
      deferredEvents = new Runnable[(int) (eventCount * 1.75f)];
      java.lang.System.arraycopy(events, 0, deferredEvents, 0, eventCount);
    }
    deferredEvents[eventCount++] = event;
  }
}
