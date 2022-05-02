package chae4ek.transgura.engine.ecs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.ObjectSet;

public final class InputProcessor implements com.badlogic.gdx.InputProcessor {

  private static final InputProcessor instance = new InputProcessor();

  private static final Key anyKey = new Key();
  private static final Key[] keys = new Key[Keys.MAX_KEYCODE + 1]; // without Keys.ANY_KEY
  private static final Button[] buttons = new Button[5];

  private static final ObjectSet<Key> keysThatJustChanged = new ObjectSet<>();
  private static final ObjectSet<Button> buttonsThatJustChanged = new ObjectSet<>();

  private static float justScrolledX;
  private static float justScrolledY;

  static {
    for (int i = 0; i < keys.length; ++i) keys[i] = new Key();
    for (int i = 0; i < buttons.length; ++i) buttons[i] = new Button();
  }

  private InputProcessor() {}

  /** Initialize the input processor for {@link Gdx#input} */
  static void init() {
    Gdx.input.setInputProcessor(instance);
  }

  /** Update keys that "just" changed */
  static void postUpdate() {
    for (final Key key : keysThatJustChanged) {
      key.isJustDown = key.isJustReleased = false;
    }
    for (final Button button : buttonsThatJustChanged) {
      button.isJustDown = button.isJustReleased = false;
    }
    keysThatJustChanged.clear();
    buttonsThatJustChanged.clear();
    justScrolledX = justScrolledY = 0f;
  }

  /**
   * It will return scrolled value once, in the next update frames it will return 0
   *
   * @return scrolled value if the mouse wheel is just scrolling horizontally right now
   */
  public static float getJustScrolledX() {
    return justScrolledX;
  }

  /**
   * It will return scrolled value once, in the next update frames it will return 0
   *
   * @return scrolled value if the mouse wheel is just scrolling vertically right now
   */
  public static float getJustScrolledY() {
    return justScrolledY;
  }

  /**
   * @return true if a key is down
   */
  public static boolean isKeyDown(final int key) {
    return key == -1 ? anyKey.isDown : keys[key].isDown;
  }

  /**
   * @return true if a button is down
   */
  public static boolean isButtonDown(final int button) {
    return buttons[button].isDown;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just down right now
   */
  public static boolean isKeyJustDownNow(final int key) {
    return key == -1 ? anyKey.isJustDown : keys[key].isJustDown;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a button is just down right now
   */
  public static boolean isButtonJustDownNow(final int button) {
    return buttons[button].isJustDown;
  }

  /**
   * @return true if a key is released
   */
  public static boolean isKeyReleased(final int key) {
    return key == -1 ? anyKey.isReleased : keys[key].isReleased;
  }

  /**
   * @return true if a button is released
   */
  public static boolean isButtonReleased(final int button) {
    return buttons[button].isReleased;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just released right now
   */
  public static boolean isKeyJustReleasedNow(final int key) {
    return key == -1 ? anyKey.isJustReleased : keys[key].isJustReleased;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a button is just released right now
   */
  public static boolean isButtonJustReleasedNow(final int button) {
    return buttons[button].isJustReleased;
  }

  public static Key getKey(final int key) {
    return key == -1 ? anyKey : keys[key];
  }

  public static Button getButton(final int button) {
    return buttons[button];
  }

  @Override
  public boolean keyDown(final int keycode) {
    final Key key = keycode == -1 ? anyKey : keys[keycode];
    key.isDown = key.isJustDown = true;
    key.isReleased = key.isJustReleased = false;
    keysThatJustChanged.add(key);
    return true;
  }

  @Override
  public boolean keyUp(final int keycode) {
    final Key key = keycode == -1 ? anyKey : keys[keycode];
    key.isDown = key.isJustDown = false;
    key.isReleased = key.isJustReleased = true;
    keysThatJustChanged.add(key);
    return true;
  }

  @Override
  public boolean keyTyped(final char character) {
    return true;
  }

  @Override
  public boolean touchDown(
      final int screenX, final int screenY, final int pointer, final int buttonCode) {
    final Button button = buttons[buttonCode];
    button.isDown = button.isJustDown = true;
    button.isReleased = button.isJustReleased = false;
    buttonsThatJustChanged.add(button);
    button.screenX = screenX;
    button.screenY = screenY;
    button.pointer = pointer;
    return true;
  }

  @Override
  public boolean touchUp(
      final int screenX, final int screenY, final int pointer, final int buttonCode) {
    final Button button = buttons[buttonCode];
    button.isDown = button.isJustDown = false;
    button.isReleased = button.isJustReleased = true;
    buttonsThatJustChanged.add(button);
    button.screenX = screenX;
    button.screenY = screenY;
    button.pointer = pointer;
    return true;
  }

  @Override
  public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
    return true;
  }

  @Override
  public boolean mouseMoved(final int screenX, final int screenY) {
    return true;
  }

  @Override
  public boolean scrolled(final float amountX, final float amountY) {
    justScrolledX = amountX;
    justScrolledY = amountY;
    return true;
  }

  public static class Key {
    private boolean isDown;
    private boolean isJustDown;
    private boolean isReleased = true;
    private boolean isJustReleased;

    public boolean isDown() {
      return isDown;
    }

    public boolean isJustDown() {
      return isJustDown;
    }

    public boolean isReleased() {
      return isReleased;
    }

    public boolean isJustReleased() {
      return isJustReleased;
    }
  }

  public static class Button {
    private boolean isDown;
    private boolean isJustDown;
    private boolean isReleased = true;
    private boolean isJustReleased;

    // the last click (press down or release)
    private int screenX;
    private int screenY;
    private int pointer;

    public boolean isDown() {
      return isDown;
    }

    public boolean isJustDown() {
      return isJustDown;
    }

    public boolean isReleased() {
      return isReleased;
    }

    public boolean isJustReleased() {
      return isJustReleased;
    }

    public int getScreenX() {
      return screenX;
    }

    public int getScreenY() {
      return screenY;
    }

    public int getPointer() {
      return pointer;
    }
  }
}
