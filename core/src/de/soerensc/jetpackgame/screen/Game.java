package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.tools.engine.ecs.World;
import de.soerensc.jetpackgame.game.InGameWorld;
import de.soerensc.jetpackgame.game.ui.UiInterface;

import java.awt.*;

public class Game extends ScreenAdapter {

    public static Game game;

    public static float delta;

    private static boolean started = false;
    private static boolean canUpdate = true;

    private Viewport gameViewport;
    private GLProfiler profiler;

    public static OrthographicCamera camera;
    private World world;
    private UiInterface ui;

    public static Color color;

    public Game() {
        if (Game.game == null) {
            Game.game = this;
        }

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        Assets.load();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        gameViewport = new ScreenViewport(camera);
        gameViewport.apply();

        ui = new UiInterface();
        ui.create();

        world = new InGameWorld();
        world.create();

        Gdx.app.log("Game", "World created!");

        world.start();

        Game.started = true;
    }

    public void update(float delta) {

        if (!Game.started || !Game.canUpdate) { return; }

        World.spriteBatch.setProjectionMatrix(camera.combined);

        ui.act(delta);

        world.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            boolean fullScreen = Gdx.graphics.isFullscreen();
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            if (fullScreen == true) {
                this.setWindowedMode();
            } else {
                Gdx.graphics.setFullscreenMode(currentMode);
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE) && !UiInterface.loggedIn) {
            UiInterface.registerContainer.setVisible(false);
            UiInterface.userContainer.setVisible(true);
        }

        this.camera.update();

        Game.canUpdate = false;

        Game.delta = delta;
    }

    private void setWindowedMode() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Gdx.graphics.setWindowedMode((int)(screenSize.getWidth() * 0.5f), (int)(screenSize.getHeight() * 0.5f));
    }

    @Override
    public void render(float delta) {
        if (!Game.started) { return; }

        profiler.reset();

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.gameViewport.apply();

        world.render();

        Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond() +
                " | DRAWCALLS: " + profiler.getDrawCalls() +
                " | TEXTUREBINDINGS: " + profiler.getTextureBindings() +
                " | VERTICIES: " + profiler.getVertexCount().total);


        ui.render();

        Game.canUpdate = true;
    }

    @Override
    public void dispose() {
        this.dispose();

        this.world.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.zoom = (float)1080 / height;

        this.gameViewport.update(width, height);
        ui.getUiStage().getViewport().update(width, height, true);
    }

    public static boolean canUpdate() {
        return Game.canUpdate;
    }
}