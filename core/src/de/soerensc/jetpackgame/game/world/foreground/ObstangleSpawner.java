package de.soerensc.jetpackgame.game.world.foreground;

import de.soerensc.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;

public class ObstangleSpawner extends GameBehaviour {

    private float bruzelTime;
    private float coinTime;

    private float bruzelTimeBounds = 3;

    private float coinTimeBounds;

    public ObstangleSpawner() {
        randomCoinTime();
    }

    @Override
    public void update(float delta) {
        /*bruzelTime += delta;

        if (bruzelTime >= bruzelTimeBounds && SawController.sawController.activePattern.isFinished() && CoinController.coinController.activePattern.isFinished()) {
            bruzelTime = 0;

            if (bruzelTimeBounds >= 1) {
                bruzelTimeBounds -= 0.1f;
            }

            SawController.sawController.newPattern();
        }

        coinTime += delta;*/

        //if (coinTime >= coinTimeBounds && SawController.sawController.activePattern.isFinished() && CoinController.coinController.activePattern.isFinished()) {
            coinTime = 0;

            randomCoinTime();

            CoinController.coinController.newPattern();
        //}
    }

    private void randomCoinTime(){
        this.coinTimeBounds = (float) Math.random() * 10 + 5;
    }
}
