package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {

  private final Vector2 vec;

  public Position(final float x, final float y) {
    vec = new Vector2(x, y);
  }

  public Position() {
    vec = new Vector2();
  }

  public Vector2 getVec() {
    return vec;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return vec.equals(((Position) o).vec);
  }

  @Override
  public int hashCode() {
    return vec.hashCode();
  }
}
