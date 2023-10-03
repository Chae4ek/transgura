package chae4ek.transgura.util.ldtk;

import chae4ek.engine.util.GameSettings;
import chae4ek.engine.util.exceptions.GameAlert;
import chae4ek.transgura.ecs.component.TextCoinCount;
import chae4ek.transgura.ecs.component.TiledSprite;
import chae4ek.transgura.ecs.entity.Coin;
import chae4ek.transgura.ecs.entity.CollisionOutline;
import chae4ek.transgura.ecs.entity.Door;
import chae4ek.transgura.ecs.entity.PhantomBlock;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.ecs.entity.Spike;
import chae4ek.transgura.third_party.ldtk.Converter;
import chae4ek.transgura.third_party.ldtk.EntityInstance;
import chae4ek.transgura.third_party.ldtk.EnumTagValue;
import chae4ek.transgura.third_party.ldtk.GridPoint;
import chae4ek.transgura.third_party.ldtk.LDtk;
import chae4ek.transgura.third_party.ldtk.LayerInstance;
import chae4ek.transgura.third_party.ldtk.TileInstance;
import chae4ek.transgura.third_party.ldtk.TilesetDefinition;
import chae4ek.transgura.util.resources.ResourceLoader;
import chae4ek.transgura.util.resources.TextureType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ChainShape;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class LDtkLoader {

  private static final GameAlert gameAlert = new GameAlert(LDtkLoader.class);

  public static void loadBackgroundTexture() {
    final AtlasRegion spikesGridAR = ResourceLoader.loadAtlasRegion(TextureType.GRASS_LEVEL_SPIKES);
    PhantomBlock bg = new PhantomBlock(0f, 0f, 1, 1, spikesGridAR, -1);
    bg.getComponent(TiledSprite.class).scale = 2;

    final AtlasRegion intGridAR = ResourceLoader.loadAtlasRegion(TextureType.GRASS_LEVEL_0);
    bg = new PhantomBlock(0f, 0f, 1, 1, intGridAR, -1);
    bg.getComponent(TiledSprite.class).scale = 2;
  }

  public static LDtk readLDtkFromFile(final String path) {
    try {
      final byte[] data = Files.readAllBytes(Path.of(path));
      final String jsonData = new String(data, StandardCharsets.UTF_8);
      return Converter.fromJsonString(jsonData);
    } catch (final IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void loadCollisions(final LDtk ldtk) {
    final LayerInstance collisionLayer = ldtk.getLevels()[0].getLayerInstances()[0];
    final LayerInstance entityLayer = ldtk.getLevels()[0].getLayerInstances()[1];
    final LayerInstance spikesLayer = ldtk.getLevels()[0].getLayerInstances()[2];

    final int height = collisionLayer.getCHei();
    final int width = collisionLayer.getCWid();

    final AtlasRegion exitAR = ResourceLoader.loadAtlasRegion(TextureType.GRASS_LEVEL_EXIT);

    TextCoinCount.coinMax = 0;
    for (final EntityInstance entity : entityLayer.getEntityInstances()) {
      if (entity.getIdentifier().equals("Player")) {
        new Player(
            (entity.getPx()[0] / 8 + 0.5f) * GameSettings.PPM,
            (height - 1 - entity.getPx()[1] / 8 + 1.5f) * GameSettings.PPM);
      }
      if (entity.getIdentifier().equals("RIP")) {
        Player.RIP_POSITION.set(
            (entity.getPx()[0] / 8 + 0.5f), (height - 1 - entity.getPx()[1] / 8 + 1.5f));
      }
      if (entity.getIdentifier().equals("Coin")) {
        new Coin(
            (entity.getPx()[0] / 8) * GameSettings.PPM,
            (height - 1 - entity.getPx()[1] / 8 + 1) * GameSettings.PPM,
            "COIN");
        TextCoinCount.coinMax++;
      }
      if (entity.getIdentifier().equals("Exit")) {
        final String type = entity.getFieldInstances()[0].getType();
        if (!type.equals("Point")) continue;
        final GridPoint gridPoint = (GridPoint) entity.getFieldInstances()[0].getValue();
        if (gridPoint == null) {
          gameAlert.warn(
              "Door doesn't have target point at {} {}", entity.getPx()[0], entity.getPx()[1]);
          continue;
        }
        new Door(
            (entity.getPx()[0] / 8) * GameSettings.PPM,
            (height - 1 - entity.getPx()[1] / 8 + 1) * GameSettings.PPM,
            exitAR,
            "EXIT",
            gridPoint.getCx() + 0.5f,
            height - 1 - gridPoint.getCy() + 0.5f);
      }
    }

    final Point[][] grid = new Point[height + 1][width + 1];
    for (int y = 0; y <= height; ++y) {
      for (int x = 0; x <= width; ++x) {
        grid[y][x] = new Point(x, y);
      }
    }

    // spikes
    for (final TileInstance tile : spikesLayer.getAutoLayerTiles()) {
      final int x = tile.getPx()[0] / 8; // 8 = grid size in pixels
      final int y = height - 1 - tile.getPx()[1] / 8;
      final boolean flipY = tile.getF() == 2;
      new Spike(x * GameSettings.PPM, y * GameSettings.PPM, flipY);
    }

    // final EnumDefinition[] enums = ldtk.getDefs().getEnums();
    final TilesetDefinition[] tilesets = ldtk.getDefs().getTilesets();
    final EnumTagValue[] enumTags = tilesets[1].getEnumTags();
    // final int[] intGrid = collisionLayer.getIntGridCsv();
    final TileInstance[] autoLayerTiles = collisionLayer.getAutoLayerTiles();

    // ground
    for (final TileInstance tile : autoLayerTiles) {
      final int tileId = tile.getT();
      final int x = tile.getPx()[0] / 8; // 8 = grid size in pixels
      final int y = height - 1 - tile.getPx()[1] / 8;

      final boolean isLeft = Arrays.stream(enumTags[0].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isRight = Arrays.stream(enumTags[1].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isTop = Arrays.stream(enumTags[2].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isBottom = Arrays.stream(enumTags[3].getTileIds()).anyMatch(id -> id == tileId);

      final boolean isLeftTop =
          Arrays.stream(enumTags[4].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isRightTop =
          Arrays.stream(enumTags[5].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isLeftBottom =
          Arrays.stream(enumTags[6].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isRightBottom =
          Arrays.stream(enumTags[7].getTileIds()).anyMatch(id -> id == tileId);

      if (isLeft) grid[y + 1][x].addAdjacent(grid[y][x]);
      if (isRight) grid[y][x + 1].addAdjacent(grid[y + 1][x + 1]);
      if (isTop) grid[y + 1][x + 1].addAdjacent(grid[y + 1][x]);
      if (isBottom) grid[y][x].addAdjacent(grid[y][x + 1]);

      if (isLeftTop) grid[y + 1][x + 1].addAdjacent(grid[y][x]);
      if (isRightTop) grid[y][x + 1].addAdjacent(grid[y + 1][x]);
      if (isLeftBottom) grid[y + 1][x].addAdjacent(grid[y][x + 1]);
      if (isRightBottom) grid[y][x].addAdjacent(grid[y + 1][x + 1]);
    }

    for (int y = 0; y <= height; ++y) {
      skip:
      for (int x = 0; x <= width; ++x) {
        // traversing through connected vertices in counterclockwise order
        final Point point = grid[y][x];
        if (!point.hasAdjacent()) continue;

        Point next = point;
        final ArrayList<Vector2> vertexes = new ArrayList<>();
        vertexes.add(new Vector2(point.x, point.y));

        while ((next = next.popAdjacent()) != point) {
          if (next == null) continue skip; // less than 3 vertexes or it's touching a world border
          vertexes.add(new Vector2(next.x, next.y));
        }

        // optimization by removing points on the same line
        {
          Vector2 A;
          Vector2 curr = vertexes.get(0);
          Vector2 B = vertexes.get(1);
          for (int i = 2; i < vertexes.size(); ) {
            A = curr;
            curr = B;
            B = vertexes.get(i);
            // if curr in A-B line, then delete it
            // e.g. if dist(A, curr) + dist(curr, B) == dist(A, B)
            if (Math.abs(curr.dst(A) + curr.dst(B) - A.dst(B)) < 1e-5) {
              vertexes.remove(i - 1);
              curr = A;
            } else {
              ++i;
            }
          }
          // check the first one
          if (vertexes.size() > 2) {
            A = vertexes.get(vertexes.size() - 1);
            curr = vertexes.get(0);
            B = vertexes.get(1);
            if (Math.abs(curr.dst(A) + curr.dst(B) - A.dst(B)) < 1e-5) {
              vertexes.remove(0);
            }
          }
          if (vertexes.size() < 3) continue;
        }

        // creating solid platforms
        {
          final ChainShape shape = new ChainShape();
          final Vector2[] vs = new Vector2[vertexes.size()];
          shape.createLoop(vertexes.toArray(vs));

          new CollisionOutline(shape);
          shape.dispose();
        }
      }
    }
  }
}
