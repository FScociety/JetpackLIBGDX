package de.soerensc.jetpackgame.tools.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpriteAnimation {

    private TextureAtlas spriteAtlas;
    private TextureAtlas.AtlasRegion[] extractedAtlas;

    private int index;
    private int length;
    private float time;
    private float frameTime;
    private boolean running = false;
    private TextureRegion currentFrame;

    private int animStart, animEnd;

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
                    if (this.index > this.animEnd) {
                        this.index = this.animStart;
                    }
                } else {
                    this.running = false;
                }
                this.currentFrame = this.extractedAtlas[index];
            }
        }
    }

    public void setFramesPerSecond(float framesPerSecond) {
        this.frameTime = 1 / framesPerSecond;
    }

    public void play(String name) {
        extractedAtlas = this.spriteAtlas.getRegions().toArray(TextureAtlas.AtlasRegion.class);

        for (int i = 0; i < extractedAtlas.length; i++) {
            if (extractedAtlas[i].name.equals(name + "0")) {
                this.animStart = i;
                break;
            }
        }

        int i = 0;
        boolean foundEnd = false;
        while (!foundEnd) {
            i++;
            TextureAtlas.AtlasRegion region = this.spriteAtlas.findRegion(name + i);

            if (region == null) {
                System.out.println("Could not find: " + name + i);
                foundEnd = true;
            }

        this.animEnd = this.animStart + i - 1;
        }

        System.out.println("Found a SpriteAnimation at: " + this.animStart + " | " + this.animEnd + " with name: " + name);

        this.index = this.animStart;
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