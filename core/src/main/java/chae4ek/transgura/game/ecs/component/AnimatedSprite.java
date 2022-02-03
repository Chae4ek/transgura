package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.engine.ecs.RenderManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimatedSprite extends RenderComponent {

  public boolean flipX;
  public boolean flipY;
  private Animation<AtlasRegion> animation;

  public AnimatedSprite(final int zOrder, final Animation<AtlasRegion> animation) {
    super(zOrder);
    this.animation = animation;
  }

  public Animation<AtlasRegion> getAnimation() {
    return animation;
  }

  public void setAnimation(final Animation<AtlasRegion> animation) {
    this.animation = animation;
  }

  @Override
  public void draw() {
    final Vector2 position = getParent().getComponent(Position.class).getVec();
    final AtlasRegion atlasRegion = animation.getKeyFrame(scene.getSceneLifetimeInSec());
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
