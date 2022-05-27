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
        "prebuild/src/main/resources/rawTextures/test",
        "core/src/main/resources/atlas",
        "test_textures");
    TexturePacker.process(
        settings,
        "prebuild/src/main/resources/rawTextures/old_man",
        "core/src/main/resources/atlas",
        "old_man");
    TexturePacker.process(
        settings,
        "prebuild/src/main/resources/rawTextures/castle",
        "core/src/main/resources/atlas",
        "castle");
    TexturePacker.process(
        settings,
        "prebuild/src/main/resources/rawTextures/decor",
        "core/src/main/resources/atlas",
        "decor");
  }
}
