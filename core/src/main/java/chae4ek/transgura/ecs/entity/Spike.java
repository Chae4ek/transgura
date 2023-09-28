package chae4ek.transgura.ecs.entity;

import chae4ek.engine.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Spike extends Entity {

  public Spike(final float x, final float y, final boolean flipY) {
    super(new Position(x, y));

    final PolygonShape shape = new PolygonShape();
    if (flipY) {
      shape.set(new float[] {0.9f, 0.9f, 0.1f, 0.9f, 0.5f, 0.1f});
    } else {
      shape.set(new float[] {0.1f, 0.1f, 0.9f, 0.1f, 0.5f, 0.9f});
    }

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(shape, 0f);
    shape.dispose();
    fixture.setSensor(true);
    fixture.setUserData(new EntityData(this, "SPIKE"));

    addComponent(physicalBody);
  }
}
