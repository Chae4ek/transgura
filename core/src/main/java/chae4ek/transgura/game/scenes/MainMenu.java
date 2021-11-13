package chae4ek.transgura.game.scenes;

import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.entity.GameObject;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.ecs.util.ResourceLoader;
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

    new GameObject(new Menu());

    new GameObject(new Position(), new Sprite(testBlock));
    new GameObject(new Position(100, 100), new Sprite(wood));
    new GameObject(new Position(150, 100), new Sprite(wood));

    new GameObject(
        new Position(200, 200), new AnimatedSprite(0.5f, PlayMode.LOOP, testBlock, wood));
  }
}
