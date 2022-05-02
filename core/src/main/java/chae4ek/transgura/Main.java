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
import chae4ek.transgura.game.util.serializers.FixtureSerializer;
import chae4ek.transgura.game.util.serializers.ParticleEffectSerializer;
import chae4ek.transgura.game.util.serializers.PolygonShapeSerializer;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.MDC;

public final class Main {
  public static void main(final String[] args) {
    MDC.put("time", new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss").format(new Date()));
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
    WorldSerializer.register(Fixture.class, new FixtureSerializer(), false);
    WorldSerializer.register(PolygonShape.class, new PolygonShapeSerializer(), false);
    WorldSerializer.register(ARAnimation.class, new AnimationSerializer(), false);
    WorldSerializer.register(ParticleEffect.class, new ParticleEffectSerializer(), false);

    new Lwjgl3Application(new Game(), config);
  }
}
