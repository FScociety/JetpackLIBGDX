package de.soerensc.jetpackgame.game.world.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.screen.Game;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;

public class ParalaxBackground extends GameBehaviour {

	public static ParalaxBackground background;
	
	public Background[] backgrounds;
	public MovingLayer bgl[];
	public int size;
	public Vector2 imageSize;

	private int changeCounter = 0;
	
	public ParalaxBackground(int size) {
		this.size = size;
		ParalaxBackground.background = this;
	}
	
	public void start() {
		bgl = new MovingLayer[size];

		TextureAtlas textureAtlas = new TextureAtlas("world/textures/backgrounds/backgrounds.atlas");

		backgrounds = new Background[5];
		backgrounds[0] = new Background(textureAtlas, "dune", new Color(205, 227, 255, 1));
		backgrounds[1] = new Background(textureAtlas, "city", new Color(0, 0, 0, 1));
		backgrounds[2] = new Background(textureAtlas, "mountain", new Color(153, 204, 255, 1));

		this.setBackground(backgrounds[0]);
		this.changed();
	}
	
	public void setBackground(Background b) {
		this.imageSize = new Vector2(b.layers[0].getRegionWidth(), b.layers[0].getRegionHeight());
		float aspectRatio = this.imageSize.x / this.imageSize.y;
		this.imageSize.y *= 6;
		this.imageSize.x = this.imageSize.y * aspectRatio;
		
		for (int i = 0; i < size; i++) {
			BackgroundData startingData = new BackgroundData(b.layers[i], this.spriteBatch);
			startingData.spriteBatch = this.spriteBatch;
			bgl[i] = new MovingLayer(startingData, imageSize, -1000, ((float)i+1) / (1+size));
			bgl[i].addInstant(3);
			startingData.parent = bgl[i].start;
		}

		Game.color = b.backgroundColor;
	}
	
	public void update(float delta) {
		for (MovingLayer movingLayer : this.bgl) {
			movingLayer.update(delta);
		}
	}
	
	public void render() {
		for (MovingLayer movingLayer : this.bgl) {
			movingLayer.render();
		}
	}

	public void changed() {
		changeCounter++;

		if (changeCounter >= 2) {
			this.setBackground(this.backgrounds[(int)(Math.round(Math.random() * 2))]);
			System.out.println("Got new Background");

			 this.changeCounter = 0;
		}
	}
}