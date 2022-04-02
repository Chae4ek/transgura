package chae4ek.transgura.engine.util.serializers;

import chae4ek.transgura.engine.util.debug.CallOnce;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;

public interface InstantiationSerializer<T> {

  /**
   * Read data of the object and serialize all fields if the object is {@link
   * HierarchicallySerializable}
   */
  @CallOnce
  default <E extends T> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    if (object instanceof HierarchicallySerializable)
      ((HierarchicallySerializable) object).serialize(serializer);
  }

  /**
   * Instantiate an object that represented by the clazz
   *
   * @return the deserialized object
   */
  T instantiate(Class<? extends T> clazz, DefaultDeserializer deserializer) throws Exception;

  // TODO: make interface for cache cleaning

  /** Prepare to start/end of serialization the {@link chae4ek.transgura.engine.ecs.Scene world} */
  default void cleanSerializedCache() {}

  /**
   * Prepare to start/end of deserialization the {@link chae4ek.transgura.engine.ecs.Scene world}
   */
  default void cleanDeserializedCache() {}
}
