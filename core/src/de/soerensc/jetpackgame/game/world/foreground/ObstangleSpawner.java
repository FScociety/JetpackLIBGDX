package de.soerensc.jetpackgame.game.world.foreground;

import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinController;
import de.soerensc.jetpackgame.game.world.foreground.saw.SawController;
import de.soerensc.jetpackgame.tools.worldlayers.MovingPattern;

public class ObstangleSpawner extends GameBehaviour {

    private float bruzelTime;
    private float coinTime;

    private float bruzelTimeBounds = 10;

    private float randomTime = 0;

    private float coinTimeBounds;

    public static MovingPattern sawPattern, coinPattern;

    public ObstangleSpawner() {
        randomCoinTime();
    }

    @Override
    public void update(float delta) {
        boolean sawIsFinished = sawPattern == null || sawPattern.isFinished();
        boolean coinIsFinished = coinPattern == null || coinPattern.isFinished();

        bruzelTime += delta;

        if (bruzelTime >= bruzelTimeBounds && sawIsFinished && coinIsFinished) {
            bruzelTime = 0;

            if (bruzelTimeBounds >= 1) {
                //TODO
                bruzelTimeBounds -= 0.1f;
            }

            SawController.sawController.newPattern();
        }

        coinTime += delta;

        if (coinTime >= coinTimeBounds && sawIsFinished && coinIsFinished) {
            coinTime = 0;

            randomCoinTime();

            CoinController.coinController.newPattern();
        }
    }

    private void randomCoinTime(){
        this.coinTimeBounds = (float) Math.random() * 10 + 5;
    }
}
