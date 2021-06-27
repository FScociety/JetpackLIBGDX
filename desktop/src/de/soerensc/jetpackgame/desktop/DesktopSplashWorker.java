package de.soerensc.jetpackgame.desktop;

import de.soerensc.jetpackgame.screen.SplashWorker;

import java.awt.*;

public class DesktopSplashWorker implements SplashWorker {
    @Override
    public void closeSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            splash.close();
        }
    }
}
