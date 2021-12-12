package chae4ek.transgura.ecs.util.render;

import chae4ek.transgura.ecs.RenderManager;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class RenderUtils {

  /** Draw a sprite at the position */
  public static void draw(final Position position, final Sprite sprite) {
    final AtlasRegion atlasRegion = sprite.getAtlasRegion();
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    RenderManager.spriteBatch.draw(
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
