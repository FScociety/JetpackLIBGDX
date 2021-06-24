package de.soerensc.jetpackgame.game.world.background;

import java.awt.Color;

import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;

public class ParalaxBackground extends GameBehaviour {
	
	public Background[] backgrounds;
	private Background activeBackground;
 	public MovingLayer bgl[];
	public int size;
	public Vector2 imageSize;
	
	public ParalaxBackground(int size) {
		this.size = size;
		
		Background backgrounds[];
	}
	
	public void start() {
		bgl = new MovingLayer[size];
		
		backgrounds = new Background[5];
		backgrounds[0] = new Background("dune", new Color(205, 227, 255));
		
		this.setBackground(backgrounds[0]);
	
	}
	
	public void setBackground(Background b) {
		this.imageSize = new Vector2(b.images[0].getWidth(), b.images[0].getHeight());
		float aspectRatio = this.imageSize.x / this.imageSize.y;
		this.imageSize.y *= 13;
		this.imageSize.x = this.imageSize.y * aspectRatio;
		
		for (int i = 0; i < size; i++) {
			BackgroundData startingData = new BackgroundData(b.images[i], this.spriteBatch);
			startingData.spriteBatch = this.spriteBatch;
			bgl[i] = new MovingLayer(startingData, imageSize, -1000, ((float)i+1) / (1+size));
			bgl[i].addInstant(2);
			startingData.parent = bgl[i].start;
		}
		
		this.activeBackground = b;
		//TODO Add Background Color
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
}