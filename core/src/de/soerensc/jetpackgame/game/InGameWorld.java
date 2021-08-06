package de.soerensc.jetpackgame.game;

import de.soerensc.jetpackgame.game.world.foreground.player.EntityShadow;
import de.soerensc.jetpackgame.tools.animation.AnimationController;
import de.soerensc.jetpackgame.tools.engine.ecs.GameObject;
import de.soerensc.jetpackgame.tools.engine.ecs.World;
import de.soerensc.jetpackgame.game.world.background.ParalaxBackground;
import de.soerensc.jetpackgame.game.world.foreground.ObstangleSpawner;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.game.world.foreground.wall.WallController;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.player.PlayerController;

public class InGameWorld extends World {

    public void create() {
        //Background
        GameObject background = new GameObject();
        background.addComponent(new ParalaxBackground(3));
        this.add(background);

        //Foreground
        GameObject foreground = new GameObject();
            //Wall
            WallController wc = new WallController();
            foreground.addComponent(wc);

            //Coins
            CoinController cc = new CoinController();
            foreground.addComponent(cc);

            //Saws
            SawController sc = new SawController();
            foreground.addComponent(sc);

            //Spawner
            ObstangleSpawner os = new ObstangleSpawner();
            foreground.addComponent(os);
        this.add(foreground);

        //Player
        GameObject player = new GameObject();
        player.addComponent(new AnimationController());
        player.addComponent(new PlayerController());
        player.addComponent(new EntityShadow(PlayerController.borders));
        this.add(player);
    }
}