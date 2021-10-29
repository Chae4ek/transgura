package chae4ek.transgura.ecs.component.components.render;

import chae4ek.transgura.ecs.component.Component;
import chae4ek.transgura.ecs.component.ComponentType;
import chae4ek.transgura.ecs.component.components.Position;
import com.badlogic.gdx.graphics.Texture;

public class Sprite extends Component {

  public Position offset;
  public Texture texture;

  public Sprite(final boolean isEnabled, final Position offset, final Texture texture) {
    super(isEnabled);
    this.offset = offset;
    this.texture = texture;
  }

  @Override
  public ComponentType getType() {
    return ComponentType.SPRITE;
  }
}
