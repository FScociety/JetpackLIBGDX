package de.soerensc.jetpackgame.game.world.background;

import java.awt.image.BufferedImage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class BackgroundData extends MovingData {
	
	public TextureRegion backgroundImage;
	
	public BackgroundData(TextureRegion img, SpriteBatch spriteBatch) {
		super(spriteBatch);
		this.backgroundImage = img;
	}
	
	@Override
	public void render() {
		this.spriteBatch.draw(backgroundImage, (float)this.parent.position, -this.parent.parent.elementBounds.y / 2 - 60, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
	}

	@Override
	public MovingData getNew() {
		return new BackgroundData(this.backgroundImage, this.spriteBatch);
	}

	@Override
	public void generateNew() {
		
	}
}
