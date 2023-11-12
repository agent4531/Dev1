import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;


public class BackEnd {

	private String user = "root";
	private String password;
	private String url = "jdbc:mysql://localhost:3306/lms";
	static Statement statement;

	public BackEnd(){
		try {
			File infile = new File(Control.class.getResource("sqlp.txt").getPath());
			Scanner fscan = new Scanner(infile);
			password = fscan.next();

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, password);
			statement = con.createStatement();

		}catch (FileNotFoundException e) {
			System.out.println("password file not found try again");
		} catch (SQLException e) {
			System.out.println("something is wrong with SQL - maybe database url, username or password");
		} catch (ClassNotFoundException e) {
			System.out.println("SQL Driver not found .... somehow :)");
		}
	}

	public void updateBook(String query) throws SQLException {
			//ResultSet result =
			statement.executeUpdate(query);
	}

	public Book bookStatus(int barcode) throws SQLException, BadData {
		String query = "SELECT Title, Author, Genre, Barcode, Status, DueDate FROM books WHERE barcode=" + barcode;
		ResultSet result = statement.executeQuery(query);
		result.next();
		Boolean isCheckedOut =(result.getInt(5) != 0);
		String dateDue;
		result.getString(6);
		if(result.wasNull()){
			dateDue = "NULL";
		}else {
			dateDue = result.getString(6);
		}

		Book usersBook = new Book(result.getString(1),result.getString(2),result.getString(3),result.getInt(4),isCheckedOut,dateDue);
		return usersBook;

	}

	public ArrayList<Book>  readDatabase() throws BadData {

		ArrayList<Book> Library = new ArrayList<Book>();
		String query = "SELECT Title, Author, Genre, Barcode, Status, DueDate FROM books";


		try {
			ResultSet result = statement.executeQuery(query);

			int i = 0;
			while (result.next()) {
				Boolean isCheckedOut =(result.getInt(5) != 0);
				String dateDue;
				result.getString(6);
				if(result.wasNull()){
					dateDue = "NULL";
				}else {
					dateDue = result.getString(6);
				}

				Library.add(new Book(result.getString(1),result.getString(2),result.getString(3),result.getInt(4),isCheckedOut,dateDue));
				i++;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return Library;
	}

	public ArrayList<Book>  listBarcodes(String userTitle)  {

		ArrayList<Book> Barcodes = new ArrayList<Book>();
		String query = "SELECT Title, Author, Genre,Barcode FROM books WHERE Title=" + userTitle;


		try {
			ResultSet result = statement.executeQuery(query);

			int i = 0;
			while (result.next()) {
				Barcodes.add(new Book(result.getString(1),result.getString(2),result.getString(3), result.getInt(4)));
				i++;
			}
		} catch (SQLException e) {
			System.out.println("issue with SQL from 'listBarcode'");
		}
		catch (BadData e) {
			System.out.println("issue with/reading database from 'listBarcode'");
		}
		return Barcodes;
	}

}
