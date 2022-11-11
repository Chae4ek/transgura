package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.RenderComponent;
import chae4ek.transgura.ecs.system.PhysicalBody;
import chae4ek.transgura.util.RenderUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;

public class Sprite extends RenderComponent {

  private final AtlasRegion atlasRegion;
  public boolean flipX;
  public boolean flipY;
  public float angle;
  public float originPivotOffsetX;
  public float originPivotOffsetY;
  private PhysicalBody stickToThis;

  public Sprite(final AtlasRegion textureType) {
    atlasRegion = textureType;
  }

  public Sprite(final int zOrder, final AtlasRegion textureType) {
    super(zOrder);
    atlasRegion = textureType;
  }

  public Sprite(
      final int zOrder,
      final AtlasRegion textureType,
      final float originPivotOffsetX,
      final float originPivotOffsetY) {
    super(zOrder);
    atlasRegion = textureType;
    this.originPivotOffsetX = originPivotOffsetX;
    this.originPivotOffsetY = originPivotOffsetY;
  }

  /** This component will be drawn at the stickToThis body position instead of parent one */
  public void setStickToBody(final PhysicalBody stickToThis) {
    this.stickToThis = stickToThis;
  }

  @Override
  public void draw() {
    final Vector2 vec =
        stickToThis != null
            ? stickToThis.getPositionByPPM()
            : getParent().getComponent(Position.class).getVec();
    draw(vec.x, vec.y);
  }

  public void draw(final float x, final float y) {
    RenderUtils.draw(
        atlasRegion, x, y, flipX, flipY, angle, originPivotOffsetX, originPivotOffsetY);
  }
}
