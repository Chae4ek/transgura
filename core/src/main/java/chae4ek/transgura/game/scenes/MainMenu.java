package chae4ek.transgura.game.scenes;

import static chae4ek.transgura.game.GameSettings.PPM_2;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.ecs.entity.SolidBlock;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.ecs.util.resources.ResourceLoader;
import chae4ek.transgura.ecs.util.resources.TextureType;
import chae4ek.transgura.ecs.util.resources.TextureType.AtlasType;
import chae4ek.transgura.game.Scene;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class MainMenu extends Scene {

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST);
    final AtlasRegion testBlock = ResourceLoader.loadAtlasRegion(TextureType.TEST_BLOCK);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);

    new Entity(new Menu());

    final var s = new Sprite(testBlock);
    s.destroy(); // TODO: hmm... it's ok
    new Entity(new Position(), s);

    final PolygonShape shape = new PolygonShape();
    shape.setAsBox(wood.getRegionWidth() / PPM_2, wood.getRegionHeight() / PPM_2);

    new SolidBlock(100f, 100f, shape, wood);
    new SolidBlock(200f, 100f, shape, wood);
    new SolidBlock(
        200f,
        200f,
        shape,
        new AnimatedSprite(0.5f, PlayMode.LOOP, new Sprite(testBlock), new Sprite(wood)));

    systemManager.addDeferredEvent(shape::dispose);

    new Player(150f, 100f);
  }
}
