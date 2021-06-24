package de.soerensc.jetpackgame.game.world.background;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	
	public Texture[] images;
	public Color backgroundColor;
	
	public Background(String backgroundName, Color backgroundColor) {
		this.backgroundColor = backgroundColor;

		images = new Texture[3];
		for (int i = 0; i < 3; i++) {
			String loadingPath = "world/textures/backgrounds/" + backgroundName + "/l" + i + ".png";

			System.out.println(loadingPath);

			 this.images[i] = new Texture(loadingPath);
		}
	}
}