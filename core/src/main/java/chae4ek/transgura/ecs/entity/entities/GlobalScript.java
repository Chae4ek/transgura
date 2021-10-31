package chae4ek.transgura.ecs.entity.entities;

import chae4ek.transgura.ecs.component.components.Script;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.ecs.system.System;
import chae4ek.transgura.ecs.system.SystemManager;

public class GlobalScript extends Entity {

  public GlobalScript(
      final int id,
      final boolean isEnabled,
      final System system,
      final SystemManager systemManager) {
    super(id);
    componentManager.addComponents(new Script(isEnabled, system));
    systemManager.addSystemScript(this);
  }
}
