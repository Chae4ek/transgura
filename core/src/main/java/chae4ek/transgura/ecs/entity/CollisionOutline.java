package chae4ek.transgura.ecs.entity;

import chae4ek.engine.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;

public class CollisionOutline extends Entity {

  public CollisionOutline(final Shape shape) {
    super(new Position(0, 0));

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, 0, 0);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(shape, 0f);
    fixture.setFriction(0.5f);
    fixture.setUserData(new EntityData(this, "GROUND"));

    addComponent(physicalBody);
  }
}
