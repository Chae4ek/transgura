package chae4ek.transgura.ecs.util.input;

import com.badlogic.gdx.Gdx;
import java.util.EnumSet;
import java.util.Set;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {

  private static final InputProcessor instance = new InputProcessor();

  private static final Set<Key> keysThatJustChanged = EnumSet.noneOf(Key.class);
  private static final Set<Button> buttonsThatJustChanged = EnumSet.noneOf(Button.class);

  private InputProcessor() {}

  /** Initialize the input processor for {@link Gdx#input} */
  public static void init() {
    Gdx.input.setInputProcessor(instance);
  }

  /** Update keys that "just" changed */
  public static void postUpdate() {
    for (final Key key : keysThatJustChanged) {
      key.isJustDown = key.isJustReleased = false;
    }
    for (final Button button : buttonsThatJustChanged) {
      button.isJustDown = button.isJustReleased = false;
    }
    keysThatJustChanged.clear();
    buttonsThatJustChanged.clear();
  }

  /** @return true if a key is down */
  public static boolean isKeyDown(final Key key) {
    return key.isDown;
  }

  /** @return true if a button is down */
  public static boolean isButtonDown(final Button button) {
    return button.isDown;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just down right now
   */
  public static boolean isKeyJustDownNow(final Key key) {
    return key.isJustDown;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a button is just down right now
   */
  public static boolean isButtonJustDownNow(final Button button) {
    return button.isJustDown;
  }

  /** @return true if a key is released */
  public static boolean isKeyReleased(final Key key) {
    return key.isReleased;
  }

  /** @return true if a button is released */
  public static boolean isButtonReleased(final Button button) {
    return button.isReleased;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just released right now
   */
  public static boolean isKeyJustReleasedNow(final Key key) {
    return key.isJustReleased;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a button is just released right now
   */
  public static boolean isButtonJustReleasedNow(final Button button) {
    return button.isJustReleased;
  }

  @Override
  public boolean keyDown(final int keycode) {
    final Key key = Key.getKey(keycode);
    if (key != null) {
      key.isDown = key.isJustDown = true;
      key.isReleased = key.isJustReleased = false;
      keysThatJustChanged.add(key);
      return true;
    }
    return false;
  }

  @Override
  public boolean keyUp(final int keycode) {
    final Key key = Key.getKey(keycode);
    if (key != null) {
      key.isDown = key.isJustDown = false;
      key.isReleased = key.isJustReleased = true;
      keysThatJustChanged.add(key);
      return true;
    }
    return false;
  }

  @Override
  public boolean keyTyped(final char character) {
    return false;
  }

  @Override
  public boolean touchDown(
      final int screenX, final int screenY, final int pointer, final int buttonCode) {
    final Button button = Button.getButton(buttonCode);
    if (button != null) {
      button.isDown = button.isJustDown = true;
      button.isReleased = button.isJustReleased = false;
      buttonsThatJustChanged.add(button);
      button.screenX = screenX;
      button.screenY = screenY;
      button.pointer = pointer;
      return true;
    }
    return false;
  }

  @Override
  public boolean touchUp(
      final int screenX, final int screenY, final int pointer, final int buttonCode) {
    final Button button = Button.getButton(buttonCode);
    if (button != null) {
      button.isDown = button.isJustDown = false;
      button.isReleased = button.isJustReleased = true;
      buttonsThatJustChanged.add(button);
      button.screenX = screenX;
      button.screenY = screenY;
      button.pointer = pointer;
      return true;
    }
    return false;
  }

  @Override
  public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
    return false;
  }

  @Override
  public boolean mouseMoved(final int screenX, final int screenY) {
    return false;
  }

  @Override
  public boolean scrolled(final float amountX, final float amountY) {
    return false;
  }
}
