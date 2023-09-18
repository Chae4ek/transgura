package chae4ek.transgura.scenes;

import chae4ek.engine.ecs.Scene;
import chae4ek.transgura.third_party.ldtk.LDtk;
import chae4ek.transgura.util.ldtk.LDtkLoader;

public class MainMenu extends Scene {

  public MainMenu() {
    LDtkLoader.loadBackgroundTexture();
    final LDtk ldtk = LDtkLoader.readLDtkFromFile("saves/world.ldtk");
    LDtkLoader.loadCollisions(ldtk);
  }
}
