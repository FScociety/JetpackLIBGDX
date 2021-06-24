package de.soerensc.jetpackgame.game;

import com.badlogic.gdx.graphics.Texture;
import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.engine.ecs.GameObject;
import de.soerensc.engine.ecs.World;
import de.soerensc.jetpackgame.game.world.background.ParalaxBackground;
import de.soerensc.jetpackgame.screen.Game;

public class InGameWorld extends World {

    public void create() {
        GameObject background = new GameObject();
        background.addComponent(new ParalaxBackground(3));
        this.add(background);
    }
}
