package de.soerensc.jetpackgame.game.world.foreground.saw;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.worldlayers.MovingData;

public class SawData extends MovingData {

    private SpriteAnimation sawAnimation;

    public static final int sawSize = 7;

    int[] saws = new int[sawSize];

    public SawData(SpriteBatch spriteBatch) { super(spriteBatch); }

    public SawData(SpriteBatch spriteBatch, SpriteAnimation sawAnimation) {
        super(spriteBatch);
        this.sawAnimation = sawAnimation;
    }

    @Override
    public MovingData getNew() {
        return new SawData(this.spriteBatch, this.sawAnimation);
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
                if (this.saws[i] != 0) {
                    //GameContainer.d.drawImage(this.coin, new Vector2(this.parent.position, (i-((float)this.coins.length)/2) * this.parent.parent.elementBounds.y), new Vector2(this.parent.parent.elementBounds.y));
                    this.spriteBatch.draw(this.sawAnimation.getCurrentFrame(), this.parent.position, (((float)this.saws.length-2)/2 - i) * this.parent.parent.elementBounds.y, this.parent.parent.elementBounds.x, this.parent.parent.elementBounds.y);
                }
            }
        }
    }
}
