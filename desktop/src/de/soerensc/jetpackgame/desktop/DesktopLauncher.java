package de.soerensc.jetpackgame.desktop;

import com.badlogic.gdx.backends.lwjgl.*;
import de.soerensc.jetpackgame.screen.Boot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.title = "Jeptack - Game";
		config.width = 960;
		config.height = 640;
		//<config.useVsync(true);
		//config.setTitle("Jetpack - Game");
		//config.setIdleFPS(60);

		//config.setWindowedMode(960, 640);
		Boot b = new Boot();
		new LwjglApplication(b, config);

	}
}
