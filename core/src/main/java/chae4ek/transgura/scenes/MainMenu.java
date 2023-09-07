package chae4ek.transgura.scenes;

import chae4ek.engine.ecs.Scene;
import chae4ek.transgura.ecs.entity.Chandelier;
import chae4ek.transgura.ecs.entity.PhantomBlock;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.ecs.entity.SolidBlock;
import chae4ek.transgura.third_party.ldtk.Converter;
import chae4ek.transgura.third_party.ldtk.LDtk;
import chae4ek.transgura.third_party.ldtk.LayerInstance;
import chae4ek.transgura.util.resources.ResourceLoader;
import chae4ek.transgura.util.resources.TextureType;
import chae4ek.transgura.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainMenu extends Scene {

  private LDtk readWorld() {
    try {
      final byte[] data = Files.readAllBytes(Path.of("saves/world.ldtk"));
      final String jsonData = new String(data, StandardCharsets.UTF_8);
      return Converter.fromJsonString(jsonData);
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST, AtlasType.CASTLE, AtlasType.DECOR);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);
    final AtlasRegion brickWall = ResourceLoader.loadAtlasRegion(TextureType.BRICK_WALL);
    final AtlasRegion castleTop = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_TOP);
    final AtlasRegion castleBottom = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_BOTTOM);
    final AtlasRegion castleRight = ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RIGHT);
    final AtlasRegion castleRTconvexCorner =
        ResourceLoader.loadAtlasRegion(TextureType.CASTLE_RT_CONVEX_CORNER);

    new SolidBlock(96f, 96f, wood);
    new SolidBlock(192f, 96f, wood);
    new SolidBlock(192f, 128f, wood);

    new SolidBlock(32f, 0f, 27, 1, castleTop);
    new SolidBlock(0f, 32f, 1, 4, castleRight);
    new SolidBlock(0f, 0f, castleRTconvexCorner);
    new PhantomBlock(0f, 0f, 5, 5, brickWall, -1);

    new SolidBlock(0f, 192f, 27, 1, castleBottom);
    new Chandelier(128f, 128f, 9);

    new Player(150f, 100f);

    // LDtk world loading

    final LDtk ldtk = readWorld();
    final LayerInstance layer = ldtk.getLevels()[0].getLayerInstances()[0];

    final int[] intGrid = layer.getIntGridCsv();
    final int gridHeight = layer.getCHei();
    final int gridWidth = layer.getCWid();
    final int[][] grid = new int[gridHeight][gridWidth];
    for (int i = 0, x = 0, y = gridHeight - 1; i < intGrid.length; ++i, ++x) {
      if (x == gridWidth) {
        x = 0;
        --y;
      }
      grid[y][x] = intGrid[i];
    }

    for (int y = 0; y < gridHeight; ++y) {
      for (int x = 0; x < gridWidth; ++x) {
        if (grid[y][x] != 0) {
          new SolidBlock(32f * x, 32f * y, wood);
        }
      }
    }
  }
}
