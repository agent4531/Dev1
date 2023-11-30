import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Control {

	public Control(){

	};
	/*
	 *   Name:               Evan Tatavitto
	 *   Course:             Dev1
	 *   Date:               10/28/2023
	 *   Class:              Control
	 *   For:                This class is the new main code for the module 8 project requirements which includes > requesting database file from user (must be comma delimited) | Reading all books from the starting file | Adding new books (validate book on creation) | Removing old books | Showing current books | Check in and out books | and shows everything to the user via a GUI
	 *
	 */

		static List<Book> Library = new ArrayList<Book>();
		static List<Book> Barcodes = new ArrayList<Book>();
		BackEnd backEnd = new BackEnd();
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
		private Button newSBtn;
		@FXML
		private TextField userTitle;
		@FXML
		private TextField userAuthor;
		@FXML
		private ComboBox userGenre;
		@FXML
		private Button fileCheckBtn;
		@FXML
		private TextField fileTxt;
		@FXML
		private TableView<Book> allTable;
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
		@FXML
		private TextField barcodeTxt;
		@FXML
		private Button titleSBtn;
		@FXML
		private TableView<Book> titleToBarcodeTable;
		@FXML
		private Label titleToBarcodeTxt;
		@FXML
		private TextField titleTxt;
		Alert alert = new Alert(Alert.AlertType.NONE); //used to show issues from input
		Stage stage;


	/*@FXML  -- disabled as file is now sql database - IE class BackEnd
	void fileCheck(ActionEvent event) throws IOException { // requests and reads a file
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
		}catch (BadData e) {// prevents bad data from being added into the database - ie wrong file/none csv file
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText(e.getMessage() + " is not valid try again");
			alert.show();
			break first;
		}// if everything is good (file and data in the file) move on to the main program
			stage = (Stage) fileCheckBtn.getScene().getWindow();
			FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
			Scene LMSScene = new Scene(LMSLoader.load());
			stage.setTitle("LMS");
			stage.setScene(LMSScene);
			stage.show();
		}
	}
	*/

	/**
	 * method for making a new book and sending it to the database
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void newSubmit(ActionEvent event) throws IOException {//validates new books then sends to main
		first:{
			Book newBook;
			try {// making new book from text from new book page
				newBook = new Book(userTitle.getText(),userAuthor.getText(),(String) userGenre.getValue(),1,false,"NULL");
			} catch (BadData e) {// throws alert if there is an issue with pulled data
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText(e.getMessage() + " is not valid try again");
				alert.show();
				break first;

			}// once done places user back in main
			int statustemp = newBook.getStatus() ? 1 : 0;
			String query = "INSERT INTO books (Title, Author, Genre, Status) VALUES ('" + newBook.getTitle() + "','" + newBook.getAuthor() + "', '" + newBook.getGenre() + "', '" + statustemp + "');";
			try {
				backEnd.updateBook(query);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			stage = (Stage) newSBtn.getScene().getWindow();
			FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
			Scene LMSScene = new Scene(LMSLoader.load());
			stage.setTitle("LMS");
			stage.setScene(LMSScene);
			stage.show();


		}

	}

	/**
	 * used load the scene for showing all the books
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void allOnClick(ActionEvent event) throws IOException {// switches to all books interface
		stage = (Stage) allBtn.getScene().getWindow();
		FXMLLoader allLoader = new FXMLLoader(GUI.class.getResource("All_GUI.fxml"));
		Scene allScene = new Scene(allLoader.load());
		stage.setTitle("Show All Books");
		stage.setScene(allScene);
		stage.show();

	}

	/**
	 * used to load all of the books in the database
	 * @param event
	 */
	@FXML
	void loadBooks(ActionEvent event) {
		// loads all books in the library

		Library.clear();
		try {
			Library = backEnd.readDatabase();
		}catch (BadData e) {// prevents bad data from being added into the database - ie wrong file/none csv file
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("Database had an error pulling data - look in \"loadBooks\" for posable need for edit ");
				alert.show();
		}
		//links columns with ArrayList data
		tableBarcode.setCellValueFactory(new PropertyValueFactory<Book,Integer>("barcode"));
		tableTitle.setCellValueFactory(new PropertyValueFactory<Book,String>("title"));
		tableAuthor.setCellValueFactory(new PropertyValueFactory<Book,String>("author"));
		tableStatus.setCellValueFactory(new PropertyValueFactory<Book,Boolean>("status"));
		tableDueDate.setCellValueFactory(new PropertyValueFactory<Book,String>("dueDate"));
		tableGenre.setCellValueFactory(new PropertyValueFactory<Book,String>("genre"));

		allTable.getColumns().clear();// clears any excess columns
		allTable.getColumns().addAll(tableBarcode,tableTitle,tableAuthor,tableStatus,tableDueDate,tableGenre); // sets columns in order of columns

		ObservableList<Book> data = FXCollections.observableArrayList(Library); // uses ObservableList to link library to compatible Table

		allTable.setItems(data); // addes all items in the library (through ObservableList)
	}

	/**
	 * use to go back to the main screen from any scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void backOnClick(ActionEvent event) throws IOException { // loads main when clicking back button on all sub scenes screens
		stage = (Stage) LMSBackBtn.getScene().getWindow();
		FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
		Scene LMSScene = new Scene(LMSLoader.load());
		stage.setTitle("LMS");
		stage.setScene(LMSScene);
		stage.show();
	}

	/**
	 * loads check in book scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void inOnClick(ActionEvent event) throws IOException { // loads check in page
		stage = (Stage) inBtn.getScene().getWindow();
		FXMLLoader inLoader = new FXMLLoader(GUI.class.getResource("In_GUI.fxml"));
		Scene inScene = new Scene(inLoader.load());
		stage.setTitle("Check In Book");
		stage.setScene(inScene);
		stage.show();
	}

	/**
	 * loads new book scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void newOnClick(ActionEvent event) throws IOException { // loads new book page
		stage = (Stage) newBtn.getScene().getWindow();
		FXMLLoader newLoader = new FXMLLoader(GUI.class.getResource("New_GUI.fxml"));
		Scene newScene = new Scene(newLoader.load());
		stage.setTitle("Make New Book");
		stage.setScene(newScene);
		stage.show();
	}

	/**
	 * loads checked out scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void outOnClick(ActionEvent event) throws IOException { // loads check out page
		stage = (Stage) outBtn.getScene().getWindow();
		FXMLLoader outLoader = new FXMLLoader(GUI.class.getResource("Out_GUI.fxml"));
		Scene outScene = new Scene(outLoader.load());
		stage.setTitle("Check Out Book");
		stage.setScene(outScene);
		stage.show();

	}

	/**
	 * loads remove book scene
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void rmOnClick(ActionEvent event) throws IOException { // loads remove book page
		stage = (Stage) removeBtn.getScene().getWindow();
		FXMLLoader rmLoader = new FXMLLoader(GUI.class.getResource("Rm_GUI.fxml"));
		Scene rmScene = new Scene(rmLoader.load());
		stage.setTitle("Remove Book");
		stage.setScene(rmScene);
		stage.show();
	}

	/**
	 * removes selected button
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void rmBarcodeBtn(ActionEvent event) throws IOException { // method to validate title or barcode and remove a book from the library
		trybarcode:// if the barcode is invalid prevents loading of main
		{

			try {
				barcode = Integer.parseInt(barcodeTxt.getText());
			} catch (NumberFormatException x) {// checks that user requested a number
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("That's not a barcodes, Barcodes are numbers, try again!");
				alert.show();
				break trybarcode;
			}
			if (barcode > 0) {
				first:
				{
// used to break try case
					try{
						// removes book at found barcode - barcode is unique so only need to find one book
						String query = "DELETE FROM books WHERE Barcode=" + barcodeTxt.getText();
						backEnd.updateBook(query);

						// sends to main after removing book
						stage = (Stage) barcodeTxt.getScene().getWindow();
						FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
						Scene LMSScene = new Scene(LMSLoader.load());
						stage.setTitle("LMS");
						stage.setScene(LMSScene);
						stage.show();

						break first;
					}catch (SQLException e) {// alerts user of barcode not in the library
						alert.setAlertType(Alert.AlertType.ERROR);
						alert.setContentText("Sorry couldn't find this barcode to remove try again");
						alert.show();
					}
				}
			}else{ // alerts user of numbers below zero
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("Barcodes are bigger than zero, try again!");
				alert.show();
			}
		}
	}

	/**
	 * used to check and load barcodes of given titles
	 * @param event
	 */
	@FXML
	void titleBtn(ActionEvent event) { // shows all barcodes linked with a title - must be exact - must use barcode or back button to go back to main
		Barcodes.clear();
		Barcodes = backEnd.listBarcodes("'" + titleTxt.getText() + "'");
		if (Barcodes.size() == 0) {// if nothing is found with this title - let user know
			alert.setAlertType(Alert.AlertType.ERROR);
			alert.setContentText("No Titles found with that name, try again!");
			alert.show();
		}else{// lists all barcodes with title
			tableBarcode.setCellValueFactory(new PropertyValueFactory<Book,Integer>("barcode"));
			titleToBarcodeTable.getColumns().clear();
			titleToBarcodeTable.getColumns().addAll(tableBarcode);
			ObservableList<Book> data = FXCollections.observableArrayList(Barcodes);
			titleToBarcodeTable.setItems(data);
			titleToBarcodeTable.setVisible(true);
			titleToBarcodeTxt.setVisible(true);
		}
	}

	/**
	 * used to check a book in
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void inBarcodeBtn(ActionEvent event) throws IOException { // used to update books to checked in
		Book userBook;
		trybarcode: // if barcode in not a number - prevents invalid loading of main
		{
			try {
				barcode = Integer.parseInt(barcodeTxt.getText());
			} catch (NumberFormatException x) {// checks that user requested a number
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("That's not a barcodes, Barcodes are numbers, try again!");
				alert.show();
				break trybarcode;
			}
			if (barcode > 0) {
				first:
				{
// used to break try case
					try {
						userBook = backEnd.bookStatus(barcode);
						if (!userBook.getStatus()) {// if checked in already - lets the user know - no action taken
							alert.setAlertType(Alert.AlertType.ERROR);
							alert.setContentText("Sorry that's been checked in please confirm if you wanted to check this out!");
							alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
							alert.show();
							break first;
						}
					}catch (SQLException e) {// prevents bad data from being added into the database - ie wrong file/none csv file
						alert.setAlertType(Alert.AlertType.ERROR);
						alert.setContentText("Barcode not found");
						alert.show();
						break first;
					}catch (BadData e) {// prevents bad data from being added into the database - ie wrong file/none csv file
						alert.setAlertType(Alert.AlertType.ERROR);
						alert.setContentText("issue pulling data from the database - look at 'inBarcodeBtn'");
						alert.show();
						break first;
					}

					try {
						String query = "UPDATE books SET Status=0, DueDate=NULL WHERE Barcode=" + barcode;
						backEnd.updateBook(query);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					// sends to main after Check In
						stage = (Stage) barcodeTxt.getScene().getWindow();
						FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
						Scene LMSScene = new Scene(LMSLoader.load());
						stage.setTitle("LMS");
						stage.setScene(LMSScene);
						stage.show();
					break trybarcode;
				}

			}else{// lets user know barcode needs to be bigger then zero
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("Barcodes are bigger than zero, try again!");
				alert.show();
			}
		}
	}

	/**
	 * used to check out a book
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void outBarcodeBtn(ActionEvent event) throws IOException {
		Book userBook;
		trybarcode: // if barcode in not a number - prevents invalid loading of main
		{
			try {
				barcode = Integer.parseInt(barcodeTxt.getText());
			} catch (NumberFormatException x) {// checks that user requested a number
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("That's not a barcodes, Barcodes are numbers, try again!");
				alert.show();
				break trybarcode;
			}
			if (barcode > 0) {
				first:
				{
// used to break try case
					try {
						userBook = backEnd.bookStatus(barcode);
						if (userBook.getStatus()) {// if checked in already - lets the user know - no action taken
							alert.setAlertType(Alert.AlertType.ERROR);
							alert.setContentText("Sorry that's been checked out please confirm if you wanted to check this in!");
							alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
							alert.show();
							break first;
						}
					}catch (SQLException e) {// prevents bad data from being added into the database - ie wrong file/none csv file
						alert.setAlertType(Alert.AlertType.ERROR);
						alert.setContentText("Barcode not found");
						alert.show();
						break first;
					}catch (BadData e) {// prevents bad data from being added into the database - ie wrong file/none csv file
						alert.setAlertType(Alert.AlertType.ERROR);
						alert.setContentText("issue pulling data from the database - look at 'outBarcodeBtn'");
						alert.show();
						break first;
					}

					try {
						LocalDate dueDate = LocalDate.now();
						dueDate = dueDate.plus(4, ChronoUnit.WEEKS);
						Date date = Date.valueOf(dueDate);
						String query = "UPDATE books SET Status=1, DueDate='" + date + "' WHERE Barcode=" + barcode;
						backEnd.updateBook(query);
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					// sends to main after Check In
					stage = (Stage) barcodeTxt.getScene().getWindow();
					FXMLLoader LMSLoader = new FXMLLoader(GUI.class.getResource("LMS_GUI.fxml"));
					Scene LMSScene = new Scene(LMSLoader.load());
					stage.setTitle("LMS");
					stage.setScene(LMSScene);
					stage.show();
					break trybarcode;
				}

			}else{// lets user know barcode needs to be bigger then zero
				alert.setAlertType(Alert.AlertType.ERROR);
				alert.setContentText("Barcodes are bigger than zero, try again!");
				alert.show();
			}
		}
	}
}
