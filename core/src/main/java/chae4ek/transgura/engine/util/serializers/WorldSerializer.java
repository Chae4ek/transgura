package chae4ek.transgura.engine.util.serializers;

import chae4ek.transgura.engine.util.exceptions.GameAlert;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import com.badlogic.gdx.utils.ObjectSet;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import org.nustaq.serialization.FSTBasicObjectSerializer;
import org.nustaq.serialization.FSTClazzInfo;
import org.nustaq.serialization.FSTClazzInfo.FSTFieldInfo;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public final class WorldSerializer {

  private static final GameAlert gameAlert = new GameAlert(WorldSerializer.class);

  private static final FSTConfiguration fst = FSTConfiguration.createJsonNoRefConfiguration();
  private static final ObjectSet<InstantiationSerializer<?>> instantiators = new ObjectSet<>();

  static {
    fst.registerSerializer(HierarchicallySerializable.class, new FSTSerializer(), true);
  }

  public static void cleanSerializedCache() {
    for (final InstantiationSerializer<?> instantiator : instantiators)
      instantiator.cleanSerializedCache();
  }

  public static void cleanDeserializedCache() {
    for (final InstantiationSerializer<?> instantiator : instantiators)
      instantiator.cleanDeserializedCache();
  }

  // TODO: make (de)serialize non-static and make this class a GameSettings member
  public static void serialize(final DataOutputStream out, final Object obj) {
    try {
      fst.encodeToStream(out, obj);
    } catch (final Exception e) {
      gameAlert.error("Serialization error", e);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T deserialize(final DataInputStream in) {
    try {
      return (T) fst.decodeFromStream(in);
    } catch (final Exception e) {
      gameAlert.error("Serialization error", e);
      throw new IllegalStateException(e);
    }
  }

  public static <T> void register(
      final Class<? extends T> clazz,
      final InstantiationSerializer<T> instantiator,
      final boolean alsoForAllSubclasses) {
    instantiators.add(instantiator);
    final FSTInstantiator<T> fstInstantiator = new FSTInstantiator<>(instantiator);
    fst.registerSerializer(clazz, fstInstantiator, alsoForAllSubclasses);
  }

  private static class FSTInstantiator<T> extends FSTBasicObjectSerializer {

    private final InstantiationSerializer<T> instantiator;

    public FSTInstantiator(final InstantiationSerializer<T> instantiator) {
      this.instantiator = instantiator;
    }

    @Override
    public void writeObject(
        final FSTObjectOutput out,
        final Object toWrite,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy,
        final int streamPosition) {
      @SuppressWarnings("unchecked")
      final T obj = (T) toWrite;
      try {
        instantiator.serialize(obj, new FSTDefaultSerializer(out, toWrite, clzInfo));
      } catch (final Exception e) {
        gameAlert.error("Serialization error", e);
      }
    }

    @Override
    public Object instantiate(
        final Class objectClass,
        final FSTObjectInput in,
        final FSTClazzInfo serializationInfo,
        final FSTFieldInfo referencee,
        final int streamPosition) {
      try {
        @SuppressWarnings("unchecked")
        final Object object =
            instantiator.instantiate(
                objectClass, new FSTDefaultDeserializer(in, serializationInfo, referencee));
        in.registerObject(object, streamPosition, serializationInfo, referencee);
        return object == null ? REALLY_NULL : object;
      } catch (final Exception e) {
        gameAlert.error("Serialization error", e);
        throw new IllegalStateException(e);
      }
    }
  }

  private static class FSTSerializer extends FSTBasicObjectSerializer {

    @Override
    public void writeObject(
        final FSTObjectOutput out,
        final Object toWrite,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy,
        final int streamPosition) {
      try {
        ((HierarchicallySerializable) toWrite)
            .serialize(new FSTDefaultSerializer(out, toWrite, clzInfo));
      } catch (final Exception e) {
        gameAlert.error("Serialization error", e);
      }
    }

    @Override
    public void readObject(
        final FSTObjectInput in,
        final Object toRead,
        final FSTClazzInfo clzInfo,
        final FSTFieldInfo referencedBy) {
      try {
        ((HierarchicallySerializable) toRead)
            .deserialize(new FSTDefaultDeserializer(in, clzInfo, referencedBy));
      } catch (final Exception e) {
        gameAlert.error("Serialization error", e);
      }
    }
  }

  private static class FSTDefaultSerializer implements DefaultSerializer {

    private final FSTObjectOutput out;
    private final Object toWrite;
    private final FSTClazzInfo clzInfo;

    public FSTDefaultSerializer(
        final FSTObjectOutput out, final Object toWrite, final FSTClazzInfo clzInfo) {
      this.out = out;
      this.toWrite = toWrite;
      this.clzInfo = clzInfo;
    }

    @Override
    public void writeThis() throws Exception {
      out.defaultWriteObject(toWrite, clzInfo);
    }

    @Override
    public void write(final Object data) throws Exception {
      out.writeObject(data);
    }

    @Override
    public void writeInt(final int value) throws Exception {
      out.writeInt(value);
    }

    @Override
    public void writeLong(final long value) throws Exception {
      out.writeLong(value);
    }

    @Override
    public void writeFloat(final float value) throws Exception {
      out.writeFloat(value);
    }

    @Override
    public void writeDouble(final double value) throws Exception {
      out.writeDouble(value);
    }

    @Override
    public void writeBoolean(final boolean value) throws Exception {
      out.writeBoolean(value);
    }

    @Override
    public void writeByte(final byte value) throws Exception {
      out.writeByte(value);
    }

    @Override
    public void writeShort(final short value) throws Exception {
      out.writeShort(value);
    }

    @Override
    public void writeChar(final char value) throws Exception {
      out.writeChar(value);
    }
  }

  private static class FSTDefaultDeserializer implements DefaultDeserializer {

    private final FSTObjectInput in;
    private final FSTClazzInfo clzInfo;
    private final FSTFieldInfo referencedBy;

    public FSTDefaultDeserializer(
        final FSTObjectInput in, final FSTClazzInfo clzInfo, final FSTFieldInfo referencedBy) {
      this.in = in;
      this.clzInfo = clzInfo;
      this.referencedBy = referencedBy;
    }

    @Override
    public void readTo(final Object writeToThis) {
      in.defaultReadObject(referencedBy, clzInfo, writeToThis);
    }

    @Override
    public Object read() throws Exception {
      return in.readObject();
    }

    @Override
    public int readInt() throws Exception {
      return in.readInt();
    }

    @Override
    public long readLong() throws Exception {
      return in.readLong();
    }

    @Override
    public float readFloat() throws Exception {
      return in.readFloat();
    }

    @Override
    public double readDouble() throws Exception {
      return in.readDouble();
    }

    @Override
    public boolean readBoolean() throws Exception {
      return in.readBoolean();
    }

    @Override
    public byte readByte() throws Exception {
      return in.readByte();
    }

    @Override
    public short readShort() throws Exception {
      return in.readShort();
    }

    @Override
    public char readChar() throws Exception {
      return in.readChar();
    }
  }
}
