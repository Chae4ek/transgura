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
    final Position parentPos = getParent().getComponent(Position.class);
    final Vector2 parentVec = parentPos.getVec();
    final Position pos = new Position(parentPos);
    final Vector2 vec = pos.getVec();
    for (int x = 0; x < countX; ++x) {
      vec.x = parentVec.x + x * scaledWidth;
      for (int y = 0; y < countY; ++y) {
        vec.y = parentVec.y + y * scaledHeight;
        RenderUtils.draw(pos, atlasRegion, flipX, flipY);
      }
    }
  }
}
