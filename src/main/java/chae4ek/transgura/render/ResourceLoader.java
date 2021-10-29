package chae4ek.transgura.render;

import com.badlogic.gdx.graphics.Texture;
import java.util.EnumMap;
import java.util.Map;

public class ResourceLoader {

  private static final Map<TextureType, Texture> typeToTexture = new EnumMap<>(TextureType.class);

  public static Texture loadTexture(final TextureType textureType) {
    return typeToTexture.computeIfAbsent(textureType, type -> new Texture(textureType.path));
  }

  public static void unloadTexture(final TextureType textureType) {
    typeToTexture.remove(textureType).dispose();
  }

  public static void unloadAllTextures() {
    for (final Texture texture : typeToTexture.values()) texture.dispose();
    typeToTexture.clear();
  }
}
