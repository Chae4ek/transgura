package chae4ek.transgura.ecs.entity;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprite;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.ecs.system.PlayerController;
import chae4ek.transgura.ecs.system.PlayerGodModController;
import chae4ek.transgura.ecs.system.settings.PlayerSettings;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends Entity {

  public Player(final float x, final float y) {
    PlayerSettings.initResources();

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.DynamicBody, x, y);
    bodyDef.linearDamping = 2.2f;
    final PolygonShape shape = new PolygonShape();
    final float size = PlayerSettings.idle.getKeyFrames()[0].getRegionWidth() / PPM;
    final float size2 = size - 0.2f;
    final float corner = 0.02f;
    final float offsetY = 0.05f;
    // shape.setAsBox(size - 0.2f, size);
    shape.set(
        new float[] {
          -size2,
          size,
          size2,
          size,
          size2,
          corner - size2 + offsetY,
          size2 - corner,
          -size + offsetY,
          corner - size2,
          -size + offsetY,
          -size2,
          corner - size + offsetY
        });
    final PhysicalBody physicalBody = new PhysicalBody(bodyDef);
    final Body body = physicalBody.getBody();
    final Fixture fixture = body.createFixture(shape, 1f);
    fixture.setFriction(0);
    fixture.setUserData("PLAYER");

    final PolygonShape shapeLegs = new PolygonShape();
    shapeLegs.setAsBox(size2 - corner - 0.005f, 0.01f, new Vector2(0f, -size2 - 0.15f), 0f);
    final Fixture fixtureLegs = body.createFixture(shapeLegs, 1f);
    fixtureLegs.setSensor(true);
    fixtureLegs.setUserData("PLAYER_BOTTOM");

    final MassData massData = body.getMassData();
    massData.mass = 0.73851955f;
    body.setMassData(massData);

    shape.dispose();

    addComponent(
        new AnimatedSprite(1, PlayerSettings.idle),
        new PlayerController(),
        new PlayerGodModController(),
        new Position(x, y),
        physicalBody);
  }
}
