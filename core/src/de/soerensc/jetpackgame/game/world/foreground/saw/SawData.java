package de.soerensc.jetpackgame.game.world.foreground.saw;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class SawData extends MovingData {

    public static final int sawSize = 7;

    int[] saws = new int[sawSize];

    public SawData(SpriteBatch spriteBatch) { super(spriteBatch); }

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
                        //GameContainer.d.drawImage(this.coin, new Vector2(this.parent.position, (i-((float)this.coins.length)/2) * this.parent.parent.elementBounds.y), new Vector2(this.parent.parent.elementBounds.y));
                        frame = SawController.sawAnimation.getCurrentFrame();
                    } else if (this.saws[i] == 2) {
                        frame = SawController.horizontalSawAnimation.getCurrentFrame();
                    } else if (this.saws[i] == 3) {
                        frame = SawController.verticalsawAnimation.getCurrentFrame();
                    }

                    this.spriteBatch.draw(frame, this.parent.position, (((float) this.saws.length - 2) / 2 - i) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
                }
            }
        }
    }
}
