package chae4ek.transgura.ecs.entity;

import chae4ek.engine.ecs.Entity;
import chae4ek.transgura.ecs.component.AnimatedSprites;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.component.TiledSprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class PhantomBlock extends Entity {

  public PhantomBlock(final float x, final float y, final AtlasRegion texture) {
    super(new Position(x, y), new Sprite(texture));
  }

  public PhantomBlock(final float x, final float y, final AnimatedSprites animatedSprites) {
    super(new Position(x, y), animatedSprites);
  }

  public PhantomBlock(
      final float x,
      final float y,
      final int countX,
      final int countY,
      final AtlasRegion texture,
      final int zOrder) {
    super(new Position(x, y), new TiledSprite(zOrder, texture, countX, countY));
  }
}
