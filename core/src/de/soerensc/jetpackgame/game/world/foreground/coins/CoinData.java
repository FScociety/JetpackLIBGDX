package de.soerensc.jetpackgame.game.world.foreground.coins;

import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class CoinData extends MovingData {
	public CoinData(SpriteBatch spriteBatch) {
		super(spriteBatch);
	}

	public static final int coinSize = 14;
	
	int[] coins = new int[coinSize];
	
	private Texture coin;
	
	public CoinData(SpriteBatch spriteBatch, Texture coin) {
		super(spriteBatch);

		this.coin = coin;
		this.generateNew();
	}

	@Override
	public MovingData getNew() {
		return new CoinData(this.spriteBatch, this.coin);
	}

	@Override
	public void generateNew() {
		if (CoinController.cc.activePattern != null) {
			this.coins = CoinController.cc.activePattern.getLine();
		}
	}

	@Override
	public void render() {
		if (this.coins != null) {
			for (int i = 0; i < coins.length; i++) {
				if (coins[i] == 1) {
					//GameContainer.d.drawImage(this.coin, new Vector2(this.parent.position, (i-((float)this.coins.length)/2) * this.parent.parent.elementBounds.y), new Vector2(this.parent.parent.elementBounds.y));
					this.spriteBatch.draw(this.coin, this.parent.position, (-i+((float)this.coins.length)/2) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
				}
			}
		}
	}
	
	public void remove(int startingY) {
		if (startingY > coins.length-3) {
			startingY = coins.length-3;
		}

		for (int i = 0; i < 3; i++) {
			coins[i + startingY] = 0;
		}
	}
}	