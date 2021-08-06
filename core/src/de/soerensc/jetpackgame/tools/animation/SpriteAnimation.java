package de.soerensc.jetpackgame.tools.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;

import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class SpriteAnimation extends GameBehaviour {

    private SpriteAnimation followingAnimation = null;
    private AnimationController animationController;
    public Consumer<SpriteAnimation> listenerMethod;
    private final TextureRegion[] animationPart;

    private String name;
    private int index;
    private float time;
    private float frameTime = 0.1f;
    public boolean running = false;
    public boolean looping = true;
    private TextureRegion currentFrame;

    private SpriteAnimation(TextureRegion[] animationPart, float frameTime, boolean looping, String name){
        this.animationPart = animationPart;
        this.index = 0;
        this.frameTime = frameTime;
        this.running = false;
        this.looping = looping;
        this.currentFrame = animationPart[0];
        this.name = name;
    }

    public SpriteAnimation(TextureAtlas textureAtlas, String name) {
        this.name = name;

        Gdx.app.debug("SpriteAnimation", "Searching frames for " + name);

        Array<TextureRegion> regions = new Array<TextureRegion>();

        int i = 0;

        while (true) {
            TextureRegion region = textureAtlas.findRegion(name + i);
            TextureRegion region2 = textureAtlas.findRegion(name.substring(0, name.length() - 1) + i);

            if (region != null) {
                regions.add(region);
            } else if (region2 != null) {
                regions.add(region2);
            } else if (i != 1 && i != 0) {
                Gdx.app.log("SpriteAnimation", "Found end for SpriteAnimation : '" + name + "', at " + i);
                break;
            }

            Gdx.app.debug(name, "Found frame at: " + i);

            i++;
        }

        this.animationPart = regions.toArray(TextureRegion.class);
        this.currentFrame = this.animationPart[0];
    }

    public SpriteAnimation(TextureAtlas textureAtlas) {
        this.animationPart = textureAtlas.getRegions().toArray(TextureRegion.class);
    }

    @Override
    public void update(float delta) {
        if (this.running) {
            this.time += delta;

            //END of frame
            if (this.time >= this.frameTime) {
                //RESET time
                this.time = 0;

                //END of animation
                if (this.index > this.animationPart.length-1) {
                    this.index = 0;

                    //TODO: Following Animation not working proeply

                    if (this.looping == false) {
                        this.running = false;

                        if (this.followingAnimation != null) {
                            if (this.followingAnimation.animationController != null) {
                                this.followingAnimation.animationController.play(this.followingAnimation.name);
                            } else {
                                this.followingAnimation.play();
                            }
                        }
                    }
                    
                    /*if (this.looping == false) {
                        this.running = false;
                    } else if (this.followingAnimation != null) {
                        this.running = false;
                        //TODO:
                        if (this.listenerMethod != null) this.listenerMethod.accept(this);
                        this.followingAnimation.play();

                        if (this.followingAnimation.animationController != null) {
                            this.followingAnimation.animationController.updateActiveAnimation(this.followingAnimation);
                        }
                    }*/
                }

                //Update 'currentFrame'
                if (this.running) this.currentFrame = this.animationPart[index];

                this.index++;
            }
        }
    }

    public void setFramesPerSecond(float framesPerSecond) {
        this.frameTime = 1 / framesPerSecond;
    }

    public void play() {
        this.index = 0;
        this.running = true;
        this.currentFrame = this.animationPart[0];
    }

    public void play(int i) {
        this.index = i;
        this.running = true;
        this.currentFrame = this.animationPart[0];
    }

    public void stop() {
        this.running = false;
    }

    public TextureRegion getCurrentFrame() {
        return this.currentFrame;
    }

    public boolean isRunning() {
        return this.running;
    }

    public SpriteAnimation getCopy() {
        return new SpriteAnimation(this.animationPart, this.frameTime, this.looping, this.name);
    }

    public void setOffset(int offset) {
        //TODO: IDK if working the hell shit
        this.time += offset * this.frameTime;
    }

    public void setFollowingAnimation(SpriteAnimation spriteAnimation) {
        this.followingAnimation = spriteAnimation;
    }

    public void setParent(AnimationController animationController) {
        this.animationController = animationController;
    }

    public AnimationController getParent() {
          return this.animationController;
    }

    public String getName() {
        return this.name;
    }
}