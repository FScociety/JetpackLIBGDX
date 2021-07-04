package de.soerensc.jetpackgame.game.world.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

	public TextureAtlas backgroundAtlas;
	public Color backgroundColor;
	public TextureRegion[] layers;
	
	public Background(TextureAtlas backgroundAtlas, String backgroundName, Color backgroundColor) {
		this.backgroundColor = backgroundColor;

		final int size = 3;

		this.layers = new TextureRegion[size];

		for (int i = 0; i < size; i++) {
			this.layers[i] = backgroundAtlas.findRegion(backgroundName + i);
		}

	}
}