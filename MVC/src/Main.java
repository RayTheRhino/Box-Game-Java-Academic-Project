import Controller.Controller;
import Model.Model;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args)  {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		Model model = new Model();
		View view = new View(stage);
		Controller controller = new Controller(model,view);

	}

}
