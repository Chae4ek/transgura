package chae4ek.transgura.ecs.component;

import chae4ek.transgura.ecs.RenderComponent;
import chae4ek.transgura.ecs.RenderManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class Particles extends RenderComponent {

  private final boolean loop;
  private final ParticleEffect particleEffect;

  public Particles(final boolean loop, final ParticleEffect particleEffect) {
    this.loop = loop;
    this.particleEffect = particleEffect;
    particleEffect.start();
  }

  public ParticleEffect getParticleEffect() {
    return particleEffect;
  }

  @Override
  public void draw() {
    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    particleEffect.setPosition(pos.x, pos.y);
    if (loop && particleEffect.isComplete()) particleEffect.reset();
    particleEffect.update(Gdx.graphics.getDeltaTime());
    particleEffect.draw(RenderManager.spriteBatch);
  }
}
