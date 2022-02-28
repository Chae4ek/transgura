package chae4ek.transgura.game.ecs.entity;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.util.HillGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import java.util.Random;

public class TestRock extends Entity {

  private final HillGenerator generator = new HillGenerator();
  private final PolygonShape tempShape = new PolygonShape();
  private final float hillX, hillY;

  public TestRock(final float x, final float y) {
    super(new Position(x, y));
    hillX = x;
    hillY = y;
    generateHill();
    tempShape.dispose();
  }

  private void generateHill() {
    final float[] randPoints = new float[100];
    final Random random = new Random(124);
    for (int i = 0; i < 100; ++i) randPoints[i] = random.nextFloat() * 10;

    generator.computeHill(randPoints);
    for (final Vector2[] polygonVertices : generator.getPolygonsVertices()) {
      final Rock rock = createRock(polygonVertices[0].x, polygonVertices[0].y);
      // TODO: bind neighbors to rock
      if (polygonVertices.length > 8) addSeparatedFixtures(rock, polygonVertices);
      else {
        tempShape.set(polygonVertices);
        addFixture(rock.getBody());
      }
    }
  }

  private void addSeparatedFixtures(final Rock rock, final Vector2[] polygonVertices) {
    final float[] vertices = new float[polygonVertices.length << 1];
    for (int i = 0, j = 0; i < polygonVertices.length; ++i) {
      vertices[j++] = polygonVertices[i].x;
      vertices[j++] = polygonVertices[i].y;
    }
    addSeparate(rock.getBody(), vertices, 2, vertices.length);
  }

  private void addSeparate(
      final Body body, final float[] vertices, final int startIndex, final int endIndex) {
    final int size = endIndex - startIndex;
    if (size > 14) {
      int newIndex = startIndex + (size >>> 1);
      if ((newIndex & 1) != 0) --newIndex;
      addSeparate(body, vertices, startIndex, newIndex + 2);
      addSeparate(body, vertices, newIndex, endIndex);
      return;
    }
    vertices[startIndex - 2] = vertices[0];
    vertices[startIndex - 1] = vertices[1];
    tempShape.set(vertices, startIndex - 2, size + 2);
    addFixture(body);
  }

  private Rock createRock(final float x, final float y) {
    // TODO: define BodyType by coords
    final Rock rock = new Rock(hillX + x, hillY + y, BodyType.DynamicBody);
    // final RevoluteJointDef jointDef = new RevoluteJointDef();
    // jointDef.bodyA =
    // jointDef.bodyB =
    // jointDef.localAnchorA =
    // jointDef.localAnchorB =
    return rock;
  }

  private void addFixture(final Body body) {
    final Fixture fixture = body.createFixture(tempShape, 1f);
    fixture.setFriction(0.5f);
    fixture.setRestitution(0.2f);
    fixture.setUserData("GROUND");
  }
}
