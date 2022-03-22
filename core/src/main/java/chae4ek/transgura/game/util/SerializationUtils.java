package chae4ek.transgura.game.util;

import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import com.badlogic.gdx.utils.Array;

public class SerializationUtils {

  public static <T> void writeArray(final DefaultSerializer serializer, final T[] array)
      throws Exception {
    serializer.writeInt(array.length);
    for (final T t : array) serializer.write(t);
  }

  public static <T> void writeArray(final DefaultSerializer serializer, final Array<T> array)
      throws Exception {
    serializer.writeInt(array.size);
    for (final T t : array) serializer.write(t);
  }

  @SuppressWarnings("unchecked")
  public static <T> T[] readArray(final DefaultDeserializer deserializer, final Class<T> clazz)
      throws Exception {
    final int size = deserializer.readInt();
    final Object[] array = (Object[]) java.lang.reflect.Array.newInstance(clazz, size);
    for (int i = 0; i < size; ++i) array[i] = deserializer.read();
    return (T[]) array;
  }
}
