package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.RenderManager;
import chae4ek.engine.util.GameSettings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class TextCoinCount extends Text {

  public int coinCount;
  public static int coinMax;

  private static final BitmapFont font;

  static {
    final FreeTypeFontGenerator generator =
        new FreeTypeFontGenerator(Gdx.files.internal("fonts/blrrpix.strict.ttf"));
    final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = 42;
    parameter.color = new Color(1, 1, 0, 1);
    parameter.borderColor = new Color(0, 0, 0, 1);
    parameter.borderWidth = 1;
    font = generator.generateFont(parameter);
    generator.dispose();
  }

  public TextCoinCount() {
    super(GameSettings.zOrderForUIRendering + 100, "Coins: 0 / 0", 8, -10);
    isDrawn = true;
  }

  @Override
  public void draw() {
    if (!isDrawn) return;
    RenderManager.spriteBatch.end();
    RenderManager.hudBatch.begin();
    font.draw(
        RenderManager.hudBatch,
        "Coins: " + coinCount + " / " + coinMax,
        xOffset,
        Gdx.graphics.getHeight() + yOffset);
    RenderManager.hudBatch.end();
    RenderManager.spriteBatch.begin();
  }
}
