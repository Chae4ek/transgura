package chae4ek.transgura.game.ecs.system;

import static chae4ek.transgura.engine.util.debug.GameSettings.PPM;

import chae4ek.transgura.engine.ecs.System;
import chae4ek.transgura.game.ecs.component.Position;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicalBody extends System {

  private final Body body;
  private boolean isAwake = true;

  public PhysicalBody(final BodyDef bodyDef) {
    body = scene.world.createBody(bodyDef);
  }

  /**
   * @return a new BodyDef with specified parameters
   */
  public static BodyDef createBodyDef(final BodyType bodyType, final float x, final float y) {
    final BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.fixedRotation = true;
    bodyDef.position.set(x / PPM, y / PPM);
    return bodyDef;
  }

  @Override
  protected void onDestroy() {
    scene.world.destroyBody(body);
  }

  public Body getBody() {
    return body;
  }

  @Override
  public void update() {
    final boolean isAwakeNow = body.isAwake();
    if (isAwakeNow || isAwake) {
      isAwake = isAwakeNow;
      final Vector2 pos = body.getPosition();
      getParent().getComponent(Position.class).getVec().set(pos.x * PPM, pos.y * PPM);
    }
  }
}
