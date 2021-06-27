package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.Gdx;

public class Boot extends com.badlogic.gdx.Game {

    private SplashWorker splashWorker;

    public static Boot INSTANCE;
    private int screenWidth, screenHeight;

    public Boot() {
        INSTANCE = this;
    }

    public void create() {
        splashWorker.closeSplashScreen();

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        setScreen(new Game());
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public SplashWorker getSplashWorker() {
        return this.splashWorker;
    }

    public void setSplashWorker(SplashWorker splashWorker) {
        this.splashWorker = splashWorker;
    }
}
