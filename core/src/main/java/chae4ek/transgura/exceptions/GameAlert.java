package chae4ek.transgura.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Using for exceptions and logging */
public class GameAlert {

  private final Logger logger;

  public GameAlert(final Class<?> classForLog) {
    logger = LoggerFactory.getLogger(classForLog);
  }

  /** @throws GameException always, but after log with ERROR level */
  public void error(final GameErrorType type, final String debugInfo) throws GameException {
    logger.error("error: [{}]; debug info: [{}]", type.toString(), debugInfo);
    throw new GameException(type);
  }

  /** @throws GameException always, but after log with ERROR level */
  public void error(final GameErrorType type, final String debugInfo, final Throwable cause)
      throws GameException {
    logger.error(
        "error: [{}]; debug info: [{}]; cause:\n\n{}",
        type.toString(),
        debugInfo,
        cause.getMessage());
    throw new GameException(type, cause);
  }

  /** Log with WARN level */
  public void warn(final GameErrorType type, final String debugInfo) {
    logger.warn("warn: [{}]; debug info: [{}]", type.toString(), debugInfo);
  }
}
