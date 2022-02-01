package chae4ek.transgura.ecs.system;

import chae4ek.transgura.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import chae4ek.transgura.ecs.util.input.InputProcessor;
import chae4ek.transgura.game.Game;
import chae4ek.transgura.game.GameSettings;
import chae4ek.transgura.game.Scene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Camera extends System {

  private final Vector2 lerpPosition = new Vector2();
  private int zoomIndex = 2;

  public Camera() {
    Scene.camera.zoom = GameSettings.normalZoom[zoomIndex];
  }

  @Override
  public boolean isUpdateEnabled() {
    return true;
  }

  @Override
  public boolean isFixedUpdateEnabled() {
    return false;
  }

  @Override
  public void update() {
    if (InputProcessor.getJustScrolledY() < 0) {
      ++zoomIndex;
      if (zoomIndex >= GameSettings.normalZoom.length) zoomIndex = 0;
      Scene.camera.zoom = GameSettings.normalZoom[zoomIndex];
    }
    if (InputProcessor.getJustScrolledY() > 0) {
      --zoomIndex;
      if (zoomIndex < 0) zoomIndex = GameSettings.normalZoom.length - 1;
      Scene.camera.zoom = GameSettings.normalZoom[zoomIndex];
    }

    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    final float lerpX = 5f * Game.getDeltaTime();
    final float lerpY = 1.5f * Game.getDeltaTime();
    lerpPosition.x += (pos.x - lerpPosition.x) * lerpX;
    lerpPosition.y += (pos.y - lerpPosition.y) * lerpY;
    Scene.camera.position.x = (int) (lerpPosition.x / Scene.camera.zoom) * Scene.camera.zoom;
    Scene.camera.position.y = (int) (lerpPosition.y / Scene.camera.zoom) * Scene.camera.zoom;
    // extra offset to remove flickering when the window has an odd size
    if ((Gdx.graphics.getWidth() & 1) != 0) Scene.camera.position.x += 0.5f * Scene.camera.zoom;
    if ((Gdx.graphics.getHeight() & 1) != 0) Scene.camera.position.y += 0.5f * Scene.camera.zoom;
  }
}
