package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class CoinData extends MovingData {
	public CoinData(SpriteBatch spriteBatch) {
		super(spriteBatch);
	}

	public static final int coinSize = 14;
	
	private Coin[] coins = new Coin[coinSize];

	private SpriteAnimation destroyAnimation;
	private SpriteAnimation rollingAnimation;
	
	public CoinData(SpriteBatch spriteBatch, SpriteAnimation rollingAnimation, SpriteAnimation destroyAnimation) {
		super(spriteBatch);

		/*for (int i = 0; i < this.coins.length; i++) {
			this.coinsAnimations[i] = rollingAnimation.getCopy();
			this.coinsAnimations[i].setOffset(i);
			this.coinsAnimations[i].play();

			if (!this.coinsAnimations[i].isLinked()) {
				rollingAnimation.link(this.coinsAnimations[i]);
			}
		}*/

		for (int i = 0; i < this.coins.length; i++) {
			this.coins[i] = new Coin(i, rollingAnimation, destroyAnimation);
		}

		this.generateNew();

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
			int[] intCoins = CoinController.coinController.activePattern.getLine();

			for (int i = 0; i < intCoins.length; i++) {
				this.coins[i].newCoin(intCoins[i]);
			}
		}
	}

	@Override
	public void render() {
		if (this.coins != null) {
			for (int i = 0; i < coins.length; i++) {
				if (coins[i].isActive()) {
					this.spriteBatch.draw(this.coins[i].getImage(), this.parent.position, (-i + ((float) this.coins.length) / 2) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
				}
			}
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < coins.length; i++) {
			/*if (!this.coinsAnimations[i].isRunning()) {
				this.coins[i] = 0;
				this.coinsAnimations[i] = this.rollingAnimation;
			}*/
			this.coins[i].update();
		}
	}
	
	public void remove(int startingY) {
		if (startingY > coins.length-3) {
			startingY = coins.length-3;
		}

		for (int i = 0; i < 3; i++) {
			/*SpriteAnimation destroyAnimationCopy = destroyAnimation.getCopy();
			coins[i + startingY] = destroyAnimationCopy;
			destroyAnimationCopy.play();
			if (!destroyAnimationCopy.isLinked()) {
				destroyAnimation.link(destroyAnimationCopy);
			}*/
			coins[i + startingY].setActive(0);

			if (coins[i + startingY].isActive()) {
				//UiInterface.coins++;
				//UiInterface.currentCoins.setText("Coins: " + UiInterface.coins);
			}
		}
	}
}	