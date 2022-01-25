package chae4ek.transgura.ecs.util.resources;

import chae4ek.transgura.ecs.util.resources.TextureType.AtlasType;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import java.util.EnumMap;
import java.util.Map;

public class ResourceLoader implements AssetErrorListener {

  private static final transient GameAlert gameAlert = new GameAlert(ResourceLoader.class);

  private static final ResourceLoader resourceLoader;
  private static final AssetManager assetManager;

  private static Map<AtlasType, TextureAtlas> textureAtlases = new EnumMap<>(AtlasType.class);
  private static Map<TextureType, Array<AtlasRegion>> atlasRegions =
      new EnumMap<>(TextureType.class);

  static {
    resourceLoader = new ResourceLoader();
    assetManager = new AssetManager();
    assetManager.setErrorListener(resourceLoader);
  }

  /** Load atlases if they haven't already loaded */
  public static void loadAtlases(final AtlasType... atlasTypes) {
    synchronized (assetManager) {
      for (final AtlasType atlasType : atlasTypes) {
        assetManager.load(atlasType.atlasPath, TextureAtlas.class);
      }
      assetManager.finishLoading();

      for (final AtlasType atlasType : atlasTypes) {
        textureAtlases.put(atlasType, assetManager.get(atlasType.atlasPath));
      }
    }
  }

  /**
   * Load the first atlas region if it hasn't already loaded
   *
   * @return the loaded atlas region
   */
  public static AtlasRegion loadAtlasRegion(final TextureType textureType) {
    final TextureAtlas atlas = textureAtlases.get(textureType.atlas);
    if (atlas == null) {
      gameAlert.error(GameErrorType.ATLAS_IS_NOT_LOADED, "TextureType: " + textureType);
    }
    return atlasRegions
        .computeIfAbsent(
            textureType, type -> new Array<>(new AtlasRegion[] {atlas.findRegion(type.regionName)}))
        .first();
  }

  /**
   * Load atlas regions if its haven't already loaded
   *
   * @return the loaded atlas region
   */
  public static Array<AtlasRegion> loadAtlasRegions(final TextureType textureType) {
    final TextureAtlas atlas = textureAtlases.get(textureType.atlas);
    if (atlas == null) {
      gameAlert.error(GameErrorType.ATLAS_IS_NOT_LOADED, "TextureType: " + textureType);
    }
    return atlasRegions.computeIfAbsent(textureType, type -> atlas.findRegions(type.regionName));
  }

  /** Unload some scene's textures if they are loaded */
  public static void unloadSceneResources() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.clear();
  }

  /** Unload and dispose all textures and other component */
  public static void dispose() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.dispose();
  }

  @Override
  public void error(final AssetDescriptor asset, final Throwable cause) {
    gameAlert.error(GameErrorType.RESOURCE_LOADER_ERROR, asset.toString(), cause);
  }
}
