package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class TiledSprite extends Sprite {

  private final int countX;
  private final int countY;
  private final int scaledWidth;
  private final int scaledHeight;

  public TiledSprite(final AtlasRegion atlasRegion, final int countX, final int countY) {
    super(atlasRegion);
    this.countX = countX;
    this.countY = countY;
    scaledWidth = atlasRegion.getRegionWidth() * 2;
    scaledHeight = atlasRegion.getRegionHeight() * 2;
  }

  @Override
  public void draw() {
    final Vector2 parentVec = getParent().getComponent(Position.class).getVec();
    final int parentPosY = (int) parentVec.y;
    int posX = (int) parentVec.x, posY;
    for (int x = 0; x < countX; ++x) {
      posY = parentPosY;
      for (int y = 0; y < countY; ++y) {
        sprite.setPosition(posX, posY);
        sprite.draw(RenderManager.spriteBatch);
        posY += scaledHeight;
      }
      posX += scaledWidth;
    }
  }
}
