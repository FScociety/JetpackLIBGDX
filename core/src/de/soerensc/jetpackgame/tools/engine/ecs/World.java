package de.soerensc.jetpackgame.tools.engine.ecs;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class World {

    public static World w;

    public ArrayList<GameObject> gameObjectsInWorld = new ArrayList<>();
    public static SpriteBatch spriteBatch;

    private BitmapFont debugFont = new BitmapFont();
    private int renderCalls;

    public World() {

        if (World.w == null) {
            World.w = this;
        }

        spriteBatch = new SpriteBatch();
    }


    public final void add(GameObject obj) {
        this.gameObjectsInWorld.add(obj);
    }

    //To be overriten
    public void create() {}

    public final void start() {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.start();
        }
    }

    public final void update(float delta) {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.update(delta);
        }
    }

    public final void render() {
        spriteBatch.begin();

        for (GameObject obj : this.gameObjectsInWorld) {
            obj.render();
        }

        //debugFont.draw(spriteBatch, "RenderCalls: " + this.renderCalls +"", 0, 0);


        this.renderCalls = spriteBatch.renderCalls;

        this.renderLater();
    }

    private final void renderLater() {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.renderLater();
        }


        spriteBatch.end();
    }

    public void dispose() {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.dispose();
        }

        spriteBatch.dispose();
    }

    public void reset() {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.reset();
        }
    }
}
