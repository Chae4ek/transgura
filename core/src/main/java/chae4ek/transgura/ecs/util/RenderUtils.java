package chae4ek.transgura.ecs.util;

import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import chae4ek.transgura.ecs.util.resources.SpriteBatchType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    final int width = sprite.atlasRegion.getRegionWidth();
    final int height = sprite.atlasRegion.getRegionHeight();
    defaultSpriteBatch.draw(
        sprite.atlasRegion.getTexture(),
        position.x + sprite.atlasRegion.offsetX,
        position.y + sprite.atlasRegion.offsetY,
        0,
        0,
        width,
        height,
        2,
        2,
        0,
        sprite.atlasRegion.getRegionX(),
        sprite.atlasRegion.getRegionY(),
        width,
        height,
        sprite.flipX,
        sprite.flipY);
  }
}
