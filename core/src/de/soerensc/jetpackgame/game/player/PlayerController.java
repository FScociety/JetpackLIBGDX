package de.soerensc.jetpackgame.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.coins.CoinController;
import de.soerensc.jetpackgame.game.world.coins.CoinData;

public class PlayerController extends GameBehaviour {
    /*
    public static PlayerController p;

    public static int borders = 450;

    int sizeY = 200;

    boolean ceilCollision, floorCollision;

    float velocityY = 0;
    public static float velX;
    float forceY = 9.81f;

    public static float movingSpeed = 1;

    private Texture testImage;

    private CoinController cc;

    public PlayerController(CoinController cc) {
        this.p = this;

        this.cc = cc;

        this.testImage = new Texture("world/textures/player/test.png");
    }

    public void update(float delta) {

        float posY = this.gameObject.transform.position.y;
        if (posY-sizeY/2 < -borders && !this.ceilCollision) {
            ceilCollision = true;
            this.velocityY = 0;
            this.gameObject.transform.position.y = -borders + sizeY/2;
        } else if (posY-sizeY/2 > -borders) {
            ceilCollision = false;
            if (posY+sizeY/2 > borders && !this.floorCollision) {
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
            this.gameObject.transform.position.add((new Vector2(0, (float) (this.velocityY * delta))));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && !this.ceilCollision) {
            this.velocityY -= (float) (this.forceY * delta * 400);
            this.gameObject.transform.rotation = (float)Math.random()*20;

            this.velX = 2;
        } else {
            this.velX = 1;
        }



        CoinData middle = ((CoinData)cc.coinList.get(21).data);
        if (middle != null) {

            //float posY = this.gameObject.getTransformWithCaution().position.y;
            posY /= cc.coinSize;
            posY += CoinData.coinSize/2;

            middle.remove((int) posY);
        }

    }

    public void render() {
		/*this.d.setColor(Color.WHITE);
		this.d.fillRect(new Vector2(sizeY));*/

        //this.d.drawImage(testImage, new Vector2(-sizeY/4, -sizeY/2), new Vector2(sizeY/2, sizeY));
    /*    this.spriteBatch.draw(testImage, this.gameObject.transform.position.x, this.gameObject.transform.position.y);
    }

    //Schule version

	public PlayerController(String name) {
		this.name = name;
		PlayerController.players.add(this);
	}

	public static ArrayList<PlayerController> players = new ArrayList<PlayerController>();
	private String name;

	public static PlayerController getPlayer(String name) {
		for (PlayerController pc : players) {
			if (pc.name == name) {
				return pc;
			}
		}
		return null;
	}
	*/
}
