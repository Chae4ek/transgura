package chae4ek.transgura.util.serializers;

import chae4ek.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.engine.util.serializers.InstantiationSerializer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

public class FixtureSerializer implements InstantiationSerializer<Fixture> {

  private static final FixtureDef fixtureDef = new FixtureDef();
  private final IntSet cacheSer = new IntSet();
  private final IntMap<Fixture> cacheDes = new IntMap<>();

  @Override
  public <E extends Fixture> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    final int addr = System.identityHashCode(object);
    serializer.writeInt(addr);
    if (!cacheSer.add(addr)) return; // already serialized

    serializer.write(object.getShape());
    serializer.writeFloat(object.getFriction());
    serializer.writeFloat(object.getRestitution());
    serializer.writeFloat(object.getDensity());
    serializer.writeBoolean(object.isSensor());

    final Filter filter = object.getFilterData();
    serializer.writeShort(filter.categoryBits);
    serializer.writeShort(filter.maskBits);
    serializer.writeShort(filter.groupIndex);

    serializer.write(object.getBody());
    serializer.write(object.getUserData());
  }

  @Override
  public Fixture instantiate(
      final Class<? extends Fixture> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final int addr = deserializer.readInt();
    Fixture fixture = cacheDes.get(addr);
    if (fixture != null) return fixture; // already deserialized

    fixtureDef.shape = (Shape) deserializer.read();
    fixtureDef.friction = deserializer.readFloat();
    fixtureDef.restitution = deserializer.readFloat();
    fixtureDef.density = deserializer.readFloat();
    fixtureDef.isSensor = deserializer.readBoolean();

    fixtureDef.filter.categoryBits = deserializer.readShort();
    fixtureDef.filter.maskBits = deserializer.readShort();
    fixtureDef.filter.groupIndex = deserializer.readShort();

    final Body body = (Body) deserializer.read();
    fixture = body.createFixture(fixtureDef);
    cacheDes.put(addr, fixture);
    fixture.setUserData(deserializer.read());

    return fixture;
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
