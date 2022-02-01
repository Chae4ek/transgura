package chae4ek.transgura.game.scenes;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.ecs.entity.SolidBlock;
import chae4ek.transgura.ecs.entity.TestRock;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.ecs.util.resources.ResourceLoader;
import chae4ek.transgura.ecs.util.resources.TextureType;
import chae4ek.transgura.ecs.util.resources.TextureType.AtlasType;
import chae4ek.transgura.game.Scene;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MainMenu extends Scene {

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST);
    final AtlasRegion testBlock = ResourceLoader.loadAtlasRegion(TextureType.TEST_BLOCK);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);

    new Entity(new Menu());

    new Entity(new Position(), new Sprite(testBlock));

    new SolidBlock(100f, 100f, wood);
    new SolidBlock(200f, 100f, wood);
    new SolidBlock(200f, 200f, new AnimatedSprite(0.5f, PlayMode.LOOP, testBlock, wood));

    new SolidBlock(0f, 0f, 27, 1, wood);
    new SolidBlock(0f, 32f, 1, 4, wood);

    new Player(150f, 100f);

    // debug test
    new TestRock(400f, 300f);
  }
}
