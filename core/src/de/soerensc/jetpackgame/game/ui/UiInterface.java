package de.soerensc.jetpackgame.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mysql.jdbc.log.Log;
import de.soerensc.dbconnection.CONNECTION;
import de.soerensc.jetpackgame.game.world.foreground.player.PlayerController;

import java.sql.SQLException;

public class UiInterface {

    public static boolean loggedIn = false;
    public static UiInterface uiInterface;

    private final Skin uiSkin;
    private final Stage uiStage;

    BitmapFont defaultFont;
    Label.LabelStyle labelStyle;
    TextButton.TextButtonStyle textButtonStyle;
    TextField.TextFieldStyle textFieldStyle;
    //Label.LabelStyle textFieldStyle;

    public int currentScore;
    public int currentCoins;

    public Table loggedInTable;

    private Table registerTable;
    private Label registerLabel;
    private TextButton finalRegisterButton;

    public static Label playText;

    private CONNECTION ImpfChipConnection;

    public UiInterface() {
        if (UiInterface.uiInterface == null) {
            UiInterface.uiInterface = this;
        }

        uiSkin = new Skin(Gdx.files.internal("metalui/metal-ui.json"));
        uiStage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(this.uiStage);

        currentScore = 17286;
        currentCoins = 2346;

        this.loadDefaultFont();

        ImpfChipConnection = new CONNECTION();

        //uiSkin.add("default-font", this.defaultFont, BitmapFont.class);
    }

