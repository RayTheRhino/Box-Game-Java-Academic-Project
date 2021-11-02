package Model;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Player {
	private String name;
	private int score;

	public Player(String name, int score) {
	this.name = name;
	this.score = score;
	}

	public void savePlayer(PrintWriter pw){
		pw.println(name);
		pw.println(score);
	}
	public int getScore() {
		return this.score;
	}
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScore(int score) {
		this.score = score;
	}




}
