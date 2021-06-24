package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.soerensc.engine.ecs.World;
import de.soerensc.jetpackgame.game.InGameWorld;
import de.soerensc.jetpackgame.game.UiInterface;

public class Game extends ScreenAdapter {

    private static boolean started = false;

    private OrthographicCamera camera;
    private Viewport viewport;
    private UiInterface ui;
    public static AssetManager assetManager;

    private World world;

    public Game() {
        assetManager = new AssetManager();
        loadResources();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        viewport = new ScreenViewport(camera);

        ui = new UiInterface();
        ui.create();

        world = new InGameWorld();
        world.create();

        Gdx.app.log("Game", "World created!");

        world.start();


        Game.started = true;
    }

    private void loadResources() {
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.finishLoading();

        Gdx.app.log("Assets", "Loaded resources!");
    }

    public void update(float delta) {
        if (!Game.started) { return; }
        World.spriteBatch.setProjectionMatrix(camera.combined);


        this.camera.update();

        ui.act(delta);

        world.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void render(float delta) {
        if (!Game.started) { return; }


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        world.render();

        //ui.render();

        update(delta);
    }

    @Override
    public void dispose() {
        this.dispose();

        this.world.dispose();
    }

    @Override
    public void resize(int width, int height) {
        ui.getUiStage().getViewport().update(width, height, true);
        viewport.update(width, height);
    }
}