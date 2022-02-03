package chae4ek.transgura.game.ecs.entity;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.system.PhysicalBody;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class TestRock extends Entity {

  private static final PolygonShape shape = new PolygonShape();
  private static final PolygonShape shape2 = new PolygonShape();

  static {
    shape.set(new float[] {0, 0, -1, 2, 0, 1});
    shape2.set(new float[] {0, 0, 0, 1, 1, 2});
  }

  public TestRock(final float x, final float y) {
    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    Fixture fixture = body.createFixture(shape, 0f);
    fixture.setFriction(0);
    fixture.setUserData("GROUND");
    fixture = body.createFixture(shape2, 0f);
    fixture.setFriction(0);
    fixture.setUserData("GROUND");

    addComponent(new Position(x, y), physicalBody);
  }
}
