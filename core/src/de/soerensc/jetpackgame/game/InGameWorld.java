package de.soerensc.jetpackgame.game;

import com.badlogic.gdx.graphics.Texture;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.engine.ecs.GameObject;
import de.soerensc.engine.ecs.World;
import de.soerensc.jetpackgame.screen.Game;

public class InGameWorld extends World {

    public void create() {
        GameObject test = new GameObject();
        test.addComponent(new GameBehaviour() {
            @Override
            public void render() {
                gameObject.getSpriteBatch().draw(Game.assetManager.<Texture>get("badlogic.jpg"), 0, 0);
            }
        });
        this.add(test);
    }
}
