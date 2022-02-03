package chae4ek.transgura.engine.util.resources;

public enum TextureType {
  TEST_BLOCK(AtlasType.TEST, "test_block"),
  WOOD(AtlasType.TEST, "wood"),
  OLD_MAN_IDLE(AtlasType.OLD_MAN, "idle"),
  OLD_MAN_RUN(AtlasType.OLD_MAN, "run");

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
