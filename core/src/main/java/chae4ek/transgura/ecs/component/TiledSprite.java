package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.util.render.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class TiledSprite extends Sprite {

  private final int countX;
  private final int countY;
  private final float scaledWidth;
  private final float scaledHeight;

  public TiledSprite(final AtlasRegion atlasRegion, final int countX, final int countY) {
    super(atlasRegion);
    this.countX = countX;
    this.countY = countY;
    scaledWidth = atlasRegion.getRegionWidth() * 2f;
    scaledHeight = atlasRegion.getRegionHeight() * 2f;
  }

  @Override
  public void draw() {
    final Vector2 parentVec = getParent().getComponent(Position.class).getVec();
    final Vector2 pos = new Vector2();
    pos.x = parentVec.x;
    for (int x = 0; x < countX; ++x) {
      pos.y = parentVec.y;
      for (int y = 0; y < countY; ++y) {
        RenderUtils.draw(pos, atlasRegion, flipX, flipY);
        pos.y += scaledHeight;
      }
      pos.x += scaledWidth;
    }
  }
}
