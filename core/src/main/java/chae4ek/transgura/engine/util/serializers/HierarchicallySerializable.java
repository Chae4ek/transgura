package chae4ek.transgura.engine.util.serializers;

import chae4ek.transgura.engine.util.debug.CallOnce;
import java.io.Serializable;

public interface HierarchicallySerializable extends Serializable {

  /** Read data of this object and serialize all fields */
  @CallOnce
  void serialize(DefaultSerializer serializer) throws Exception;

  /** Deserialize all fields and write data to this object */
  @CallOnce
  void deserialize(DefaultDeserializer deserializer) throws Exception;

  interface DefaultSerializer {

    /**
     * Call {@link #serialize} on all {@link HierarchicallySerializable} fields of obj, then write
     * data of the object to stream without calling {@link #deserialize} if obj is this object
     *
     * <p>Note: this object can be passed only once.
     *
     * <p>TODO(?): add writeThis() with @CallOnce
     *
     * @param obj the object to write
     */
    void write(Object obj) throws Exception;

    // TODO: primitives (byte, short, char)
    void write(int i) throws Exception;

    void write(long l) throws Exception;

    void write(float f) throws Exception;

    void write(double d) throws Exception;

    void write(boolean b) throws Exception;
  }

  interface DefaultDeserializer {

    /**
     * Call {@link #deserialize} on all {@link HierarchicallySerializable} fields of this object,
     * then write data to this object without calling {@link #deserialize}
     *
     * @param writeToThis the object for writing read data
     */
    @CallOnce
    void readTo(Object writeToThis) throws Exception;

    /**
     * Read data of object from stream, then call {@link #deserialize} on all {@link
     * HierarchicallySerializable} fields of the object and if it's {@link
     * HierarchicallySerializable} object then call {@link #deserialize}
     *
     * @return a deserialized object
     */
    Object read() throws Exception;

    // TODO: primitives (byte, short, char)
    int readInt() throws Exception;

    long readLong() throws Exception;

    float readFloat() throws Exception;

    double readDouble() throws Exception;

    boolean readBoolean() throws Exception;
  }
}
