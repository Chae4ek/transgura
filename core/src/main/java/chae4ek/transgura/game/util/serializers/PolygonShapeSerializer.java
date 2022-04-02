package chae4ek.transgura.game.util.serializers;

import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.transgura.engine.util.serializers.InstantiationSerializer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

public class PolygonShapeSerializer implements InstantiationSerializer<PolygonShape> {

  private static final Vector2 tmp = new Vector2();
  private static float[] verts = new float[6];
  private final IntSet cacheSer = new IntSet();
  private final IntMap<PolygonShape> cacheDes = new IntMap<>();

  @Override
  public <E extends PolygonShape> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    final int addr = System.identityHashCode(object);
    serializer.writeInt(addr);
    if (!cacheSer.add(addr)) return; // already serialized

    final int size = object.getVertexCount();
    serializer.writeInt(size);
    for (int i = 0, j = 0; i < size; ++i) {
      object.getVertex(i, tmp);
      serializer.writeFloat(tmp.x);
      serializer.writeFloat(tmp.y);
    }
  }

  @Override
  public PolygonShape instantiate(
      final Class<? extends PolygonShape> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final int addr = deserializer.readInt();
    PolygonShape polygonShape = cacheDes.get(addr);
    if (polygonShape != null) return polygonShape; // already deserialized

    final int size = 2 * deserializer.readInt();
    if (size > verts.length) verts = new float[size];
    for (int i = 0; i < size; ) {
      verts[i++] = deserializer.readFloat();
      verts[i++] = deserializer.readFloat();
    }

    polygonShape = new PolygonShape();
    polygonShape.set(verts, 0, size);
    cacheDes.put(addr, polygonShape);

    return polygonShape;
  }

  @Override
  public void cleanSerializedCache() {
    cacheSer.clear();
  }

  @Override
  public void cleanDeserializedCache() {
    cacheDes.clear();
  }
}
