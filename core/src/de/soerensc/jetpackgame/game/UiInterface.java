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

    public UiInterface() {
        uiSkin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.uiStage);

        this.loadDefaultFont();
    }

    private void loadDefaultFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fonts/tight_pixel.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.borderWidth = 1;
        parameter.color = Color.WHITE;
        defaultFont = generator.generateFont(parameter); // font size 24 pixels
        generator.dispose();
    }


    public void create() {
        uiStage.setDebugAll(true);



        Table completeTable = new Table();
        completeTable.setFillParent(true);
        completeTable.left();
        completeTable.top();
        uiStage.addActor(completeTable);


        Table statsTable = new Table();
        statsTable.pad(20);
        statsTable.left().top();
        statsTable.defaults().align(Align.left);
        completeTable.add(statsTable);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = defaultFont;

        Label scoreLabel = new Label("Score: 87236m",labelStyle);
        scoreLabel.setSize(100, 100);
        scoreLabel.setPosition(100, Gdx.graphics.getHeight()-50);
        statsTable.add(scoreLabel);

        statsTable.row();

        Label moneyLabel = new Label("Coins: 8726",labelStyle);
        moneyLabel.setSize(100, 100);
        moneyLabel.setPosition(100, Gdx.graphics.getHeight()-50);
        statsTable.add(moneyLabel);

        Container exitButtonContainer = new Container();
        exitButtonContainer.right().top();
        exitButtonContainer.setLayoutEnabled(true);
        completeTable.add(exitButtonContainer);

        Texture settingsIconTexture = new Texture(Gdx.files.internal("ui/settings.png"));
        TextureRegionDrawable settingsIconRendering = new TextureRegionDrawable(settingsIconTexture);
        ImageButton exitButton = new ImageButton(uiSkin);
        exitButton.getStyle().imageUp = settingsIconRendering;
        exitButton.pad(10);
        exitButtonContainer.setActor(exitButton);


        completeTable.row();


        Table rootUiTable = new Table();
        rootUiTable.left();
        rootUiTable.pad(50);
        rootUiTable.padTop(Value.percentHeight(0.7f));
        rootUiTable.padRight(Value.percentWidth(0.8f));

        rootUiTable.defaults().space(20);
        rootUiTable.defaults().grow();
        //completeTable.add(rootUiTable);

        Button startButton = new Button(uiSkin);
        rootUiTable.add(startButton);
        rootUiTable.row();

        Button settingsButton = new Button(uiSkin);
        rootUiTable.add(settingsButton);

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