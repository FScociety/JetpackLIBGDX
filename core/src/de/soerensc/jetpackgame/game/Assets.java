package de.soerensc.jetpackgame.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.xml.soap.Text;
import java.nio.charset.StandardCharsets;

public class Assets {

    public static final AssetManager manager = new AssetManager();

    //In-Game Textures

    //UI-Textures
    public static final String defaultFont = "ui/fonts/PIXEARG_.ttf";
    public static final String defaultFont_Bold = "ui/fonts/PIXEAB__.ttf";
    public static final String uiElements = "ui/uiAtlas.atlas";
    public static final String title = "ui/title.png";
    public static TextButton.TextButtonStyle default_ButtonStyle;
    public static Label.LabelStyle default_LabelStyle;
    public static Label.LabelStyle bold_LabelStyle;
    public static TextField.TextFieldStyle default_TextFieldStyle;
    public static NinePatchDrawable default_panelTexture;

    //Sounds
    public static String coinSound = "sound/coinCollect.wav";
    public static String runningSound = "sound/running.wav";


    public static void load() {
        //FONT
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parms_default = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parms_default.fontFileName = defaultFont;
        parms_default.fontParameters.size = 50;
        parms_default.fontParameters.minFilter = Texture.TextureFilter.Nearest;
        parms_default.fontParameters.magFilter = Texture.TextureFilter.Nearest;
        manager.load(defaultFont, BitmapFont.class, parms_default);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parms_bold = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parms_bold.fontFileName = defaultFont_Bold;
        parms_bold.fontParameters.size = 50;
        parms_bold.fontParameters.minFilter = Texture.TextureFilter.Nearest;
        parms_bold.fontParameters.magFilter = Texture.TextureFilter.Nearest;
        manager.load(defaultFont_Bold, BitmapFont.class, parms_bold);

        //UI-Textures
        manager.load(uiElements, TextureAtlas.class);

        //SOUND
        manager.load(coinSound, Sound.class);
        manager.load(runningSound, Sound.class);

        //FINISH
        manager.finishLoading();

        Assets.loadPanelTexture();
        Assets.loadButtonStyle();
        Assets.loadLabelStyle();
        Assets.loadTextFieldStyle();
    }

    private static void loadPanelTexture() {
        TextureAtlas uiElementsAtlas = manager.get(uiElements);

        //DEFAULT Panel
        NinePatch panel_default = new NinePatch(uiElementsAtlas.findRegion("canvas"), 4, 4, 4, 6);
        panel_default.scale(6, 6);
        Assets.default_panelTexture = new NinePatchDrawable(panel_default);
    }

    private static void loadTextFieldStyle() {
        Assets.default_TextFieldStyle = new TextField.TextFieldStyle();
        default_TextFieldStyle.font = manager.get(defaultFont);
        default_TextFieldStyle.fontColor = Color.DARK_GRAY;

        TextureAtlas uiElementsAtlas = manager.get(uiElements);

        //DEFAULT TextField
        NinePatch textfield_default = new NinePatch(uiElementsAtlas.findRegion("textfield_default"), 3, 3, 3, 3);
        textfield_default.scale(6, 6);
        Assets.default_TextFieldStyle.background = new NinePatchDrawable(textfield_default);

        //FOCUSED TextField
        NinePatch textfield_selected = new NinePatch(uiElementsAtlas.findRegion("textfield_selected"), 3, 3, 3, 3);
        textfield_selected.scale(6, 6);
        Assets.default_TextFieldStyle.focusedBackground = new NinePatchDrawable(textfield_selected);

        //CURSOR
        TextureRegionDrawable cursor = new TextureRegionDrawable(uiElementsAtlas.findRegion("cursor"));
        default_TextFieldStyle.cursor = new TextureRegionDrawable(cursor);

        //SELECTION
        TextureRegionDrawable selection = new TextureRegionDrawable(uiElementsAtlas.findRegion("selection"));
        default_TextFieldStyle.selection = new TextureRegionDrawable(selection);

    }

    private static void loadButtonStyle() {
        Assets.default_ButtonStyle = new TextButton.TextButtonStyle();
        default_ButtonStyle.font = manager.get(defaultFont);
        default_ButtonStyle.pressedOffsetY = -10;

        TextureAtlas uiElementsAtlas = manager.get(uiElements);

        //DEFAULT Button
        NinePatch button_default = new NinePatch(uiElementsAtlas.findRegion("button_default"), 7, 7, 7, 7);
        button_default.scale(6, 6);
        default_ButtonStyle.up = new NinePatchDrawable(button_default);

        //HOVER Button
        NinePatch button_press = new NinePatch(uiElementsAtlas.findRegion("button_press"), 7, 7, 7, 7);
        button_press.scale(6, 6);
        default_ButtonStyle.down = new NinePatchDrawable(button_press);

        //PRESS Button
        NinePatch button_hover = new NinePatch(uiElementsAtlas.findRegion("button_hover"), 7, 7, 7, 7);
        button_hover.scale(6, 6);
        default_ButtonStyle.over = new NinePatchDrawable(button_hover);
    }

    private static void loadLabelStyle() {
        Assets.default_LabelStyle = new Label.LabelStyle();
        default_LabelStyle.font = manager.get(defaultFont);
        default_LabelStyle.fontColor = Color.WHITE;

        Assets.bold_LabelStyle = new Label.LabelStyle();
        bold_LabelStyle.font = manager.get(defaultFont_Bold);
        bold_LabelStyle.fontColor = Color.WHITE;
    }
}
