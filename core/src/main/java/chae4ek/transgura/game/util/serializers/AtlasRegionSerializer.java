package chae4ek.transgura.game.util.serializers;

import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.transgura.engine.util.serializers.InstantiationSerializer;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.resources.TextureType;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public class AtlasRegionSerializer implements InstantiationSerializer<AtlasRegion> {

  @Override
  public <E extends AtlasRegion> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    serializer.write(object.name);
    serializer.write(object.index == -1 ? 0 : object.index);
  }

  @Override
  public AtlasRegion instantiate(
      final Class<? extends AtlasRegion> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final String name = (String) deserializer.read();
    final int index = deserializer.readInt();
    return ResourceLoader.loadAtlasRegions(TextureType.map.get(name))[index];
  }
}
