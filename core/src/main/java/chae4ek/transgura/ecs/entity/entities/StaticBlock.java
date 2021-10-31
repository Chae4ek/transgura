package chae4ek.transgura.ecs.entity.entities;

import chae4ek.transgura.ecs.component.components.Position;
import chae4ek.transgura.ecs.component.components.render.Sprite;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.render.RenderManager;
import com.badlogic.gdx.graphics.Texture;

public class StaticBlock extends Entity {

  public StaticBlock(
      final int id,
      final Position position,
      final Texture texture,
      final RenderManager renderManager) {
    super(id, position, new Sprite(true, new Position(), texture));
    renderManager.addSpriteEntity(this);
  }
}
