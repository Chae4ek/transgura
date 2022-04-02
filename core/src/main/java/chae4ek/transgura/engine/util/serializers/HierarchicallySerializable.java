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
     * Call {@link #serialize} on all {@link HierarchicallySerializable} fields of
     * <strong>this</strong> object, then write data of the object to a stream without calling
     * {@link #deserialize} on <strong>this</strong> object
     */
    @CallOnce
    void writeThis() throws Exception;

    /**
     * Call {@link #serialize} on all {@link HierarchicallySerializable} fields of the obj, then
     * write data of the obj to a stream and call {@link #deserialize} on the obj
     *
     * @param obj the object to write
     */
    void write(Object obj) throws Exception;

    void writeInt(int value) throws Exception;

    void writeLong(long value) throws Exception;

    void writeFloat(float value) throws Exception;

    void writeDouble(double value) throws Exception;

    void writeBoolean(boolean value) throws Exception;

    void writeByte(byte value) throws Exception;

    void writeShort(short value) throws Exception;

    void writeChar(char value) throws Exception;
  }

  interface DefaultDeserializer {

    /**
     * Call {@link #deserialize} on all {@link HierarchicallySerializable} fields of
     * <strong>this</strong> object, then write data to <strong>this</strong> object without calling
     * {@link #deserialize} on <strong>this</strong> object
     *
     * @param writeToThis the object for writing read data of <strong>this</strong> object
     */
    @CallOnce
    void readTo(Object writeToThis) throws Exception;

    /**
     * Read data of an object from a stream, then call {@link #deserialize} on all {@link
     * HierarchicallySerializable} fields of the object and if it's a {@link
     * HierarchicallySerializable} object then call {@link #deserialize} on it
     *
     * @return a deserialized object
     */
    Object read() throws Exception;

    int readInt() throws Exception;

    long readLong() throws Exception;

    float readFloat() throws Exception;

    double readDouble() throws Exception;

    boolean readBoolean() throws Exception;

    byte readByte() throws Exception;

    short readShort() throws Exception;

    char readChar() throws Exception;
  }
}
