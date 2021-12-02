package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.MultipleComponent;
import com.badlogic.gdx.math.Vector2;

public class Position extends MultipleComponent {

  private Vector2 vec;

  /** Create a position with a vector that references vecRef */
  public Position(final Vector2 vecRef) {
    vec = vecRef;
  }

  public Position(final float x, final float y) {
    vec = new Vector2(x, y);
  }

  public Position() {
    vec = new Vector2();
  }

  public Vector2 getVec() {
    return vec;
  }

  public void setVecRef(final Vector2 vecRef) {
    vec = vecRef;
  }

  @Override
  public boolean equals(final Object o) {
    return this == o || o != null && getClass() == o.getClass() && vec.equals(((Position) o).vec);
  }

  @Override
  public int hashCode() {
    return vec.hashCode();
  }
}
