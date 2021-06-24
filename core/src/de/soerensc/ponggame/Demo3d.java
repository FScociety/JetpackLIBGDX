package de.soerensc.ponggame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * See: http://blog.xoppa.com/basic-3d-using-libgdx-2/
 * @author Xoppa
 */
public class Demo3d implements ApplicationListener {
    public Texture defaultTexture;
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;
    public SpriteBatch spriteBatch;
    public Model model;
    public ArrayList<GameObject> instances = new ArrayList<GameObject>();
    public Environment environment;
    public CameraInputController camController;
    public Shader shader;
    private Viewport viewport;

    public static int renderCounter = 0;

    @Override
    public void create() {
        this.createCamera();;
        this.createModel();

        int size = 20;

        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                instances.add(new GameObject(model, new Vector3(x * 10, 0, y * 10)));
            }
        }

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        shader = new TestShader();
        shader.init();

        this.viewport = new ExtendViewport(1920, 1080, cam);

        spriteBatch = new SpriteBatch();

        defaultTexture = new Texture(Gdx.files.internal("badlogic.jpg"));

    }

    private void createModel() {
        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                Usage.Position | Usage.Normal);
    }

    private void createCamera() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 10f;
        cam.far = 10000f;
        cam.update();
        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
    }

    private void update() {
        for (GameObject inst : this.instances) {
            inst.move();
        }
    }

    @Override
    public void render() {
        Demo3d.renderCounter ++;

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camController.update();
        update();

        modelBatch.begin(cam);

        for (GameObject inst : this.instances) {
            modelBatch.render(inst.modelInstance, environment, shader);
        }

        modelBatch.end();

       /* spriteBatch.begin();

        spriteBatch.draw(defaultTexture, 0, 0);

        spriteBatch.end();*/
    }

    @Override
    public void dispose() {
        shader.dispose();
        model.dispose();
        modelBatch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}