package chae4ek.transgura.render;

public enum TextureType {
  TEST_BLOCK("rawTextures/test_block.png"),
  WOOD("rawTextures/wood.png");

  public final String path;

  TextureType(final String path) {
    this.path = path;
  }
}
