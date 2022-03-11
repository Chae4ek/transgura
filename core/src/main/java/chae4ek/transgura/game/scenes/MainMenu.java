package chae4ek.transgura.game.scenes;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.ecs.WorldSerializer;
import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.component.Sprite;
import chae4ek.transgura.game.ecs.entity.Player;
import chae4ek.transgura.game.ecs.entity.SolidBlock;
import chae4ek.transgura.game.ecs.entity.TestRock;
import chae4ek.transgura.game.ecs.system.Menu;
import chae4ek.transgura.game.resources.ResourceLoader;
import chae4ek.transgura.game.resources.TextureType;
import chae4ek.transgura.game.resources.TextureType.AtlasType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class MainMenu extends Scene {

  public MainMenu() {
    world.setGravity(new Vector2(0, -9.81f / GameSettings.PPM));

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

    // debug test
    new TestRock(400f, 300f);

    WorldSerializer.saveWorld("saves/test_world_0");
  }
}
