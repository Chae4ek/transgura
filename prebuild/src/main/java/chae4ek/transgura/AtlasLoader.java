package chae4ek.transgura;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class AtlasLoader {

  private final Settings settings = new Settings();

  public static void main(final String[] args) {
    final AtlasLoader loader = new AtlasLoader();

    loader.settings.maxWidth = 512;
    loader.settings.maxHeight = 512;

    loader.process("test_textures");
    loader.process("old_man");
    loader.process("castle");
    loader.process("decor");
    loader.process("grass_biome");
  }

  private void process(final String name) {
    TexturePacker.process(
        settings,
        "prebuild/src/main/resources/rawTextures/" + name,
        "core/src/main/resources/atlas",
        name);
  }
}
