package chae4ek.transgura.game.util;

import com.badlogic.gdx.math.DelaunayTriangulator;
import com.badlogic.gdx.math.GeometryUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HillGenerator {

  private final DelaunayTriangulator triangulator = new DelaunayTriangulator();
  private Map<Edge, HillPoint> edgeToCircumcenter;
  private Map<Vector2, Array<HillPoint>> siteToPolygon;
  private Array<HillPoint> hillPoints; // TODO: why?
  private Array<Vector2[]> polygonsVertices;

  public Array<HillPoint> getHillPoints() {
    return hillPoints;
  }

  public Array<Vector2[]> getPolygonsVertices() {
    return polygonsVertices;
  }

  public void computeHill(final float[] pointsCloud) {
    generateVoronoi(pointsCloud);
    filterPolygons();
  }

  private void generateVoronoi(final float[] pointsCloud) {
    siteToPolygon = new HashMap<>();
    hillPoints = new Array<>(false, 16, HillPoint.class);
    edgeToCircumcenter = new HashMap<>();
    final ShortArray points = triangulator.computeTriangles(pointsCloud, false);
    for (int i = 0; i < points.size; i += 3) {
      final Triangle triangle =
          new Triangle(
              pointsCloud[points.items[i] << 1],
              pointsCloud[(points.items[i] << 1) + 1],
              pointsCloud[points.items[i + 1] << 1],
              pointsCloud[(points.items[i + 1] << 1) + 1],
              pointsCloud[points.items[i + 2] << 1],
              pointsCloud[(points.items[i + 2] << 1) + 1]);
      final Edge edge1 = new Edge(triangle.x1, triangle.y1, triangle.x2, triangle.y2);
      final Edge edge2 = new Edge(triangle.x2, triangle.y2, triangle.x3, triangle.y3);
      final Edge edge3 = new Edge(triangle.x3, triangle.y3, triangle.x1, triangle.y1);
      hillPoints.add(triangle.circumcenter);
      putTriangleEdge(triangle.circumcenter, edge1);
      putTriangleEdge(triangle.circumcenter, edge2);
      putTriangleEdge(triangle.circumcenter, edge3);
    }
  }

  private void putTriangleEdge(final HillPoint circumcenter, final Edge edge) {
    edgeToCircumcenter.compute(
        edge,
        (e, c) -> {
          if (c == null) return circumcenter;
          c.addNeighbor(circumcenter);
          circumcenter.addNeighbor(c);
          putPolygonEdge(e.x1, e.y1, c, circumcenter);
          putPolygonEdge(e.x2, e.y2, c, circumcenter);
          return null; // the edge occured twice, we don't need it anymore
        });
  }

  private void putPolygonEdge(
      final float x, final float y, final HillPoint p1, final HillPoint p2) {
    siteToPolygon.compute(
        new Vector2(x, y),
        (point, list) -> {
          if (list == null) list = new Array<>(false, 5, HillPoint.class);
          list.add(p1, p2);
          return list;
        });
  }

  private void filterPolygons() {
    polygonsVertices = new Array<>(false, 16, Vector2[].class);
    for (final Array<HillPoint> polygonPoints : siteToPolygon.values()) {
      final Vector2[] probablyPolygon = new Vector2[polygonPoints.size];

      // finding continuous polygon
      boolean isContinuous = true;
      HillPoint point = polygonPoints.items[0];
      HillPoint prev = null;
      for (int i = 0; i < polygonPoints.size; ++i) {
        for (int j = 0; j < point.neighborCount; ++j) {
          final HillPoint neighbor = point.neighbors[j];
          if (neighbor != prev && polygonPoints.contains(neighbor, true)) {
            probablyPolygon[i] = point.position;
            prev = point;
            point = neighbor;
            break;
          }
        }
        if (probablyPolygon[i] == null) {
          isContinuous = false;
          break;
        }
      }

      if (isContinuous) polygonsVertices.add(probablyPolygon);
    }
  }

  private static class HillPoint implements Comparable<HillPoint> {

    public final Vector2 position = new Vector2();
    public HillPoint[] neighbors = new HillPoint[3];
    public int neighborCount;

    public void addNeighbor(final HillPoint point) {
      neighbors[neighborCount++] = point;
    }

    @Override
    public int compareTo(final HillPoint point) {
      float delta = position.x - point.position.x;
      if (delta == 0f) delta = position.y - point.position.y;
      return delta == 0f ? 0 : delta > 0 ? 1 : -1;
    }
  }

  private static class Triangle {

    public final float x1, y1, x2, y2, x3, y3;
    public final HillPoint circumcenter = new HillPoint();

    public Triangle(
        final float x1,
        final float y1,
        final float x2,
        final float y2,
        final float x3,
        final float y3) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.x3 = x3;
      this.y3 = y3;
      GeometryUtils.triangleCircumcenter(x1, y1, x2, y2, x3, y3, circumcenter.position);
    }
  }

  private static class Edge {

    public final float x1, y1, x2, y2;

    public Edge(final float x1, final float y1, final float x2, final float y2) {
      if (x1 < x2) {
        this.x1 = x2;
        this.x2 = x1;
        this.y1 = y2;
        this.y2 = y1;
      } else if (x1 == x2 && y1 < y2) {
        this.x1 = this.x2 = x1;
        this.y1 = y2;
        this.y2 = y1;
      } else {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
      }
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      final Edge edge = (Edge) o;
      return x1 == edge.x1 && y1 == edge.y1 && x2 == edge.x2 && y2 == edge.y2;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x1, y1, x2, y2);
    }
  }
}
