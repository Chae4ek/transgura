package chae4ek.transgura.util.ldtk;

import chae4ek.transgura.ecs.component.TiledSprite;
import chae4ek.transgura.ecs.entity.CollisionOutline;
import chae4ek.transgura.ecs.entity.PhantomBlock;
import chae4ek.transgura.third_party.ldtk.Converter;
import chae4ek.transgura.third_party.ldtk.EnumTagValue;
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

  public static void loadBackgroundTexture() {
    final AtlasRegion intGridAR = ResourceLoader.loadAtlasRegion(TextureType.GRASS_LEVEL_0);
    final PhantomBlock bg = new PhantomBlock(0f, 0f, 1, 1, intGridAR, -1);
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
    final LayerInstance layer = ldtk.getLevels()[0].getLayerInstances()[0];

    final int height = layer.getCHei();
    final int width = layer.getCWid();

    final Point[][] grid = new Point[height + 1][width + 1];
    for (int y = 0; y <= height; ++y) {
      for (int x = 0; x <= width; ++x) {
        grid[y][x] = new Point(x, y);
      }
    }

    // final EnumDefinition[] enums = ldtk.getDefs().getEnums();
    final TilesetDefinition[] tilesets = ldtk.getDefs().getTilesets();
    final EnumTagValue[] enumTags = tilesets[1].getEnumTags();
    // final int[] intGrid = layer.getIntGridCsv();
    final TileInstance[] autoLayerTiles = layer.getAutoLayerTiles();

    for (final TileInstance tile : autoLayerTiles) {
      final int tileId = tile.getT();
      final int x = tile.getPx()[0] / 8; // 8 = grid size in pixels
      final int y = height - 1 - tile.getPx()[1] / 8;

      final boolean isLeft = Arrays.stream(enumTags[0].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isRight = Arrays.stream(enumTags[1].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isTop = Arrays.stream(enumTags[2].getTileIds()).anyMatch(id -> id == tileId);
      final boolean isBottom = Arrays.stream(enumTags[3].getTileIds()).anyMatch(id -> id == tileId);

      if (isLeft) grid[y + 1][x].addAdjacent(grid[y][x]);
      if (isRight) grid[y][x + 1].addAdjacent(grid[y + 1][x + 1]);
      if (isTop) grid[y + 1][x + 1].addAdjacent(grid[y + 1][x]);
      if (isBottom) grid[y][x].addAdjacent(grid[y][x + 1]);
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
