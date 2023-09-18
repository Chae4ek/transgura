package chae4ek.transgura.util.collision;

import chae4ek.engine.ecs.Entity;

public final class EntityData {

  public final Entity parent;
  public final String tag;

  public EntityData(final Entity parent, final String tag) {
    this.parent = parent;
    this.tag = tag;
  }
}
