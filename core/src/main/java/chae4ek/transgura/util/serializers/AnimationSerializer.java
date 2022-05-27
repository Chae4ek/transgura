package chae4ek.transgura.util.serializers;

import chae4ek.engine.util.serializers.HierarchicallySerializable.DefaultDeserializer;
import chae4ek.engine.util.serializers.HierarchicallySerializable.DefaultSerializer;
import chae4ek.engine.util.serializers.InstantiationSerializer;
import chae4ek.transgura.util.ARAnimation;
import chae4ek.transgura.util.SerializationUtils;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

public class AnimationSerializer implements InstantiationSerializer<ARAnimation> {

  private final IntSet cacheSer = new IntSet();
  private final IntMap<ARAnimation> cacheDes = new IntMap<>();

  @Override
  public <E extends ARAnimation> void serialize(final E object, final DefaultSerializer serializer)
      throws Exception {
    final int addr = System.identityHashCode(object);
    serializer.writeInt(addr);
    if (!cacheSer.add(addr)) return; // already serialized

    SerializationUtils.writeArray(serializer, object.getKeyFrames());
    serializer.writeFloat(object.getFrameDuration());
    serializer.write(object.getPlayMode());
  }

  @Override
  public ARAnimation instantiate(
      final Class<? extends ARAnimation> clazz, final DefaultDeserializer deserializer)
      throws Exception {
    final int addr = deserializer.readInt();
    ARAnimation animation = cacheDes.get(addr);
    if (animation != null) return animation; // already deserialized

    final AtlasRegion[] keyFrames = SerializationUtils.readArray(deserializer, AtlasRegion.class);
    final float frameDuration = deserializer.readFloat();
    final PlayMode playMode = (PlayMode) deserializer.read();

    animation = new ARAnimation(frameDuration, new Array<>(keyFrames), playMode);
    cacheDes.put(addr, animation);
    return animation;
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
