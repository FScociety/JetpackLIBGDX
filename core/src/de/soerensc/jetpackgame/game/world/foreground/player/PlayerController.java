package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.ui.UiInterface;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawData;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;
import sun.security.provider.ConfigFile;

public class PlayerController extends GameBehaviour {
	
	public static PlayerController p;
	
	public static int borders = 375;
	
	int sizeY = 200; 
	
	boolean ceilCollision, floorCollision;
	boolean wannaMoveUp = false;
	
	float velocityY = 0;
	float forceY = 9.81f;

	private boolean started = false;
	
	public static float movingSpeed = 0;
	
	private SpriteAnimation playerRunningAnimation, playerJumpingAnimation, playerFallingAnimation;
	private SpriteAnimation activeAnim;
	private Texture entityShadow;
	
	private CoinController cc;
	
	public PlayerController(CoinController cc) {
		PlayerController.p = this;
		
		this.cc = cc;

		//this.testImage = new Texture("world/textures/player/test.png");

		TextureAtlas playerAnimationAtlas = new TextureAtlas("world/textures/player/playerAnimAtlas.atlas");

		this.playerRunningAnimation = new SpriteAnimation(playerAnimationAtlas, "runningIdle000");
		this.activeAnim = this.playerRunningAnimation;
		this.playerRunningAnimation.play();
		this.playerRunningAnimation.setFramesPerSecond(15);

		this.playerJumpingAnimation = new SpriteAnimation(playerAnimationAtlas, "jump00");
		this.playerJumpingAnimation.setFramesPerSecond(15);
		this.playerJumpingAnimation.looping = false;

		this.playerFallingAnimation = new SpriteAnimation(playerAnimationAtlas, "land00");
		this.playerFallingAnimation.looping = false;

		entityShadow = new Texture("world/EntityShadow.png");
	}
	
	public void update(float delta) {

		this.activeAnim.update(delta);

		float fpsMultiplie = this.gameObject.transform.position.y + sizeY / 2 + PlayerController.borders / 2;
		fpsMultiplie = PlayerController.borders /  fpsMultiplie;

		/*if (!this.playerJumpingAnimation.isRunning() && this.playerJumpingAnimation == this.activeAnim && !this.wannaMoveUp) {
			this.activeAnim = this.playerFallingAnimation;
			this.playerJumpingAnimation.setFramesPerSecond(fpsMultiplie * 200);
			this.activeAnim.play();
		} else if (!this.playerFallingAnimation.isRunning() && this.playerFallingAnimation == this.activeAnim) {
			this.activeAnim = this.playerRunningAnimation;
			this.activeAnim.play();
		}*/

		if (this.floorCollision && this.activeAnim != this.playerRunningAnimation) {
			this.activeAnim = this.playerRunningAnimation;
			this.activeAnim.play();
		}
		
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
			if (!this.wannaMoveUp && this.floorCollision) {
				this.velocityY = -750;
				this.floorCollision = false;
			}

			if (this.activeAnim != this.playerJumpingAnimation) {
				this.activeAnim = this.playerJumpingAnimation;
				this.activeAnim.play();
			}

			this.wannaMoveUp = true;
			this.velocityY -= (float) (this.forceY * delta * 400);
			this.gameObject.transform.rotation = (float)Math.random() * 20;
		} else {
			this.wannaMoveUp = false;
		}
		
		

		//Coin Collision
		CoinData middleCoin = (CoinData) cc.coinList.start.get(20).data;
		if (middleCoin != null) {
			posY /= cc.coinSize;
			posY += CoinData.coinSize/2;

			middleCoin.remove((int) posY);
		}

		SawData middleSaw = (SawData) SawController.sawController.sawList.start.get(5).data;
		if (middleSaw != null) {
			posY /= cc.coinSize * 2;
			posY += CoinData.coinSize;

			if (middleSaw.isColliding((int) posY)) {
				System.out.println("DEAD!!!!!!");
			}
		}



		PlayerController.movingSpeed *= 1 + delta / 100;

		if (UiInterface.loggedIn) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				play();
				UiInterface.uiInterface.loggedInTable.setVisible(false);
			}
		}
	}

	public static void play() {
		if (PlayerController.p.started) { return; }
		PlayerController.p.started = true;
		PlayerController.movingSpeed = 1;

	}

	@Override
	public void render() {

		//Shadows HUUHUHU
		float mutliplier = this.gameObject.transform.position.y;
		mutliplier += borders;
		mutliplier = mutliplier / (2 * borders) + 0.5f;
		mutliplier *= 2;

		float newWidth = this.entityShadow.getWidth() * mutliplier;
		float newHeigth = this.entityShadow.getHeight() * mutliplier;

		this.spriteBatch.draw(this.entityShadow, - newWidth / 2f + 50, -365 - newHeigth / 2, newWidth, newHeigth * 0.65f);

		Sprite render = new Sprite(this.activeAnim.getCurrentFrame());

		render.setPosition(-sizeY/4 + this.gameObject.transform.position.x, -sizeY/2 - this.gameObject.transform.position.y);
		render.setSize(sizeY, sizeY*2);

		if (this.wannaMoveUp) {
			render.setRotation((float) (Math.random() - 0.5f) * 10);
		}

		render.draw(this.spriteBatch);
	}

	@Override
	public void dispose() {
		System.out.println("Me gpoing way");
	}
}