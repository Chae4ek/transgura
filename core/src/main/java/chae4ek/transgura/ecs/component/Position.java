package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.MultipleComponent;

public class Position extends MultipleComponent {

  public float x;
  public float y;

  public Position(final float x, final float y) {
    this.x = x;
    this.y = y;
  }

  public Position() {
    x = y = 0f;
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
    return (int) (961f + 31f * x + y);
  }
}