    public void create() {
        uiStage.setDebugAll(true);

        final Table userTable = new Table();
        userTable.setFillParent(true);
        userTable.padLeft(Value.percentWidth(0.2f));
        userTable.padRight(Value.percentWidth(0.2f));
        userTable.padTop(Value.percentHeight(0.6f));
        userTable.padBottom(Value.percentHeight(0.2f));
        userTable.defaults().pad(10);

            final Table onlineTable = new Table();
            onlineTable.defaults().pad(10);
            userTable.add(onlineTable).grow();

                final TextButton loginButton = new TextButton("LOGIN", uiSkin);
                loginButton.addListener(new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        registerTable.setVisible(true);
                        userTable.setVisible(false);

                        registerLabel.setText("login to a new account");
                        finalRegisterButton.getLabel().setText("LOGIN");
                    }
                });
                loginButton.getLabel().setStyle(this.labelStyle);
                onlineTable.add(loginButton).grow();

                final TextButton registerButton = new TextButton("REGISTER", uiSkin);
                registerButton.addListener(new ClickListener() {
                    @Override
                    public void clicked (InputEvent event, float x, float y) {
                        registerTable.setVisible(true);
                        userTable.setVisible(false);

                        registerLabel.setText("register a new account");
                        finalRegisterButton.getLabel().setText("REGISTER");

                        //TODO: Database

                        //if connected ===>
                        loggedIn = true;
                        loggedInTable.setVisible(true);
                    }
                });
                registerButton.getLabel().setStyle(this.labelStyle);
                onlineTable.add(registerButton).grow();

            TextButton offlineButton = new TextButton("PLAY OFFLINE", uiSkin);
            offlineButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    userTable.setVisible(false);
                    loggedIn = true;
                    loggedInTable.setVisible(true);
                }
            });
            TextButton.TextButtonStyle defaultButtonStyle = offlineButton.getStyle();
            defaultButtonStyle.font = this.defaultFont;
            offlineButton.setStyle(defaultButtonStyle);
            userTable.row();
            userTable.add(offlineButton).growX();

        uiStage.addActor(userTable);
        userTable.setVisible(true);


        registerTable = new Table();
        registerTable.defaults().align(Align.center);
        registerTable.setFillParent(true);
        registerTable.padLeft(Value.percentWidth(0.4f));
        registerTable.padRight(Value.percentWidth(0.4f));
        registerTable.padTop(Value.percentHeight(0.3f));
        registerTable.padBottom(Value.percentHeight(0.3f));
        registerTable.defaults().pad(10);

            registerLabel = new Label("register a new account", uiSkin);
            registerLabel.setStyle(this.labelStyle);
            registerLabel.setAlignment(Align.center);
            registerTable.add(registerLabel).growX();

            registerTable.row();

            Label usernameLabel = new Label("Username:", uiSkin);
            usernameLabel.setStyle(this.labelStyle);
            registerTable.add(usernameLabel).growX();

            registerTable.row();

            final TextField usernameInputField = new TextField("", uiSkin);
            TextField.TextFieldStyle defaultTextFieldStyle = usernameInputField.getStyle();
            defaultTextFieldStyle.font = this.defaultFont;
            usernameInputField.setStyle(defaultTextFieldStyle);
            registerTable.add(usernameInputField).grow();

            registerTable.row();

            Label passwordLabel = new Label("Password:", uiSkin);
            passwordLabel.setStyle(this.labelStyle);
            registerTable.add(passwordLabel).growX();

            registerTable.row();

            final TextField passwordInputField = new TextField("", uiSkin);
            registerTable.add(passwordInputField).grow();

            registerTable.row();

            finalRegisterButton = new TextButton("REGISTER", uiSkin);
            finalRegisterButton.addListener(new ClickListener() {
                @Override
                public void clicked (InputEvent event, float x, float y) {
                    //TODO: Database

                    //if connected ===>
                    if (finalRegisterButton.getLabel().getText().toString().equals("REGISTER")) {
                        try {
                            if (ImpfChipConnection.create_acc(usernameInputField.getText(), passwordInputField.getText())) {
                                userTable.setVisible(false);
                                onlineTable.setVisible(false);
                                loggedIn = true;
                                loggedInTable.setVisible(true);
                            }
                        } catch (SQLException throwables) {
                            Gdx.app.error("IMPFCHIP", "WAIT WHAT ?! | Illegal IMPF-Chip => Cant create Impf Chip");
                            throwables.printStackTrace();
                        }
                    } else {
                        try {
                            if (ImpfChipConnection.login(usernameInputField.getText(), passwordInputField.getText())) {
                                userTable.setVisible(false);
                                onlineTable.setVisible(false);
                                loggedIn = true;
                                loggedInTable.setVisible(true);
                            }
                        } catch (SQLException throwables) {
                            Gdx.app.error("IMPFCHIP", "Could not logging to IMPF-CHIP");
                            throwables.printStackTrace();
                        }
                    }
                }
            });
            //finalRegisterButton.setStyle(this.textButtonStyle);
            finalRegisterButton.setStyle(defaultButtonStyle);

            registerTable.add(finalRegisterButton);

            registerTable.row();

        uiStage.addActor(registerTable);
        registerTable.setVisible(false);

        loggedInTable = new Table();
        loggedInTable.setFillParent(true);
        loggedInTable.bottom();
        loggedInTable.padBottom(Value.percentHeight(0.15f));

            Container playTextContainer = new Container();
            playTextContainer.setFillParent(true);

                Label playText = new Label("Press Space to PLAY", uiSkin);
                playText.setStyle(this.labelStyle);
                playText.setFontScale(2);
                loggedInTable.add(playText);

            loggedInTable.add(playTextContainer);

        uiStage.addActor(loggedInTable);
        loggedInTable.setVisible(false);


        /* set backgroundcolor
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(Color.BLACK);
        bgPixmap.fill();
        TextureRegionDrawable textureRegionDrawableBg = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));*/


        // Teil zum Spiel starten

        /*Table playTable = new Table();
        playTable.setFillParent(true);
        playTable.padTop(Value.percentHeight(0.5f));

        playText = new Label("Leertaste - Spielen", uiSkin);
        playText.setFontScale(3);
        playTable.add(playText);
        uiStage.addActor(playTable);



        // Teil zum anmelden

        Table loginTable = new Table();
        loginTable.setFillParent(true);
        loginTable.padTop(Value.percentHeight(0f));
        //loginTable.setBackground(textureRegionDrawableBg);

        Label loginLabel = new Label("Log In", uiSkin);
        loginLabel.setFontScale(2);
        Label usernameLabel = new Label("Benutzername: ", uiSkin);
        loginUsername = new TextArea("", uiSkin);
        Label passwordLabel = new Label("Passwort: ", uiSkin);
        loginPassword = new TextArea("", uiSkin);
        TextButton loginBtn = new TextButton("Anmelden!", uiSkin);

        loginBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!loginUsername.getText().equals("") && !loginPassword.getText().equals("")) {
                    System.out.println(loginUsername.getText() + ", " + loginPassword.getText());
                    // TODO: login() funktion aufrufen
                }
            }
        });

        loginTable.add(loginLabel);
        loginTable.row();
        loginTable.add(usernameLabel);
        loginTable.row();
        loginTable.add(loginUsername);
        loginTable.row();
        loginTable.add(passwordLabel);
        loginTable.row();
        loginTable.add(loginPassword);
        loginTable.row();
        loginTable.add(loginBtn);
        uiStage.addActor(loginTable);
        loginTable.setVisible(false);




        // Teil zum Account erstellen

        Table createAccTable = new Table();
        createAccTable.setFillParent(true);
        createAccTable.padTop(Value.percentHeight(0f));
        //createAccTable.setBackground(textureRegionDrawableBg);

        Label accCreateLabel = new Label("Account erstellen!", labelStyle);
        Label accCreateUsernameLabel = new Label("Benutzername: ", labelStyle);
        accCreateUsername = new TextArea("", uiSkin);
        Label accCreatePasswordLabel = new Label("Passwort: ", labelStyle);
        accCreatePassword = new TextArea("", uiSkin);

        TextButton createAccBtn = new TextButton("Anmelden!", uiSkin);

        createAccBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!accCreateUsername.getText().equals("") && !accCreatePassword.getText().equals("")) {
                    System.out.println(accCreateUsername.getText() + ", " + accCreatePassword.getText());
                    // TODO: createAccount() funktion aufrufen
                }
            }
        });

        createAccTable.add(accCreateLabel);
        createAccTable.row();
        createAccTable.add(accCreateUsernameLabel);
        createAccTable.row();
        createAccTable.add(accCreateUsername);
        createAccTable.row();
        createAccTable.add(accCreatePasswordLabel);
        createAccTable.row();
        createAccTable.add(accCreatePassword);
        createAccTable.row();
        createAccTable.add(createAccBtn);
        uiStage.addActor(createAccTable);
        createAccTable.setVisible(false);*/


    }

    private void loadDefaultFont() {
        FreeTypeFontGenerator defaultGenerator = new FreeTypeFontGenerator(Gdx.files.internal("ui/fonts/ThaleahFat.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter defaultParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        defaultParameter.size = 50;
        defaultParameter.borderWidth = 0;
        defaultParameter.color = Color.BLACK;
        defaultFont = defaultGenerator.generateFont(defaultParameter);

        this.labelStyle = new Label.LabelStyle();
        labelStyle.font = defaultFont;

        this.textFieldStyle = new TextField.TextFieldStyle();
        this.textFieldStyle.font = defaultFont;

        this.textButtonStyle = new TextButton.TextButtonStyle();
        this.textButtonStyle.font = defaultFont;

        defaultGenerator.dispose();
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