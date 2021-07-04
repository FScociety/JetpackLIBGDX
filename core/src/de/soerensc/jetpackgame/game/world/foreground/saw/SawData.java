package de.soerensc.jetpackgame.game.world.foreground.saw;

import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

import java.util.Arrays;

public class SawData extends MovingData {

    public static final int sawSize = 7;

    int[] saws = new int[sawSize];

    BitmapFont font;

    public SawData(SpriteBatch spriteBatch) {
        super(spriteBatch);

        font = Assets.manager.get(Assets.defaultFont);
    }

    @Override
    public MovingData getNew() {
        return new SawData(this.spriteBatch);
    }

    @Override
    public void generateNew() {
        if (SawController.sawController.activePattern != null) {
            this.saws = SawController.sawController.activePattern.getLine();
        }
    }

    @Override
    public void render() {
        if (this.saws != null) {
            for (int i = 0; i < this.saws.length; i++) {
                TextureRegion frame = null;
                if (this.saws[i] != 0) {

                    if (this.saws[i] == 1) {
                        frame = SawController.sawAnimation.getCurrentFrame();
                    } else if (this.saws[i] == 2) {
                        frame = SawController.horizontalSawAnimation.getCurrentFrame();
                    } else if (this.saws[i] == 3) {
                        frame = SawController.verticalsawAnimation.getCurrentFrame();
                    }

                    this.spriteBatch.draw(frame, this.parent.position, (((float) this.saws.length - 2) / 2 - i) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);

                    //this.font.getData().setScale(10);
                    //this.font.draw(this.spriteBatch, i + "", this.parent.position, ((((float) this.saws.length - 2) / 2 - i) + 1) * this.parent.parent.elementBounds.y);
                }
            }
        }
    }

    public boolean isColliding(int start) {
        //System.out.println("Given : " + start);
        //CLIP to min=1, to prevent errors later
        start = start <= 0 ? 1 : start;

        int collision = 0;

        //Bottom-Body collision
        collision += this.saws[start]   != 0 ? 1 : 0;
        //System.out.println("Tested with : " + start + " " + collision);

        //Top-Body collison
        collision += this.saws[start-1] != 0 ? 1 : 0;
        //System.out.println("Tested with : " + (start-1) + " " + collision);

        //Return the shit
        return collision > 0;
    }

    @Override
    public void reset() {
        Arrays.fill(this.saws, 0);
    }
}
