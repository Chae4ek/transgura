package chae4ek.transgura.game.util.serializers;

import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.transgura.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.transgura.engine.util.serializers.InstantiationSerializer;
import chae4ek.transgura.game.util.resources.ParticlesType;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleEffectSerializer implements InstantiationSerializer<ParticleEffect> {

  @Override
  public <E extends ParticleEffect> void serialize(
      final E object, final DefaultSerializer serializer) throws Exception {
    serializer.write(ResourceLoader.getLoadedParticleTypeOrdinal(object));
  }

  @Override
  public ParticleEffect instantiate(
      final Class<? extends ParticleEffect> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final int ordinal = deserializer.readInt();
    return ResourceLoader.loadParticleEffect(ParticlesType.values()[ordinal]);
  }
}
