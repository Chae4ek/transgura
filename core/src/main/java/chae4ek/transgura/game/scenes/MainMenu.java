package chae4ek.transgura.game.scenes;

import chae4ek.transgura.engine.ecs.Entity;
import chae4ek.transgura.engine.ecs.Game;
import chae4ek.transgura.engine.ecs.Scene;
import chae4ek.transgura.engine.util.GameSettings;
import chae4ek.transgura.game.ecs.component.Position;
import chae4ek.transgura.game.ecs.component.Shader;
import chae4ek.transgura.game.ecs.component.Sprite;
import chae4ek.transgura.game.ecs.entity.Player;
import chae4ek.transgura.game.ecs.entity.SolidBlock;
import chae4ek.transgura.game.ecs.entity.TestRock;
import chae4ek.transgura.game.ecs.system.Menu;
import chae4ek.transgura.game.util.resources.ResourceLoader;
import chae4ek.transgura.game.util.resources.TextureType;
import chae4ek.transgura.game.util.resources.TextureType.AtlasType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

public class MainMenu extends Scene {

  public MainMenu() {
    Game.getScene()
        .camera
        .position
        .set(Gdx.graphics.getWidth() >> 1, Gdx.graphics.getHeight() >> 1, 0f);
    b2dWorld.setGravity(new Vector2(0, -9.81f / GameSettings.PPM));

    ResourceLoader.loadAtlases(AtlasType.TEST);
    final AtlasRegion testBlock = ResourceLoader.loadAtlasRegion(TextureType.TEST_BLOCK);
    final AtlasRegion wood = ResourceLoader.loadAtlasRegion(TextureType.WOOD);

    new Entity(new Menu());

    new Entity(new Position(), new Sprite(testBlock));

    new SolidBlock(100f, 100f, wood);
    new SolidBlock(200f, 100f, wood);
    new SolidBlock(200f, 110f, wood);

    new SolidBlock(0f, 0f, 27, 1, wood);
    new SolidBlock(0f, 32f, 1, 4, wood);

    new Player(150f, 100f);

    // debug test
    new TestRock(400f, 300f);

    final ShaderProgram postProcessingShader =
        new ShaderProgram(
            "attribute vec4 a_position;\n"
                + "attribute vec2 a_texCoord0;\n"
                + "uniform mat4 u_projTrans;\n"
                + "varying vec2 v_texCoords;\n"
                + "void main() {\n"
                + "  v_texCoords = a_texCoord0;\n"
                + "  gl_Position = u_projTrans * a_position;\n"
                + "}\n",
            "#ifdef GL_ES\n"
                + "#define LOWP lowp\n"
                + "precision lowp float;\n"
                + "#else\n"
                + "#define LOWP\n"
                + "#endif\n"
                + "uniform vec2 u_camCoords;\n"
                + "varying vec2 v_texCoords;\n"
                + "uniform sampler2D u_texture;\n"
                + "#define mul(z1, z2) vec2(z1.x * z2.x - z1.y * z2.y, z1.x * z2.y + z1.y * z2.x)\n"
                + "vec2 rot(in vec2 st, in float angle) {\n"
                + "  float c = cos(angle), s = sin(angle);\n"
                + "  return mat2(c, -s, s, c) * st;\n"
                + "}"
                + "void main() {\n"
                + "  vec2 st = v_texCoords - 0.5 - u_camCoords;\n"
                + "  st *= 1.5;\n"
                + "  st = rot(st, -45.0);\n"
                + "  vec4 color = texture2D(u_texture, mul(st, st) + 0.5 + u_camCoords);\n"
                + "  gl_FragColor = color;\n"
                + "}\n");
    final Consumer<ShaderProgram> setUpShader =
        shader ->
            shader.setUniformf(
                "u_camCoords",
                Game.getScene().camera.position.x / Gdx.graphics.getWidth(),
                Game.getScene().camera.position.y / Gdx.graphics.getHeight());
    new Entity(new Shader(999, postProcessingShader, setUpShader));

    try (final DataOutputStream out =
        new DataOutputStream(Gdx.files.local("saves/world0").write(false, 8192))) {
      saveWorld(out);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }
}
