package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.RenderComponent;
import chae4ek.transgura.util.ARAnimation;
import chae4ek.transgura.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimatedSprites extends RenderComponent {

  public boolean flipX;
  public boolean flipY;
  private ARAnimation animation;

  public AnimatedSprites(final int zOrder, final ARAnimation animation) {
    super(zOrder);
    this.animation = animation;
  }

  public void setAnimation(final ARAnimation animation) {
    this.animation = animation;
  }

  @Override
  public void draw() {
    final Position pos = getParent().getComponent(Position.class);
    final Vector2 vec = pos.getVec();
    final AtlasRegion atlasRegion = animation.getKeyFrame(Game.getScene().getSceneLifetimeInSec());
    RenderUtils.draw(atlasRegion, vec.x, vec.y, flipX, flipY, 0f, 0f, 0f);
  }
}
