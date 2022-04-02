package chae4ek.transgura.engine.util;

public interface ResourceManager {

  /** Unload some scene's textures if they are loaded */
  void unloadSceneResources();

  /** Unload and dispose all textures and other component */
  void dispose();

  class NullResourceLoader implements ResourceManager {

    @Override
    public void unloadSceneResources() {}

    @Override
    public void dispose() {}
  }
}
