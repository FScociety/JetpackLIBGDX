package de.soerensc.jetpackgame.tools.worldlayers;

import de.soerensc.engine.ecs.GameBehaviour;

public abstract class MovingPatternControllerBehaviour extends GameBehaviour {

    public MovingPattern activePattern;
    public MovingPattern[] patterns;

    public String patternPath = "";

    public void create(int patternsSize, int patternHeight) {
        patterns = new MovingPattern[patternsSize];

        MovingPattern.loadingPath = patternPath;

        for (int i = 0; i < patternsSize; i++) {
            this.patterns[i] = new MovingPattern(i, patternHeight);
        }
    }

    public abstract MovingPattern newPattern();
}