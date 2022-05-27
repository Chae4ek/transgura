package chae4ek.engine.util.exceptions;

import static chae4ek.engine.util.GameSettings.isWARNStackTraceOn;
import static chae4ek.engine.util.GameSettings.isWARNThrowOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Using for exceptions and logging */
public final class GameAlert {

  private final Logger logger;

  public GameAlert(final Class<?> classForLog) {
    logger = LoggerFactory.getLogger(classForLog);
  }

  /* -------------------------- ERROR level -------------------------- */

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String message) throws GameException {
    error0(message);
    logStacktraceErrorAndThrow(message);
  }

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String format, final Object arg) throws GameException {
    error0(format, arg);
    logStacktraceErrorAndThrow(null);
  }

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String format, final Object arg1, final Object arg2)
      throws GameException {
    error0(format, arg1, arg2);
    logStacktraceErrorAndThrow(null);
  }

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String format, final Object... args) throws GameException {
    error0(format, args);
    logStacktraceErrorAndThrow(null);
  }

  /**
   * @throws GameException always, but after log at the ERROR level
   */
  public void error(final String message, final Throwable arg) throws GameException {
    error0(message, arg);
    throw new GameException(message, arg);
  }

  private void logStacktraceErrorAndThrow(final String message) throws GameException {
    final GameException e = new GameException(message);
    logger.error("Stacktrace:", e);
    throw e;
  }

  /* -------------------------- ERROR-0 level (without throwing) -------------------------- */

  /** Log at the ERROR level without throwing an exception */
  public void error0(final String message) {
    logger.error(message);
  }

  /** Log at the ERROR level without throwing an exception */
  public void error0(final String format, final Object arg) {
    logger.error(format, arg);
  }

  /** Log at the ERROR level without throwing an exception */
  public void error0(final String format, final Object arg1, final Object arg2) {
    logger.error(format, arg1, arg2);
  }

  /** Log at the ERROR level without throwing an exception */
  public void error0(final String format, final Object... args) {
    logger.error(format, args);
  }

  /** Log at the ERROR level without throwing an exception */
  public void error0(final String format, final Throwable arg) {
    logger.error(format, arg);
  }

  /* -------------------------- WARN level -------------------------- */

  /** Log at the WARN level */
  public void warn(final String message) {
    logger.warn(message);
    logStacktraceWarnAndThrow(message);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object arg) {
    logger.warn(format, arg);
    logStacktraceWarnAndThrow(null);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object arg1, final Object arg2) {
    logger.warn(format, arg1, arg2);
    logStacktraceWarnAndThrow(null);
  }

  /** Log at the WARN level */
  public void warn(final String format, final Object... args) {
    logger.warn(format, args);
    logStacktraceWarnAndThrow(null);
  }

  /** Log at the WARN level */
  public void warn(final String message, final Throwable arg) {
    logger.warn(message, arg);
    if (isWARNThrowOn) throw new GameException(message, arg);
  }

  private void logStacktraceWarnAndThrow(final String message) {
    if (isWARNThrowOn) {
      final GameException e = new GameException(message);
      logger.warn("Stacktrace:", e);
      throw e;
    }
    if (isWARNStackTraceOn) logger.warn("Stacktrace:", new GameException(message));
  }

  /* -------------------------- DEBUG level -------------------------- */

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
  public void debug(final String message, final Throwable arg) {
    logger.debug(message, arg);
  }
}
