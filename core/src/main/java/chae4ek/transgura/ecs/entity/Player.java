package chae4ek.transgura.ecs.entity;

import static chae4ek.transgura.game.GameSettings.PPM_2;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.ecs.system.PlayerController;
import chae4ek.transgura.ecs.util.render.ResourceLoader;
import chae4ek.transgura.ecs.util.resources.TextureType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends Entity {

  public Player(final float x, final float y) {
    final AtlasRegion texture = ResourceLoader.loadAtlasRegion(TextureType.WOOD);

    final BodyDef bodyDef = PhysicalBody.createBodyDef(BodyType.DynamicBody, x, y);
    final PolygonShape shape = new PolygonShape();
    shape.setAsBox(texture.getRegionWidth() / PPM_2, texture.getRegionHeight() / PPM_2);

    addComponent(
        new Sprite(1, texture),
        new PlayerController(),
        new Position(x, y),
        new PhysicalBody(bodyDef, shape, 1f));

    scene.systemManager.addDeferredEvent(shape::dispose);
  }
}
