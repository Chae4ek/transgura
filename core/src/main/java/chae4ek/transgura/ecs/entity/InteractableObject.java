package chae4ek.transgura.ecs.entity;

import static chae4ek.engine.util.GameSettings.reversePPM;

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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class InteractableObject extends Entity {

  public InteractableObject(
      final float x, final float y, final AtlasRegion texture, final String tag) {
    final Sprite sprite = new Sprite(texture);
    sprite.scale = 2;

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final PolygonShape shape = new PolygonShape();
    shape.setAsBox(16 * reversePPM, 16 * reversePPM, new Vector2(0.5f, 0.5f), 0);
    final Fixture fixture = body.createFixture(shape, 0f);
    shape.dispose();
    fixture.setSensor(true);
    final Filter filter = new Filter();
    filter.categoryBits = 2;
    fixture.setFilterData(filter);
    fixture.setUserData(new EntityData(this, tag));

    addComponent(new Position(x, y), physicalBody, sprite);
  }
}
