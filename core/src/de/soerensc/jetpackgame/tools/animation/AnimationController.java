package de.soerensc.jetpackgame.tools.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;
import javafx.animation.Animation;

import java.util.ArrayList;

public class AnimationController extends GameBehaviour {

    private ArrayList<SpriteAnimation> animations = new ArrayList<>();

    private SpriteAnimation activeAnimation;

    public AnimationController() {

    }

    public void addAnimation(SpriteAnimation spriteAnimation) {
        spriteAnimation.setParent(this);
        this.animations.add(spriteAnimation);
    }

    @Override
    public void update(float delta) {
        this.activeAnimation.update(delta);

        if (this.lastTimeRun != -1) {
            this.lastTimeRun += delta;
        }
    }

    public float lastTimeRun = -1;
    public String lastRun;

    public void play(String name) {
        SpriteAnimation newAnimation = findAnimation(name);
        if (newAnimation == null) return;

        this.activeAnimation = newAnimation;
        this.activeAnimation.play();

        lastRun = Thread.currentThread().getStackTrace()[2].getLineNumber() + "";
        this.lastTimeRun = 0;
    }

    private SpriteAnimation findAnimation(String name) {
        for (SpriteAnimation spriteAnimation : animations) {
            //FOUND SpriteAnimation with correct name
            if (spriteAnimation.getName().equalsIgnoreCase(name)) {
                return spriteAnimation;
            }
        }

        Gdx.app.error("AnimationController", "Could not found animation with name: " + name);
        //NOT_FOUND
        return null;
    }

    public void updateActiveAnimation(SpriteAnimation spriteAnimation) {
        this.activeAnimation = spriteAnimation;
        //TODO
    }

    public SpriteAnimation getActiveAnimation() {
        return this.activeAnimation;
    }

    public boolean isPlaying(String name) {
        return this.activeAnimation.getName().equals(name);
    }

    public boolean isPlayed(String name) {
        return this.activeAnimation.getName().equals(name) && !this.activeAnimation.isRunning();
    }

    public void link(SpriteAnimation base, SpriteAnimation link) {
        base.setFollowingAnimation(link);
    }

    public TextureRegion getActiveFrame() {
        if (this.activeAnimation == null) return null;

        return this.activeAnimation.getCurrentFrame();
    }
}