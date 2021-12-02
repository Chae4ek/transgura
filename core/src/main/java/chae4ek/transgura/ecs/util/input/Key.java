package chae4ek.transgura.ecs.util.input;

import com.badlogic.gdx.Input.Keys;
import java.util.HashMap;
import java.util.Map;

public enum Key {
  ESCAPE(Keys.ESCAPE),
  SPACE(Keys.SPACE),
  ENTER(Keys.ENTER),
  W(Keys.W),
  A(Keys.A),
  S(Keys.S),
  D(Keys.D);

  private static final Map<Integer, Key> keys = new HashMap<>(Key.values().length);

  static {
    for (final Key key : Key.values()) keys.put(key.keycode, key);
  }

  final int keycode;
  boolean isDown;
  boolean isJustDown;
  boolean isReleased = true;
  boolean isJustReleased;

  Key(final int keycode) {
    this.keycode = keycode;
  }

  public static Key getKey(final int keycode) {
    // TODO: switch or massive[Keys.MAX_KEYCODE]
    return keys.get(keycode);
  }
}
