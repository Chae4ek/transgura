package chae4ek.transgura.engine.util.exceptions;

import static chae4ek.transgura.engine.util.GameSettings.isWARNStackTraceOn;
import static chae4ek.transgura.engine.util.GameSettings.isWARNThrowOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Using for exceptions and logging */
public final class GameAlert {

  private final Logger logger;

  public GameAlert(final Class<?> classForLog) {
    logger = LoggerFactory.getLogger(classForLog);
  }

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String message) throws GameException {
    logger.error(message);
    logErrorThrow(new GameException(message));
  }

  /**
   * @throws GameException always, but after log with ERROR level
   */
  public void error(final String format, final Object arg) throws GameException {
    logger.error(format, arg);
    logErrorThrow(new GameException(format));
  }

  /**
   * @throws GameException always, but after log with ERROR level
   */
  public void error(final String format, final Object arg1, final Object arg2)
      throws GameException {
    logger.error(format, arg1, arg2);
    logErrorThrow(new GameException(format));
  }

  /**
   * @throws GameException always, but after log with ERROR level
   */
  public void error(final String format, final Object... args) throws GameException {
    logger.error(format, args);
    logErrorThrow(new GameException(format));
  }

  /**
   * @throws GameException always, but after log with ERROR level
   */
  public void error(final String format, final Throwable arg) throws GameException {
    logger.error(format, arg);
    logErrorThrow(new GameException(arg));
  }

  /** Log at the WARN level */
  public void warn(final String message) {
    logger.warn(message);
    logWarnThrow(message);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object arg) {
    logger.warn(format, arg);
    logWarnThrow(format);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object arg1, final Object arg2) {
    logger.warn(format, arg1, arg2);
    logWarnThrow(format);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object... args) {
    logger.warn(format, args);
    logWarnThrow(format);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Throwable arg) {
    logger.warn(format, arg);
    logWarnThrow(arg);
  }

  private void logErrorThrow(final GameException e) throws GameException {
    logger.error(getStackTraceToString(e.getStackTrace()));
    throw e;
  }

  private void logWarnThrow(final String message) throws GameException {
    if (isWARNThrowOn) {
      final GameException e = new GameException(message);
      if (isWARNStackTraceOn) logger.warn(getStackTraceToString(e.getStackTrace()));
      throw e;
    }
    if (isWARNStackTraceOn) {
      logger.warn(getStackTraceToString(Thread.currentThread().getStackTrace()));
    }
  }

  private void logWarnThrow(final Throwable error) throws GameException {
    if (isWARNThrowOn) {
      final GameException e = new GameException(error);
      if (isWARNStackTraceOn) logger.warn(getStackTraceToString(e.getStackTrace()));
      throw e;
    }
    if (isWARNStackTraceOn) {
      logger.warn(getStackTraceToString(error.getStackTrace()));
    }
  }

  private String getStackTraceToString(final StackTraceElement[] stackTrace) {
    final StringBuilder sb = new StringBuilder();
    for (final StackTraceElement element : stackTrace) {
      sb.append(element.toString()).append(System.lineSeparator());
    }
    return sb.toString();
  }

  /** Log at the DEBUG level */
  public void debug(final String message) {
    logger.debug(message);
  }

  /** Log at the DEBUG level */
  public void debug(final String format, final Object arg) {
    logger.debug(format, arg);
  }

  /** Log at the DEBUG level */
  public void debug(final String format, final Object arg1, final Object arg2) {
    logger.debug(format, arg1, arg2);
  }

  /** Log at the DEBUG level */
  public void debug(final String format, final Object... args) {
    logger.debug(format, args);
  }

  /** Log at the DEBUG level */
  public void debug(final String format, final Throwable arg) {
    logger.debug(format, arg);
  }
}
