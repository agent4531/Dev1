import java.util.List;
import java.util.regex.Pattern;

import static java.sql.Types.NULL;

public class Book {
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		if (title.length() < 50 && !title.isBlank()) {
			Title = title;
		}
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		if (author.length() < 50 && !author.isBlank()) {
			Author = author;
		}
	}

	public String getGenre() {
		return Genre;
	}

	public void setGenre(String genre) {
		if (genre.length() < 11 && !genre.isBlank()) {
			Genre = genre;
		}
	}

	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {
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

	public void setStatus(Boolean status) {
		Status = status;
	}

	public int getBarcode() {
		return Barcode;
	}

	public void setBarcode(int barcode) {
		if (barcode>0) {
			Barcode = barcode;
		}
	}

	private String Title="";
	private String Author="";
	private String Genre ="";
	private String DueDate = "NULL";
	private Boolean Status =false;
	private int Barcode =  NULL;

	public Book (String title, String author,String genre, List<Book> Library) throws BadData{
		setTitle(title);
		setAuthor(author);
		setGenre(genre);
		this.setBarcode(giveBarcode(Library));

		checkBook();
	}
	public int giveBarcode(List<Book> Library){
		int lastR =0;
		if (Library.size() == 0){
			return 1;
		}else {
			lastR = Library.size() - 1;
			return (Library.get(lastR).getBarcode()) + 1;
		}
	}

	private void checkBook() throws BadData{

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
	public String toString() {
		return 	Title + ", " + Author +	", " + Genre + ", " + DueDate +	", " + Status +	", " + Barcode;
	}
}
