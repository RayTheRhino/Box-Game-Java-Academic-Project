package Model;

import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleGame {
	private ArrayList<Rectangle> rsArr;
	private ArrayList<RectangleShape> rects;
	private ArrayList<FoundRectangle> foundRects;
	private int size;
	private Color[] colorArr;
	private Random rand;
	private int score;
	private Player[] players;
	private int playerCounter;
	private File file;

	public RectangleGame(File file) throws FileNotFoundException {
		rsArr = new ArrayList<Rectangle>();
		rects = new ArrayList<RectangleShape>();
		foundRects = new ArrayList<FoundRectangle>();
		rand = new Random();
		colorArr = new Color[5];
		players = new Player[10];
		generateColors();
		this.file = file;
		readPlayers();
	}

	private void generateColors() {
		for (int i = 0; i < colorArr.length; i++) {
			colorArr[i] = Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
		}
	}

	public void createMatrix(int newSize) {
		score = 0;
		rsArr.clear();
		rects.clear();
		foundRects.clear();
		this.size = newSize;
		int placeX = 0, placeY = 0, rectSize = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				RectangleShape rect = new RectangleShape(placeX, placeY, colorArr);
				rsArr.add(rect.getRec());
				rects.add(rect);
				rectSize = rect.getSize();
				placeX += rectSize;
			}
			placeX = 0;
			placeY += rectSize;

		}
	}

	public void findRects() {
		int width = 0, length = 0, row = 0, col = 0, countRow = 0, countCol = 0;
		boolean foundRight = false, foundDown = false;
		RectangleShape tleft = null, tright = null, bleft = null, bright = null;
		for (int i = 0; i < size * (size); i++) {
			tright = null;
			bleft = null;
			bright = null;
			length = 0;
			foundDown = false;
			foundRight = false;
			width = 0;
			tleft = rects.get(i);
			Color currentColor = tleft.getRecColor();
			for (int j = 1; j < size - countCol; j++) {
				/// check right
				if (currentColor == rects.get(i + j).getRecColor()) {
					foundRight = true;
					tright = rects.get(i + j);
					col = countCol + j;
					length++;
					if (foundRight) {
						for (int k = 1; k < size - countRow; k++) {
							if (currentColor == rects.get(i + k * size).getRecColor()) {
								foundDown = true;
								bleft = rects.get(i + k * size);
								row = countRow + k;
								width++;
								if (foundDown && foundRight) {
									/// check the bottom right
									if (currentColor == rects.get(row * size + col).getRecColor()) {
										bright = rects.get(row * size + col);
										if (tleft != null && tright != null && bleft != null && bright != null) {
											int[] index = new int[4];
											index[0] = rects.indexOf(tleft);
											index[1] = rects.indexOf(tright);
											index[2] = rects.indexOf(bleft);
											index[3] = rects.indexOf(bright);
											FoundRectangle found = new FoundRectangle(tleft, tright, bleft, bright,
													width * length, index);
											foundRects.add(found);
										}
									}
								}
							} else
								width++;
						}
					}
				} else
					length++;
			}
			countCol++;
			if (countCol == size) {
				countCol = 0;
				countRow++;
			}
		}
	}

	public void printRec(RectangleShape rec, RectangleShape rec2, RectangleShape rec3, RectangleShape rec4) {
		System.out.println(rects.indexOf(rec) + ", " + rects.indexOf(rec2) + ", " + rects.indexOf(rec3) + ", "
				+ rects.indexOf(rec4));
	}

	public ArrayList<Rectangle> getRsArr() {
		return rsArr;
	}

	public void setRsArr(ArrayList<Rectangle> rsArr) {
		this.rsArr = rsArr;
	}

	public void changeStatus(int recIndex) {
		rects.get(recIndex).setSelected();

	}

	public boolean getRecStatus(int recIndex) {
		return rects.get(recIndex).isSelected();

	}

	public ArrayList<RectangleShape> getRectangleShape() {
		return rects;
	}

	public boolean checkFoundRec(int[] selectedIndex) {
		Arrays.sort(selectedIndex);
		for (int i = 0; i < foundRects.size(); i++) {
			if (foundRects.get(i).equals(selectedIndex)) {
				replaceColors(selectedIndex);
				foundRects.clear();
				findRects();
				return true;
			}
		}
		return false;
	}

	public boolean hasRects() {
		return foundRects.size() > 0;
	}

	private void replaceColors(int[] selectedIndex) {
		int width = selectedIndex[1] - selectedIndex[0];
		int length = (selectedIndex[2] - selectedIndex[0]) / size;
		int counter = 0;
		score += (width + 1) * (length + 1);
		for (int i = 0; i <= length; i++) {
			for (int j = 0; j <= width; j++) {
				rects.get(selectedIndex[0] + i * size + j).generateColors();
				counter++;
			}
		}
	}

	public int getScore() {
		return score;
	}

	public void readPlayers() throws FileNotFoundException {
		int readScore;
		Scanner scan = new Scanner(file);
		if (scan.hasNext()) {
			playerCounter = Integer.parseInt(scan.nextLine());
		}
		for (int i = 0; i < playerCounter; i++) {
			String name = scan.nextLine();
			readScore = Integer.parseInt(scan.nextLine());
			players[i] = new Player(name, readScore);
		}
		scan.close();
	}

	public void writeToFile() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(file);
		pw.write(playerCounter + "\n");
		for (int i = 0; i < playerCounter; i++) {
			players[i].savePlayer(pw);
		}
		pw.close();
	}

	static void bubbleSort(Player[] arr) {
		int n = arr.length;
		Player temp = new Player(null, 0);
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (arr[j - 1].getScore() < arr[j].getScore()) {
					// swap elements
					temp.setScore(arr[j - 1].getScore());
					temp.setName(arr[j - 1].getName());
					arr[j - 1].setScore(arr[j].getScore());
					arr[j - 1].setName(arr[j].getName());
					arr[j].setScore(temp.getScore());
					arr[j].setName(temp.getName());
				}

			}
		}

	}

	public String updatePlayer(String name) {
		Player player = new Player(name, score);
		if (playerCounter < 10) {
			players[playerCounter] = player;
			playerCounter++;
		} else {
			for (int i = 0; i < playerCounter; i++) {
				if (players[i].getScore() < player.getScore()) {
					players[i] = player;
					break;
				}
			}
			bubbleSort(players);
		}
		try {
			writeToFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playersText();
	}

	private String playersText() {
		StringBuffer str = new StringBuffer("Top 10 players:\n");
		for (int i = 0; i < playerCounter; i++) {
			str.append("(" + (i + 1) + ") Name: " + players[i].getName() + ".................Score: " + players[i].getScore()
					+ "\n");
		}
		return str.toString();
	}

}
