package de.soerensc.jetpackgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.VisTextField;

import java.awt.event.ContainerAdapter;

public class UiInterface {

    private Skin uiSkin;
    private Stage uiStage;
    BitmapFont defaultFont;

    public int currentScore;
    public int currentCoins;

    public static Label playText;

    public UiInterface() {
        uiSkin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.uiStage);

        currentScore = 17286;
        currentCoins = 2346;

        this.loadDefaultFont();

        uiSkin.add("default-font", this.defaultFont, BitmapFont.class);
    }

    public void create() {
        uiStage.setDebugAll(true);

        Table playTable = new Table();
        playTable.setFillParent(true);
        playTable.padTop(Value.percentHeight(0.5f));

        playText = new Label("Drücke 'Lehrtaste' umzu spielen", uiSkin);
        playText.setFontScale(3);
        playTable.add(playText);
        uiStage.addActor(playTable);

    }

    private void loadDefaultFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fonts/tight_pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        defaultFont = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
    }

    public void render() {
        uiStage.draw();
    }

    public void act(float delta) {
        uiStage.act(delta);
    }

    public void dispose() {
        this.uiSkin.dispose();
        this.uiStage.dispose();
    }

    public Stage getUiStage() {
        return uiStage;
    }

}