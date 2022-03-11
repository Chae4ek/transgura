package chae4ek.transgura.engine.util;

import chae4ek.transgura.engine.util.debug.CallOnce;
import java.io.IOException;
import java.io.Serializable;

public interface HierarchicallySerializable extends Serializable {

  /** Read data of this object and serialize all fields */
  @CallOnce
  void serialize(DefaultSerializer defaultSerializer) throws IOException;

  /** Deserialize all fields and write data to this object */
  @CallOnce
  void deserialize(DefaultDeserializer defaultDeserializer) throws Exception;

  interface DefaultSerializer {
    /**
     * Call {@link #serialize} of all {@link HierarchicallySerializable} fields, then read data of
     * this object
     */
    @CallOnce
    void run() throws IOException;
  }

  interface DefaultDeserializer {
    /**
     * Call {@link #deserialize} of all {@link HierarchicallySerializable} fields, then write data
     * to this object
     */
    @CallOnce
    void run() throws Exception;
  }
}
