package chae4ek.transgura.render;

import chae4ek.transgura.ecs.component.ComponentType;
import chae4ek.transgura.ecs.component.components.Position;
import chae4ek.transgura.ecs.component.components.render.Sprite;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.HashMap;
import java.util.Map;

public class RenderManager {

  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  private final ExtendViewport viewport =
      new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

  private final Map<Integer, Entity> idToSpriteEntity = new HashMap<>();

  private final SpriteBatch spriteBatch = new SpriteBatch();

  /** Add a sprite entity to this system manager */
  public void addSpriteEntity(final Entity spriteEntity) {
    if (spriteEntity.componentManager.getComponent(ComponentType.SPRITE) == null) {
      gameAlert.warn(GameErrorType.ENTITY_HAS_NOT_SPRITE, spriteEntity.toString());
    } else if (spriteEntity.componentManager.getComponent(ComponentType.POSITION) == null) {
      gameAlert.warn(GameErrorType.ENTITY_HAS_NOT_POSITION, spriteEntity.toString());
    } else idToSpriteEntity.put(spriteEntity.id, spriteEntity);
  }

  /** Render all sprites */
  public void renderAll() {
    viewport.apply();

    ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1);

    final Matrix4 projection = viewport.getCamera().combined;
    spriteBatch.setProjectionMatrix(projection);
    spriteBatch.begin();

    for (final Entity spriteEntity : idToSpriteEntity.values()) {
      final Sprite sprite =
          (Sprite) spriteEntity.componentManager.getComponent(ComponentType.SPRITE);

      if (sprite.isEnabled()) {
        final Position position =
            (Position) spriteEntity.componentManager.getComponent(ComponentType.POSITION);

        spriteBatch.draw(
            sprite.texture, position.x + sprite.offset.x, position.y + sprite.offset.y, 216, 216);
      }
    }

    spriteBatch.end();
  }

  /** Re-render this scene when resizing the window */
  public void resize(final int width, final int height) {
    viewport.update(width, height, true);
  }

  /** Clean all resources */
  public void close() {
    ResourceLoader.unloadAllTextures();
    spriteBatch.dispose();
  }
}
