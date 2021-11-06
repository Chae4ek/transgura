package chae4ek.transgura.ecs.util;

import chae4ek.transgura.ecs.component.Position;
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

  /** Draw an atlasRegion at the position with {@link #defaultSpriteBatch} */
  public static void drawDefault(final Position position, final AtlasRegion atlasRegion) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    defaultSpriteBatch.draw(
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
