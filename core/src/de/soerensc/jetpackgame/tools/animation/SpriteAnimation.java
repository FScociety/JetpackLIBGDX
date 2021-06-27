package de.soerensc.jetpackgame.tools.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteAnimation {

    private TextureAtlas spriteAtlas;

    private int index;
    private int length;
    private float time;
    private float frameTime;
    private boolean running = false;
    private TextureRegion currentFrame;

    public boolean looping = true;

    public SpriteAnimation(TextureAtlas spriteAtlas, float framesPerSecond) {
        this.spriteAtlas = spriteAtlas;
        this.setFramesPerSecond(framesPerSecond);

        this.length = spriteAtlas.getRegions().size;
        this.currentFrame = this.spriteAtlas.getRegions().get(0);
    }

    public void update(float delta) {
        if (this.running) {
            this.time += delta;

            if (this.time >= this.frameTime) {
                this.index++;
                this.time = 0;

                if (this.looping) {
                    if (this.index > this.length-1) {
                        this.index = 0;
                    }
                } else {
                    this.running = false;
                }

                this.currentFrame = this.spriteAtlas.getRegions().get(this.index);
            }
        }
    }

    public void setFramesPerSecond(float framesPerSecond) {
        this.frameTime = 1 / framesPerSecond;
    }

    public void play() {
        this.running = true;
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
}