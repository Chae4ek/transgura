package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.RenderComponent;
import chae4ek.engine.ecs.RenderManager;
import chae4ek.transgura.ecs.system.PhysicalBody;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class Text extends RenderComponent {

  private static final BitmapFont font;

  static {
    final FreeTypeFontGenerator generator =
        new FreeTypeFontGenerator(Gdx.files.internal("fonts/blrrpix.strict.ttf"));
    final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = 12;
    parameter.color = new Color(1, 1, 0, 1);
    parameter.borderColor = new Color(0, 0, 0, 1);
    parameter.borderWidth = 1;
    font = generator.generateFont(parameter);
    generator.dispose();
  }

  public boolean isDrawn;
  public String text;
  public float xOffset;
  public float yOffset;
  private PhysicalBody stickToThis;

  public Text(final int zOrder, final String text, final float xOffset, final float yOffset) {
    super(zOrder);
    this.text = text;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  /** This component will be drawn at the stickToThis body position instead of parent one */
  public void setStickToBody(final PhysicalBody stickToThis) {
    this.stickToThis = stickToThis;
  }

  @Override
  public void draw() {
    if (!isDrawn) return;
    final Vector2 vec =
        stickToThis != null
            ? stickToThis.getPositionByPPM().scl(0.5f)
            : getParent().getComponent(Position.class).getVec();
    font.draw(RenderManager.spriteBatch, text, vec.x + xOffset, vec.y + yOffset);
  }
}
