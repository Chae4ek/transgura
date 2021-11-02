package chae4ek.transgura.game.scenes;

import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.entity.GlobalScript;
import chae4ek.transgura.ecs.entity.StaticBlock;
import chae4ek.transgura.ecs.system.Menu;
import chae4ek.transgura.game.Scene;
import chae4ek.transgura.render.ResourceLoader;
import chae4ek.transgura.render.TextureType;
import chae4ek.transgura.render.TextureType.AtlasType;

public class MainMenu extends Scene {

  public MainMenu() {
    ResourceLoader.loadAtlases(AtlasType.TEST);
  }

  @Override
  public void start() {
    new GlobalScript(true, new Menu(true));

    new StaticBlock(new Position(), ResourceLoader.loadAtlasRegion(TextureType.TEST_BLOCK));
    new StaticBlock(new Position(100, 100), ResourceLoader.loadAtlasRegion(TextureType.WOOD));
    new StaticBlock(new Position(150, 100), ResourceLoader.loadAtlasRegion(TextureType.WOOD));
  }
}