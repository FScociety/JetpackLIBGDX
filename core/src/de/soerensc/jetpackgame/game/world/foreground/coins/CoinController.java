package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.wall.WallController;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPattern;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPatternControllerBehaviour;

public class CoinController extends MovingPatternControllerBehaviour {
	public static CoinController cc;
	public MovingLayer coinList;
	
	Texture coin;
	
	public int coinSize = 50;
	
	private float time;
	private float timeBounds;

	private WallController wc;
	
	public CoinController(WallController wc) {
		//Signleton assignment
		if (CoinController.cc == null) {
			CoinController.cc = this;
		} else {
			return;
		}

		//Textures
		this.coin = new Texture("world/textures/coin.png");

		//Patterns
		this.patternPath = "world/coinPatterns";
		create(7);

		Gdx.app.log("CoinController", "Coin Patterns were loaded");
		
		newPattern();
	}
	
	public void start() {
		
		CoinData defaultCoin = new CoinData(this.spriteBatch, this.coin);
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
		if (this.time >= this.timeBounds) {
			if (this.activePattern.isFinished()) {
				this.newPattern();
				this.time = 0;
				this.timeBounds = (float) (Math.random()+0.4f);
			}
		} else {
			this.time += delta;
		}

		coinList.update(delta);
	}
	
	public void render() {
		coinList.render();
	}
}