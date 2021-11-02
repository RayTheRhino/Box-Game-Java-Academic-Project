package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Controller.Controller;
import javafx.scene.shape.Rectangle;

public class Model {
	private RectangleGame game;
	private Controller control;
	public Model() throws FileNotFoundException {
		File file = new File("PlayersRecord.txt");
		game = new RectangleGame(file);	
	}

	public ArrayList<Rectangle> startGame(int size){
		game.createMatrix(size);
		game.findRects();
		return game.getRsArr();

	}

	public void setControl(Controller control) {
		this.control = control;
	}

	public void changeRecStatus(int recIndex) {
		game.changeStatus(recIndex);
	}
	public boolean getRecStatus(int recIndex){
		return game.getRecStatus(recIndex);
	}

	public ArrayList<RectangleShape> getRectangleShape() {
		return game.getRectangleShape();
	}

	public boolean checkFoundRec(int[] selectedIndex) {
		return game.checkFoundRec(selectedIndex);

	}

	public ArrayList<Rectangle> getRecs() {
		return game.getRsArr();
	}

	public int getScore() {
		return game.getScore();
	}

	public boolean hasRects() {
		return game.hasRects();
	}

	public String updatePlayer(String name) {
		return game.updatePlayer(name);
		
	}


}
