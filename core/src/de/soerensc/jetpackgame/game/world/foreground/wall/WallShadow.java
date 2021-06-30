package de.soerensc.jetpackgame.game.world.foreground.wall;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.screen.Game;

import java.awt.*;

public class WallShadow extends GameBehaviour {

    Texture wallShadow;

    public WallShadow() {
        this.wallShadow = new Texture("world/wall/WallDefault_Shadow.png");
    }

    public void render() {
        float width = Gdx.graphics.getWidth();
        width *= Game.camera.zoom;


        //TODO: eigene Texture für Shadows
        this.spriteBatch.draw(this.wallShadow, - width / 2, 0, width, 500);
    }
}
