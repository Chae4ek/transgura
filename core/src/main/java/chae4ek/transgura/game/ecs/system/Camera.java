package chae4ek.transgura.game.ecs.system;

import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.InputProcessor;
import chae4ek.transgura.engine.ecs.System;
import chae4ek.transgura.game.ecs.component.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Camera extends System {

  private static final float[] pixelPerfectZoom = new float[10];

  static {
    pixelPerfectZoom[0] = 2;
    for (int i = 1; i < pixelPerfectZoom.length; ) {
      pixelPerfectZoom[i] = pixelPerfectZoom[i - 1] * i / ++i;
    }
  }

  private final Vector2 lerpPosition = new Vector2();
  private int zoomIndex = 2;

  public Camera() {
    scene.camera.zoom = pixelPerfectZoom[zoomIndex];
  }

  @Override
  public void update() {
    if (InputProcessor.getJustScrolledY() < 0) {
      ++zoomIndex;
      if (zoomIndex >= pixelPerfectZoom.length) zoomIndex = pixelPerfectZoom.length - 1;
    }
    if (InputProcessor.getJustScrolledY() > 0) {
      --zoomIndex;
      if (zoomIndex < 0) zoomIndex = 0;
    }
    final float lerpZoom = 5f * Game.getDeltaTime();
    scene.camera.zoom += (pixelPerfectZoom[zoomIndex] - scene.camera.zoom) * lerpZoom;

    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    final float lerpX = 5f * Game.getDeltaTime();
    final float lerpY = 1.5f * Game.getDeltaTime();
    lerpPosition.x += (pos.x - lerpPosition.x) * lerpX;
    lerpPosition.y += (pos.y - lerpPosition.y) * lerpY;
    scene.camera.position.x = (int) (lerpPosition.x / scene.camera.zoom) * scene.camera.zoom;
    scene.camera.position.y = (int) (lerpPosition.y / scene.camera.zoom) * scene.camera.zoom;
    // extra offset to remove flickering when the window has an odd size
    if ((Gdx.graphics.getWidth() & 1) != 0) scene.camera.position.x += 0.5f * scene.camera.zoom;
    if ((Gdx.graphics.getHeight() & 1) != 0) scene.camera.position.y += 0.5f * scene.camera.zoom;
  }
}
