package chae4ek.transgura.game.resources;

import chae4ek.transgura.engine.ecs.ResourceManager;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import chae4ek.transgura.game.resources.TextureType.AtlasType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import java.util.EnumMap;
import java.util.Map;

public final class ResourceLoader extends ResourceManager implements AssetErrorListener {

  private static final GameAlert gameAlert = new GameAlert(ResourceLoader.class);

  private static final AssetManager assetManager = new AssetManager();

  private static Map<ParticlesType, ParticleEffect> particleEffects =
      new EnumMap<>(ParticlesType.class);
  private static Map<AtlasType, TextureAtlas> textureAtlases = new EnumMap<>(AtlasType.class);
  private static Map<TextureType, Array<AtlasRegion>> atlasRegions =
      new EnumMap<>(TextureType.class);

  public ResourceLoader() {
    assetManager.setErrorListener(this);
  }

  /** Load a particle effect if it hasn't already loaded */
  public static ParticleEffect loadParticleEffect(final ParticlesType particlesType) {
    return particleEffects.computeIfAbsent(
        particlesType,
        type -> {
          final String particlePath = "particles/" + type.particleName;
          assetManager.load(particlePath, ParticleEffect.class);
          assetManager.finishLoading();
          return assetManager.get(particlePath, ParticleEffect.class);
        });
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
   * Load the first atlas region if it hasn't already loaded
   *
   * @return the loaded atlas region
   */
  public static AtlasRegion loadAtlasRegion(final TextureType textureType) {
    // TODO: wierd load
    final TextureAtlas atlas = textureAtlases.get(textureType.atlas);
    if (atlas == null) gameAlert.error("The atlas is not loaded for TextureType {}", textureType);
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
    // TODO: wierd load
    final TextureAtlas atlas = textureAtlases.get(textureType.atlas);
    if (atlas == null) gameAlert.error("The atlas is not loaded for TextureType {}", textureType);
    return atlasRegions.computeIfAbsent(textureType, type -> atlas.findRegions(type.regionName));
  }

  /** Unload some scene's textures if they are loaded */
  @Override
  protected void unloadSceneResources() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    for (final ParticleEffect particleEffect : particleEffects.values()) particleEffect.dispose();
    particleEffects = new EnumMap<>(ParticlesType.class);
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.clear();
  }

  /** Unload and dispose all textures and other component */
  @Override
  protected void dispose() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    for (final ParticleEffect particleEffect : particleEffects.values()) particleEffect.dispose();
    particleEffects = new EnumMap<>(ParticlesType.class);
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.dispose();
  }

  @Override
  public void error(final AssetDescriptor asset, final Throwable cause) {
    gameAlert.error0("Resource loader error occurs. Asset {}", asset);
    gameAlert.error("Resource loader error", cause);
  }
}
