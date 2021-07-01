package de.soerensc.jetpackgame.game.world.foreground.wall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class WallData extends MovingData {

    public static boolean started;

    TextureAtlas wall;
    TextureRegion activeWall;
    public static TextureRegion staticActiveWall;
    public static TextureRegion filledWall, wireWall, shadowWall;

    public static final int bounds = 5;
    public static int i = bounds;
    public static boolean wallWire = false;

    public WallData(SpriteBatch spriteBatch, TextureAtlas wall) {
        super(spriteBatch);
        this.wall = wall;

        if (!WallData.started) {
            WallData.filledWall = wall.findRegion("WallDefault");
            WallData.wireWall = wall.findRegion("WallWire");
            WallData.shadowWall = wall.findRegion("WallShadow");

            WallData.staticActiveWall = WallData.filledWall;
        }

        this.activeWall = WallData.staticActiveWall;

        WallData.started = true;
    }

    @Override
    public MovingData getNew() {
        return new WallData(this.spriteBatch, wall);
    }

    @Override
    public void generateNew() {
        WallData.i --;
        if (WallData.i <= 0) {
            WallData.i = WallData.bounds;
            wallWire = !wallWire;

            System.out.println("Changed");

            if (wallWire) {
                WallData.staticActiveWall = WallData.wireWall;
            } else {
                WallData.staticActiveWall = WallData.filledWall;
            }
        }

        this.activeWall = WallData.staticActiveWall;
    }

    @Override
    public void render() {
        this.spriteBatch.draw(this.activeWall, this.parent.position - 1, -this.parent.parent.elementBounds.y/2 - 1, this.parent.parent.elementBounds.x + 2, this.parent.parent.elementBounds.y + 2);
    }

    @Override
    public void renderLater() {
        System.out.println("Rendered Later");
        this.spriteBatch.draw(WallData.shadowWall, (int)this.parent.position, (int)-this.parent.parent.elementBounds.y/2 , this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
    }
}