package chae4ek.transgura;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class AtlasLoader {
  public static void main(final String[] args) {
    final Settings settings = new Settings();

    settings.maxWidth = 512;
    settings.maxHeight = 512;

    TexturePacker.process(
        settings,
        "prebuild/src/main/resources/rawTextures",
        "core/src/main/resources/atlas",
        "all_textures");
  }
}
