package de.soerensc.ponggame.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.soerensc.ponggame.helper.Const;
import de.soerensc.ponggame.objects.Ball;
import de.soerensc.ponggame.objects.Player;
import de.soerensc.ponggame.objects.PlayerAI;
import de.soerensc.ponggame.objects.Wall;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private GameContactListener gameContactListener;
    private Viewport viewport;

    //Game-Objects
    private Player player;
    private PlayerAI playerAI;
    private Ball ball;
    private Wall wallTop, wallBottom;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.gameContactListener = new GameContactListener(this);
        this.world.setContactListener(this.gameContactListener);

        this.viewport = new FitViewport(960, 550, camera);

        this.player = new Player(16, Boot.INSTANCE.getScreenHeight() / 2, this);
        this.playerAI = new PlayerAI(950 - 16, Boot.INSTANCE.getScreenHeight() / 2, this);
        this.ball = new Ball(this);

        this.wallTop = new Wall(32, this);
        this.wallBottom = new Wall(Boot.INSTANCE.getScreenHeight() - 32, this);
    }

    public void update(float delta) {
        world.step(1/60.0f, 6, 2);

        this.camera.update();
        this.player.update();
        this.playerAI.update();
        this.ball.update();

        batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            this.ball.reset();
        }
    }

    public Ball getBall() {
        return ball;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        this.player.render(batch);
        this.playerAI.render(batch);
        this.ball.render(batch);
        this.wallTop.render(batch);
        this.wallBottom.render(batch);

        batch.end();

        //this.box2DDebugRenderer.render(world, camera.combined.scl(Const.PPM));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerAI getPlayerAI() {
        return playerAI;
    }
}