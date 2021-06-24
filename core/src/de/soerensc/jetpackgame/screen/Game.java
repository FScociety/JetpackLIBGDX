package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.soerensc.jetpackgame.systems.World;

public class Game extends ScreenAdapter {

    private PerspectiveCamera camera;
    private Viewport viewport;
    private UiInterface ui;
    private AssetManager assetManager;

    private World world;

    public Game() {
        assetManager = new AssetManager();
        loadResources();

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 500f;
        camera.update();
        viewport = new ScreenViewport(camera);

        ui = new UiInterface();
        ui.create();

        world = new World(camera);
    }

    private void loadResources() {
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.finishLoading();
    }

    public void update(float delta) {
        this.camera.update();
        //batch.setProjectionMatrix(camera.combined);

        ui.act(delta);

        world.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        world.render();

        ui.render();

        update(delta);
    }

    @Override
    public void dispose() {
        this.dispose();

        world.dispose();
    }

    @Override
    public void resize(int width, int height) {
        ui.getUiStage().getViewport().update(width, height, true);
        viewport.update(width, height);
    }
}