package chae4ek.transgura.render;

import com.badlogic.gdx.graphics.Texture;
import java.util.EnumMap;
import java.util.Map;

public class ResourceLoader {

  private static final Map<TextureType, Texture> typeToTexture = new EnumMap<>(TextureType.class);

  /**
   * Load a texture if it hasn't already loaded
   *
   * @return the loaded texture
   */
  public static Texture loadTexture(final TextureType textureType) {
    return typeToTexture.computeIfAbsent(textureType, type -> new Texture(textureType.path));
  }

  /**
   * Unload a texture if it is loaded
   *
   * @param textureType the type of a texture to unload
   */
  public static void unloadTexture(final TextureType textureType) {
    typeToTexture.remove(textureType).dispose();
  }

  /** Unload all textures if they are loaded */
  public static void unloadAllTextures() {
    for (final Texture texture : typeToTexture.values()) texture.dispose();
    typeToTexture.clear();
  }
}
