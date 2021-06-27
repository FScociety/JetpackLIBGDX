package de.soerensc.jetpackgame.game.world.foreground.saw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingLayer;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPattern;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPatternControllerBehaviour;

public class SawController extends MovingPatternControllerBehaviour {

    public static SawController sawController;

    private MovingLayer sawList;
    private TextureAtlas sawAtlas;
    private SpriteAnimation spriteAnimation;

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
        sawAtlas = new TextureAtlas("world/textures/obstangles/saw/saw.atlas");

        //Patterns
        spriteAnimation = new SpriteAnimation(sawAtlas, 100);

        this.patternPath = "world/sawPatterns";
        create(6);

        Gdx.app.log("SawController", "Coin Patterns were loaded");

        newPattern();
    }

    @Override
    public void start() {
        SawData defaultSaw = new SawData(this.spriteBatch, spriteAnimation);
        sawList = new MovingLayer(defaultSaw, new Vector2(100, 100), -1000, 1f);
        sawList.addInstant(22);
        defaultSaw.parent = sawList.start;

        spriteAnimation.play();
    }

    @Override
    public void update(float delta) {
        sawList.update(delta);

        spriteAnimation.update(delta);

        if (this.time >= this.timeBounds) {
            if (this.activePattern.isFinished()) {
                this.newPattern();
                this.time = 0;
                this.timeBounds = (float) (Math.random()+0.4f);
            }
        } else {
            this.time += delta;
        }
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