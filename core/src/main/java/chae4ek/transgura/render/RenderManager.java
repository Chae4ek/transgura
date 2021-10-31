package chae4ek.transgura.render;

import chae4ek.transgura.ecs.component.ComponentType;
import chae4ek.transgura.ecs.component.components.Position;
import chae4ek.transgura.ecs.component.components.render.Sprite;
import chae4ek.transgura.ecs.entity.Entity;
import chae4ek.transgura.exceptions.GameAlert;
import chae4ek.transgura.exceptions.GameErrorType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.HashMap;
import java.util.Map;

public class RenderManager {

  private static final transient GameAlert gameAlert = new GameAlert(RenderManager.class);

  private static transient int width = Gdx.graphics.getWidth();
  private static transient int height = Gdx.graphics.getHeight();
  private static final transient ExtendViewport viewport = new ExtendViewport(width, height);

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
    Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
            sprite.texture, position.x + sprite.offset.x, position.y + sprite.offset.y, 32, 32);
      }
    }

    spriteBatch.end();
  }

  /** Re-render this scene when resizing the window */
  public void resize(final int width, final int height) {
    RenderManager.width = width;
    RenderManager.height = height;
    viewport.update(width, height, true);
  }

  /** Clean all resources */
  public void dispose() {
    ResourceLoader.unloadAllTextures();
    spriteBatch.dispose();
  }
}
