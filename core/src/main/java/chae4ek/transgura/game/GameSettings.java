package chae4ek.transgura.game;

/** You can change these values at runtime if you know something more */
public class GameSettings {
  public static final float fixedDeltaTime = 1f / 25f;

  public static int AVG_SYSTEMS_PER_ENTITY = 3;
  public static int AVG_UPDATE_TASKS = 16;

  public static int AVG_RENDER_COMPONENTS_PER_ENTITY = 2;

  public static int AVG_COMPONENTS_PER_ENTITY =
      2 + AVG_SYSTEMS_PER_ENTITY + AVG_RENDER_COMPONENTS_PER_ENTITY;
  public static int AVG_PARENTS_PER_COMPONENT = 2;
}
