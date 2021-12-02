package chae4ek.transgura.ecs.util.render;

import chae4ek.transgura.ecs.util.resources.SpriteBatchType;
import chae4ek.transgura.ecs.util.resources.TextureType;
import chae4ek.transgura.ecs.util.resources.TextureType.AtlasType;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

public class ResourceLoader implements AssetErrorListener {

  private static final transient GameAlert gameAlert = new GameAlert(ResourceLoader.class);

  private static final ResourceLoader resourceLoader;
  private static final AssetManager assetManager;

  private static final Map<SpriteBatchType, SpriteBatch> spriteBatches =
      new EnumMap<>(SpriteBatchType.class);
  private static Map<AtlasType, TextureAtlas> textureAtlases = new EnumMap<>(AtlasType.class);
  private static Map<TextureType, AtlasRegion> atlasRegions = new EnumMap<>(TextureType.class);

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

  /**
   * Load a sprite batch if it hasn't already loaded
   *
   * @return the loaded sprite batch
   */
  public static SpriteBatch loadSpriteBatch(final SpriteBatchType spriteBatchType) {
    SpriteBatch spriteBatch = spriteBatches.get(spriteBatchType);
    if (spriteBatch == null) {
      synchronized (spriteBatches) {
        if ((spriteBatch = spriteBatches.get(spriteBatchType)) == null) {
          spriteBatch = spriteBatchType.spriteBatchFactory.get();
          spriteBatches.put(spriteBatchType, spriteBatch);
        }
      }
    }
    return spriteBatch;
  }

  /** @return the all loaded sprite batches */
  static Collection<SpriteBatch> getSpriteBatches() {
    return spriteBatches.values();
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
    for (final SpriteBatch spriteBatch : spriteBatches.values()) spriteBatch.dispose();
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
