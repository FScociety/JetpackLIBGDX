package de.soerensc.jetpackgame.tools.engine.ecs.components;

import com.badlogic.gdx.math.Vector2;
import de.soerensc.jetpackgame.tools.engine.ecs.GameBehaviour;

public class Transform2D extends GameBehaviour {
    public final Vector2 position = new Vector2();
    public float rotation;
}