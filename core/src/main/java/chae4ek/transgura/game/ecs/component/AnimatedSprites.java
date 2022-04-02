package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.game.util.ARAnimation;
import chae4ek.transgura.game.util.RenderUtils;
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
    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    final AtlasRegion atlasRegion = animation.getKeyFrame(Game.getScene().getSceneLifetimeInSec());
    RenderUtils.draw(atlasRegion, pos.x, pos.y, flipX, flipY);
  }
}
