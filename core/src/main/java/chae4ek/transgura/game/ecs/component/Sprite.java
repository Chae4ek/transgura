package chae4ek.transgura.game.ecs.component;

import chae4ek.transgura.engine.ecs.RenderComponent;
import chae4ek.transgura.game.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Sprite extends RenderComponent {

  private final AtlasRegion atlasRegion;
  public boolean flipX;
  public boolean flipY;

  public Sprite(final AtlasRegion textureType) {
    atlasRegion = textureType;
  }

  public Sprite(final int zOrder, final AtlasRegion textureType) {
    super(zOrder);
    atlasRegion = textureType;
  }

  @Override
  public void draw() {
    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    draw(pos.x, pos.y);
  }

  public void draw(final float x, final float y) {
    RenderUtils.draw(atlasRegion, x, y, flipX, flipY);
  }
}
