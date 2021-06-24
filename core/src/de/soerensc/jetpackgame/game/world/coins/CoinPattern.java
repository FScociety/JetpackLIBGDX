package de.soerensc.jetpackgame.game.world.coins;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CoinPattern {

	/*
	private int[][] pattern;
	private int posCount = 0;
	private int randomPosY = 0;
 	
	public CoinPattern(int number) {
		String filePath = "/coins/patterns/pattern-" + number + ".txt";
		
		InputStream fileStream = CoinPattern.class.getResourceAsStream(filePath);
		
		Scanner fileScanner = new Scanner(fileStream);
		
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
		if (this.posCount <= this.pattern.length) {
			int[] stripe = new int[CoinData.coinSize];
			
			for (int i = 0; i < this.pattern[0].length; i++) {
				stripe[i+randomPosY] = this.pattern[this.posCount-1][i];
			}
			
			return stripe;
		} else {
			return null;
		}
	}
	
	public boolean isFinished() {
		return this.posCount > this.pattern.length;
	}
	
	public void newYOffset() {
		randomPosY = (int) Math.round(Math.random()*(CoinData.coinSize-this.pattern[0].length));
	}

	*/
}