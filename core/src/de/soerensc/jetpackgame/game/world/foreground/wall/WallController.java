package de.soerensc.jetpackgame.game.world.foreground.wall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;

public class WallController extends GameBehaviour {

    public MovingLayer wallList;

    private Texture wallTexture;
    private Vector2 imageSize;

    public WallController() {
        wallTexture = new Texture("world/wall/WallDefault.png");
        imageSize = new Vector2(wallTexture.getWidth(), wallTexture.getHeight());
    }

    @Override
    public void start() {
        WallData defaultWall = new WallData(this.spriteBatch, wallTexture);

        wallList = new MovingLayer(defaultWall, new Vector2(imageSize.x * 10, imageSize.y * 10), -1000, 0.9f);
        wallList.name = "WallList";
        wallList.addInstant(40);
        defaultWall.parent = wallList.start;
    }

    @Override
    public void update(float delta) {
        wallList.update(delta);
    }

    @Override
    public void render() {
        wallList.render();
    }
}