package chae4ek.transgura;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.util.GameConfig;
import chae4ek.transgura.engine.util.serializers.WorldSerializer;
import chae4ek.transgura.game.scenes.MainMenu;
import chae4ek.transgura.game.util.ARAnimation;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.serializers.AnimationSerializer;
import chae4ek.transgura.game.util.serializers.AtlasRegionSerializer;
import chae4ek.transgura.game.util.serializers.BodySerializer;
import chae4ek.transgura.game.util.serializers.ParticleEffectSerializer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;

public final class Main {
  public static void main(final String[] args) {
    final Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

    config.setTitle("Transgura");
    config.setWindowedMode(848, 477);
    config.useVsync(false);
    config.setForegroundFPS(500);

    GameConfig.mainScene = MainMenu::new;
    GameConfig.resourceManager = ResourceLoader::new;
    GameConfig.isBox2DDebugRendererOn = true;

    // TODO: refactoring
    WorldSerializer.register(AtlasRegion.class, new AtlasRegionSerializer(), false);
    WorldSerializer.register(Body.class, new BodySerializer(), false);
    WorldSerializer.register(ARAnimation.class, new AnimationSerializer(), false);
    WorldSerializer.register(ParticleEffect.class, new ParticleEffectSerializer(), false);

    new Lwjgl3Application(new Game(), config);
  }
}
