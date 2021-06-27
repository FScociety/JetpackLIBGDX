package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import de.soerensc.engine.ecs.GameBehaviour;

public class ShaderTest extends GameBehaviour {

    private ShaderProgram shader;
    private String vertexShader, fragmentShader;

    private Texture image;


    public ShaderTest() {
    }

    public void start() {
        ShaderProgram.pedantic = false;

        image = new Texture("badlogic.jpg");

        this.vertexShader = Gdx.files.internal("shaders/normallighting_VERTEX.glsl").readString();
        this.fragmentShader = Gdx.files.internal("shaders/normallighting_FRAGMENT.glsl").readString();
        shader = new ShaderProgram(spriteBatch.getShader().getVertexShaderSource(), fragmentShader);
        if (!shader.isCompiled()) {
            System.err.println(shader.getLog());
            System.exit(0);
        }

        shader.setUniformf("dir", 0f, 0f);
        shader.setUniformf("resolution", 256);
        shader.setUniformf("radius", 1f);
    }

    public void render() {
        spriteBatch.setShader(shader);

        spriteBatch.draw(image,0,0);

        spriteBatch.setShader(null);
    }

    public void dispose() {
        shader.dispose();
        image.dispose();
    }
}
