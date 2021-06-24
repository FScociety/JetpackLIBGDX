package de.soerensc.jetpackgame.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class Ground extends WorldObject {

    public Model model;
    public ModelInstance groundWall, ground;

    public Ground() {
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createRect(
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f,
                1, 0, 0,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        groundWall = new ModelInstance(model);
        ground = new ModelInstance(model);
        ground.transform.rotate(Vector3.X, -90f);
        ground.transform.setTranslation(new Vector3(0, 0.5f, -0.5f));
    }

    @Override
    public void dispose() {
        model.dispose();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(final ModelBatch modelBatch, final Environment environment) {
        modelBatch.render(groundWall, environment);
        modelBatch.render(ground, environment);
    }
}