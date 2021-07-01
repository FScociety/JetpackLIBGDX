package de.soerensc.jetpackgame.game.world.foreground.wall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.screen.Game;

import java.awt.*;

public class WallShadow extends GameBehaviour {

    TextureRegion wallShadow;

    public WallShadow(WallController wc) {
        this.wallShadow = wc.wallAtlas.findRegion("WallShadow");
    }

    public void render() {
        float width = Gdx.graphics.getWidth();
        width *= Game.camera.zoom;


        //TODO: eigene Texture für Shadows
        this.spriteBatch.draw(this.wallShadow, - width / 2, 0, width, 500);
    }
}
