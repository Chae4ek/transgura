package chae4ek.transgura.game.scenes;

import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.entity.GameObject;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.ecs.util.render.ResourceLoader;
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

    final var s = new Sprite(testBlock);
    s.destroy(); // TODO: hmm... it's ok
    new GameObject(new Position(), s);

    new GameObject(new Position(100, 100), new Sprite(wood));
    new GameObject(
        new Position(200, 200),
        new AnimatedSprite(0.5f, PlayMode.LOOP, new Sprite(testBlock), new Sprite(wood)));

    new Player();
  }
}
