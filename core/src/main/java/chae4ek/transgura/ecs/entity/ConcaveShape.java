package chae4ek.transgura.ecs.entity;

import chae4ek.engine.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;

public class ConcaveShape extends Entity {

  private static final ChainShape shape = new ChainShape();

  private static final int ZORDER = 50;

  static {
    // counter-clockwise to get the normal is being outside
    final Vector2[] vs =
        new Vector2[] {new Vector2(0, 0), new Vector2(3, 2), new Vector2(6, 0), new Vector2(3, 6)};
    shape.createLoop(vs);
  }

  public ConcaveShape(final float x, final float y, final AtlasRegion texture) {
    super(new Position(x, y), new Sprite(ZORDER, texture));

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(shape, 0f);
    fixture.setFriction(0.5f);
    fixture.setUserData(new EntityData(this, "GROUND"));

    addComponent(physicalBody);
  }
}
