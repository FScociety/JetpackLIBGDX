package de.soerensc.jetpackgame.systems;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class WorldObject {
    public final Vector3 position = new Vector3(0,0,0);

    public abstract void update(float delta);

    public abstract void render(final ModelBatch batch, final Environment environment);

    public abstract void dispose();
}