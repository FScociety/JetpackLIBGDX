package de.soerensc.jetpackgame.tools.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;

public class SpriteAnimation {

    private final TextureRegion[] animationPart;

    private int index;
    private float time;
    private float frameTime = 0.1f;
    private boolean running = false;
    public boolean looping = true;
    private TextureRegion currentFrame;

    private int animStart, animEnd;

    private SpriteAnimation(TextureRegion[] animationPart, float frameTime, boolean looping) {
        this.animationPart = animationPart;
        this.index = 0;
        this.frameTime = frameTime;
        this.running = false;
        this.looping = looping;
        this.currentFrame = animationPart[0];
    }

    public SpriteAnimation(TextureAtlas textureAtlas, String name) {

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

            i++;
        }

        this.animationPart = regions.toArray(TextureRegion.class);
        this.currentFrame = this.animationPart[0];
    }

    public SpriteAnimation(TextureAtlas textureAtlas) {
        this.animationPart = textureAtlas.getRegions().toArray(TextureRegion.class);
        //this.currentFrame = this.animationPart[0];
    }

    public void update(float delta) {
        if (this.running) {
            this.time += delta;

            if (this.time >= this.frameTime) {
                this.time = 0;


                if (this.index > this.animationPart.length-1) {
                    this.index = 0;
                    if (!this.looping) {
                        this.running = false;
                    }
                }

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
        return new SpriteAnimation(this.animationPart, this.frameTime, this.looping);
    }

    public void setOffset(int offset) {
        //TODO: IDK if working the hell shit
        this.time += offset * this.frameTime;
    }
}