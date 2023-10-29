import java.util.List;
import java.util.regex.Pattern;

import static java.sql.Types.NULL;

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
		if (title.length() < 50 && !title.isBlank()) {
			Title = title;
		}
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {// sets the author and check that it is not blank and less the 50 char (planed limit for SQL) - if false add nothing - checked after all setters are ran
		if (author.length() < 50 && !author.isBlank()) {
			Author = author;
		}
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {// sets the genre and check that it is not blank and less the 11 char (planed limit for SQL) - if false add nothing - checked after all setters are ran
		if (genre.length() < 12 && !genre.isBlank()) {
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

	public int getBarcode() {
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
	private int Barcode =  NULL;

	public Book (String title, String author,String genre, List<Book> Library) throws BadData{ //initializer - receives all required data for a book in the Library then checks data for validity and if invalid will throw baddata exception which tells the user which data is bad
		setTitle(title);
		setAuthor(author);
		setGenre(genre);
		this.setBarcode(giveBarcode(Library));

		checkBook();
	}
	public int giveBarcode(List<Book> Library){//checks what the current highest barcode is and sets it one higher - as this is a List Array the highest element should be the last one, so removes the need to validate vs all books as they are sequential
		int lastR =0;
		if (Library.size() == 0){
			return 1;
		}else {
			lastR = Library.size() - 1;
			return (Library.get(lastR).getBarcode()) + 1;
		}
	}

	private void checkBook() throws BadData{//checks all data for valid info, and if invalid throws baddata exception to let the user know which field is bad - only throws one exception but could have more than one on the inputted data - does not matter as if one piece is invalid the whole book is invalid

		if(getTitle().isBlank()) {
			throw new BadData("Title");
		} else if (getAuthor().isBlank()) {
			throw new BadData("Author");
		} else if (getBarcode() < 1) {
			throw new BadData("Barcode");
		} else if (getGenre().isBlank()) {
			throw new BadData("Genre");
		}
	}

	@Override
	public String toString() {// shows the current status of the book in the Library in comma delimiter
		return 	Title + ", " + Author +	", " + Genre + ", " + DueDate +	", " + Status +	", " + Barcode;
	}
}
