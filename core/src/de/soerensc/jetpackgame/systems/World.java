package de.soerensc.jetpackgame.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;

import java.sql.Array;
import java.util.ArrayList;

public class World {
    public static World world;

    private final ArrayList<WorldObject> objects;
    private ModelBatch modelBatch;
    private PerspectiveCamera camera;

    private Environment environment;

    private CameraInputController cameraInputController;

    public World(PerspectiveCamera camera) {
        if (World.world != null) {
            World.world = this;
        }

        this.camera = camera;

        modelBatch = new ModelBatch();
        objects = new ArrayList<WorldObject>();

        create();
    }

    public void create() {
        cameraInputController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(cameraInputController);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        Ground ground = new Ground();
        this.add(ground);
    }

    public void dispose() {
        for (WorldObject worldObject : objects) {
            worldObject.dispose();
        }

        modelBatch.dispose();
    }

    public void update(float delta) {
        cameraInputController.update();

        for (WorldObject worldObject : objects) {
            worldObject.update(delta);
        }
    }

    public void render() {
        modelBatch.begin(camera);

        for (WorldObject worldObject : objects) {
            worldObject.render(modelBatch, environment);
        }

        modelBatch.end();
    }

    private void add(WorldObject worldObject) {
        objects.add(worldObject);
    }
}