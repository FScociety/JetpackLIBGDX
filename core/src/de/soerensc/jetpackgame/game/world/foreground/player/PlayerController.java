package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.UiInterface;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawData;
import de.soerensc.ponggame.objects.Player;

public class PlayerController extends GameBehaviour {
	
	public static PlayerController p;
	
	public static int borders = 450;
	
	int sizeY = 200; 
	
	boolean ceilCollision, floorCollision;
	
	float velocityY = 0;
	public static float velX;
	float forceY = 9.81f;

	private boolean started = false;
	
	public static float movingSpeed = 0;
	
	private Texture testImage;
	
	private CoinController cc;
	
	public PlayerController(CoinController cc) {
		PlayerController.p = this;
		
		this.cc = cc;

		this.testImage = new Texture("world/textures/player/test.png");
	}
	
	public void update(float delta) {
		
		float posY = this.gameObject.transform.position.y;
		if (posY-sizeY/2 < -borders && this.ceilCollision == false) {
			ceilCollision = true;
			this.velocityY = 0;
			this.gameObject.transform.position.y = -borders + sizeY/2;
		} else if (posY-sizeY/2 > -borders) {
			ceilCollision = false;
			if (posY+sizeY/2 > borders && this.floorCollision == false) {
				floorCollision = true ;
				this.velocityY = 0;
				this.gameObject.transform.position.y = borders - sizeY/2;
			} else if (posY+sizeY/2 < borders) {
				floorCollision = false;
			}
		}
		
		if (!this.floorCollision) {
			this.velocityY += (float) (this.forceY * delta * 200);
		}
		
		if ((!this.floorCollision && !this.ceilCollision) ||
			(this.floorCollision && this.velocityY < 0) ||
			(this.ceilCollision && this.velocityY > 0)) {
				this.gameObject.transform.position.add(0, (this.velocityY * delta));
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.ceilCollision) {
			this.velocityY -= (float) (this.forceY * delta * 400);
			this.gameObject.transform.rotation = (float)Math.random() * 20;
			
			velX = 2;
		} else {
			velX = 1;
		}
		
		

		//Coin Collision
		CoinData middleCoin = (CoinData) cc.coinList.start.get(20).data;
		if (middleCoin != null) {
			posY /= cc.coinSize;
			posY += CoinData.coinSize/2;

			middleCoin.remove((int) posY);
		}


		//TODO: Anspassen

		/*//SawCollision
		SawData middleSaw = (SawData) cc.coinList.start.get(10).data;
		if (middleSaw != null) {
			posY /= cc.coinSize;
			posY += CoinData.coinSize/2;

			middleCoin.remove((int) posY);
		}

		PlayerController.movingSpeed *= 1 + delta / 100;*/

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !started){
			this.started = true;
			PlayerController.movingSpeed = 1;
			UiInterface.playText.setVisible(false);
		}
	}

	@Override
	public void render() {
		this.spriteBatch.draw(testImage, -sizeY/4 + this.gameObject.transform.position.x, -sizeY/2 - this.gameObject.transform.position.y, sizeY/2, sizeY);
	}

	@Override
	public void dispose() {
		System.out.println("Me gpoing way");
	}
}