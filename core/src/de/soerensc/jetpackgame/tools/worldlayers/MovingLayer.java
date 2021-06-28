package de.soerensc.jetpackgame.tools.worldlayers;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MovingLayer {

    public String name;
    private MovingData[] data;
    public MovingElement start;
    public float speed;

    public boolean removeElements;
    public boolean addElements;

    public Vector2 elementBounds;
    public float positionBounds;

    public int elements = 0;

    public MovingLayer(MovingData startingData, Vector2 elementBounds, float positionBounds, float speed) {
        this.elementBounds = elementBounds;
        this.positionBounds = positionBounds;

        //Just one Data information
        data = new MovingData[1];
        data[0] = startingData;

        this.speed = speed;
    }

    public MovingLayer(MovingData[] startingData, Vector2 elementBounds, float positionBounds, float speed) {
        this.elementBounds = elementBounds;
        this.positionBounds = positionBounds;

        //Apply more than one Data
        this.data = startingData;

        this.speed = speed;
    }

    public void addInstant(int i) {

        if (start == null) {
            start = new MovingElement(this, data, this.positionBounds);
            i--;
        }
        start.add(i);
    }

    public void addOverTime(int i) {
        if (start == null) {
            start = new MovingElement(this, data, -this.positionBounds + this.elementBounds.x);
            i--;
        }
        start.add(i);
    }

    public void removeOverTime() {
        this.removeElements = true;
    }

    public void reactivadeOverTime() {
        this.removeElements = false;
        this.start.realign(-this.positionBounds + this.elementBounds.x);
    }

    public MovingElement get(int i) {
        return this.start.get(i-1);
    }

    public void update(float delta) {

        //TODO: Speed Adjust
        this.start.move(speed * delta * 200);
    }

    public void render() {
        this.start.render();
    }

    /*public int getAmoutOverScreen() {
		System.out.println("Generating Elements");
		System.out.println("Screen size: " + GameContainer.windowSize);
		System.out.println("Element Bounds: " + this.elementBounds);
		float maxSpace = GameContainer.windowSize.x;
		maxSpace /= this.elementBounds.x;
		maxSpace = (float) Math.ceil(maxSpace);
		maxSpace++; //BUFFER

		System.out.println(maxSpace);

		return (int)maxSpace;
	}*/
}