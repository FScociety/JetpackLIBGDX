package de.soerensc.engine.ecs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class GameObject {
    private ArrayList<GameBehaviour> components = new ArrayList<>();
    private SpriteBatch spriteBatch;

    public GameObject() {

    }

    public void addComponent(GameBehaviour gb) {
        this.components.add(gb);
    }

    public GameBehaviour getComponent(Class<? extends GameBehaviour> e) {
        for (final GameBehaviour component2 : this.components) {
            if (component2.getClass() == e || e.isInstance(component2)) {
                return component2;
            }
        }
        return null;
    }

    public void start() {
        for (GameBehaviour bg : this.components) {
            bg.start();
        }
    }

    public void update(float delta) {
        for (GameBehaviour bg : this.components) {
            bg.update(delta);
        }
    }

    public void render() {
        for (GameBehaviour bg : this.components) {
            bg.render();
        }
    }

    public void dispose() {
        for (GameBehaviour bg : this.components) {
            bg.dispose();
        }
    }

    public void setSpriteBatch(SpriteBatch spriteBatch) {
        if (this.spriteBatch == null) {
            this.spriteBatch = spriteBatch;
        } else {
            Gdx.app.error("lol", "You cannot overrite the existing spriteBatch");
        }
    }

    public SpriteBatch getSpriteBatch() {
        return this.spriteBatch;
    }
}