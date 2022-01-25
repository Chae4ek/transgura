package chae4ek.transgura.ecs.util.input;

import com.badlogic.gdx.Input.Buttons;

public enum Button {
  LEFT(Buttons.LEFT),
  RIGHT(Buttons.RIGHT),
  MIDDLE(Buttons.MIDDLE),
  BACK(Buttons.BACK),
  FORWARD(Buttons.FORWARD);

  final int buttonCode;
  boolean isDown;
  boolean isJustDown;
  boolean isReleased = true;
  boolean isJustReleased;

  // the last click
  int screenX;
  int screenY;
  int pointer;

  Button(final int buttonCode) {
    this.buttonCode = buttonCode;
  }

  public static Button getButton(final int buttonCode) {
    return switch (buttonCode) {
      case Buttons.LEFT -> LEFT;
      case Buttons.RIGHT -> RIGHT;
      case Buttons.MIDDLE -> MIDDLE;
      case Buttons.BACK -> BACK;
      case Buttons.FORWARD -> FORWARD;
      default -> throw new IllegalStateException();
    };
  }
}
