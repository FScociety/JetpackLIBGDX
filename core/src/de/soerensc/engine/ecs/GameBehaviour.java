package de.soerensc.engine.ecs;

public abstract class GameBehaviour {

    public GameObject gameObject;

    public void start() {}
    public void update(float delta) {}
    public void render() {}
    public void dispose() {}
}
