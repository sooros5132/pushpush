package com.team.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestProgram {

	public static void main(String[] args) {
		
		int round = 4;
		int min = 4;
		
		List<String> temp = new ArrayList<>();
		String[][] quiz = new String[round][];
		Random rand = new Random();
		for (int i = 0; i < round; i++) {
			quiz[i] = new String[min];
			for (int j = 0; j < min; j++) {
				quiz[i][j] = Integer.toString(37 + rand.nextInt(4)); // [37][38][39][37]
													// [38][40][39][38][38]
			}
			min++;
			temp.add(String.join(":", quiz[i]));
		}
		
		String quizCSV = String.join(",", temp);
		System.out.println(quizCSV);
	}

}
