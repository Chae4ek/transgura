package chae4ek.transgura.game.scenes;

import chae4ek.transgura.ecs.component.components.Position;
import chae4ek.transgura.ecs.entity.entities.GlobalScript;
import chae4ek.transgura.ecs.entity.entities.StaticBlock;
import chae4ek.transgura.ecs.system.systems.Menu;
import chae4ek.transgura.game.Scene;
import chae4ek.transgura.render.ResourceLoader;
import chae4ek.transgura.render.TextureType;

public class MainMenu extends Scene {

  public MainMenu() {
    entityManager.addNewEntity(id -> new GlobalScript(id, true, new Menu(true), systemManager));

    entityManager.addNewEntity(
        id ->
            new StaticBlock(
                id,
                new Position(),
                ResourceLoader.loadTexture(TextureType.TEST_BLOCK),
                renderManager));
    entityManager.addNewEntity(
        id ->
            new StaticBlock(
                id,
                new Position(100, 100),
                ResourceLoader.loadTexture(TextureType.WOOD),
                renderManager));
    entityManager.addNewEntity(
        id ->
            new StaticBlock(
                id,
                new Position(150, 100),
                ResourceLoader.loadTexture(TextureType.WOOD),
                renderManager));
  }
}
