package de.soerensc.jetpackgame.desktop;

import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import de.soerensc.jetpackgame.screen.Boot;
import de.soerensc.jetpackgame.screen.Game;

import java.sql.SQLException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//Sprite Atlas

		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.maxHeight = 2048;
		settings.maxWidth = 2048;
		settings.edgePadding = true;
		settings.filterMin = Texture.TextureFilter.Nearest;
		settings.filterMag = Texture.TextureFilter.Nearest;

		//TexturePacker.process(settings, "world/textures/backgrounds/dune", "world/textures/backgrounds/dune", "dune");

		TexturePacker.process(settings, "world/textures/obstangles/saw", "world/textures/obstangles/saw", "sawAtlas");


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.foregroundFPS = 144;
		config.title = "Jeptack - Game";
		config.useGL30 = false;
		config.width = 960;
		config.height = 640;
		Boot game = new Boot();
		game.setSplashWorker(new DesktopSplashWorker());
		LwjglApplication application = new LwjglApplication(game, config);



		/*CONN connection = new CONN();
		try {
			connection.create_acc("paul", "dumm");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}*/
	}
}
