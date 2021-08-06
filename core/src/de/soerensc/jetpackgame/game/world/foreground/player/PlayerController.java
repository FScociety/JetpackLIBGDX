package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.screen.Game;
import de.soerensc.jetpackgame.tools.animation.AnimationController;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.ui.UiInterface;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawData;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;

public class PlayerController extends GameBehaviour {
	
	public static PlayerController p;

	//CONFIG
	public final static int borders = (int)(SawController.sawController.sawSize * 3.9f);
	private final static int sizeY = 200;
	private final static int startingPosY = -1000;

	//COLLISION
	boolean ceilCollision, floorCollision;
	boolean wannaMoveUp = false;
	float velocityY = 0;
	float forceY = 9.81f;
	float posY;

	private int state = 0;
	private final static int NOT_STARTED = 0;
	private final static int STARTING = 1, STARTED = 2;
	private final static int DYING = -1, DEAD = -2;

	public static float movingSpeed = 0;
	public static float meters;
	public static float coins;

	//Animation
	private AnimationController playerAnimation;
	private SpriteAnimation playerRunningAnimation;
	private TextureAtlas playerAnimationAtlas;
	
	public PlayerController() {
		//SINGELTON INSTANCE
		if (PlayerController.p == null) {
			PlayerController.p = this;
		} else {
			return;
		}

		//Get the PlayerAnimationAtlas
		this.playerAnimationAtlas = Assets.manager.get(Assets.playerAtlas);
	}

	@Override
	public void start() {
		this.gameObject.transform.position.y = PlayerController.startingPosY;

		this.playerAnimation = (AnimationController) this.gameObject.getComponent(AnimationController.class);

		//loading the 'RUNNING' Animation
		playerRunningAnimation = new SpriteAnimation(playerAnimationAtlas, "running");
		playerRunningAnimation.setFramesPerSecond(15);
		this.playerAnimation.addAnimation(playerRunningAnimation);
		this.playerAnimation.play("running");

		//loading the 'FALLING' Animation
		SpriteAnimation playerFallingAnimation = new SpriteAnimation(playerAnimationAtlas, "falling");
		playerFallingAnimation.setFramesPerSecond(30);
		playerFallingAnimation.looping = true;
		this.playerAnimation.addAnimation(playerFallingAnimation);

		//loading the 'FALL' Animation
		SpriteAnimation playerFallAnimation = new SpriteAnimation(playerAnimationAtlas, "fall");
		playerFallAnimation.setFramesPerSecond(30);
		playerFallAnimation.looping = false;
		playerFallAnimation.setFollowingAnimation(playerFallingAnimation);
		this.playerAnimation.addAnimation(playerFallAnimation);

		//loading the 'LANDING' Animation
		SpriteAnimation playerLandingAnimation = new SpriteAnimation(playerAnimationAtlas, "land");
		playerLandingAnimation.setFramesPerSecond(30);
		playerLandingAnimation.looping = false;
		playerLandingAnimation.setFollowingAnimation(playerRunningAnimation);
		this.playerAnimation.addAnimation(playerLandingAnimation);

		//loading the 'JUMPING' Animation
		SpriteAnimation playerJumpingAnimation = new SpriteAnimation(playerAnimationAtlas, "jumping");
		playerJumpingAnimation.setFramesPerSecond(30);
		playerJumpingAnimation.looping = true;
		this.playerAnimation.addAnimation(playerJumpingAnimation);

		//loading the 'JUMPING' Animation
		SpriteAnimation playerJumpAnimation = new SpriteAnimation(playerAnimationAtlas, "jump");
		playerJumpAnimation.setFramesPerSecond(30);
		playerJumpAnimation.looping = false;
		playerJumpAnimation.setFollowingAnimation(playerJumpingAnimation);
		this.playerAnimation.addAnimation(playerJumpAnimation);
		//playerJumpingAnimation.listenerMethod = PlayerController::AnimationFinish;
	}

