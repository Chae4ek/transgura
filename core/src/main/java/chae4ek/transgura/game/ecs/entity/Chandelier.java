package chae4ek.transgura.game.ecs.entity;

import static chae4ek.transgura.engine.util.GameSettings.reversePPM;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.game.ecs.component.PointLight;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.component.Sprite;
import chae4ek.transgura.game.ecs.system.FlickeringPointLight;
import chae4ek.transgura.game.ecs.system.PhysicalBody;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.resources.TextureType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Chandelier extends Entity {

  private static final PolygonShape chandelierShape = new PolygonShape();
  private static final CircleShape chainShape = new CircleShape();
  private static final float offsetY = -5 * reversePPM;

  static {
    chandelierShape.setAsBox(26 * reversePPM, 3 * reversePPM, new Vector2(0f, offsetY), 0f);
    chainShape.setRadius(2 * reversePPM);
  }

  public Chandelier(final float x, final float y, final int chainFragmentCount) {
    super(new Position(x, y));
    final Body body = createBody(x, y);
    createChain(body, x, y, chainFragmentCount);
    createLight(body);
  }

  private void createChain(Body body, final float x, float y, final int chainFragmentCount) {
    final World world = Game.getScene().b2dWorld;

    y += 16;
    RevoluteJointDef startDef = new RevoluteJointDef();
    final Body startBody = createChainBody(x, y, BodyType.DynamicBody);
    startDef.bodyA = body;
    startDef.bodyB = startBody;
    startDef.localAnchorA.set(-12 * reversePPM, -2 * reversePPM);
    startDef.localAnchorB.set(-12 * reversePPM, -12 * reversePPM);
    world.createJoint(startDef);
    startDef = new RevoluteJointDef();
    startDef.bodyA = body;
    startDef.bodyB = startBody;
    startDef.localAnchorA.set(12 * reversePPM, -2 * reversePPM);
    startDef.localAnchorB.set(12 * reversePPM, -12 * reversePPM);
    world.createJoint(startDef);

    body = startBody;
    for (int i = 1; i < chainFragmentCount; ++i) {
      final RevoluteJointDef jointDef = new RevoluteJointDef();

      jointDef.bodyA = body;
      jointDef.bodyB =
          body =
              createChainBody(
                  x,
                  y + i * 4,
                  i == chainFragmentCount - 1 ? BodyType.StaticBody : BodyType.DynamicBody);
      jointDef.localAnchorA.set(0f, 2 * reversePPM);
      jointDef.localAnchorB.set(0f, -2 * reversePPM);

      world.createJoint(jointDef);
    }
  }

  private Body createChainBody(final float x, final float y, final BodyType type) {
    final BodyDef bodyDef = PhysicalBody.createBodyDef(type, x, y);
    bodyDef.fixedRotation = false;
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    physicalBody.setEnabled(false);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(chainShape, 1f);
    fixture.setSensor(true);

    addComponent(physicalBody);
    return body;
  }

  private Body createBody(final float x, final float y) {
    final AtlasRegion chandelier = ResourceLoader.loadAtlasRegion(TextureType.DECOR_CHANDELIER);
    final AtlasRegion chandelierMounting =
        ResourceLoader.loadAtlasRegion(TextureType.DECOR_CHANDELIER_MOUNTING);
    chandelierMounting.offsetY = 3f; // TODO: make a better approach to get resources
    final Sprite chandelierSprite = new Sprite(101, chandelier);
    final Sprite chandelierMountingSprite = new Sprite(99, chandelierMounting, 0f, -3f);

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.DynamicBody, x, y);
    bodyDef.fixedRotation = false;
    final PhysicalBody physicalBody =
        new PhysicalBody(bodyDef, chandelierSprite, chandelierMountingSprite);
    final Body body = physicalBody.getBody();

    final Fixture fixture = body.createFixture(chandelierShape, 1f);
    fixture.setFriction(0.5f);
    fixture.setRestitution(0.2f);
    fixture.setUserData("GROUND");

    addComponent(physicalBody, chandelierSprite, chandelierMountingSprite);
    return body;
  }

  private void createLight(final Body body) {
    final box2dLight.PointLight pointLight =
        PointLight.createPointLight(body, new Color(0.75f, 0.75f, 0.5f, 0.75f), 10f, 0f, offsetY);
    addComponent(new FlickeringPointLight(pointLight, 123f));
  }
}
