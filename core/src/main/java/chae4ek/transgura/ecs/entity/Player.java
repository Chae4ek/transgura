package chae4ek.transgura.ecs.entity;

import static chae4ek.transgura.game.GameSettings.PPM;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.ecs.system.PlayerController;
import chae4ek.transgura.ecs.util.render.ResourceLoader;
import chae4ek.transgura.ecs.util.resources.TextureType;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends Entity {

  public Player() {
    addComponent(new Sprite(1, ResourceLoader.loadAtlasRegion(TextureType.WOOD)));
    addComponent(new PlayerController());
    addComponent(new Position());

    final BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DynamicBody;
    bodyDef.gravityScale = 0f;
    bodyDef.fixedRotation = true;
    bodyDef.linearDamping = 1f;
    bodyDef.position.set(150f / PPM, 100f / PPM);

    final PolygonShape shape = new PolygonShape();
    shape.setAsBox(32f / 2f / PPM, 32f / 2f / PPM);

    addComponent(new PhysicalBody(bodyDef, shape, 1f));

    scene.systemManager.addDeferredEvent(shape::dispose);
  }
}
