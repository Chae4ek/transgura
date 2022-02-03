package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Sprite extends RenderComponent {

  protected final com.badlogic.gdx.graphics.g2d.Sprite sprite;

  public Sprite(final AtlasRegion atlasRegion) {
    sprite = new com.badlogic.gdx.graphics.g2d.Sprite(atlasRegion);
    sprite.setScale(2f);
    sprite.setOrigin(atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight());
  }

  public Sprite(final int zOrder, final AtlasRegion atlasRegion) {
    super(zOrder);
    sprite = new com.badlogic.gdx.graphics.g2d.Sprite(atlasRegion);
    sprite.setScale(2f);
  }

  public com.badlogic.gdx.graphics.g2d.Sprite getSprite() {
    return sprite;
  }

  @Override
  public void draw() {
    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    sprite.setPosition(pos.x, pos.y);
    sprite.draw(RenderManager.spriteBatch);
  }
}
