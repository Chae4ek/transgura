package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.Component;
import chae4ek.engine.ecs.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

public class PointLight extends Component {

  private final transient box2dLight.PointLight pointLight;
  private final float offsetX;
  private final float offsetY;

  public PointLight(final Body body, final Color color, final float distance) {
    this(body, color, distance, 0f, 0f);
  }

  public PointLight(
      final Body body,
      final Color color,
      final float distance,
      final float offsetX,
      final float offsetY) {
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    pointLight = createPointLight(body, color, distance, offsetX, offsetY);
  }

  public static box2dLight.PointLight createPointLight(
      final Body body,
      final Color color,
      final float distance,
      final float offsetX,
      final float offsetY) {
    final box2dLight.PointLight pointLight =
        new box2dLight.PointLight(Game.getScene().rayHandler, 120, color, distance, 0f, 0f);
    pointLight.attachToBody(body, offsetX, offsetY);
    pointLight.setIgnoreAttachedBody(true);
    pointLight.setSoftnessLength(1.5f);
    return pointLight;
  }

  public box2dLight.PointLight getPointLight() {
    return pointLight;
  }

  @Override
  protected void onDestroy() {
    pointLight.dispose();
  }
}
