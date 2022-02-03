package chae4ek.transgura.engine.ecs;

import java.util.HashSet;
import java.util.Set;

public class SystemManager {

  // using to change the systems when iterating
  private static Runnable[] deferredEvents = new Runnable[12];
  protected final Set<System> systems = new HashSet<>();
  private int eventCount;

  /**
   * Add a system to this system manager
   *
   * <p>Note: the parentEntity should NOT have the system. The parentEntity may not exist
   */
  protected void addSystem(final System system) {
    addDeferredEvent(() -> systems.add(system));
  }

  /**
   * Remove the system of this system manager
   *
   * <p>Note: the parentEntity SHOULD exist and SHOULD have the system
   */
  protected void removeSystem(final System system) {
    addDeferredEvent(() -> systems.remove(system));
  }

  /** Invoke update() and fixedUpdate() in all enabled systems */
  protected void updateAndFixedUpdate(int fixedUpdateCount) {
    runDeferredEvents();
    // the fix of immediately enabling
    for (final System system : systems) system.wasEnabled = system.isEnabled();
    for (final System system : systems) {
      if (system.wasEnabled) {
        system.update();
        // the first fixed update is called with the update together to accelerate the cycle
        if (fixedUpdateCount > 0 && system.isEnabled()) system.fixedUpdate();
      }
    }

    while (--fixedUpdateCount > 0) {
      InputProcessor.postUpdate(); // updating just pressed/released keys
      runDeferredEvents();
      // for (final System system : systems) system.wasEnabled = system.isEnabled();
      for (final System system : systems) {
        if (system.isEnabled()) system.fixedUpdate();
      }
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
