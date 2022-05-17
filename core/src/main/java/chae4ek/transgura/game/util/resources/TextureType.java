package chae4ek.transgura.game.util.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TextureType {
  TEST_BLOCK(AtlasType.TEST, "test_block"),
  WOOD(AtlasType.TEST, "wood"),
  BRICK_WALL(AtlasType.TEST, "brick_wall"),
  BRICK_WALL_2(AtlasType.TEST, "brick_wall2"),
  OLD_MAN_IDLE(AtlasType.OLD_MAN, "idle"),
  OLD_MAN_RUN(AtlasType.OLD_MAN, "run");

  public static final Map<String, TextureType> map;

  static {
    final Map<String, TextureType> map0 = new HashMap<>(TextureType.values().length);
    for (final TextureType type : TextureType.values()) map0.put(type.regionName, type);
    map = Collections.unmodifiableMap(map0);
  }

  public final AtlasType atlas;
  public final String regionName;

  TextureType(final AtlasType atlas, final String regionName) {
    this.atlas = atlas;
    this.regionName = regionName;
  }

  public enum AtlasType {
    TEST("atlas/test_textures.atlas"),
    OLD_MAN("atlas/old_man.atlas");

    public final String atlasPath;

    AtlasType(final String atlasPath) {
      this.atlasPath = atlasPath;
    }
  }
}
