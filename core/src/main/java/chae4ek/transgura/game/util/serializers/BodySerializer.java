package chae4ek.transgura.game.util.serializers;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.transgura.engine.util.serializers.InstantiationSerializer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

public class BodySerializer implements InstantiationSerializer<Body> {

  private static final BodyDef bodyDef = new BodyDef();
  private final IntSet cacheSer = new IntSet();
  private final IntMap<Body> cacheDes = new IntMap<>();

  @Override
  public <E extends Body> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    final int addr = System.identityHashCode(object);
    serializer.writeInt(addr);
    if (!cacheSer.add(addr)) return; // already serialized

    // TODO: refactoring
    serializer.write(object.getType());
    serializer.write(object.getPosition());
    serializer.writeFloat(object.getAngle());
    serializer.write(object.getLinearVelocity());
    serializer.writeFloat(object.getAngularVelocity());
    serializer.writeFloat(object.getLinearDamping());
    serializer.writeFloat(object.getAngularDamping());
    serializer.writeBoolean(object.isSleepingAllowed());
    serializer.writeBoolean(object.isAwake());
    serializer.writeBoolean(object.isFixedRotation());
    serializer.writeBoolean(object.isBullet());
    serializer.writeBoolean(object.isActive());
    serializer.writeFloat(object.getGravityScale());
    serializer.write(object.getUserData());
    // TODO: Array<Fixture> fixtures
    // TODO: Array<JointEdge> joints
  }

  @Override
  public Body instantiate(final Class<? extends Body> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final int addr = deserializer.readInt();
    Body body = cacheDes.get(addr);
    if (body != null) return body; // already deserialized

    // TODO: refactoring
    bodyDef.type = (BodyType) deserializer.read();
    bodyDef.position.set((Vector2) deserializer.read());
    bodyDef.angle = deserializer.readFloat();
    bodyDef.linearVelocity.set((Vector2) deserializer.read());
    bodyDef.angularVelocity = deserializer.readFloat();
    bodyDef.linearDamping = deserializer.readFloat();
    bodyDef.angularDamping = deserializer.readFloat();
    bodyDef.allowSleep = deserializer.readBoolean();
    bodyDef.awake = deserializer.readBoolean();
    bodyDef.fixedRotation = deserializer.readBoolean();
    bodyDef.bullet = deserializer.readBoolean();
    bodyDef.active = deserializer.readBoolean();
    bodyDef.gravityScale = deserializer.readFloat();

    body = Game.getScene().b2dWorld.createBody(bodyDef);

    body.setUserData(deserializer.read());
    // TODO: Array<Fixture> fixtures
    // TODO: Array<JointEdge> joints

    cacheDes.put(addr, body);
    return body;
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
