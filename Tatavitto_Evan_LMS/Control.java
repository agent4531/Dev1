import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Control {

		static List<Book> Library = new ArrayList<Book>();
		File infile;
		int barcode = 0;
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
		private Button LMSBackBtn;
		@FXML
		private Button showBtn;
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
		@FXML
		private TextField userAuthor;
		@FXML
		private ComboBox userGenre;
		@FXML
		private Label newBookConfirm;
		@FXML
		private Button fileCheckBtn;
		@FXML
		private TextField fileTxt;
		@FXML
		private TableView<Book> allTable; //= new TableView<>();
		@FXML
		private TableColumn<Book, String> tableAuthor;
		@FXML
		private TableColumn<Book, Integer> tableBarcode;
		@FXML
		private TableColumn<Book, String> tableDueDate;
		@FXML
		private TableColumn<Book, String> tableGenre;
		@FXML
		private TableColumn<Book, Boolean> tableStatus;
		@FXML
		private TableColumn<Book, String> tableTitle;
		Alert alert = new Alert(Alert.AlertType.NONE);
		Stage stage;


	@FXML
	void fileCheck(ActionEvent event) throws IOException {
		first:{
		try {
			infile = new File(GUI.class.getResource(fileTxt.getText()).getPath());
			Scanner fscan = new Scanner(infile);
			fscan.useDelimiter(",|\\r\\n");
			while (fscan.hasNext()) { // loops until full file is read
				String title ="";
				String author ="";
				String genre ="";
				for (int i=0;i<3;i++){ // only loops 3 times for Title, Author, Genre
					title =fscan.next();
					author =fscan.next();
					genre =fscan.next();
					Library.add(new Book(title,author,genre,Library));
				}
			}
			fscan.close();
		}catch (IOException | NullPointerException i){// validates file and if not lets the user know - this is only ran at the beginning so must be completed so loops until done
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText("Sorry \"" + fileTxt.getText() + "\" is not a valid File!");
			alert.show();
			break first;
		}catch (BadData e) {
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage() + " is not valid try again");
			alert.show();
			break first;
		}
			stage = (Stage) fileCheckBtn.getScene().getWindow();
			FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
			Scene LMSScene = new Scene(LMSLoader.load());
			stage.setTitle("LMS");
			stage.setScene(LMSScene);
			stage.show();
		}
	}
	@FXML
	void newSubmit(ActionEvent event) throws IOException {
		newBookConfirm.setText("New book is: " + userTitle.getText() + " made by: " + userAuthor.getText() + " which is in the " + userGenre.getValue() + " genre");
		first:{
		try {
			Library.add(new Book(userTitle.getText(),userAuthor.getText(),(String) userGenre.getValue(),Library));
		} catch (BadData e) {
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage() + " is not valid try again");
			alert.show();
			break first;

		}
		stage = (Stage) newSBtn.getScene().getWindow();
		FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
		Scene LMSScene = new Scene(LMSLoader.load());
		stage.setTitle("LMS");
		stage.setScene(LMSScene);
		stage.show();
		}

	}
	@FXML
	void allOnClick(ActionEvent event) throws IOException {
		stage = (Stage) allBtn.getScene().getWindow();
		FXMLLoader allLoader = new FXMLLoader(GUI.class.getResource("All_GUI.fxml"));
		Scene allScene = new Scene(allLoader.load());
		stage.setTitle("Show All Books");
		stage.setScene(allScene);
		stage.show();

//		alert.setAlertType(Alert.AlertType.ERROR);
//		alert.setContentText(Library.get(0).getBarcode() + ", " + Library.get(0).getTitle() + ", " + Library.get(0).getAuthor() + ", " + Library.get(0).getStatus() + ", " + Library.get(0).getDueDate() + ", " + Library.get(0).getGenre());
//		alert.show();



	}
	@FXML
	void loadBooks(ActionEvent event) {


		tableBarcode.setCellValueFactory(new PropertyValueFactory<Book,Integer>("barcode"));
		tableTitle.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		tableAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
		tableStatus.setCellValueFactory(new PropertyValueFactory<Book,Boolean>("status"));
		tableDueDate.setCellValueFactory(new PropertyValueFactory<Book,String>("dueDate"));
		tableGenre.setCellValueFactory(new PropertyValueFactory<Book,String>("genre"));

		allTable.getColumns().clear();
		allTable.getColumns().addAll(tableBarcode,tableTitle,tableAuthor,tableStatus,tableDueDate,tableGenre);

		ObservableList<Book> data = FXCollections.observableArrayList(Library);


		allTable.setItems(data);
		System.out.println(allTable.getItems().toString());

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
	void rmOnClick(ActionEvent event) throws IOException {
		stage = (Stage) removeBtn.getScene().getWindow();
		FXMLLoader rmLoader = new FXMLLoader(GUI.class.getResource("Rm_GUI.fxml"));
		Scene rmScene = new Scene(rmLoader.load());
		stage.setTitle("Remove Book");
		stage.setScene(rmScene);
		stage.show();
	}

}
