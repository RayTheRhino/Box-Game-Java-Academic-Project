package View;

import javafx.stage.Stage;

import java.util.ArrayList;

import Controller.Controller;
import Model.RectangleShape;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class View {
	private Controller control;
	private Stage primaryStage;
	private Group root;
	private Scene scene;
	private BorderPane bp;
	private Pane drawPane;
	private Button nGame;
	private Button lvl1;
	private Button lvl2;
	private Button lvl3;
	private VBox scoreVbox, lvlVbox;
	private HBox gameName;
	private Text title;
	private Text scoreText;
	private ArrayList<Rectangle> rsArr;
	private ArrayList<RectangleShape> rects;
	private TextField playerNameTF;

	public View(Stage stage) {
		rsArr = new ArrayList<Rectangle>();
		rects = new ArrayList<RectangleShape>();
		primaryStage = stage;
		root = new Group();

		// level vBox buttons
		nGame = new Button("New Game");
		lvl1 = new Button("level 1");
		lvl2 = new Button("level 2");
		lvl3 = new Button("level 3");
		setButt(lvl1, true);
		setButt(lvl2, true);
		setButt(lvl3, true);

		lvlVbox = new VBox();
		lvlVbox.setPadding(new Insets(150, 10, 50, 10));
		lvlVbox.setAlignment(Pos.CENTER_LEFT);
		lvlVbox.getChildren().addAll(nGame, lvl1, lvl2, lvl3);

		// Score vBox
		scoreText = new Text("Score: 0");
		scoreText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		scoreText.setFill(Color.rgb(175, 107, 88));
		scoreVbox = new VBox();
		scoreText.setVisible(false);
		scoreVbox.setAlignment(Pos.TOP_RIGHT);
		scoreVbox.setPadding(new Insets(0, 0, 0, 20));
		scoreVbox.getChildren().add(scoreText);

		// Hbox
		title = new Text("Coloring Game:");
		title.setFill(Color.rgb(175, 107, 88));
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
		gameName = new HBox();
		gameName.setPadding(new Insets(15, 0, 15, 0));
		gameName.setAlignment(Pos.TOP_CENTER);
		gameName.getChildren().add(title);

		// draw the big rectangle
		drawPane = new Pane();

		bp = new BorderPane();
		bp.setLeft(lvlVbox);
		bp.setTop(gameName);
		bp.setCenter(drawPane);
		bp.setRight(scoreVbox);

		root.getChildren().add(bp);
		scene = new Scene(root, 750, 600);
		scene.setFill(Color.rgb(219, 208, 192));

		primaryStage.setScene(scene);

		primaryStage.show();

	}

	public void setControl(Controller control) {
		this.control = control;
	}

	// new game button action
	public void nGamePress(EventHandler<ActionEvent> nGamePressed) {
		nGame.setOnAction(nGamePressed);
	}

	// level 1 button action
	public void lvl1Press(EventHandler<ActionEvent> lvl1Pressed) {
		lvl1.setOnAction(lvl1Pressed);
	}

	// level 2 button action
	public void lvl2Press(EventHandler<ActionEvent> lvl2Pressed) {
		lvl2.setOnAction(lvl2Pressed);
	}

	// level 3 button action
	public void lvl3Press(EventHandler<ActionEvent> lvl3Pressed) {
		lvl3.setOnAction(lvl3Pressed);
	}

	public void updateRectangles(ArrayList<Rectangle> rsArr, int lvlnum) {
		this.rsArr = rsArr;
		drawPane.getChildren().clear();
		drawPane.getChildren().addAll(this.rsArr);
		control.setRecClickEvent();
		scoreText.setVisible(true);

		switch (lvlnum) {
		case 1:
			setButt(lvl1, true);
			setButt(nGame, false);
			setButt(lvl2, true);
			setButt(lvl3, true);
			break;
		case 2:
			setButt(lvl2, true);
			setButt(nGame, false);
			setButt(lvl1, true);
			setButt(lvl3, true);
			break;
		case 3:
			setButt(lvl3, true);
			setButt(nGame, false);
			setButt(lvl1, true);
			setButt(lvl2, true);
			break;

		default:
		}

	}

	public int gerecIndex(Object source) {
		return rsArr.indexOf(source);
	}

	public void updateColors(ArrayList<Rectangle> rsArr) {
		this.rsArr = rsArr;
		drawPane.getChildren().clear();
		drawPane.getChildren().addAll(this.rsArr);
	}

	public void updateSelectedrec(ArrayList<RectangleShape> rectangleShape) {
		rects = rectangleShape;
		for (int i = 0; i < rects.size(); i++) {
			if (rects.get(i).isSelected()) {
				if (rects.get(i).getRec().getFill() == rects.get(i).getRecColor()) {
					Color currentColor = (Color) rects.get(i).getRec().getFill();
					rects.get(i).getRec().setFill(currentColor.darker());
				}
			} else {
				rects.get(i).getRec().setFill(rects.get(i).getRecColor());
			}
		}
	}

	public void AddClickedEvent(EventHandler<MouseEvent> click) {
		for (int i = 0; i < rsArr.size(); i++) {
			rsArr.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, click);
		}

	}

	public void updateScore(int score) {
		scoreText.setText("Score: " + score);
	}

	private void setButt(Button but, boolean val) {
		but.setDisable(val);
	}

	public void newGamePressed() {
		setButt(nGame, true);
		setButt(lvl1, false);
		setButt(lvl2, false);
		setButt(lvl3, false);
		updateScore(0);

	}

	public void gameOver() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Game Over!");
		dialog.setHeaderText("Enter your name");
		dialog.showAndWait();
		String name = dialog.getResult();
		showRecords(control.updatePlayer(name));
	}

	private void showRecords(String updatePlayer) {
		drawPane.getChildren().clear();
		Label lbl = new Label();
		lbl.setText(updatePlayer);
		lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
		lbl.setTextFill(Color.rgb(158, 119, 119));
		drawPane.getChildren().add(lbl);
	}

}
