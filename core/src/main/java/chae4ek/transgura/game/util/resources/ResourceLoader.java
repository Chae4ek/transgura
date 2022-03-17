package chae4ek.transgura.game.util.resources;

import chae4ek.transgura.engine.util.ResourceManager;
import chae4ek.transgura.engine.util.exceptions.GameAlert;
import chae4ek.transgura.game.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.ObjectIntMap;
import java.util.EnumMap;
import java.util.Map;

public final class ResourceLoader implements ResourceManager, AssetErrorListener {

  private static final GameAlert gameAlert = new GameAlert(ResourceLoader.class);

  private static final AssetManager assetManager = new AssetManager();

  private static Map<ParticlesType, ParticleEffect> particleEffects =
      new EnumMap<>(ParticlesType.class);
  private static ObjectIntMap<ParticleEffect> particles =
      new ObjectIntMap<>(ParticlesType.values().length);

  private static Map<AtlasType, TextureAtlas> textureAtlases = new EnumMap<>(AtlasType.class);
  private static Map<TextureType, AtlasRegion[]> atlasRegions = new EnumMap<>(TextureType.class);

  public ResourceLoader() {
    assetManager.setErrorListener(this);
  }

  /** Load a particle effect if it hasn't already loaded */
  public static ParticleEffect loadParticleEffect(final ParticlesType particlesType) {
    // TODO: wierd load
    final ParticleEffect particleEffect =
        particleEffects.computeIfAbsent(
            particlesType,
            type -> {
              final String particlePath = "particles/" + type.particleName;
              assetManager.load(particlePath, ParticleEffect.class);
              assetManager.finishLoading();
              return assetManager.get(particlePath, ParticleEffect.class);
            });
    particles.put(particleEffect, particlesType.ordinal());
    return particleEffect;
  }

  /**
   * @return the particle type ordinal or -1 if the particleEffect isn't loaded
   */
  public static int getLoadedParticleTypeOrdinal(final ParticleEffect particleEffect) {
    return particles.get(particleEffect, -1);
  }

  /** Load atlases if they haven't been loaded yet */
  public static void loadAtlases(final AtlasType... atlasTypes) {
    boolean load = false;
    for (final AtlasType atlasType : atlasTypes) {
      if (!textureAtlases.containsKey(atlasType)) {
        load = true;
        assetManager.load(atlasType.atlasPath, TextureAtlas.class);
      }
    }
    if (load) assetManager.finishLoading();

    for (final AtlasType atlasType : atlasTypes) {
      textureAtlases.putIfAbsent(atlasType, assetManager.get(atlasType.atlasPath));
    }
  }

  private static TextureAtlas getOrLoadTextureAtlas(final AtlasType atlasType) {
    final TextureAtlas atlas = textureAtlases.get(atlasType);
    if (atlas != null) return atlas;
    assetManager.load(atlasType.atlasPath, TextureAtlas.class);
    assetManager.finishLoading();
    final TextureAtlas loadedAtlas = assetManager.get(atlasType.atlasPath);
    textureAtlases.put(atlasType, loadedAtlas);
    return loadedAtlas;
  }

  /**
   * Load the first atlas region if it hasn't been loaded yet
   *
   * <p>Note: the index of the region must be -1
   *
   * @return the loaded atlas region
   */
  public static AtlasRegion loadAtlasRegion(final TextureType textureType) {
    return atlasRegions
        .computeIfAbsent(
            textureType,
            type -> {
              final AtlasRegion region =
                  getOrLoadTextureAtlas(textureType.atlas).findRegion(type.regionName);
              if (region.index != -1) gameAlert.error("The atlas region's index is not -1");
              return new AtlasRegion[] {region};
            })[0];
  }

  /**
   * Load atlas regions if they haven't been loaded yet
   *
   * @return the loaded atlas region. Note: the returned array is a REFERENCE
   */
  public static AtlasRegion[] loadAtlasRegions(final TextureType textureType) {
    // TODO(?): array reference is bad
    return atlasRegions.computeIfAbsent(
        textureType,
        type -> getOrLoadTextureAtlas(textureType.atlas).findRegions(type.regionName).shrink());
  }

  /** Unload some scene's textures if they are loaded */
  @Override
  public void unloadSceneResources() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    for (final ParticleEffect particleEffect : particleEffects.values()) particleEffect.dispose();
    particleEffects = new EnumMap<>(ParticlesType.class);
    particles = new ObjectIntMap<>();
    textureAtlases = new EnumMap<>(AtlasType.class);
    atlasRegions = new EnumMap<>(TextureType.class);
    assetManager.clear();
  }

  /** Unload and dispose all textures and other component */
  @Override
  public void dispose() {
    for (final TextureAtlas textureAtlas : textureAtlases.values()) textureAtlas.dispose();
    for (final ParticleEffect particleEffect : particleEffects.values()) particleEffect.dispose();
    particleEffects = new EnumMap<>(ParticlesType.class);
    particles = new ObjectIntMap<>();
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
