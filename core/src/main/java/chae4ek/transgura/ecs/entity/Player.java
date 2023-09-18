package chae4ek.transgura.ecs.entity;

import box2dLight.Light;
import box2dLight.RayHandler;
import chae4ek.engine.ecs.Entity;
import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.Scene;
import chae4ek.engine.util.GameSettings;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.component.PointLight;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.shaders.Vignette;
import chae4ek.transgura.ecs.system.Camera;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.ecs.system.PlayerController;
import chae4ek.transgura.ecs.system.PlayerGodModController;
import chae4ek.transgura.util.ARAnimation;
import chae4ek.transgura.util.collision.EntityData;
import chae4ek.transgura.util.resources.ResourceLoader;
import chae4ek.transgura.util.resources.TextureType;
import chae4ek.transgura.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

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

  // TODO(?): animation resource
  public final ARAnimation idle;
  public final ARAnimation run;

  private void preLoadScene() {
    final Scene scene = Game.getScene();
    scene.camera.position.set(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1, 0f);
    scene.b2dWorld.setGravity(new Vector2(0, -9.81f * GameSettings.reversePPM));
    scene.rayHandler.setAmbientLight(0.9f);
    RayHandler.useDiffuseLight(false);
    Light.setGlobalContactFilter((short) 1, (short) 0, (short) 1);
  }

  public Player(final float x, final float y) {
    preLoadScene();
    ResourceLoader.loadAtlases(AtlasType.OLD_MAN);

    final AtlasRegion[] idleFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_IDLE);
    for (final AtlasRegion region : idleFrames) region.offsetY = 4f;
    idle = new ARAnimation(0.1f, idleFrames);
    idle.setPlayMode(PlayMode.LOOP);

    final AtlasRegion[] runFrames = ResourceLoader.loadAtlasRegions(TextureType.OLD_MAN_RUN);
    for (final AtlasRegion region : runFrames) region.offsetY = 4f;
    run = new ARAnimation(0.08f, runFrames);
    run.setPlayMode(PlayMode.LOOP);

    final AnimatedSprites animation =
        new AnimatedSprites(GameSettings.zOrderForUIRendering + 100, idle);
    animation.centered = true;

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.DynamicBody, x, y);
    bodyDef.linearDamping = 2.2f;
    bodyDef.gravityScale = 0f;
    final PolygonShape shape = new PolygonShape();
    final float size = 0.5f;
    final float size2 = size - 0.2f;
    final float corner = 0.02f;
    final float offsetY = 0.05f;
    shape.set(
        new float[] {
          // top
          -size2,
          size - corner,
          -size2 + corner,
          size,
          size2 - corner,
          size,
          size2,
          size - corner,
          // bottom
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
    Fixture fixture = body.createFixture(shape, 1f);
    fixture.setFriction(0f);
    fixture.setUserData(new EntityData(this, "PLAYER"));
    final Filter filter = new Filter();
    filter.categoryBits = 2;
    fixture.setFilterData(filter);

    shape.setAsBox(size2 - corner - 0.005f, 0.01f, new Vector2(0f, size), 0f);
    fixture = body.createFixture(shape, 1f);
    fixture.setFriction(0f);
    fixture.setRestitution(0.3f);
    fixture.setUserData(new EntityData(this, "PLAYER"));
    filter.categoryBits = 2;
    fixture.setFilterData(filter);

    shape.setAsBox(size2 - corner - 0.005f, 0.01f, new Vector2(0f, offsetY - size), 0f);
    fixture = body.createFixture(shape, 1f);
    fixture.setSensor(true);
    fixture.setUserData(new EntityData(this, "PLAYER_BOTTOM"));
    filter.categoryBits = 2;
    fixture.setFilterData(filter);

    final MassData massData = body.getMassData();
    massData.mass = 0.73851955f;
    body.setMassData(massData);

    shape.dispose();

    addComponent(
        new Menu(),
        new PointLight(body, new Color(0.75f, 0.75f, 0.75f, 0.75f), 5f),
        new Vignette(999),
        animation,
        new PlayerController(),
        new PlayerGodModController(),
        new Position(x, y),
        new Camera(),
        // new Particles(true, ResourceLoader.loadParticleEffect(ParticlesType.BLUE)),
        physicalBody);
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    preLoadScene();
    super.deserialize(deserializer);
    for (final AtlasRegion region : idle.getKeyFrames()) region.offsetY = 4f;
    for (final AtlasRegion region : run.getKeyFrames()) region.offsetY = 4f;
  }
}
