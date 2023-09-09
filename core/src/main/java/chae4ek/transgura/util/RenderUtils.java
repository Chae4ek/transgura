package chae4ek.transgura.util;

import chae4ek.engine.ecs.RenderManager;
import chae4ek.engine.util.GameSettings;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class RenderUtils {

  public static void draw(
      final AtlasRegion atlasRegion,
      final float x,
      final float y,
      final boolean flipX,
      final boolean flipY,
      final float angle,
      final float originPivotOffsetX,
      final float originPivotOffsetY,
      final float scale,
      final boolean centered) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    final int halfWidth = centered ? width >> 1 : 0;
    final int halfHeight = centered ? height >> 1 : 0;
    RenderManager.spriteBatch.draw(
        atlasRegion.getTexture(),
        x * GameSettings.reverseRenderScale - halfWidth + atlasRegion.offsetX,
        y * GameSettings.reverseRenderScale - halfHeight + atlasRegion.offsetY,
        halfWidth + originPivotOffsetX,
        halfHeight + originPivotOffsetY,
        width,
        height,
        scale,
        scale,
        angle,
        atlasRegion.getRegionX(),
        atlasRegion.getRegionY(),
        width,
        height,
        flipX,
        flipY);
  }
}
