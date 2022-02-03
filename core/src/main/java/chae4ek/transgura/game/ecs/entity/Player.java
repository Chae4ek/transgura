package chae4ek.transgura.game.ecs.entity;

import static chae4ek.transgura.engine.util.debug.GameSettings.PPM;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.ResourceLoader;
import chae4ek.transgura.engine.util.resources.ParticlesType;
import chae4ek.transgura.engine.util.resources.TextureType;
import chae4ek.transgura.engine.util.resources.TextureType.AtlasType;
import chae4ek.transgura.game.ecs.component.AnimatedSprite;
import chae4ek.transgura.game.ecs.component.Particles;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.system.Camera;
import chae4ek.transgura.game.ecs.system.PhysicalBody;
import chae4ek.transgura.game.ecs.system.PlayerController;
import chae4ek.transgura.game.ecs.system.PlayerGodModController;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {

  public static int PLAYER_LEFT = Keys.A;
  public static int PLAYER_RIGHT = Keys.D;
  public static int PLAYER_UP = Keys.W;
  public static int PLAYER_DOWN = Keys.S;

  public static int PLAYER_DASH = Buttons.RIGHT;
  public static int GOD_MOD = Keys.G;

  public static float SPEED = 0.3f;
  public static float JUMP_FORCE = 0.85f;
  public static float DASH_FORCE = 5.5f;

  public static Animation<AtlasRegion> idle;
  public static Animation<AtlasRegion> run;

  public Player(final float x, final float y) {
    initResources();

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.DynamicBody, x, y);
    bodyDef.linearDamping = 2.2f;
    final PolygonShape shape = new PolygonShape();
    final float size = 16f / PPM;
    final float size2 = size - 0.2f;
    final float corner = 0.02f;
    final float offsetY = 0.05f;
    shape.set(
        new float[] {
          -size2,
          size,
          size2,
          size,
          size2,
          corner - size + offsetY,
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
        new AnimatedSprite(1, idle),
        new PlayerController(),
        new PlayerGodModController(),
        new Position(x, y),
        new Camera(),
        new Particles(true, ResourceLoader.loadParticleEffect(ParticlesType.BLUE)),
        physicalBody);
  }

  private static void initResources() {
    ResourceLoader.loadAtlases(AtlasType.OLD_MAN);

    final Array<AtlasRegion> idleFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_IDLE);
    idleFrames.forEach(atlasRegion -> atlasRegion.offsetY = 8.5f);
    idle = new Animation<>(0.1f, idleFrames);
    idle.setPlayMode(PlayMode.LOOP);

    final Array<AtlasRegion> runFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_RUN);
    runFrames.forEach(atlasRegion -> atlasRegion.offsetY = 8.5f);
    run = new Animation<>(0.08f, runFrames);
    run.setPlayMode(PlayMode.LOOP);
  }
}
