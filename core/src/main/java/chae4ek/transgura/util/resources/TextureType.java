package chae4ek.transgura.util.resources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TextureType {
  GRASS_LEVEL_0(AtlasType.GRASS_BIOME, "Collision"),
  GRASS_LEVEL_SPIKES(AtlasType.GRASS_BIOME, "Spikes"),
  GRASS_LEVEL_EXIT(AtlasType.GRASS_BIOME, "exit"),

  COIN(AtlasType.COIN, "coin"),

  TEST_BLOCK(AtlasType.TEST, "test_block"),
  WOOD(AtlasType.TEST, "wood"),
  BRICK_WALL(AtlasType.TEST, "brick_wall"),
  BRICK_WALL_2(AtlasType.TEST, "brick_wall2"),
  OLD_MAN_IDLE(AtlasType.OLD_MAN, "idle"),
  OLD_MAN_RUN(AtlasType.OLD_MAN, "run"),

  DECOR_CHANDELIER(AtlasType.DECOR, "chandelier"),
  DECOR_CHANDELIER_MOUNTING(AtlasType.DECOR, "chandelier_mounting"),
  DECOR_CHAIN_FRAGMENT(AtlasType.DECOR, "chain_fragment"),

  CASTLE_LT_CORNER(AtlasType.CASTLE, "castle1"),
  CASTLE_TOP(AtlasType.CASTLE, "castle2"),
  CASTLE_RT_CORNER(AtlasType.CASTLE, "castle3"),
  CASTLE_LEFT(AtlasType.CASTLE, "castle4"),
  CASTLE_FILLER(AtlasType.CASTLE, "castle5"),
  CASTLE_RIGHT(AtlasType.CASTLE, "castle6"),
  CASTLE_LB_CORNER(AtlasType.CASTLE, "castle7"),
  CASTLE_BOTTOM(AtlasType.CASTLE, "castle8"),
  CASTLE_RB_CORNER(AtlasType.CASTLE, "castle9"),
  CASTLE_LT_CONVEX_CORNER(AtlasType.CASTLE, "castle10"),
  CASTLE_RT_CONVEX_CORNER(AtlasType.CASTLE, "castle11"),
  CASTLE_LB_CONVEX_CORNER(AtlasType.CASTLE, "castle12"),
  CASTLE_RB_CONVEX_CORNER(AtlasType.CASTLE, "castle13"),
  CASTLE_BRICK_BLOCK(AtlasType.CASTLE, "castle14"),
  CASTLE_RB_BRICK_BLOCK(AtlasType.CASTLE, "castle15"),
  CASTLE_LB_BRICK_BLOCK(AtlasType.CASTLE, "castle16"),
  CASTLE_BOTTOM_BRICK_BLOCK(AtlasType.CASTLE, "castle17"),
  CASTLE_LEFT_BRICK_BLOCK(AtlasType.CASTLE, "castle18"),
  CASTLE_RIGHT_BRICK_BLOCK(AtlasType.CASTLE, "castle19"),
  CASTLE_FILLER_RANDOM_1(AtlasType.CASTLE, "castle20"),
  CASTLE_FILLER_RANDOM_2(AtlasType.CASTLE, "castle21"),
  CASTLE_RB_STAIRS(AtlasType.CASTLE, "castle22"),
  CASTLE_LB_STAIRS(AtlasType.CASTLE, "castle23"),
  CASTLE_LT_STAIRS(AtlasType.CASTLE, "castle24"),
  CASTLE_RT_STAIRS(AtlasType.CASTLE, "castle25");

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
    COIN("atlas/coin.atlas"),
    GRASS_BIOME("atlas/grass_biome.atlas"),
    TEST("atlas/test_textures.atlas"),
    OLD_MAN("atlas/old_man.atlas"),
    DECOR("atlas/decor.atlas"),
    CASTLE("atlas/castle.atlas");

    public final String atlasPath;

    AtlasType(final String atlasPath) {
      this.atlasPath = atlasPath;
    }
  }
}
