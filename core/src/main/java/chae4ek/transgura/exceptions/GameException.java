package chae4ek.transgura.exceptions;

public class GameException extends RuntimeException {

  protected GameException(final GameErrorType error) {
    super(error.toString());
  }
}
