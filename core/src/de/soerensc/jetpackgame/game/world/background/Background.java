package de.soerensc.jetpackgame.game.world.background;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	//public Texture[] images;
	public TextureAtlas backgroundAtlas;
	public TextureRegion[] images;
	public Color backgroundColor;
	
	public Background(String backgroundName, Color backgroundColor) {
		this.backgroundColor = backgroundColor;

		this.backgroundAtlas = new TextureAtlas("world/textures/backgrounds/dune/dune.atlas");

		images = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			 this.images[i] = backgroundAtlas.findRegion("l" + i);
		}
	}
}