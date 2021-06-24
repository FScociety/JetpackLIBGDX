package de.soerensc.ponggame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import de.soerensc.ponggame.core.Boot;
import de.soerensc.ponggame.core.GameScreen;
import de.soerensc.ponggame.helper.BodyHelper;
import de.soerensc.ponggame.helper.ContactType;

public class Wall {

    private Body body;
    private float x, y;
    private int width, height;
    private Texture texture;

    public Wall(float y, GameScreen gameScreen) {
        this.x = Boot.INSTANCE.getScreenWidth() / 2;
        this.y = y;
        this.width = Boot.INSTANCE.getScreenWidth();
        this.height = 64;

        this.texture = new Texture("application-icon.png");
        this.body = BodyHelper.createBody(x, y, width, height, true, 0, gameScreen.getWorld(), ContactType.WALL);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x - (width / 2), y - (height / 2), width, height);
    }
}