package de.soerensc.jetpackgame.tools.worldlayers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.soerensc.jetpackgame.game.world.foreground.coins.CoinData;

import java.util.ArrayList;
import java.util.Scanner;

public class MovingPattern {

    private int[][] pattern;
    private int posCount = 0;
    private int randomPosY = 0;

    private int maxArraySize;

    public static String loadingPath;

    public static void setPath(String path) {
        MovingPattern.loadingPath = path;
    }

    public MovingPattern(int number, int size) {
        this.maxArraySize = size;
        //FileHandle handle = Gdx.files.internal("world/coinPatterns/pattern-" + number + ".txt");
        FileHandle handle = Gdx.files.internal(MovingPattern.loadingPath + "/pattern-" + number + ".txt");

        Scanner fileScanner = new Scanner(handle.read());

        ArrayList<int[]> elements = new ArrayList<>();

        while(fileScanner.hasNextLine()) {
            char[] data = fileScanner.nextLine().toCharArray();

            int[] lineConvert = new int[data.length];
            for (int charI = 0; charI < data.length; charI++) {
                lineConvert[charI] = data[charI] == '0' ? 0 : 1;
            }

            elements.add(lineConvert);
        }

        //SAVING
        int width = elements.get(0).length;
        int height = elements.size();

        this.pattern = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.pattern[x][y] = elements.get(y)[x];
            }
        }
    }

    public void refresh() {
        this.posCount = 0;
    }

    public int[] getLine() {
        this.posCount++;

        int[] stripe = new int[maxArraySize];

        if (this.posCount <= this.pattern.length) {

            System.arraycopy(this.pattern[this.posCount - 1], 0, stripe, randomPosY, this.pattern[0].length);
        }
        return stripe;
    }

    public boolean isFinished() {
        return this.posCount > this.pattern.length;
    }

    public void newYOffset() {
        randomPosY = (int) Math.round(Math.random()*(maxArraySize-this.pattern[0].length));
    }
}
