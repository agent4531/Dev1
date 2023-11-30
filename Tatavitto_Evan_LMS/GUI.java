import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * runs application
 */
public class GUI extends Application {

	/*
	 *   Name:               Evan Tatavitto
	 *   Course:             Dev1
	 *   Date:               10/28/2023
	 *   Class:              GUI
	 *   For:                This class makes the new window for the GUI
	 *
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * loads main screen
	 * @param stage
	 * @throws IOException
	 */
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
		Scene LMSScene = new Scene(LMSLoader.load());
		stage.setTitle("LMS");
		stage.setScene(LMSScene);
		stage.getIcons().add(new Image(GUI.class.getResourceAsStream("icon.jpg")));
		stage.show();
	}
}
