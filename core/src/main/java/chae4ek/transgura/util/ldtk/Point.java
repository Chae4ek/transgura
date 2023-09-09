package chae4ek.transgura.util.ldtk;

public class Point {

  public final float x;
  public final float y;
  private Point[] adjacent;
  private int size;

  public Point(final float x, final float y) {
    this.x = x;
    this.y = y;
  }

  public void addAdjacent(final Point point) {
    if (adjacent == null) adjacent = new Point[4];
    adjacent[size] = point;
    ++size;
  }

  public boolean hasAdjacent() {
    return size != 0;
  }

  public Point popAdjacent() {
    if (size == 0) return null;
    --size;
    return adjacent[size];
  }
}
