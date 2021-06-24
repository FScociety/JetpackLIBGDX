package de.soerensc.jetpackgame.components.base;

import java.sql.Array;
import java.util.ArrayList;

public class MEntity {
    private ArrayList<MBaseComponent> baseComponents = new ArrayList<>();
    private ArrayList<MComponent> components = new ArrayList<>();

    public MEntity() {

    }

    public void addComponent(MBaseComponent component) {
        this.baseComponents.add(component);
    }

    public void addComponent(MComponent component) {
        this.baseComponents.add(component);
    }

    public MBaseComponent getComponent(Class<? extends MBaseComponent> e) {
        for (final MBaseComponent component : baseComponents) {
            if (component.getClass() == e || e.isInstance(component)) {
                return component;
            }
        }

        return null;
    }
}