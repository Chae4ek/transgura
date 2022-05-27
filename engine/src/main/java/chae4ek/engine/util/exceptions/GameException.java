package chae4ek.engine.util.exceptions;

public final class GameException extends RuntimeException {

  GameException(final String message) {
    super(message);
  }

  GameException(final String message, final Throwable e) {
    super(message, e);
  }
}
