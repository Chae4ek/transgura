package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.util.GameSettings;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class TiledSprite extends Sprite {

  private final int countX;
  private final int countY;
  private final float scaledWidth;
  private final float scaledHeight;

  public TiledSprite(
      final int zOrder, final AtlasRegion atlasRegion, final int countX, final int countY) {
    super(zOrder, atlasRegion);
    this.countX = countX;
    this.countY = countY;
    scaledWidth = atlasRegion.getRegionWidth() * GameSettings.renderScale;
    scaledHeight = atlasRegion.getRegionHeight() * GameSettings.renderScale;
  }

  public TiledSprite(final AtlasRegion atlasRegion, final int countX, final int countY) {
    this(0, atlasRegion, countX, countY);
  }

  @Override
  public void draw() {
    final Position pos = getParent().getComponent(Position.class);
    final Vector2 parentVec = pos.getVec();
    for (int x = 0; x < countX; ++x) {
      final float posX = parentVec.x + x * scaledWidth;
      for (int y = 0; y < countY; ++y) {
        final float posY = parentVec.y + y * scaledHeight;
        draw(posX, posY);
      }
    }
  }
}
