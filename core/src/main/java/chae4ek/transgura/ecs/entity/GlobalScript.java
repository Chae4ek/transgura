package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;

public class GlobalScript extends Entity {

  public GlobalScript(final boolean isEnabled, final System... systems) {
    addComponents(systems);
  }
}
