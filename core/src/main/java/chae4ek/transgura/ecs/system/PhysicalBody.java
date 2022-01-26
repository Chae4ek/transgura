package chae4ek.transgura.ecs.system;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicalBody extends System {

  private final Body body;

  public PhysicalBody(final BodyDef bodyDef) {
    body = scene.systemManager.world.createBody(bodyDef);
  }

  /** @return a new BodyDef with specified parameters */
  public static BodyDef createBodyDef(final BodyType bodyType, final float x, final float y) {
    final BodyDef bodyDef = new BodyDef();
    bodyDef.type = bodyType;
    bodyDef.fixedRotation = true;
    bodyDef.position.set(x / PPM, y / PPM);
    return bodyDef;
  }

  @Override
  public void onDestroy() {
    scene.systemManager.world.destroyBody(body);
  }

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
    getParent().getComponent(Position.class).setVecRef(body.getPosition(), true);
  }
}
