package chae4ek.transgura.render.resources;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.function.Supplier;

public enum SpriteBatchType {
  DEFAULT(SpriteBatch::new);

  public Supplier<SpriteBatch> spriteBatchFactory;

  SpriteBatchType(final Supplier<SpriteBatch> spriteBatchFactory) {
    this.spriteBatchFactory = spriteBatchFactory;
  }
}
