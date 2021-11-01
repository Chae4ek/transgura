package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.MultipleComponent;

public class Position extends MultipleComponent {

  public int x;
  public int y;

  public Position(final int x, final int y) {
    super(true);
    this.x = x;
    this.y = y;
  }

  public Position() {
    this(0, 0);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return 961 + 31 * x + y;
  }
}
