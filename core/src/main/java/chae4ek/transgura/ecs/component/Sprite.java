package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.Entity;
import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class Sprite extends RenderComponent {

  public final AtlasRegion atlasRegion;

  public Sprite(final AtlasRegion atlasRegion) {
    this.atlasRegion = atlasRegion;
  }

  @Override
  public void draw() {
    for (final Entity parent : getParentEntities()) {
      RenderUtils.drawDefault(parent.getComponent(Position.class), atlasRegion);
    }
  }
}
