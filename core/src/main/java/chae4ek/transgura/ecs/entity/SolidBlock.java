package chae4ek.transgura.ecs.entity;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.component.TiledSprite;
import chae4ek.transgura.ecs.system.PhysicalBody;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SolidBlock extends Entity {

  private static final PolygonShape shape = new PolygonShape();
  private static final Vector2 center = new Vector2();

  static {
    shape.setAsBox(16 / PPM, 16 / PPM);
  }

  public SolidBlock(final float x, final float y, final AtlasRegion texture) {
    super(new Position(x, y), new Sprite(texture));
    create(x, y);
  }

  public SolidBlock(final float x, final float y, final AnimatedSprite animatedSprite) {
    super(new Position(x, y), animatedSprite);
    create(x, y);
  }

  public SolidBlock(
      final float x, final float y, final int countX, final int countY, final AtlasRegion texture) {
    super(new Position(x, y), new TiledSprite(texture, countX, countY));

    center.set((countX - 1) * 16 / PPM, (countY - 1) * 16 / PPM);
    shape.setAsBox(16 * countX / PPM, 16 * countY / PPM, center, 0f);
    create(x, y);
    shape.setAsBox(16 / PPM, 16 / PPM);
  }

  private void create(final float x, final float y) {
    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(shape, 0f);
    fixture.setFriction(0);
    fixture.setUserData("GROUND");

    addComponent(physicalBody);
  }
}
