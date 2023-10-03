package chae4ek.transgura.ecs.entity;

import static chae4ek.engine.util.GameSettings.reversePPM;

import chae4ek.engine.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.TextCoinCount;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.util.ARAnimation;
import chae4ek.transgura.util.EventListener;
import chae4ek.transgura.util.collision.EntityData;
import chae4ek.transgura.util.resources.ResourceLoader;
import chae4ek.transgura.util.resources.TextureType;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Coin extends Entity implements EventListener<Player> {

  public Coin(final float x, final float y, final String tag) {
    final AtlasRegion[] idleFrames = ResourceLoader.loadAtlasRegions(TextureType.COIN);
    // for (final AtlasRegion region : idleFrames) region.offsetY = 4f;
    final ARAnimation idle = new ARAnimation(0.1f, idleFrames);
    idle.setPlayMode(PlayMode.LOOP);

    final AnimatedSprites animation = new AnimatedSprites(-2, idle);
    animation.scale = 2;

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.StaticBody, x, y);
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();

    final PolygonShape shape = new PolygonShape();
    shape.setAsBox(14 * reversePPM, 14 * reversePPM, new Vector2(0.5f, 0.5f), 0);
    final Fixture fixture = body.createFixture(shape, 0f);
    shape.dispose();
    fixture.setSensor(true);
    final Filter filter = new Filter();
    filter.categoryBits = 2;
    fixture.setFilterData(filter);
    fixture.setUserData(new EntityData(this, tag));

    addComponent(new Position(x, y), physicalBody, animation);
  }

  @Override
  public void run(final Player player) {
    player.getComponent(TextCoinCount.class).coinCount++;
    destroy();
  }
}
