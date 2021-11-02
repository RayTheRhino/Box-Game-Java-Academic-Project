package Controller;

import java.util.ArrayList;

import Model.Model;
import Model.RectangleGame;
import Model.RectangleShape;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class Controller {
	private View view;
	private Model model;
	private int clickCounter;
	private int[] selectedIndex;

	public Controller(Model m, View v) {
		model = m;
		view = v;
		selectedIndex = new int[4];
		model.setControl(this);
		view.setControl(this);
		EventHandler<ActionEvent> nGamepRessed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				view.newGamePressed();
			}
		};
		view.nGamePress(nGamepRessed);

		EventHandler<ActionEvent> lvl1pRessed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ArrayList<Rectangle> lvl1Arr = model.startGame(6);
				
				view.updateRectangles(lvl1Arr,1);
				if(!model.hasRects()) {
					view.gameOver();
				}
			}
		};
		view.lvl1Press(lvl1pRessed);

		EventHandler<ActionEvent> lvl2pRessed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ArrayList<Rectangle> lvl2Arr = model.startGame(8);
				view.updateRectangles(lvl2Arr,2);
				if(!model.hasRects()) {
					view.gameOver();
				}
			}
		};
		view.lvl2Press(lvl2pRessed);

		EventHandler<ActionEvent> lvl3pRessed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				ArrayList<Rectangle> lvl3Arr = model.startGame(11);
				view.updateRectangles(lvl3Arr,3);
				if(!model.hasRects()) {
					view.gameOver();
				}
			}
		};
		view.lvl3Press(lvl3pRessed);

	}

	public void setRecClickEvent() {
		// Create event handler for clicking on rectangle
		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				int recIndex = view.gerecIndex(event.getSource());
				if (clickCounter < 4) {

					if (model.getRecStatus(recIndex)) {
						clickCounter--;
					} else {
						selectedIndex[clickCounter] = recIndex;
						clickCounter++;
						if (clickCounter == 4) {
							if (model.checkFoundRec(selectedIndex)) {
								view.updateSelectedrec(model.getRectangleShape());
								view.updateColors(model.getRecs());
								view.updateScore(model.getScore());
								clickCounter = 0;
								if(!model.hasRects()) {
									view.gameOver();
								}
							}
						}
					}

					model.changeRecStatus(recIndex);
					// now update the view
					view.updateSelectedrec(model.getRectangleShape());
				} else {
					if (model.getRecStatus(recIndex)) {
						clickCounter--;
						model.changeRecStatus(recIndex);
						view.updateSelectedrec(model.getRectangleShape());
					}
				}
			}
		};
		view.AddClickedEvent(click);

	}

	public String updatePlayer(String name) {
		return model.updatePlayer(name);
		
	}

}
