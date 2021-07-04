package de.soerensc.jetpackgame.tools.engine.ecs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameBehaviour {

    public GameObject gameObject;
    public SpriteBatch spriteBatch;

    public void start() {}
    public void update(float delta) {}
    public void render() {}
    public void renderLater() {}
    public void dispose() {}
    public void reset() {}
}
