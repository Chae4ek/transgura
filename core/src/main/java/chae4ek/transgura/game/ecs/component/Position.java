package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.Component;
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
}
