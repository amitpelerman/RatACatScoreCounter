package app1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MVC extends Application implements styleLabels, Finals {
	private ImageView mainBackground = new ImageView(new Image(getClass().getResourceAsStream(mainBg)));
	private Button startBtMVC = new Button("Start new game");

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane stackPane = new Pane();
		startBtMVC.setLayoutY(5);
		startBtMVC.setLayoutX((widthSize / 2) - 100);
		startBtMVC.setStyle(buttonsStyle);
		mainBackground.setFitWidth(widthSize);
		stackPane.getChildren().addAll(mainBackground, startBtMVC);
		startBtMVC.setAlignment(Pos.CENTER);
		primaryStage.setOnCloseRequest(e -> Platform.exit()); // set to close all stages
		primaryStage.setScene(new Scene(stackPane, widthSize, heightSize));
		/** model view controller ---> start */
		startBtMVC.setOnAction(e -> {
			Model model = new Model();
			model.setGame(new Game());
			View view = new View();
			Controller controller = new Controller(model, view);
			model.setController(controller);
			view.setController(controller);
			controller.updateView();
		});
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		MVC.launch((String[]) args);
	}
}