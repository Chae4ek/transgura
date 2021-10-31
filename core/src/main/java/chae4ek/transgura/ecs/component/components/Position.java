package chae4ek.transgura.ecs.component.components;

import chae4ek.transgura.ecs.component.Component;
import chae4ek.transgura.ecs.component.ComponentType;

public class Position extends Component {

  public int x;
  public int y;

  public Position(final boolean isEnabled) {
    super(isEnabled);
  }

  public Position(final int x, final int y, final boolean isEnabled) {
    super(isEnabled);
    this.x = x;
    this.y = y;
  }

  public Position() {
    super(true);
  }

  public Position(final int x, final int y) {
    this(x, y, true);
  }

  @Override
  public ComponentType getType() {
    return ComponentType.POSITION;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Position position = (Position) o;
    return x == position.x && y == position.y;
  }

  @Override
  public int hashCode() {
    return 961 + 31 * x + y;
  }
}