	@Override
	public void update(float delta) {
		//Get the current position
		posY = this.gameObject.transform.position.y;

		//if the game is NOT NOT started
		if (this.state > PlayerController.NOT_STARTED || this.state == PlayerController.DEAD) {
			//if the Player is colliding with the ceiling
			//AND if this is the first time
			//AND Game has been started => Can fall through ceiling in the beginning
			if (posY - sizeY / 2 <= -borders && this.ceilCollision == false && this.state == PlayerController.STARTED) {
				ceilCollision = true;
				this.velocityY = 0;

				//FIX Collision accuracy
				this.gameObject.transform.position.y = -borders + sizeY / 2;
			//if Player is not colliding with the ceiling
			} else if (posY - sizeY / 2 > -borders) {
				ceilCollision = false;

				//if Player is colliding with the floor
				//AND if this is the first time
				//TODO:
				if (posY + sizeY / 2 > borders && this.floorCollision == false) {
					floorCollision = true;
					this.velocityY = 0;

					//FIX Collision accuracy
					this.gameObject.transform.position.y = borders - sizeY / 2;

					//if Game is in STARTING state
					if (this.state == PlayerController.STARTING) {
						this.realStart();
					}

				} else {
					if (posY + sizeY / 2 < borders) {
						floorCollision = false;

						if (posY + sizeY / 2 + 50 > borders && !this.wannaMoveUp) {
							this.playerAnimation.play("land");
						}
					}
				}
			}

			//if NOT colliding with floor
			//AND NOT Playing Space
			if (!this.floorCollision && !Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				//Apply Gravity
				this.velocityY += (float) (this.forceY * delta * 200);

				if (this.playerAnimation.isPlaying("jumping") || this.playerAnimation.isPlayed("jump")) {
					this.playerAnimation.play("fall");
				}
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.ceilCollision && UiInterface.loggedIn && this.state == PlayerController.STARTED) {
			if (!this.wannaMoveUp && this.floorCollision) {
				this.velocityY = -750;
				this.floorCollision = false;
				this.playerAnimation.play("jump");
			} else if (this.wannaMoveUp && !this.floorCollision) {
				this.playerAnimation.play("jumping");
			}

			this.wannaMoveUp = true;
			this.velocityY -= (float) (this.forceY * delta * 400);

		} else {
			this.wannaMoveUp = false;
		}

		//if NOT colliding with floor OR ceiling
		//OR colliding with floor, BUT wanna move up
		//OR colliding with ceiling, BUT wanna move down
		if ((!this.floorCollision && !this.ceilCollision) || (this.floorCollision && this.velocityY < 0) || (this.ceilCollision && this.velocityY > 0)) {
			this.gameObject.transform.position.add(0, (this.velocityY * delta));
		}

		//if Dying
		if (this.state == PlayerController.DYING) {
			//if Moving speed is very small
			if (PlayerController.movingSpeed < 0.1f) {
				//if happening for the first time
				if (this.state != PlayerController.DEAD) {
					PlayerController.movingSpeed = 0;
					this.state = PlayerController.DEAD;
				}
			//Moving speed can be subtracted
			} else {
				PlayerController.movingSpeed -= delta;
			}
		//not dying
		} else if (this.state == PlayerController.STARTED) {
			PlayerController.movingSpeed *= 1 + delta / 100;
		}

		/*if (this.started) {
			PlayerController.meters += PlayerController.movingSpeed * delta * 2;
			UiInterface.meters.setText("Distance:" + (float)Math.round(PlayerController.meters * 100) / 100 + "m");
		}*/

		if (UiInterface.loggedIn && this.state == PlayerController.NOT_STARTED) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				play();
				UiInterface.uiInterface.loggedInTable.setVisible(false);
			}
		}

		tryCollidingWithCoins(posY);
		tryCollidingWithSaws(posY);
	}

	private void tryCollidingWithCoins(float posY) {
		for (int i = 19; i < 21; i++) {
			CoinData middleCoin = (CoinData) CoinController.coinController.coinList.start.get(i).data;
			if (middleCoin != null) {
				float coinPos = posY;
				coinPos /= CoinController.coinController.coinSize;
				coinPos += CoinData.coinSize / 2;

				if (coinPos >= 1) {
					middleCoin.remove((int) coinPos);
				}
			}
		}
	}

	private void tryCollidingWithSaws(float posY) {
		SawData middleSaw = (SawData) SawController.sawController.sawList.start.get(10).data;
		if (middleSaw != null) {
			float sawPos = posY;
			sawPos /= SawController.sawController.sawSize;
			sawPos += SawData.sawSize / 2;

			if (middleSaw.isColliding((int)Math.ceil(sawPos))) {
				this.state = PlayerController.DYING;

				UiInterface.deadTable.setVisible(true);

				UiInterface.deadMeters.setText("You ran: " + (float)Math.round(PlayerController.meters*10)/10 + "m");
				UiInterface.deadCoins.setText("and collected: " + PlayerController.coins + " Coins");

				UiInterface.revive.setDisabled(PlayerController.coins < 100 ? true : false);
				System.out.println(UiInterface.revive.isDisabled());
			}
		}
	}

	private void updatePlayerAnimationSpeed() {
		float fpsMultiplie = this.gameObject.transform.position.y + sizeY / 2 + PlayerController.borders / 2;
		fpsMultiplie = PlayerController.borders / fpsMultiplie;

		this.playerRunningAnimation.setFramesPerSecond(10 * PlayerController.movingSpeed);
	}

	private void play() {
		this.state = PlayerController.STARTING;
	}

	private void realStart() {
		this.ceilCollision = false;
		this.floorCollision = true;
		this.state = PlayerController.STARTED;
		PlayerController.movingSpeed = 1f;

		UiInterface.titleTable.setVisible(false);
	}

	@Override
	public void render() {
		if (this.state == PlayerController.DEAD) return;

		this.spriteBatch.draw(this.playerAnimation.getActiveFrame(), this.gameObject.transform.position.x - sizeY, -sizeY/2 - this.gameObject.transform.position.y, sizeY * 2, sizeY * 2);

		BitmapFont lol = Assets.manager.get(Assets.defaultFont);
		lol.draw(this.spriteBatch, state + "", 200, 0);

		lol.draw(this.spriteBatch, this.floorCollision + " " + this.ceilCollision, 200, -75);

		lol.draw(this.spriteBatch, Math.round(this.posY) + " ", 200, -150);

		lol.draw(this.spriteBatch, PlayerController.borders + " ", 200, -215);

		lol.draw(this.spriteBatch, this.playerAnimation.getActiveAnimation().getName() + " ", 200, -300);

		lol.draw(this.spriteBatch, this.playerAnimation.lastRun + " - " + (float)Math.round(this.playerAnimation.lastTimeRun*100)/100, 200, -400);
	}

	@Override
	public void dispose() {
		System.out.println("Me gpoing way");
	}

	@Override
	public void reset() {
		this.state = PlayerController.NOT_STARTED;
		this.gameObject.transform.position.y = -1000;
	}

	public static void AnimationFinish(SpriteAnimation spriteAnimation) {

	}
}