package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.wall.WallController;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPattern;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPatternControllerBehaviour;

public class CoinController extends MovingPatternControllerBehaviour {
	public static CoinController coinController;
	public MovingLayer coinList;
	
	SpriteAnimation coinIdleAnimation, coinDestoryAnimation;
	
	public int coinSize = 50;
	
	private float time;
	private float timeBounds;

	private boolean wannaSpawnPattern = false;
	
	public CoinController() {
		//Signleton assignment
		if (CoinController.coinController == null) {
			CoinController.coinController = this;
		} else {
			return;
		}

		TextureAtlas coinAnimations = new TextureAtlas("world/textures/coins/coinAtlas.atlas");

		//Textures
		this.coinIdleAnimation = new SpriteAnimation(coinAnimations, "coinRotate");

		this.coinDestoryAnimation = new SpriteAnimation(coinAnimations, "coinDestory");
		this.coinDestoryAnimation.looping = false;
		this.coinDestoryAnimation.setFramesPerSecond(10);

		//Patterns
		this.patternPath = "world/coinPatterns";
		create(7, CoinData.coinSize);

		Gdx.app.log("CoinController", "Coin Patterns were loaded");
		
		newPattern();
	}
	
	public void start() {
		
		CoinData defaultCoin = new CoinData(this.spriteBatch, this.coinIdleAnimation, this.coinDestoryAnimation);
		coinList = new MovingLayer(defaultCoin, new Vector2(coinSize, coinSize), -1000, 1f);
		coinList.name = "CoinList";
		coinList.addInstant(43);
		//coinList.addOverTime(43);
		defaultCoin.parent = coinList.start;
	}

	@Override
	public MovingPattern newPattern() {
		int random = (int)(Math.random()*this.patterns.length);
		this.activePattern = this.patterns[random];
		this.activePattern.refresh();
		
		//System.out.println(this.activeCoinPattern + "[s size: " + this.coinPatterns.length + "; " + random);
		
		this.activePattern.newYOffset();
		return this.activePattern;
	}
	
	public void update(float delta) {
		coinList.update(delta);

		this.coinIdleAnimation.update(delta);
		this.coinDestoryAnimation.update(delta);

		/*if (this.time >= this.timeBounds) {
			if (this.activePattern.isFinished()) {
				this.time = 0;
				this.timeBounds = (float) (Math.random()+0.4f);

				if (SawController.sawController.activePattern.isFinished()) {
					this.wannaSpawnPattern = true;
				}
			}
		} else {
			this.time += delta;
		}

		if (this.wannaSpawnPattern) {
			this.newPattern();
		}*/
	}
	
	public void render() {
		coinList.render();
	}
}