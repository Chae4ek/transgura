package chae4ek.transgura.engine.util;

public abstract class SerializationEvent {

  /** Write user data. Be sure you called super.beforeSerialize() at first */
  protected abstract void beforeSerialize();

  /** Read user data. Be sure you called super.afterDeserialize() at first */
  protected abstract void afterDeserialize();
}
