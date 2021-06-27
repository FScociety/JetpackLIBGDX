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
		//for (int i = 0; i < 300; i++) {
			//TODO
			//GameContainer.d.drawImage(img, new Vector2(this.parent.position, -this.parent.parent.elementBounds.y/2+1), Vector2.add(this.parent.parent.elementBounds, 2));
		//}
		//this.spriteBatch.draw(img, this.parent.position, -this.parent.parent.elementBounds.y / 2 + 1);
		this.spriteBatch.draw(backgroundImage, (float)this.parent.position, -this.parent.parent.elementBounds.y / 2, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
	}

	@Override
	public MovingData getNew() {
		return new BackgroundData(this.backgroundImage, this.spriteBatch);
	}

	@Override
	public void generateNew() {
		
	}
}
