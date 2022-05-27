package chae4ek.transgura.ecs.system;

import box2dLight.PointLight;
import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.System;

public class FlickeringPointLight extends System {

  // TODO: serialization
  private final transient PointLight pointLight;
  private final float randomSeed;
  private final float distanceOrigin;

  public FlickeringPointLight(final PointLight pointLight, final float randomSeed) {
    this.pointLight = pointLight;
    this.randomSeed = randomSeed;
    distanceOrigin = pointLight.getDistance();
  }

  @Override
  protected void onDestroy() {
    pointLight.dispose();
  }

  @Override
  public void update() {
    final float time = Game.getScene().getSceneLifetimeInSec();
    pointLight.setDistance(distanceOrigin + rand(randomSeed + time));
  }

  private float rand(float time) {
    time = (float) Math.sin(4.5f * time);
    time += (float) Math.sin(6.5f * time);
    time += (float) Math.sin(0.5f * time);
    return time;
  }
}
