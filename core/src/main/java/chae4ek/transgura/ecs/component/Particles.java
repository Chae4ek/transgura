package chae4ek.transgura.ecs.component;

import chae4ek.engine.ecs.Game;
import chae4ek.engine.ecs.RenderComponent;
import chae4ek.engine.ecs.RenderManager;
import chae4ek.engine.util.GameSettings;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;

public class Particles extends RenderComponent {

  public final boolean loop;
  public final ParticleEffect particleEffect;

  public Particles(final boolean loop, final boolean start, final ParticleEffect particleEffect) {
    this.loop = loop;
    this.particleEffect = particleEffect;
    if (start) particleEffect.start();
  }

  @Override
  public void draw() {
    final Vector2 pos = getParent().getComponent(Position.class).getVec();
    particleEffect.setPosition(
        pos.x * GameSettings.reverseRenderScale, pos.y * GameSettings.reverseRenderScale);
    if (loop && particleEffect.isComplete()) particleEffect.reset();
    particleEffect.update(Game.getDeltaTime());
    particleEffect.draw(RenderManager.spriteBatch);
  }
}
