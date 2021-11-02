package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.render.ResourceLoader;
import chae4ek.transgura.render.resources.SpriteBatchType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Sprite extends RenderComponent {

  private static final SpriteBatch spriteBatch =
      ResourceLoader.loadSpriteBatch(SpriteBatchType.DEFAULT);

  public final AtlasRegion atlasRegion;

  public Sprite(final boolean isEnabled, final AtlasRegion atlasRegion) {
    super(isEnabled);
    this.atlasRegion = atlasRegion;
  }

  @Override
  public void draw() {
    for (final Entity parent : getParentEntities()) {
      draw(parent.getComponent(Position.class), atlasRegion);
    }
  }

  /** Draw an atlasRegion at the position */
  private void draw(final Position position, final AtlasRegion atlasRegion) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    spriteBatch.draw(
        atlasRegion.getTexture(),
        position.x + atlasRegion.offsetX,
        position.y + atlasRegion.offsetY,
        0,
        0,
        width,
        height,
        2,
        2,
        0,
        atlasRegion.getRegionX(),
        atlasRegion.getRegionY(),
        width,
        height,
        false,
        false);
  }
}
