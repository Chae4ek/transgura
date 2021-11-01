package chae4ek.transgura.ecs.util;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.MultipleComponent;
import java.util.Map;
import java.util.Set;

public abstract class NullObjects {
  public static final Map<Class<? extends MultipleComponent>, MultipleComponent> nullComponentsMap =
      Map.of();
  public static final Set<Entity> nullEntitySet = Set.of();
  public static final SetGuard<Entity> nullEntitySetGuard = new SetGuard<>(nullEntitySet);
}
