package chae4ek.transgura.scenes;

import chae4ek.engine.ecs.Scene;
import chae4ek.transgura.ecs.entity.SolidBlock;
import chae4ek.transgura.third_party.ldtk.LDtk;
import chae4ek.transgura.util.ldtk.LDtkLoader;
import chae4ek.transgura.util.resources.ResourceLoader;
import chae4ek.transgura.util.resources.TextureType;
import chae4ek.transgura.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class MainMenu extends Scene {

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST, AtlasType.CASTLE, AtlasType.DECOR);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);
    final AtlasRegion brickWall = ResourceLoader.loadAtlasRegion(TextureType.BRICK_WALL);
    final AtlasRegion castleTop = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_TOP);
    final AtlasRegion castleBottom = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_BOTTOM);
    final AtlasRegion castleRight = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RIGHT);
    final AtlasRegion castleRTconvexCorner =
        ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RT_CONVEX_CORNER);

    // new SolidBlock(96f, 96f, wood);
    // new SolidBlock(192f, 96f, wood);
    // new SolidBlock(192f, 128f, wood);

    new SolidBlock(32f, 0f, 27, 1, castleTop);
    // new SolidBlock(0f, 32f, 1, 4, castleRight);
    // new SolidBlock(0f, 0f, castleRTconvexCorner);
    // new PhantomBlock(0f, 0f, 5, 5, brickWall, -1);

    // new SolidBlock(0f, 192f, 27, 1, castleBottom);
    // new Chandelier(128f, 128f, 9);

    // new Player(150f, 100f);

    // LDtk world loading
    LDtkLoader.loadBackgroundTexture();
    final LDtk ldtk = LDtkLoader.readLDtkFromFile("saves/world.ldtk");
    LDtkLoader.loadCollisions(ldtk);
  }
}
