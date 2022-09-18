package chae4ek.transgura.util.collision;

import chae4ek.engine.ecs.Entity;
import chae4ek.engine.util.serializers.HierarchicallySerializable;

public final class EntityData implements HierarchicallySerializable {

  public final Entity parent;
  public final String tag;

  public EntityData(final Entity parent, final String tag) {
    this.parent = parent;
    this.tag = tag;
  }

  @Override
  public void serialize(final DefaultSerializer serializer) throws Exception {
    serializer.writeThis();
  }

  @Override
  public void deserialize(final DefaultDeserializer deserializer) throws Exception {
    deserializer.readTo(this);
  }
}
