package chae4ek.transgura.ecs.util.input;

import com.badlogic.gdx.Gdx;
import java.util.EnumSet;
import java.util.Set;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {

  private static final InputProcessor instance = new InputProcessor();

  private static final Set<Key> keysToJustRelease = EnumSet.noneOf(Key.class);

  private InputProcessor() {}

  /** Initialize the input processor for {@link Gdx#input} */
  public static void init() {
    Gdx.input.setInputProcessor(instance);
  }

  /** Update keys that "just" changed */
  public static void postUpdate() {
    for (final Key key : keysToJustRelease) {
      key.isJustReleased = false;
    }
    keysToJustRelease.clear();
  }

  /** @return true if a key is down */
  public static boolean isKeyDown(final Key key) {
    return key.isDown;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just down right now
   * @deprecated it's not recommended to use cause it doesn't work sometimes. TODO: fix
   */
  @Deprecated
  public static boolean isKeyJustDownNow(final Key key) {
    return Gdx.input.isKeyJustPressed(key.keycode);
  }

  /** @return true if a key is released */
  public static boolean isKeyReleased(final Key key) {
    return key.isReleased;
  }

  /**
   * It will return true once, in the next update frames it will return false
   *
   * @return true if a key is just released right now
   */
  public static boolean isKeyJustReleasedNow(final Key key) {
    return key.isJustReleased;
  }

  @Override
  public boolean keyDown(final int keycode) {
    final Key key = Key.getKey(keycode);
    if (key != null) {
      key.isDown = true;
      key.isReleased = false;
      if (keysToJustRelease.remove(key)) key.isJustReleased = false;
      return true;
    }
    return false;
  }

  @Override
  public boolean keyUp(final int keycode) {
    final Key key = Key.getKey(keycode);
    if (key != null) {
      key.isDown = false;
      key.isReleased = key.isJustReleased = true;
      keysToJustRelease.add(key);
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
      final int screenX, final int screenY, final int pointer, final int button) {
    return false;
  }

  @Override
  public boolean touchUp(
      final int screenX, final int screenY, final int pointer, final int button) {
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
