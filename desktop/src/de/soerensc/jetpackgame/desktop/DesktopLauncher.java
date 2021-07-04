package de.soerensc.jetpackgame.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import de.soerensc.jetpackgame.screen.Boot;
import de.soerensc.jetpackgame.screen.Game;

import javax.xml.soap.Text;
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

		//TexturePacker.process(settings, "world/textures/backgrounds/", "world/textures/backgrounds/", "backgrounds");

		//TexturePacker.process(settings, "world/textures/obstangles/saw", "world/textures/obstangles/saw", "sawAtlas");

		//TexturePacker.process(settings, "world/textures/coins", "world/textures/coins", "coinAtlas");

		//TexturePacker.process(settings, "world/textures/newPlayerAnim", "world/textures/newPlayerAnim", "playerAnimAtlas");

		//TexturePacker.process(settings, "world/wall", "world/wall", "wallAtlas");

		//TexturePacker.process(settings, "ui", "ui", "uiAtlas");


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.foregroundFPS = 144;
		config.title = "Jeptack - Game";
		config.useGL30 = false;
		config.width = 960;
		config.height = 640;
		//config.fullscreen = true;
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
