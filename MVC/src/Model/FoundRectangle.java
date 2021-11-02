package Model;

public class FoundRectangle {
	private RectangleShape topLeft;
	private RectangleShape topRight;
	private RectangleShape bottomLeft;
	private RectangleShape bottomRight;
	private int[] index;
	private int score;

	public FoundRectangle(RectangleShape tleft, RectangleShape tright, RectangleShape bleft, RectangleShape bright,
			int score, int[] index) {
		topLeft = tleft;
		topRight = tright;
		bottomLeft = bleft;
		bottomRight = bright;
		this.score = score;
		this.index = index;
	}

	public boolean equals(int [] otherRec) {
		for (int i = 0; i < index.length; i++) {
			if(index[i]!=otherRec[i])
				return false;
		}
		topLeft.setSelected();
		topRight.setSelected();
		bottomLeft.setSelected();
		bottomRight.setSelected();
		return true;
	}

}
