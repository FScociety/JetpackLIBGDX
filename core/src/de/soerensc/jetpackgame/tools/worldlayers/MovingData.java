package de.soerensc.jetpackgame.tools.worldlayers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovingData {

    public MovingElement parent;
    public SpriteBatch spriteBatch;

    public MovingData(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    public MovingData getNew() { return null; }

    public void generateNew() {}

    public void update() {}

    public void render() {}

    public void renderLater() {}
}