package chae4ek.transgura.game.util.serializers;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.util.GameConfig;
import chae4ek.transgura.engine.util.serializers.WorldSerializer;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.component.Sprite;
import chae4ek.transgura.game.ecs.entity.Player;
import chae4ek.transgura.game.ecs.entity.SolidBlock;
import chae4ek.transgura.game.ecs.system.Menu;
import chae4ek.transgura.game.util.ARAnimation;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.resources.TextureType;
import chae4ek.transgura.game.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class WorldSerializerTest {

  private static Lwjgl3ApplicationConfiguration config;

  @BeforeAll
  static void setUp() {
    config = new Lwjgl3ApplicationConfiguration();

    config.setTitle("Transgura");
    config.setWindowedMode(848, 477);
    config.useVsync(false);
    config.setForegroundFPS(500);

    // TODO: refactoring
    WorldSerializer.register(AtlasRegion.class, new AtlasRegionSerializer(), false);
    WorldSerializer.register(Body.class, new BodySerializer(), false);
    WorldSerializer.register(ARAnimation.class, new AnimationSerializer(), false);
    WorldSerializer.register(ParticleEffect.class, new ParticleEffectSerializer(), false);

    GameConfig.resourceManager = ResourceLoader::new;
    GameConfig.isBox2DDebugRendererOn = true;
  }

  @Test
  void saveWorld() {
    GameConfig.mainScene = SaveScene::new;
    new Lwjgl3Application(new Game(), config);
  }

  @Test
  void loadWorld() {
    GameConfig.mainScene = LoadScene::new;
    new Lwjgl3Application(new Game(), config);
  }

  private static class SaveScene extends Scene {
    public SaveScene() {
      ResourceLoader.loadAtlases(AtlasType.TEST);
      final AtlasRegion testBlock = ResourceLoader.loadAtlasRegion(TextureType.TEST_BLOCK);
      final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);
      new Entity(new Menu());
      new Entity(new Position(), new Sprite(testBlock));
      new SolidBlock(100f, 100f, wood);
      new SolidBlock(200f, 100f, wood);
      new SolidBlock(200f, 110f, wood);
      new SolidBlock(0f, 0f, 27, 1, wood);
      new SolidBlock(0f, 32f, 1, 4, wood);
      new Player(150f, 100f);

      // TODO
      // Game.getScene().saveWorld("saves/test_world");
    }
  }

  private static class LoadScene extends Scene {
    public LoadScene() {
      // Game.getScene().loadWorld("saves/test_world");
    }
  }
}
