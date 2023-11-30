import java.util.List;
import java.util.regex.Pattern;

import static java.sql.Types.NULL;

/**
 * Class linking all data points of a book for easy management
 */
public class Book {
	/*
	 *   Name:               Evan Tatavitto
	 *   Course:             Dev1
	 *   Date (updated):     9/26/2023
	 *   Class:              Book
	 *   For:                This class is the parts of the book including Title, Author, Barcode, Checked out (T/F), if checked out the due date, and the genre
	 *
	 */
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {// sets the title and check that it is not blank and less the 50 char (planed limit for SQL) - if false add nothing - checked after all setters are ran
		if (title.length() < 50 && title != "") {
			Title = title;
		}
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {// sets the author and check that it is not blank and less the 50 char (planed limit for SQL) - if false add nothing - checked after all setters are ran
		if (author.length() < 50 && author != "") {
			Author = author;
		}
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {// sets the genre and check that it is not blank and less the 11 char (planed limit for SQL) - if false add nothing - checked after all setters are ran
		if (genre.length() < 12 && genre != "") {
			Genre = genre;
		}
	}

	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {// sets the due date and must be year-month-day - if false throw error (not set at creation [besides null] then checks if what is requested to change is valid
		final Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
		if (pattern.matcher(dueDate).matches()|| dueDate.equals("NULL")) {
			DueDate = dueDate;
		}else {
			System.out.println("Thats Not valid!");
		}
	}

	public Boolean getStatus() {
		return Status;
	}
	public void setStatus(Boolean status) {// sets the status - can only be T|F
		Status = status;
	}

	public Integer getBarcode() {
		return Barcode;
	}

	public void setBarcode(int barcode) { // makes sure requested barcode is a non-zero number (or negative) - should be set by a method keeping track of the current highest barcode should not be possible to receive non-zero (or negative) number but checks nonetheless
		if (barcode>0) {
			Barcode = barcode;
		}
	}
//defaults to blank to error check after setters are called on creation
	private String Title="";
	private String Author="";
	private String Genre ="";
	private String DueDate = "NULL";
	private Boolean Status =false;
	private Integer Barcode =  NULL;

	/**
	 * used for cmd lms - depricated
	 * @param title title of book
	 * @param author author of book
	 * @param genre genre of book
	 * @param Library array of current books
	 * @throws BadData
	 */
	public Book (String title, String author, String genre, List<Book> Library) throws BadData{ //initializer - receives all required data for a book in the Library then checks data for validity and if invalid will throw baddata exception which tells the user which data is bad
		setTitle(title);
		setAuthor(author);
		setGenre(genre);


		checkBook();
	}

	/**
	 * used for sql and gui
	 * @param title title of book
	 * @param author author of book
	 * @param genre genre of book
	 * @param barcode barcode of book
	 * @param status status of book
	 * @param dueDate due date of book
	 * @throws BadData
	 */
	public Book (String title, String author, String genre, int barcode, Boolean status, String dueDate) throws BadData{ //initializer - receives all required data for a book in the Library then checks data for validity and if invalid will throw baddata exception which tells the user which data is bad
		setTitle(title);
		setAuthor(author);
		setGenre(genre);
		setBarcode(barcode);
		setStatus(status);
		setDueDate(dueDate);

		checkBook();
	}
	/*public int giveBarcode(List<Book> Library){ // removing to keep barcode from database
		int lastR =0;
		if (Library.size() == 0){
			return 1;
		}else {
			lastR = Library.size() - 1;
			return (Library.get(lastR).getBarcode()) + 1;
		}
	}*/

	/**
	 * used to validate a book in case of badData
	 * @throws BadData
	 */
	private void checkBook() throws BadData{//checks all data for valid info, and if invalid throws baddata exception to let the user know which field is bad - only throws one exception but could have more than one on the inputted data - does not matter as if one piece is invalid the whole book is invalid

		if(getTitle() == "") {
			throw new BadData("Title");
		} else if (getAuthor() == "") {
			throw new BadData("Author");
		} else if (getBarcode() < 1) {
			throw new BadData("Barcode");
		} else if (getGenre() == "") {
			throw new BadData("Genre");
		}
	}

	@Override
	public String toString() {// shows the current status of the book in the Library in comma delimiter
		return 	Barcode +	", " + Title + ", " + Author  +	", " + Status + ", " + DueDate + ", " + Genre;
	}
}
