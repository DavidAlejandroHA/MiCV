package dad.micv;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MiCVApp extends Application {

	public static Stage primaryStage;

	private MainController mainController = new MainController();

	@Override
	public void start(Stage primaryStage) throws Exception {

		MiCVApp.primaryStage = primaryStage;
		primaryStage.setTitle("Constructor de currículums");
		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.getIcons().add(new Image(MiCVApp.class.getResourceAsStream("/images/nuevo.gif")));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
