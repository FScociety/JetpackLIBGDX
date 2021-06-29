package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class CoinData extends MovingData {
	public CoinData(SpriteBatch spriteBatch) {
		super(spriteBatch);
	}

	public static final int coinSize = 14;
	
	private int[] coins = new int[coinSize];
	private SpriteAnimation[] coinsAnimations = new SpriteAnimation[coinSize];

	private SpriteAnimation destroyAnimation;
	private SpriteAnimation rollingAnimation;
	
	public CoinData(SpriteBatch spriteBatch, SpriteAnimation rollingAnimation, SpriteAnimation destroyAnimation) {
		super(spriteBatch);

		this.generateNew();

		for (int i = 0; i < this.coins.length; i++) {
			this.coinsAnimations[i] = rollingAnimation.getCopy();
			this.coinsAnimations[i].setOffset(i);
			this.coinsAnimations[i].play();

			if (!rollingAnimation.isLinked()) {
				rollingAnimation.link(this.coinsAnimations[i]);
			}
		}

		this.rollingAnimation = rollingAnimation;
		this.destroyAnimation = destroyAnimation;
	}

	@Override
	public MovingData getNew() {
		return new CoinData(this.spriteBatch, this.rollingAnimation, this.destroyAnimation);
	}

	@Override
	public void generateNew() {
		if (CoinController.coinController.activePattern != null) {
			this.coins = CoinController.coinController.activePattern.getLine();
		}
	}

	@Override
	public void render() {
		if (this.coins != null) {
			for (int i = 0; i < coins.length; i++) {
				if (coins[i] == 1) {
					this.spriteBatch.draw(this.coinsAnimations[i].getCurrentFrame(), this.parent.position, (-i + ((float) this.coins.length) / 2) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
				}
			}
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < coins.length; i++) {
			if (!this.coinsAnimations[i].isRunning()) {
				this.coins[i] = 0;
			}
		}
	}
	
	public void remove(int startingY) {
		if (startingY > coins.length-3) {
			startingY = coins.length-3;
		}

		for (int i = 0; i < 3; i++) {
			//coins[i + startingY] = 0;
			SpriteAnimation destroyAnimationCopy = destroyAnimation.getCopy();
			coinsAnimations[i + startingY] = destroyAnimationCopy;
			destroyAnimationCopy.play();
			if (!destroyAnimationCopy.isLinked()) {
				destroyAnimation.link(destroyAnimationCopy);
			}
		}
	}
}	