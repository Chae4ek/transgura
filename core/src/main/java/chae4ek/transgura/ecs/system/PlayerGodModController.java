package chae4ek.transgura.ecs.system;

import chae4ek.engine.ecs.Entity;
import chae4ek.engine.ecs.InputProcessor;
import chae4ek.engine.ecs.System;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.entity.Player;
import chae4ek.transgura.util.collision.CollisionProcessor;
import chae4ek.transgura.util.collision.EntityData;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class PlayerGodModController extends System {

  public float speed = 20;

  public PlayerGodModController() {
    super(false);
  }

  @Override
  public void update() {
    final boolean godMod = InputProcessor.isKeyJustDownNow(Player.GOD_MOD);
    if (godMod) switchToNormalMod();
  }

  public void switchToNormalMod() {
    setEnabled(false);
    final Entity parent = getParent();
    parent.getComponent(PlayerController.class).setEnabled(true);
    final Array<Fixture> array = parent.getComponent(PhysicalBody.class).getBody().getFixtureList();
    for (final Fixture fixture : array) {
      final EntityData entityData = CollisionProcessor.getEntityData(fixture.getUserData());
      if (entityData != null && "PLAYER".equals(entityData.tag)) {
        fixture.setSensor(false);
      }
    }
  }

  @Override
  public void fixedUpdate() {
    final Player player = (Player) getParent();
    final AnimatedSprites animation = player.getComponent(AnimatedSprites.class);
    final Body body = player.getComponent(PhysicalBody.class).getBody();

    final boolean right = InputProcessor.isKeyDown(Player.PLAYER_RIGHT);
    final boolean left = InputProcessor.isKeyDown(Player.PLAYER_LEFT);
    final boolean up = InputProcessor.isKeyDown(Player.PLAYER_UP);
    final boolean down = InputProcessor.isKeyDown(Player.PLAYER_DOWN);

    final Vector2 appliedVelocity = body.getLinearVelocity();

    if (right && !left) {
      animation.flipX = false;
      appliedVelocity.x = speed;
    }
    if (!right && left) {
      animation.flipX = true;
      appliedVelocity.x = -speed;
    }

    if (up && !down) {
      appliedVelocity.y = speed;
    }
    if (!up && down) {
      appliedVelocity.y = -speed;
    }

    body.setLinearVelocity(appliedVelocity);
    animation.setAnimation(player.idle);
  }
}
