package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AnimatedSprite extends RenderComponent {

  public final Animation<AtlasRegion> animation;

  public AnimatedSprite(
      final float frameDurationInSec, final PlayMode playMode, final AtlasRegion... textureFrames) {
    animation = new Animation<>(frameDurationInSec, textureFrames);
    animation.setPlayMode(playMode);
  }

  @Override
  public void draw() {
    final float time = scene.getSceneLifetimeInSec();
    for (final Entity parent : getParentEntities()) {
      RenderUtils.drawDefault(parent.getComponent(Position.class), animation.getKeyFrame(time));
    }
  }
}
