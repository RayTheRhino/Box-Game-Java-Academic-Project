package Model;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleShape {
	private Rectangle rec;
	private int x, y;
	private int size = 40;
	private int green, blue, red;
	private Random rand;
	private boolean selected;
	private Color recColor;
	private Color[] colorArr;

	public RectangleShape(int x, int y, Color[] colorArr) {
		rand = new Random();
		rec = new Rectangle(x, y, size, size);
		this.colorArr = colorArr;
		generateColors();

	}

	public void generateColors() {
		int num = rand.nextInt(colorArr.length);
		recColor = colorArr[num];
		rec.setFill(colorArr[num]);
		rec.setStroke(Color.BLACK);

	}

	public void setSelected() {
		if (selected == true) {
			selected = false;
		} else {
			selected = true;
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public Color getRecColor() {
		return recColor;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public Rectangle getRec() {
		return rec;
	}

}
