package de.soerensc.jetpackgame.game;

import de.soerensc.engine.ecs.GameObject;
import de.soerensc.engine.ecs.World;
import de.soerensc.jetpackgame.game.world.background.ParalaxBackground;
import de.soerensc.jetpackgame.game.world.foreground.ObstangleSpawner;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.wall.WallController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.player.PlayerController;
import de.soerensc.jetpackgame.game.world.foreground.wall.WallShadow;
import de.soerensc.jetpackgame.screen.Game;

public class InGameWorld extends World {

    public void create() {
        GameObject background = new GameObject();
        background.addComponent(new ParalaxBackground(3));
        this.add(background);

        GameObject foreground = new GameObject();
        WallController wc = new WallController();
        foreground.addComponent(wc);
        CoinController cc = new CoinController();
        foreground.addComponent(cc);
        SawController sc = new SawController();
        foreground.addComponent(sc);
        foreground.addComponent(new ObstangleSpawner());
        this.add(foreground);

        GameObject player = new GameObject();
        player.addComponent(new PlayerController(cc));
        this.add(player);

        //GameObject wallShadow = new GameObject();
        //wallShadow.addComponent(new WallShadow(wc));
        //this.add(wallShadow);
    }
}