package chae4ek.transgura.ecs.component;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.MultipleComponent;
import com.badlogic.gdx.math.Vector2;

public class Position extends MultipleComponent {

  private Vector2 scaledVec;
  private Vector2 vec;

  public Position(final float x, final float y) {
    vec = new Vector2(x, y);
  }

  public Position() {
    vec = new Vector2();
  }

  public Vector2 getVec() {
    return scaledVec == null ? vec : scaledVec;
  }

  /**
   * Set a position with a vector that references vecRef
   *
   * @param usePPM if true {@link #getVec} will return scaled vector by {@link
   *     chae4ek.transgura.game.GameSettings#PPM}
   */
  public void setVecRef(final Vector2 vecRef, final boolean usePPM) {
    vec = vecRef;
    if (usePPM) {
      if (scaledVec == null) scaledVec = new Vector2();
      scaledVec.set(vec.x * PPM, vec.y * PPM);
    } else scaledVec = null;
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
