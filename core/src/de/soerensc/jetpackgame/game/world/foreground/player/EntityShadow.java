package de.soerensc.jetpackgame.game.world.foreground.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.tools.animation.SpriteAnimation;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;

public class EntityShadow extends GameBehaviour {

    private TextureAtlas.AtlasRegion entityShadow;
    private float groundY;
    private float newWidth, newHeigth;

    public EntityShadow(float groundY) {
        TextureAtlas playerAtlas = Assets.manager.get(Assets.playerAtlas);
        this.entityShadow = playerAtlas.findRegion("EntityShadow");

        this.groundY = groundY;
    }

    public void update(float delta) {
        float mutliplier = this.gameObject.transform.position.y;
        mutliplier += groundY;
        mutliplier = mutliplier / (2 * groundY) + 0.5f;
        mutliplier *= 2;

        mutliplier = mutliplier <= 0 ? 0 : mutliplier;

        newWidth = this.entityShadow.getRegionWidth() * mutliplier;
        newHeigth = this.entityShadow.getRegionHeight() * mutliplier;
    }

    public void render() {
        this.spriteBatch.draw(this.entityShadow, - newWidth / 2f, -365 - newHeigth / 2, newWidth, newHeigth * 0.65f);
    }
}
