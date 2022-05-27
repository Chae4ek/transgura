package chae4ek.transgura.ecs.system;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.InputProcessor;
import chae4ek.engine.ecs.System;
import chae4ek.transgura.ecs.component.Position;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    Game.getScene().camera.zoom = pixelPerfectZoom[zoomIndex];
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
    final OrthographicCamera camera = Game.getScene().camera;
    final float lerpZoom = 5f * Game.getDeltaTime();
    camera.zoom += (pixelPerfectZoom[zoomIndex] - camera.zoom) * lerpZoom;

    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    final float lerpX = 5f * Game.getDeltaTime();
    final float lerpY = 1.5f * Game.getDeltaTime();
    lerpPosition.x += (pos.x - lerpPosition.x) * lerpX;
    lerpPosition.y += (pos.y - lerpPosition.y) * lerpY;
    camera.position.x = (int) (lerpPosition.x / camera.zoom) * camera.zoom;
    camera.position.y = (int) (lerpPosition.y / camera.zoom) * camera.zoom;
    // extra offset to remove flickering when the window has an odd size
    if ((Gdx.graphics.getWidth() & 1) != 0) camera.position.x += 0.5f * camera.zoom;
    if ((Gdx.graphics.getHeight() & 1) != 0) camera.position.y += 0.5f * camera.zoom;
  }
}
