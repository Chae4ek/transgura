package chae4ek.transgura.ecs.util.render;

import chae4ek.transgura.ecs.RenderManager;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.component.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class RenderUtils {

  /** Draw a sprite at the position */
  public static void draw(final Position position, final Sprite sprite) {
    draw(position.getVec(), sprite.getAtlasRegion(), sprite.flipX, sprite.flipY);
  }

  /** Draw an atlas region at the position */
  public static void draw(
      final Vector2 position,
      final AtlasRegion atlasRegion,
      final boolean flipX,
      final boolean flipY) {
    final int width = atlasRegion.getRegionWidth();
    final int height = atlasRegion.getRegionHeight();
    RenderManager.spriteBatch.draw(
        atlasRegion.getTexture(),
        position.x + atlasRegion.offsetX,
        position.y + atlasRegion.offsetY,
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
