package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.jetpackgame.screen.Game;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;

public class Coin {

    private boolean visible = false;
    private int active = 0;

    private SpriteAnimation destroyAnimation, idleAnimation;
    private SpriteAnimation activeAnimation = null;

    public Coin(int active, SpriteAnimation idleAnimation, SpriteAnimation destroyAnimation) {
        this.idleAnimation = idleAnimation.getCopy();
        this.destroyAnimation = destroyAnimation.getCopy();
        this.setActive(active);
        this.visible = this.active == 1 ? true : false;
    }

    public void newCoin(int active) {
        this.setActive(active);
        this.visible = this.active == 1 ? true : false;
    }

    public void setActive(int active) {
        this.active = active;
        this.updateAnim();
    }

    private void updateAnim() {
        if (active == 0) {
            this.activeAnimation = destroyAnimation;
        } else {
            this.activeAnimation = idleAnimation;
        }

        this.activeAnimation.play();
    }

    public void update() {
        this.activeAnimation.update(Game.delta);
        if (this.activeAnimation == this.destroyAnimation && !this.destroyAnimation.isRunning()) {
            this.visible = false;
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public TextureRegion getImage() {
        return activeAnimation.getCurrentFrame();
    }
}