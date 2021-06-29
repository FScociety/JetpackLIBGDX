package de.soerensc.jetpackgame.game.world.foreground.coins;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.soerensc.jetpackgame.screen.Game;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;

public class Coin {
    private int active = 0;

    private SpriteAnimation destroyAnimation, idleAnimation;
    private SpriteAnimation activeAnimation = null;

    public Coin(int active, SpriteAnimation idleAnimation, SpriteAnimation destroyAnimation) {
        this.idleAnimation = idleAnimation.getCopy();
        this.destroyAnimation = destroyAnimation.getCopy();
        this.setActive(active);
    }

    public void newCoin(int active) {
        this.active = active;
        this.setActive(1);
    }

    public void setActive(int active) {
        if (active == 0) {
            this.activeAnimation = destroyAnimation;
            this.activeAnimation.play();
        } else {
            this.activeAnimation = idleAnimation;
            this.activeAnimation.play();
        }
    }

    public void update() {
        this.activeAnimation.update(Game.delta);
    }

    public boolean isActive() {
        return this.active == 1;
    }

    public TextureRegion getImage() {
        System.out.println(activeAnimation.getCurrentFrame());
        return activeAnimation.getCurrentFrame();
    }
}