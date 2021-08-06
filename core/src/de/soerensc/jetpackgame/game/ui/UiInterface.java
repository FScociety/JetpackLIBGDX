package de.soerensc.jetpackgame.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;
import de.soerensc.dbconnection.CONNECTION;
import de.soerensc.jetpackgame.game.Assets;
import de.soerensc.jetpackgame.game.world.background.ParalaxBackground;
import de.soerensc.jetpackgame.game.world.foreground.player.PlayerController;
import de.soerensc.jetpackgame.tools.engine.ecs.World;

import java.awt.*;
import java.sql.SQLException;

public class UiInterface {
    public static boolean loggedIn = false;
    public static UiInterface uiInterface;

    private final Stage uiStage;

    public Table loggedInTable;

    public static Table registerContainer;
    private Label registerLabel;
    private Label dataBaseError;
    private TextButton finalRegisterButton;
    public static Table deadTable;
    public static Label coins, meters;
    public static Table titleTable;
    public static Label deadCoins, deadMeters;
    public static TextButton revive;
    public static Table userContainer;
    public static Table userInfoTable;

    TextureAtlas customUISkin;

    private final CONNECTION ImpfChipConnection;

    public UiInterface() {
        if (UiInterface.uiInterface == null) {
            UiInterface.uiInterface = this;
        }

        uiStage = new Stage(new ExtendViewport(1920, 1080));
        this.customUISkin = Assets.manager.get(Assets.uiElements);
        Gdx.input.setInputProcessor(this.uiStage);

        ImpfChipConnection = new CONNECTION();
    }

