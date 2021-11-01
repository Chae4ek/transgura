package chae4ek.transgura.ecs.entity;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class StaticBlock extends Entity {

  public StaticBlock(final Position position, final AtlasRegion atlasRegion) {
    addComponents(position, new Sprite(true, atlasRegion));
  }
}
