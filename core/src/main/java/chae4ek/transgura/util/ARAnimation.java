package chae4ek.transgura.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class ARAnimation extends Animation<AtlasRegion> {

  public ARAnimation(final float frameDuration, final Array<? extends AtlasRegion> keyFrames) {
    super(frameDuration, keyFrames);
  }

  public ARAnimation(
      final float frameDuration,
      final Array<? extends AtlasRegion> keyFrames,
      final PlayMode playMode) {
    super(frameDuration, keyFrames, playMode);
  }

  public ARAnimation(final float frameDuration, final AtlasRegion... keyFrames) {
    super(frameDuration, keyFrames);
  }
}
