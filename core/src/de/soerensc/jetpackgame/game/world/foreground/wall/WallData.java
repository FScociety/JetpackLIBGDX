package de.soerensc.jetpackgame.game.world.foreground.wall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class WallData extends MovingData {

    Texture wall;

    public WallData(SpriteBatch spriteBatch, Texture wall) {
        super(spriteBatch);
        this.wall = wall;
    }

    @Override
    public MovingData getNew() {
        return new WallData(this.spriteBatch, wall);
    }

    @Override
    public void generateNew() {
        //TODO: Variants
    }

    @Override
    public void render() {
        this.spriteBatch.draw(wall, this.parent.position, -this.parent.parent.elementBounds.y/2, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
    }
}