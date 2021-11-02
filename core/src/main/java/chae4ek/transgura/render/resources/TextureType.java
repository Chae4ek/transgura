package chae4ek.transgura.render.resources;

public enum TextureType {
  TEST_BLOCK(AtlasType.TEST, "test_block"),
  WOOD(AtlasType.TEST, "wood");

  public final AtlasType atlas;
  public final String regionName;

  TextureType(final AtlasType atlas, final String regionName) {
    this.atlas = atlas;
    this.regionName = regionName;
  }

  @Override
  public String toString() {
    return "atlas: [" + atlas + "], regionName: " + regionName;
  }

  public enum AtlasType {
    TEST("atlas/all_textures.atlas");

    public final String atlasPath;

    AtlasType(final String atlasPath) {
      this.atlasPath = atlasPath;
    }

    @Override
    public String toString() {
      return "atlasPath: " + atlasPath;
    }
  }
}
