package chae4ek.transgura.render;

import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import chae4ek.transgura.render.TextureType.AtlasType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.EnumMap;
import java.util.Map;

public class ResourceLoader implements AssetErrorListener {

  private static final transient GameAlert gameAlert = new GameAlert(ResourceLoader.class);
  private static final ResourceLoader resourceLoader;
  private static final AssetManager assetManager;
  private static Map<AtlasType, TextureAtlas> textureAtlases = new EnumMap<>(AtlasType.class);
  private static Map<TextureType, AtlasRegion> atlasRegions = new EnumMap<>(TextureType.class);

  static {
    resourceLoader = new ResourceLoader();
    assetManager = new AssetManager();
    assetManager.setErrorListener(resourceLoader);
  }

  /** Load atlases if they haven't already loaded */
  public static void loadAtlases(final AtlasType... atlasTypes) {
    for (final AtlasType atlasType : atlasTypes) {
      assetManager.load(atlasType.atlasPath, TextureAtlas.class);
    }
    assetManager.finishLoading();

    for (final AtlasType atlasType : atlasTypes) {
      textureAtlases.put(atlasType, assetManager.get(atlasType.atlasPath));
    }
  }

  /**
   * Load an atlas region if it hasn't already loaded
   *
   * @return the loaded atlas region
   */
  public static AtlasRegion loadAtlasRegion(final TextureType textureType) {
    final TextureAtlas atlas = textureAtlases.get(textureType.atlas);
    if (atlas == null) {
      gameAlert.error(GameErrorType.ATLAS_IS_NOT_LOADED, "TextureType: " + textureType);
    }
    return atlasRegions.computeIfAbsent(textureType, type -> atlas.findRegion(type.regionName));
  }

  /** Unload all textures if they are loaded */
  public static void unloadAllResources() {
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.clear();
  }

  /** Unload and dispose all textures and other component */
  public static void dispose() {
    unloadAllResources();
    assetManager.dispose();
  }

  @Override
  public void error(final AssetDescriptor asset, final Throwable cause) {
    gameAlert.error(GameErrorType.RESOURCE_LOADER_ERROR, asset.toString(), cause);
  }
}