    public void create() {
        userInfoTable = new Table();
        userInfoTable.setFillParent(true);
        userInfoTable.right().top();
        userInfoTable.pad(10);
        userInfoTable.padBottom(Value.percentHeight(0.85f));
        userInfoTable.padRight(Value.percentWidth(0.8f));
        userInfoTable.setVisible(false);

            final Table userInfoPanel = new Table();
            userInfoPanel.setBackground(Assets.default_panelTexture);

                final Label playerName = new Label("- offline -", new Label.LabelStyle(Assets.default_LabelStyle));
                userInfoPanel.add(playerName);

                userInfoPanel.row();

                coins = new Label("Coins: 0", new Label.LabelStyle(Assets.default_LabelStyle));
                coins.setFontScale(0.5f);
                coins.getStyle().fontColor = Color.YELLOW;
                userInfoPanel.add(coins).grow();

                userInfoPanel.row();

                meters = new Label("Meters: 0m", new Label.LabelStyle(Assets.default_LabelStyle));
                meters.setFontScale(0.5f);
                userInfoPanel.add(meters).grow();

            userInfoTable.add(userInfoPanel).grow();

        uiStage.addActor(userInfoTable);

        titleTable = new Table();
        titleTable.setFillParent(true);
        titleTable.padLeft(Value.percentWidth(0.1f));
        titleTable.padRight(Value.percentWidth(0.1f));
        titleTable.padBottom(Value.percentHeight(0.4f));

        TextureRegionDrawable titleImage = new TextureRegionDrawable(Assets.manager.<TextureAtlas>get(Assets.uiElements).findRegion("title"));
        Image title = new Image(titleImage);
        title.setScaling(Scaling.fit);
        titleTable.add(title).grow();

        uiStage.addActor(titleTable);

        final Table userTable = new Table();
        userTable.setBackground(Assets.default_panelTexture);

        registerContainer = new Table();

        userTable.defaults().pad(10);

            final Table onlineTable = new Table();
            onlineTable.defaults().pad(10);

            userTable.add(onlineTable).grow();

                final TextButton loginButton = new TextButton("Login", Assets.default_ButtonStyle);
                loginButton.addListener(new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        registerContainer.setVisible(true);
                        userContainer.setVisible(false);

                        registerLabel.setText("Login to a new Account");
                        finalRegisterButton.getLabel().setText("Login");
                    }
                });
                onlineTable.add(loginButton).grow();

                final TextButton registerButton = new TextButton("Register", Assets.default_ButtonStyle);
                registerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        registerContainer.setVisible(true);
                        userContainer.setVisible(false);

                        registerLabel.setText("Register a new Account");
                        finalRegisterButton.getLabel().setText("Register");
                    }
                });
                onlineTable.add(registerButton).grow();

            final TextButton offlineButton = new TextButton("Play Offline", Assets.default_ButtonStyle);

            offlineButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    userContainer.setVisible(false);
                    loggedIn = true;
                    loggedInTable.setVisible(true);
                    userInfoTable.setVisible(true);
                }
            });
            TextButton.TextButtonStyle defaultButtonStyle = offlineButton.getStyle();
            offlineButton.setStyle(defaultButtonStyle);
            userTable.row();
            userTable.add(offlineButton).growX();

        userContainer = new Table();
        userContainer.setFillParent(true);
        userContainer.center();
        userContainer.align(Align.center);
        userContainer.padLeft(Value.percentWidth(0.3f));
        userContainer.padRight(Value.percentWidth(0.3f));
        userContainer.padTop(Value.percentHeight(0.7f));
        userContainer.padBottom(Value.percentHeight(0.2f));
        userContainer.add(userTable).grow();

        uiStage.addActor(userContainer);
        userTable.setVisible(true);


        Table registerTable = new Table();
        registerTable.setBackground(Assets.default_panelTexture);
        registerTable.defaults().align(Align.center);
        registerTable.defaults().pad(10);

            registerLabel = new Label("Register a new Account", new Label.LabelStyle(Assets.bold_LabelStyle));
            registerLabel.getStyle().font = Assets.manager.get(Assets.defaultFont_Bold);
            registerLabel.setAlignment(Align.center);
            registerTable.add(registerLabel).growX();

            registerTable.row();

            Label usernameLabel = new Label("Username:", new Label.LabelStyle(Assets.default_LabelStyle));
            usernameLabel.getStyle().fontColor = Color.DARK_GRAY;
            registerTable.add(usernameLabel).growX();

            registerTable.row();

            final TextField usernameInputField = new TextField("", Assets.default_TextFieldStyle);
            registerTable.add(usernameInputField).grow();

            registerTable.row();

            Label passwordLabel = new Label("Password:", new Label.LabelStyle(Assets.default_LabelStyle));
            passwordLabel.getStyle().fontColor = Color.DARK_GRAY;
            registerTable.add(passwordLabel).growX();

            registerTable.row();

            final TextField passwordInputField = new TextField("", Assets.default_TextFieldStyle);
            registerTable.add(passwordInputField).grow();

            registerTable.row();

            finalRegisterButton = new TextButton("Register", Assets.default_ButtonStyle);
            finalRegisterButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    //TODO: Database

                    //if connected ===>
                    if (finalRegisterButton.getLabel().getText().toString().equals("Register")) {
                        try {
                            if (ImpfChipConnection.create_acc(usernameInputField.getText(), passwordInputField.getText())) {
                                userContainer.setVisible(false);
                                registerContainer.setVisible(false);
                                loggedIn = true;
                                loggedInTable.setVisible(true);
                                playerName.setText(CONNECTION.username);
                                userInfoTable.setVisible(true);
                            } else {
                                dataBaseError.setText("Could not create account!");
                                dataBaseError.setVisible(true);
                            }
                        } catch (SQLException throwable) {
                            Gdx.app.error("IMPFCHIP", "WAIT WHAT ?! | Illegal IMPF-Chip => Cant create Impf Chip");
                            throwable.printStackTrace();
                        }
                    } else {
                        try {
                            if (ImpfChipConnection.login(usernameInputField.getText(), passwordInputField.getText())) {
                                userContainer.setVisible(false);
                                registerContainer.setVisible(false);
                                loggedIn = true;
                                loggedInTable.setVisible(true);
                                playerName.setText(CONNECTION.username);
                                userInfoTable.setVisible(true);
                            } else {
                                dataBaseError.setText("Could not login!");
                                dataBaseError.setVisible(true);
                            }
                        } catch (SQLException throwable) {
                            Gdx.app.error("IMPFCHIP", "Could not logging to IMPF-CHIP");
                            throwable.printStackTrace();
                        }
                    }
                }
            });
            //finalRegisterButton.setStyle(this.textButtonStyle);
            finalRegisterButton.setStyle(defaultButtonStyle);

            registerTable.add(finalRegisterButton);

            dataBaseError = new Label("To be replaced",  new Label.LabelStyle(Assets.default_LabelStyle));
            dataBaseError.getStyle().fontColor = Color.RED;
            registerTable.row();
            registerTable.add(dataBaseError);
            dataBaseError.setVisible(false);

        registerContainer.setFillParent(true);
        registerContainer.center();
        registerContainer.align(Align.center);
        registerContainer.padLeft(Value.percentWidth(0.4f));
        registerContainer.padRight(Value.percentWidth(0.4f));
        registerContainer.padTop(Value.percentHeight(0.3f));
        registerContainer.padBottom(Value.percentHeight(0.3f));
        registerContainer.add(registerTable);

        uiStage.addActor(registerContainer);
        registerContainer.setVisible(false);

        loggedInTable = new Table();
        loggedInTable.setFillParent(true);
        loggedInTable.bottom();
        loggedInTable.padTop(Value.percentHeight(0.8f));
        loggedInTable.padLeft(Value.percentWidth(0.2f));
        loggedInTable.padRight(Value.percentWidth(0.2f));
        loggedInTable.padBottom(Value.percentHeight(0.15f));

                final Table loggedInSubTable = new Table();
                loggedInSubTable.defaults().pad(10);
                loggedInSubTable.setBackground(Assets.default_panelTexture);

                Label playText = new Label("Press Space to PLAY", new Label.LabelStyle(Assets.default_LabelStyle));
                playText.getStyle().fontColor = new Color(255 / 256f, 221 / 256f, 160 / 256f, 1);
                playText.setFontScale(1.5f);
                playText.setAlignment(Align.bottom);
                loggedInSubTable.add(playText).grow();

        loggedInTable.add(loggedInSubTable).grow();

        uiStage.addActor(loggedInTable);
        loggedInTable.setVisible(false);



        deadTable = new Table();
        deadTable.setFillParent(true);
        deadTable.top();
        deadTable.padLeft(Value.percentWidth(0.3f));
        deadTable.padRight(Value.percentWidth(0.3f));
        deadTable.padTop(Value.percentHeight(0.3f));
        deadTable.padBottom(Value.percentHeight(0.3f));
        deadTable.setVisible(false);

        Table deadPanel = new Table();
        deadPanel.setBackground(Assets.default_panelTexture);
        deadPanel.top();
        deadPanel.defaults().pad(10);

        Label YDead = new Label("You are dead!", Assets.bold_LabelStyle);
        YDead.getStyle().fontColor = Color.RED;
        deadPanel.add(YDead);

        deadPanel.row();

        deadMeters = new Label("You ran: 88m", new Label.LabelStyle(Assets.default_LabelStyle));
        deadPanel.add(deadMeters);

        deadPanel.row();

        deadCoins = new Label("and collected: 88Coins", new Label.LabelStyle(Assets.default_LabelStyle));
        deadPanel.add(deadCoins);

        deadPanel.row();

        Table deadButtonPanel = new Table();

        TextButton okDeadButton = new TextButton("Ok!", new TextButton.TextButtonStyle(Assets.default_ButtonStyle));
        okDeadButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                World.w.reset();

                try {
                    if (ImpfChipConnection.get_coins() < PlayerController.coins) {
                        ImpfChipConnection.update_coins((int) PlayerController.coins);
                    }

                    if (ImpfChipConnection.get_highscore() < PlayerController.meters) {
                        ImpfChipConnection.new_highscore((int)PlayerController.meters);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                PlayerController.meters = 0;

                deadTable.setVisible(false);
                titleTable.setVisible(true);

                loggedInTable.setVisible(true);
            }
        });
        deadButtonPanel.add(okDeadButton);

        revive = new TextButton("Revive for 100C", new TextButton.TextButtonStyle(Assets.default_ButtonStyle));
        revive.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                PlayerController.coins -= 100;
                if (PlayerController.coins >= 0) {
                    World.w.reset();

                    deadTable.setVisible(false);
                    PlayerController.p.start();
                } else {
                    PlayerController.coins += 100;
                }

                UiInterface.coins.setText("Coins: " + PlayerController.coins);

            }
        });
        deadButtonPanel.add(revive);

        deadPanel.add(deadButtonPanel);

        deadTable.add(deadPanel).grow();

        uiStage.addActor(deadTable);
    }

    public void render() {
        this.uiStage.getViewport().apply();
        uiStage.draw();
    }

    public void act(float delta) {
        uiStage.act(delta);
    }

    public void dispose() {
        this.uiStage.dispose();
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public void rezise(int width, int height) {
        this.uiStage.getViewport().update(width, height);
    }

}