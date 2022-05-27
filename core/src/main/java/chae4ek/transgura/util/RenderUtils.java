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
      final float originPivotOffsetY) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    final int halfWidth = width >> 1;
    final int halfHeight = height >> 1;
    RenderManager.spriteBatch.draw(
        atlasRegion.getTexture(),
        x * GameSettings.reverseRenderScale - halfWidth + atlasRegion.offsetX,
        y * GameSettings.reverseRenderScale - halfHeight + atlasRegion.offsetY,
        halfWidth + originPivotOffsetX,
        halfHeight + originPivotOffsetY,
        width,
        height,
        1f,
        1f,
        angle,
        atlasRegion.getRegionX(),
        atlasRegion.getRegionY(),
        width,
        height,
        flipX,
        flipY);
  }
}
