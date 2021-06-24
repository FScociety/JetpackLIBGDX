package de.soerensc.engine.ecs.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import de.soerensc.engine.ecs.GameBehaviour;

public class Transform2D extends GameBehaviour {
    public final Vector2 position = new Vector2();
    public float rotation;
}