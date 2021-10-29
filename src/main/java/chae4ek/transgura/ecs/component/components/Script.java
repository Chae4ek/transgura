package chae4ek.transgura.ecs.component.components;

import chae4ek.transgura.ecs.component.Component;
import chae4ek.transgura.ecs.component.ComponentType;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.ecs.system.System;

/**
 * To add a system in this script to update() game method you need to invoke {@link
 * chae4ek.transgura.ecs.system.SystemManager#addSystemScript(Entity)}
 */
public class Script extends Component {

  public final System system;

  public Script(final boolean isEnabled, final System system) {
    super(isEnabled);
    this.system = system;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.SCRIPT;
  }
}
