package de.soerensc.jetpackgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class UiInterface {

    private Skin uiSkin;
    private Stage uiStage;

    public UiInterface() {
        uiSkin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        uiStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this.uiStage);
    }

    public void create() {
        //Begin UI-TABLE
        Table completeTable = new Table();
        completeTable.setFillParent(true);
        uiStage.addActor(completeTable);

        Table rootUiTable = new Table();
        rootUiTable.setFillParent(true);
        rootUiTable.left();
        rootUiTable.pad(50);
        rootUiTable.padTop(Value.percentHeight(0.7f));
        rootUiTable.padRight(Value.percentWidth(0.8f));

        rootUiTable.defaults().space(20);
        rootUiTable.defaults().grow();
        completeTable.add(rootUiTable).grow();

        //Adding UI-Elements
        Button startButton = new Button(uiSkin);
        rootUiTable.add(startButton);
        rootUiTable.row();

        Button settingsButton = new Button(uiSkin);
        rootUiTable.add(settingsButton);
        //END UI-TABLE
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