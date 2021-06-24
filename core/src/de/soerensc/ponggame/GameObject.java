package de.soerensc.ponggame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class GameObject {
    public ModelInstance modelInstance;
    public Vector3 position;

    public GameObject(Model baseModel, Vector3 pos) {
        modelInstance = new ModelInstance(baseModel, pos);
        modelInstance.userData = new Color((pos.x+5f)/10f, (pos.z+5f)/10f, 0, 1);
        this.position = pos;
    }

    public void move() {
        /*float posY = 0;
        posY = (float)Math.sin(((float)Demo3d.renderCounter + position.x / 5 + position.z / 5) / 20) * 20;

        modelInstance.transform.setTranslation(new Vector3(position.x,
                posY,
                position.z));

         */
    }
}
