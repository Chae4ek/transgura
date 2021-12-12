package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.util.render.RenderUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class AnimatedSprite extends RenderComponent {

  private final Animation<Sprite> animation;

  public AnimatedSprite(
      final float frameDurationInSec, final PlayMode playMode, final Sprite... spriteFrames) {
    animation = new Animation<>(frameDurationInSec, spriteFrames);
    animation.setPlayMode(playMode);
  }

  @Override
  public void draw() {
    final float time = scene.getSceneLifetimeInSec();
    for (final Entity parent : getParentEntities()) {
      RenderUtils.draw(parent.getComponent(Position.class), animation.getKeyFrame(time));
    }
  }
}
