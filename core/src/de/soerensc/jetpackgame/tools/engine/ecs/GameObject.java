package de.soerensc.jetpackgame.tools.engine.ecs;

import de.soerensc.jetpackgame.tools.engine.ecs.components.Transform2D;

import java.util.ArrayList;

public class GameObject {
    private ArrayList<GameBehaviour> components = new ArrayList<>();

    public final Transform2D transform = new Transform2D();

    public GameObject() {
        this.addComponent(transform);
    }

    public void addComponent(GameBehaviour gb) {
        this.components.add(gb);

        gb.gameObject = this;
        gb.spriteBatch = World.spriteBatch;
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

    public void renderLater() {
        for (GameBehaviour bg : this.components) {
            bg.renderLater();
        }
    }

    public void dispose() {
        for (GameBehaviour bg : this.components) {
            bg.dispose();
        }
    }

    public void reset() {
        for (GameBehaviour bg : this.components) {
            bg.reset();
        }
    }

    public GameBehaviour getComponent(Class<? extends GameBehaviour> e) {
        for (final GameBehaviour component2 : this.components) {
            if (component2.getClass() == e || e.isInstance(component2)) {
                return component2;
            }
        }
        return null;
    }
}