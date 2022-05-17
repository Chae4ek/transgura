package chae4ek.transgura.game.util;

import chae4ek.transgura.engine.ecs.RenderManager;
import chae4ek.transgura.engine.util.GameSettings;
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
        x / GameSettings.renderScale - (float) (width / 2) + atlasRegion.offsetX,
        y / GameSettings.renderScale - (float) (height / 2) + atlasRegion.offsetY,
        width,
        height,
        atlasRegion.getRegionX(),
        atlasRegion.getRegionY(),
        width,
        height,
        flipX,
        flipY);
  }
}
