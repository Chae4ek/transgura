package chae4ek.transgura.ecs.util.render;

import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.util.resources.SpriteBatchType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import java.util.Collection;

public class RenderUtils {

  /** Default sprite batch for drawing */
  public static final SpriteBatch defaultSpriteBatch =
      ResourceLoader.loadSpriteBatch(SpriteBatchType.DEFAULT);

  /** @return the all loaded sprite batches */
  public static Collection<SpriteBatch> getSpriteBatches() {
    return ResourceLoader.getSpriteBatches();
  }

  /** Draw a sprite at the position with {@link #defaultSpriteBatch} */
  public static void drawDefault(final Position position, final Sprite sprite) {
    final AtlasRegion atlasRegion = sprite.getAtlasRegion();
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    defaultSpriteBatch.draw(
        atlasRegion.getTexture(),
        position.getVec().x + atlasRegion.offsetX,
        position.getVec().y + atlasRegion.offsetY,
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
        sprite.flipX,
        sprite.flipY);
  }
}
