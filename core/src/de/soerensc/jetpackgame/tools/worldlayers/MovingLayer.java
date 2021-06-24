package de.soerensc.jetpackgame.tools.worldlayers;

import com.badlogic.gdx.math.Vector2;

public class MovingLayer {

    private MovingData startingData;
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
        this.startingData = startingData;

        this.speed = speed;
    }

    public void addInstant(int i) {

        if (start == null) {
            start = new MovingElement(this, startingData, this.positionBounds);
            i--;
        }
        start.add(i);
    }

    public void addOverTime(int i) {
        if (start == null) {
            start = new MovingElement(this, startingData, -this.positionBounds + this.elementBounds.x);
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
        this.start.move((float) (speed * delta * 1000 * 10));
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