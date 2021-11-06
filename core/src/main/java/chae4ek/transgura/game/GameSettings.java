package chae4ek.transgura.game;

/** You can change these values at runtime if you know something more */
public class GameSettings {
  public static int AVG_SYSTEMS_PER_ENTITY = 3;
  public static int AVG_UPDATE_TASKS = 16;
  public static int AVG_DEFERRED_EVENTS = 7;

  public static int AVG_COMPONENTS_PER_ENTITY = 4 + AVG_SYSTEMS_PER_ENTITY;
  public static int AVG_PARENTS_PER_COMPONENT = 2;
}
