package de.soerensc.jetpackgame.game.world.foreground.saw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingElement;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPattern;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPatternControllerBehaviour;

public class SawController extends MovingPatternControllerBehaviour {

    public static SawController sawController;

    private MovingLayer sawList;
    private TextureAtlas sawAtlas;

    public static SpriteAnimation sawAnimation, horizontalSawAnimation, verticalsawAnimation;

    private float time;
    private float timeBounds;

    public SawController() {
        //Singleton-Assignment
        if (SawController.sawController == null) {
            SawController.sawController = this;
        } else {
            return;
        }

        //Textures
        sawAtlas = new TextureAtlas("world/textures/obstangles/saw/sawAtlas.atlas");

        //Patterns
        this.sawAnimation = new SpriteAnimation(sawAtlas, "saw");
        this.horizontalSawAnimation = new SpriteAnimation(sawAtlas, "horizontalSaw");
        this.verticalsawAnimation = new SpriteAnimation(sawAtlas, "verticalSaw");

        this.patternPath = "world/sawPatterns";
        create(6, SawData.sawSize);

        Gdx.app.log("SawController", "Coin Patterns were loaded");

        newPattern();
    }

    @Override
    public void start() {
        SawData defaultSaw = new SawData(this.spriteBatch);
        sawList = new MovingLayer(defaultSaw, new Vector2(100, 100), -1000, 1f);
        sawList.addInstant(22);
        defaultSaw.parent = sawList.start;

        sawAnimation.play();
        horizontalSawAnimation.play();
        verticalsawAnimation.play();
    }

    @Override
    public void update(float delta) {
        sawList.update(delta);

        sawAnimation.update(delta);
        horizontalSawAnimation.update(delta);
        verticalsawAnimation.update(delta);

        /*if (this.time >= this.timeBounds) {
            if (this.activePattern.isFinished()) {
                this.time = 0;
                this.timeBounds = (float) (Math.random()+0.4f);

                if (CoinController.cc.activePattern.isFinished()) {
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

    private boolean testCollisionWithCoins() {
        /*MovingElement coinElement3 = this.cc.coinList.get(this.cc.coinList.elements);
        MovingElement coinElement2 = this.cc.coinList.get(this.cc.coinList.elements - 1);
        MovingElement coinElement1 = this.cc.coinList.get(this.cc.coinList.elements - 2);

        if (coinElement1 == null) {
            return true;
        } else {
            CoinData coinData1 = (CoinData) coinElement1.data;
        }*/

        return false;
    }

    @Override
    public void render() {
        sawList.render();
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
}