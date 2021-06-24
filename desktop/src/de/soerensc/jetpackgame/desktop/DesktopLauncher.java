package de.soerensc.jetpackgame.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.soerensc.jetpackgame.screen.Boot;
//import de.soerensc.ponggame.core.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setIdleFPS(60);
		config.useVsync(true);
		config.setTitle("Jetpack - Game");

		config.setWindowedMode(960, 640);
		new Lwjgl3Application(new Boot(), config);
	}
}
