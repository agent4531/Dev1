import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

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

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("File_GUI.fxml"));
		Scene LMSScene = new Scene(LMSLoader.load());
		stage.setTitle("File Selector");
		stage.setScene(LMSScene);
		stage.getIcons().add(new Image(GUI.class.getResourceAsStream("icon.jpg")));
		stage.show();
	}
}
