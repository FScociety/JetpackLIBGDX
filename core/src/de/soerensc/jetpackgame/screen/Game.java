package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.soerensc.engine.ecs.World;
import de.soerensc.jetpackgame.game.InGameWorld;
import de.soerensc.jetpackgame.game.UiInterface;

public class Game extends ScreenAdapter {

    public static float delta;

    private static boolean started = false;
    private static boolean canUpdate = true;

    private Viewport viewport;
    public static AssetManager assetManager;
    private GLProfiler profiler;

    private OrthographicCamera camera;
    private World world;
    private UiInterface ui;

    public Game() {

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

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
        if (!Game.started || !Game.canUpdate) { return; }

        World.spriteBatch.setProjectionMatrix(camera.combined);

        ui.act(delta);

        world.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        this.camera.update();

        Game.canUpdate = false;

        Game.delta = delta;
    }

    @Override
    public void render(float delta) {
        if (!Game.started) { return; }

        profiler.reset();

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.render();

        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond() +
                " | DRAWCALLS: " + profiler.getDrawCalls() +
                " | TEXTUREBINDINGS: " + profiler.getTextureBindings() +
                " | VERTICIES: " + profiler.getVertexCount().total);
        //ui.render();

        Game.canUpdate = true;
    }

    @Override
    public void dispose() {
        this.dispose();

        this.world.dispose();
    }

    @Override
    public void resize(int width, int height) {
        ui.getUiStage().getViewport().update(width, height, true);
        camera.zoom = (float)1080 / height;
        viewport.update(width, height);
    }

    public static boolean canUpdate() {
        return Game.canUpdate;
    }
}