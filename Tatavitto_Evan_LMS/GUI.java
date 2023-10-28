import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
		//FXMLLoader newLoader = new FXMLLoader(GUI.class.getResource("New_GUI.fxml"));
		//FXMLLoader rmLoader = new FXMLLoader(GUI.class.getResource("Rm_GUI.fxml"));
		//FXMLLoader outLoader = new FXMLLoader(GUI.class.getResource("Out_GUI.fxml"));
		//FXMLLoader inLoader = new FXMLLoader(GUI.class.getResource("In_GUI.fxml"));
		//FXMLLoader allLoader = new FXMLLoader(GUI.class.getResource("All_GUI.fxml"));
		Scene LMSScene = new Scene(LMSLoader.load());
		//Scene newScene = new Scene(newLoader.load());
		//Scene rmScene = new Scene(rmLoader.load());
		//Scene outScene = new Scene(outLoader.load());
		//Scene inScene = new Scene(inLoader.load());
		//Scene allScene = new Scene(allLoader.load());
		stage.setTitle("LMS");
		stage.setScene(LMSScene);
		stage.show();
	}
}
