package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.ui.UiInterface;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawData;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;

public class PlayerController extends GameBehaviour {
	
	public static PlayerController p;
	
	public static int borders = (int)(SawController.sawController.sawSize * 3.9f);
	
	int sizeY = 200; 
	
	boolean ceilCollision, floorCollision;
	boolean wannaMoveUp = false;
	
	float velocityY = 0;
	float forceY = 9.81f;

	private CoinData oldCoinData;

	private boolean wannaStart = false;
	private boolean started = false;
	private boolean slowingDown = false;
	private boolean dead;
	
	public static float movingSpeed = 0;
	public static float meters;
	public static float coins;
	
	private SpriteAnimation playerRunningAnimation, playerJumpingAnimation, playerFallingAnimation, playerLandingAnimation;
	private SpriteAnimation activeAnim;
	private Texture entityShadow;
	
	private CoinController cc;
	
	public PlayerController(CoinController cc) {
		PlayerController.p = this;
		
		this.cc = cc;

		//this.testImage = new Texture("world/textures/player/test.png");

		TextureAtlas playerAnimationAtlas = new TextureAtlas("world/textures/newPlayerAnim/playerAnimAtlas.atlas");

		this.playerRunningAnimation = new SpriteAnimation(playerAnimationAtlas, "running");
		this.activeAnim = this.playerRunningAnimation;
		this.playerRunningAnimation.play();
		this.playerRunningAnimation.setFramesPerSecond(15);

		this.playerJumpingAnimation = new SpriteAnimation(playerAnimationAtlas, "jump");
		this.playerJumpingAnimation.setFramesPerSecond(30);
		this.playerJumpingAnimation.looping = false;

		this.playerFallingAnimation = new SpriteAnimation(playerAnimationAtlas, "fall");
		this.playerFallingAnimation.setFramesPerSecond(30);
		this.playerFallingAnimation.looping = false;

		this.playerLandingAnimation = new SpriteAnimation(playerAnimationAtlas, "land");
		this.playerLandingAnimation.setFramesPerSecond(30);
		this.playerLandingAnimation.looping = false;

		entityShadow = new Texture("world/EntityShadow.png");
	}

	@Override
	public void start() {
		this.gameObject.transform.position.y = -1000;
	}

