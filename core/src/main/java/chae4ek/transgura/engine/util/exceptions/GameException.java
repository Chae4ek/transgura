package chae4ek.transgura.engine.util.exceptions;

public final class GameException extends RuntimeException {

  GameException(final String message) {
    super(message);
  }

  GameException(final String message, final Throwable e) {
    super(message, e);
  }
}
