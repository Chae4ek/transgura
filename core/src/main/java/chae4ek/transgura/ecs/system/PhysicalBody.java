package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.util.annotations.DeferredEvent;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Null;

public class PhysicalBody extends System {

  /** This is null until the next update frame */
  @Null private Body body;

  private boolean isSet;

  /** @param shape you should dispose this yourself as a deferred event */
  @DeferredEvent
  public PhysicalBody(final BodyDef bodyDef, final PolygonShape shape, final float density) {
    scene.systemManager.addDeferredEvent(
        () -> {
          body = scene.systemManager.world.createBody(bodyDef);
          body.createFixture(shape, density);
        });
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
    if (isSet) body.getPosition();
    else {
      // for some reason the position cannot update until the world is updated for the first time
      isSet = true;
      for (final Entity parent : getParentEntities()) {
        parent.getComponent(Position.class).setVecRef(body.getPosition());
      }
    }
  }
}
