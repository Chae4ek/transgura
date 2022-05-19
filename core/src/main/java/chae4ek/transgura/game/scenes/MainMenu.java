package chae4ek.transgura.game.scenes;

import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.game.ecs.entity.PhantomBlock;
import chae4ek.transgura.game.ecs.entity.Player;
import chae4ek.transgura.game.ecs.entity.SolidBlock;
import chae4ek.transgura.game.ecs.entity.TestRock;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.resources.TextureType;
import chae4ek.transgura.game.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.io.DataOutputStream;
import java.io.IOException;

public class MainMenu extends Scene {

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST, AtlasType.CASTLE);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);
    final AtlasRegion brickWall = ResourceLoader.loadAtlasRegion(TextureType.BRICK_WALL);
    final AtlasRegion castleTop = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_TOP);
    final AtlasRegion castleRight = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RIGHT);
    final AtlasRegion castleRTconvexCorner =
        ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RT_CONVEX_CORNER);

    new SolidBlock(100f, 100f, wood);
    new SolidBlock(200f, 100f, wood);
    new SolidBlock(200f, 110f, wood);

    new SolidBlock(32f, 0f, 27, 1, castleTop);
    new SolidBlock(0f, 32f, 1, 4, castleRight);
    new SolidBlock(0f, 0f, castleRTconvexCorner);
    new PhantomBlock(0f, 0f, 5, 5, brickWall, -1);

    new Player(150f, 100f);

    // debug test
    new TestRock(400f, 300f);

    try (final DataOutputStream out =
        new DataOutputStream(Gdx.files.local("saves/world0").write(false, 8192))) {
      saveWorld(out);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
