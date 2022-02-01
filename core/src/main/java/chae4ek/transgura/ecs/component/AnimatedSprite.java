package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.util.render.RenderUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class AnimatedSprite extends RenderComponent {

  public boolean flipX;
  public boolean flipY;
  private Animation<AtlasRegion> animation;

  @Deprecated
  public AnimatedSprite(
      final float frameDurationInSec, final PlayMode playMode, final AtlasRegion... frames) {
    animation = new Animation<>(frameDurationInSec, frames);
    animation.setPlayMode(playMode);
  }

  public AnimatedSprite(final int zOrder, final Animation<AtlasRegion> animation) {
    super(zOrder);
    this.animation = animation;
  }

  public AnimatedSprite(
      final int zOrder,
      final float frameDurationInSec,
      final PlayMode playMode,
      final Array<AtlasRegion> frames) {
    super(zOrder);
    animation = new Animation<>(frameDurationInSec, frames);
    animation.setPlayMode(playMode);
  }

  public Animation<AtlasRegion> getAnimation() {
    return animation;
  }

  public void setAnimation(final Animation<AtlasRegion> animation) {
    this.animation = animation;
  }

  @Override
  public void draw() {
    final float time = scene.getSceneLifetimeInSec();
    RenderUtils.draw(
        getParent().getComponent(Position.class).getVec(),
        animation.getKeyFrame(time),
        flipX,
        flipY);
  }
}
