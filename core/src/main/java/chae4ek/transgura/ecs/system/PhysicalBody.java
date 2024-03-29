package chae4ek.transgura.ecs.system;

import static chae4ek.engine.util.GameSettings.PPM;
import static chae4ek.engine.util.GameSettings.reversePPM;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicalBody extends System {

  private static final float RAD_TO_DEG = (float) (180 / Math.PI);
  private final Body body;
  private boolean isAwake = true;
  private final Sprite[] sprites;
  private boolean updateParentPosition = true;
  private final Vector2 positionByPPM = new Vector2();

  public PhysicalBody(final BodyDef bodyDef) {
    body = Game.getScene().b2dWorld.createBody(bodyDef);
    sprites = null;
  }

  public PhysicalBody(final BodyDef bodyDef, final Sprite... sprites) {
    body = Game.getScene().b2dWorld.createBody(bodyDef);
    this.sprites = sprites;
  }

  /**
   * @return a new BodyDef with specified parameters
   */
  public static BodyDef createBodyDef(final BodyType bodyType, final float x, final float y) {
    final BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.fixedRotation = true;
    bodyDef.position.set(x * reversePPM, y * reversePPM);
    return bodyDef;
  }

  @Override
  protected void onDestroy() {
    Game.getScene()
        .systemManager
        .addDeferredEvent(() -> Game.getScene().b2dWorld.destroyBody(body));
  }

  public Body getBody() {
    return body;
  }

  public void setParentPositionUpdate(final boolean updateParentPosition) {
    this.updateParentPosition = updateParentPosition;
  }

  public Vector2 getPositionByPPM() {
    return positionByPPM.set(body.getPosition()).scl(PPM);
  }

  @Override
  public void update() {
    final boolean isAwakeNow = body.isAwake();
    if (isAwakeNow || isAwake) {
      isAwake = isAwakeNow;
      if (updateParentPosition) {
        final Vector2 bodyPos = body.getPosition();
        final Position pos = getParent().getComponent(Position.class);
        pos.getVec().set(bodyPos.x * PPM, bodyPos.y * PPM);
      }
      if (sprites != null) {
        final float angle = body.getAngle() * RAD_TO_DEG;
        for (final Sprite sprite : sprites) sprite.angle = angle;
      }
    }
  }
}
