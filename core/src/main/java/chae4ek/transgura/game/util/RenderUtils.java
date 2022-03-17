package chae4ek.transgura.game.util;

import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class RenderUtils {

  public static void draw(
      final AtlasRegion atlasRegion,
      final float x,
      final float y,
      final boolean flipX,
      final boolean flipY) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    RenderManager.spriteBatch.draw(
        atlasRegion.getTexture(),
        x + atlasRegion.offsetX,
        y + atlasRegion.offsetY,
        width,
        height,
        width,
        height,
        2f,
        2f,
        0f,
        atlasRegion.getRegionX(),
        atlasRegion.getRegionY(),
        width,
        height,
        flipX,
        flipY);
  }
}
