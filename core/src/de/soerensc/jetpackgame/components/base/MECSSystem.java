package de.soerensc.jetpackgame.components.base;

import java.util.ArrayList;

public class MECSSystem {
    public ArrayList<MEntity> entities = new ArrayList<>();

    public MECSSystem() {

    }

    public void addEntity(MEntity entity) {
        entities.add(entity);
    }

    public void update() {

    }

    public void render() {

    }
}