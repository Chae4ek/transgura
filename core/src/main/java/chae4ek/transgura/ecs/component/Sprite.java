package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Matrix4;

public class Sprite extends RenderComponent {

  private static final SpriteBatch spriteBatch = new SpriteBatch();

  public final AtlasRegion atlasRegion;

  public Sprite(final boolean isEnabled, final AtlasRegion atlasRegion) {
    super(isEnabled);
    this.atlasRegion = atlasRegion;
  }

  @Override
  public void draw(final Matrix4 projection) {
    spriteBatch.setProjectionMatrix(projection);
    spriteBatch.begin();
    for (final Entity parent : getParentEntities()) {
      draw(parent.getComponent(Position.class), atlasRegion);
    }
    spriteBatch.end();
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
