package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Sprite extends RenderComponent {

  public final AtlasRegion atlasRegion;
  public boolean flipX;
  public boolean flipY;

  public Sprite(final AtlasRegion atlasRegion) {
    this.atlasRegion = atlasRegion;
  }

  public Sprite(final int zOrder, final AtlasRegion atlasRegion) {
    super(zOrder);
    this.atlasRegion = atlasRegion;
  }

  public Sprite(final AtlasRegion atlasRegion, final boolean flipX, final boolean flipY) {
    this.atlasRegion = atlasRegion;
    this.flipX = flipX;
    this.flipY = flipY;
  }

  @Override
  public void draw() {
    for (final Entity parent : getParentEntities()) {
      RenderUtils.drawDefault(parent.getComponent(Position.class), this);
    }
  }
}