	@Override
	public void update(float delta) {

		float posY = this.gameObject.transform.position.y;

		if ((this.wannaStart == true && this.started == false) || this.started == true) {

			if (posY - sizeY / 2 < -borders && this.ceilCollision == false && !this.wannaStart) {
				ceilCollision = true;
				this.velocityY = 0;
				this.gameObject.transform.position.y = -borders + sizeY / 2;
			} else if (posY - sizeY / 2 > -borders) {
				ceilCollision = false;
				if (posY + sizeY / 2 > borders && this.floorCollision == false) {
					floorCollision = true;
					this.velocityY = 0;
					this.gameObject.transform.position.y = borders - sizeY / 2;

					if (this.wannaStart == true) {
						this.realStart();
					}
				} else if (posY + sizeY / 2 < borders) {
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

			if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.ceilCollision && UiInterface.loggedIn && !this.slowingDown) {

				if (!this.wannaMoveUp && this.floorCollision) {
					this.velocityY = -750;
					this.floorCollision = false;
				}

				if (this.activeAnim != this.playerJumpingAnimation) {
					this.activeAnim = this.playerJumpingAnimation;

					if (this.activeAnim == this.playerFallingAnimation) {
						this.activeAnim.play(8);
					} else {
						this.activeAnim.play();
					}
				}

				this.wannaMoveUp = true;
				this.velocityY -= (float) (this.forceY * delta * 400);
				this.gameObject.transform.rotation = (float) Math.random() * 20;
			} else {
				this.wannaMoveUp = false;
			}
		}

			//Animation Stuff

			this.activeAnim.update(delta);

			float fpsMultiplie = this.gameObject.transform.position.y + sizeY / 2 + PlayerController.borders / 2;
			fpsMultiplie = PlayerController.borders / fpsMultiplie;

			this.playerRunningAnimation.setFramesPerSecond(10 * PlayerController.movingSpeed);

			if (posY + sizeY / 2 + 200 > borders && !this.floorCollision) {
				if (this.activeAnim == this.playerFallingAnimation && this.activeAnim != this.playerLandingAnimation) {
					this.activeAnim = this.playerLandingAnimation;
					this.activeAnim.play();
				}
			} else if (this.floorCollision) {
				//System.out.println((this.activeAnim == this.playerLandingAnimation) + " " + this.playerLandingAnimation.isRunning());
				if (this.activeAnim == this.playerLandingAnimation && !this.playerLandingAnimation.isRunning() && this.activeAnim != this.playerRunningAnimation) {
					this.activeAnim = this.playerRunningAnimation;
					this.activeAnim.play();
				}
			} else if (!this.floorCollision && !this.ceilCollision && this.velocityY > 0 && this.activeAnim != this.playerFallingAnimation) {
				this.activeAnim = this.playerFallingAnimation;
				this.activeAnim.play();
			}


			//Coin Collision
			CoinData middleCoin = (CoinData) cc.coinList.start.get(20).data;
			if (middleCoin != null && this.oldCoinData != middleCoin) {
				float coinPos = posY;
				coinPos /= cc.coinSize;
				coinPos += CoinData.coinSize / 2;

				if (coinPos >= 1) {
					middleCoin.remove((int) coinPos);
				}
			}

			this.oldCoinData = middleCoin;

			//Saw Collision
			SawData middleSaw = (SawData) SawController.sawController.sawList.start.get(10).data;
			if (middleSaw != null) {
				float sawPos = posY;
				sawPos /= SawController.sawController.sawSize;
				sawPos += SawData.sawSize / 2;

				if (middleSaw.isColliding(Math.round(sawPos))) {
						this.slowingDown = true;

						UiInterface.deadTable.setVisible(true);

						UiInterface.deadMeters.setText("You ran: " + (float)Math.round(PlayerController.meters*10)/10 + "m");
						UiInterface.deadCoins.setText("and collected: " + PlayerController.coins + " Coins");

						UiInterface.revive.setDisabled(PlayerController.coins < 100 ? true : false);
						System.out.println(UiInterface.revive.isDisabled());
				}
			}

		if (this.slowingDown) {
			if (PlayerController.movingSpeed < 0.1f) {
				if (!this.dead) {
					PlayerController.movingSpeed = 0;
					this.dead = true;
				}
			} else {
				PlayerController.movingSpeed -= delta;
			}
		} else {
			PlayerController.movingSpeed *= 1 + delta / 100;
		}

		if (this.started) {
			PlayerController.meters += PlayerController.movingSpeed * delta * 2;
			UiInterface.meters.setText("Distance:" + (float)Math.round(PlayerController.meters * 100) / 100 + "m");
		}

		if (UiInterface.loggedIn && !this.wannaStart && !this.started) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				play();
				UiInterface.uiInterface.loggedInTable.setVisible(false);
			}
		}
	}

	private void play() {
		this.wannaStart = true;
	}

	private void realStart() {
		this.ceilCollision = false;
		this.floorCollision = true;
		this.wannaStart = false;
		this.started = true;
		PlayerController.movingSpeed = 1;

		UiInterface.titleTable.setVisible(false);

		//this.activeAnim = this.playerLandingAnimation;
		//this.playerLandingAnimation.play();
	}

	@Override
	public void render() {

		if (this.dead) return;

		//Shadows HUUHUHU
		float mutliplier = this.gameObject.transform.position.y;
		mutliplier += borders;
		mutliplier = mutliplier / (2 * borders) + 0.5f;
		mutliplier *= 2;

		float newWidth = this.entityShadow.getWidth() * mutliplier;
		float newHeigth = this.entityShadow.getHeight() * mutliplier;

		this.spriteBatch.draw(this.entityShadow, - newWidth / 2f + 50, -365 - newHeigth / 2, newWidth, newHeigth * 0.65f);

		Sprite render = new Sprite(this.activeAnim.getCurrentFrame());

		render.setPosition(this.gameObject.transform.position.x - sizeY, -sizeY/2 - this.gameObject.transform.position.y);
		render.setSize(sizeY * 2, sizeY * 2);

		if (this.wannaMoveUp) {
			//render.setRotation((float) (Math.random() - 0.5f) * 10);
		}

		render.draw(this.spriteBatch);
	}

	@Override
	public void dispose() {
		System.out.println("Me gpoing way");
	}

	@Override
	public void reset() {
		this.dead = false;
		this.slowingDown = false;
		this.wannaStart = false;
		this.started = false;
		this.gameObject.transform.position.y = -1000;
	}
}