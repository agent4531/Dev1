import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Controller {

		@FXML
		private Button allBtn;
		@FXML
		private Button inBtn;
		@FXML
		private Button newBtn;
		@FXML
		private Button outBtn;
		@FXML
		private Button removeBtn;
		@FXML
		private Label authorTxt;
		@FXML
		private Label genreTxt;
		@FXML
		private Button newSBtn;
		@FXML
		private Label titleTxt;
		@FXML
		private TextField userTitle;
		Stage stage;
	@FXML
	void newSubmit(ActionEvent event) {


	}
	@FXML
	void allOnClick(ActionEvent event) throws IOException {
		stage = (Stage) allBtn.getScene().getWindow();
		FXMLLoader allLoader = new FXMLLoader(GUI.class.getResource("All_GUI.fxml"));
		Scene allScene = new Scene(allLoader.load());
		stage.setTitle("Show All Books");
		stage.setScene(allScene);
		stage.show();
	}

	@FXML
	void inOnClick(ActionEvent event) throws IOException {
		stage = (Stage) inBtn.getScene().getWindow();
		FXMLLoader inLoader = new FXMLLoader(GUI.class.getResource("In_GUI.fxml"));
		Scene inScene = new Scene(inLoader.load());
		stage.setTitle("Check In Book");
		stage.setScene(inScene);
		stage.show();

	}

	@FXML
	void newOnClick(ActionEvent event) throws IOException {
		stage = (Stage) newBtn.getScene().getWindow();
		FXMLLoader newLoader = new FXMLLoader(GUI.class.getResource("New_GUI.fxml"));
		Scene newScene = new Scene(newLoader.load());
		stage.setTitle("Make New Book");
		stage.setScene(newScene);
		stage.show();
	}

	@FXML
	void outOnClick(ActionEvent event) throws IOException {
		stage = (Stage) outBtn.getScene().getWindow();
		FXMLLoader outLoader = new FXMLLoader(GUI.class.getResource("Out_GUI.fxml"));
		Scene outScene = new Scene(outLoader.load());
		stage.setTitle("Check Out Book");
		stage.setScene(outScene);
		stage.show();

	}

	@FXML
	void rmOnClick(ActionEvent event) {

	}

}
