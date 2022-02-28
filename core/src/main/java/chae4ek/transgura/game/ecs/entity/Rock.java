package chae4ek.transgura.game.ecs.entity;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.system.PhysicalBody;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.utils.Array;

public class Rock extends Entity {

  private final Body body;

  // TODO
  private Array<RevoluteJoint> joints;
  private Array<Rock> neighbors;

  public Rock(final float x, final float y, final BodyType bodyType) {
    final BodyDef bodyDef = PhysicalBody.createBodyDef(bodyType, x, y);
    bodyDef.fixedRotation = false;
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    body = physicalBody.getBody();
    addComponent(physicalBody, new Position(x, y));

    // TODO: create texture
  }

  public void addNeighbor(final Rock rock) {
    // TODO: scene.world.destroyJoint();
  }

  public Body getBody() {
    return body;
  }
}
