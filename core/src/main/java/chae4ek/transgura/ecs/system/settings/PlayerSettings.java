package chae4ek.transgura.ecs.system.settings;

import chae4ek.transgura.ecs.util.input.Button;
import chae4ek.transgura.ecs.util.input.Key;
import chae4ek.transgura.ecs.util.resources.ResourceLoader;
import chae4ek.transgura.ecs.util.resources.TextureType;
import chae4ek.transgura.ecs.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerSettings {
  public static Key PLAYER_LEFT = Key.A;
  public static Key PLAYER_RIGHT = Key.D;
  public static Key PLAYER_UP = Key.W;
  public static Key PLAYER_DOWN = Key.S;

  public static Button PLAYER_DASH = Button.RIGHT;
  public static Key GOD_MOD = Key.G;

  public static float SPEED = 0.3f;
  public static float JUMP_FORCE = 0.85f;
  public static float DASH_FORCE = 5.5f;

  public static Animation<AtlasRegion> idle;
  public static Animation<AtlasRegion> run;

  public static void initResources() {
    ResourceLoader.loadAtlases(AtlasType.OLD_MAN);

    final Array<AtlasRegion> idleFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_IDLE);
    idleFrames.forEach(atlasRegion -> atlasRegion.offsetY = 8.5f);
    idle = new Animation<>(0.1f, idleFrames);
    idle.setPlayMode(PlayMode.LOOP);

    final Array<AtlasRegion> runFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_RUN);
    runFrames.forEach(atlasRegion -> atlasRegion.offsetY = 8.5f);
    run = new Animation<>(0.08f, runFrames);
    run.setPlayMode(PlayMode.LOOP);
  }
}
