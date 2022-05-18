package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.Component;
import chae4ek.transgura.engine.ecs.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

public class PointLight extends Component {

  // TODO: improve serialization
  private transient box2dLight.PointLight pointLight;

  public PointLight(final Body body) {
    create(body);
  }

  private void create(final Body body) {
    pointLight =
        new box2dLight.PointLight(
            Game.getScene().rayHandler, 120, new Color(0.75f, 0.75f, 0.75f, 0.75f), 15f, 0f, 0f);
    pointLight.attachToBody(body);
    pointLight.setIgnoreAttachedBody(true);
    pointLight.setSoftnessLength(1.5f);
  }

  public box2dLight.PointLight getPointLight() {
    return pointLight;
  }

  @Override
  protected void onDestroy() {
    pointLight.dispose();
  }

  @Override
  public void serialize(final DefaultSerializer serializer) throws Exception {
    serializer.write(pointLight.getBody());
    super.serialize(serializer);
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    create((Body) deserializer.read());
    super.deserialize(deserializer);
  }
}
