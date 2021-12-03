package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.system.PhysicalBody;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SolidBlock extends Entity {

  public SolidBlock(
      final float x, final float y, final PolygonShape shape, final AtlasRegion texture) {
    super(new Position(x, y), new Sprite(texture));

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    addComponent(new PhysicalBody(bodyDef, shape, 0f));
  }

  public SolidBlock(
      final float x, final float y, final PolygonShape shape, final AnimatedSprite animatedSprite) {
    super(new Position(x, y), animatedSprite);

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    addComponent(new PhysicalBody(bodyDef, shape, 0f));
  }
}
