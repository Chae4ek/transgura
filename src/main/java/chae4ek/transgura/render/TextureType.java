package chae4ek.transgura.render;

public enum TextureType {
  TEST_BLOCK("textures/test_block.png"),
  WOOD("textures/wood.png");

  public final String path;

  TextureType(final String path) {
    this.path = path;
  }
}
