package de.soerensc.engine.ecs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class World {

    public ArrayList<GameObject> gameObjectsInWorld = new ArrayList<>();
    public static SpriteBatch spriteBatch;

    public World() {
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

        spriteBatch.end();
    }

    public void dispose() {
        for (GameObject obj : this.gameObjectsInWorld) {
            obj.dispose();
        }

        spriteBatch.dispose();
    }
}
