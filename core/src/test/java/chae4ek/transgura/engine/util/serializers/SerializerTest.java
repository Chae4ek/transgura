package chae4ek.transgura.engine.util.serializers;

import chae4ek.transgura.engine.ecs.Component;
import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.EntityManager;
import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.util.ReflectUtils;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SerializerTest {

  private final Field entityManagerField = ReflectUtils.getField(Scene.class, "entityManager");

  @BeforeAll
  static void setUp() {
    ReflectUtils.setFieldValue(null, Game.class, "scene", Mockito.mock(Scene.class));
  }

  @BeforeEach
  void setUpEach() {
    GameSettings.isWARNThrowOn = true;
    final EntityManager entityManager = Mockito.mock(EntityManager.class);
    ReflectUtils.setFieldValue(Game.getScene(), entityManagerField, entityManager);
  }

  @Test
  void serialize_deserialize__correctSerDes() {
    final Entity1 entity = new Entity1(342);
    entity.addComponent(new Component1());

    final byte[] data = WorldSerializer.serialize(entity);
    final Entity1 jsonEntity = WorldSerializer.deserialize(data);

    Assertions.assertEquals(entity.i, jsonEntity.i);
    Assertions.assertEquals(jsonEntity, jsonEntity.getComponent(Component1.class).getParent());
  }

  @Test
  void instantiator__HierarchicallySer__correctSerDes() {
    WorldSerializer.register(Entity2.class, new Entity2Factory(), false);

    final Entity2 entity = new Entity2(9);

    final byte[] data = WorldSerializer.serialize(entity);
    final Entity2 jsonEntity = WorldSerializer.deserialize(data);

    Assertions.assertEquals(entity.i, jsonEntity.i);
    Assertions.assertEquals(entity.j, jsonEntity.j);
    Assertions.assertEquals(entity.k, jsonEntity.k);
  }

  @Test
  void instantiator__SimpleSer__correctSerDes() {
    WorldSerializer.register(Entity3.class, new Entity3Factory(), false);

    final Entity3 entity = new Entity3(9);

    final byte[] data = WorldSerializer.serialize(entity);
    final Entity3 jsonEntity = WorldSerializer.deserialize(data);

    Assertions.assertEquals(1, jsonEntity.i);
  }

  private static class Component1 extends Component {}

  private static class Entity1 extends Entity {
    int i;

    public Entity1(final int i) {
      this.i = i;
    }
  }

  private static class Entity2 implements HierarchicallySerializable {
    int i;
    transient int j, k;

    public Entity2(final int i) {
      this.i = j = k = i;
    }

    @Override
    public void serialize(final DefaultSerializer serializer) throws Exception {
      serializer.write(j);
      serializer.write(k = 123);
      serializer.write(this);
    }

    @Override
    public void deserialize(final DefaultDeserializer deserializer) throws Exception {
      j = deserializer.readInt();
      k = deserializer.readInt();
      deserializer.readTo(this);
    }
  }

  private static class Entity2Factory implements InstantiationSerializer<Entity2> {
    @Override
    public Entity2 instantiate(
        final Class<? extends Entity2> clazz, final DefaultDeserializer deserializer)
        throws Exception {
      final Entity2 e = new Entity2(1);
      e.deserialize(deserializer);
      return e;
    }
  }

  private static class Entity3 {
    int i;

    public Entity3(final int i) {
      this.i = i;
    }
  }

  private static class Entity3Factory implements InstantiationSerializer<Entity3> {
    @Override
    public Entity3 instantiate(
        final Class<? extends Entity3> clazz, final DefaultDeserializer deserializer) {
      return new Entity3(1);
    }
  }
}
