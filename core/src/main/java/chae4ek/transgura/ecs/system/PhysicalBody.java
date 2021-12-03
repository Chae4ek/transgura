package chae4ek.transgura.ecs.system;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Null;

public class PhysicalBody extends System {

  /** This is null until the next update frame */
  @Null private Body body;

  /** @param shape you should dispose this yourself as a deferred event */
  @DeferredEvent
  public PhysicalBody(final BodyDef bodyDef, final PolygonShape shape, final float density) {
    scene.systemManager.addDeferredEvent(
        () -> {
          body = scene.systemManager.world.createBody(bodyDef);
          body.createFixture(shape, density);
        });
  }

  /** @return a new BodyDef with specified parameters */
  public static BodyDef createBodyDef(final BodyType bodyType, final float x, final float y) {
    final BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.gravityScale = 0f;
    bodyDef.fixedRotation = true;
    bodyDef.position.set(x / PPM, y / PPM);
    return bodyDef;
  }

  /** @return null until the next update frame */
  @Null
  public Body getBody() {
    return body;
  }

  @Override
  public boolean isUpdateEnabled() {
    return true;
  }

  @Override
  public boolean isFixedUpdateEnabled() {
    return false;
  }

  @Override
  public void update() {
    final Vector2 pos = body.getPosition();
    for (final Entity parent : getParentEntities()) {
      parent.getComponent(Position.class).setVecRef(pos, true);
    }
  }
}
