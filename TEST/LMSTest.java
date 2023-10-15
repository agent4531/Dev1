import org.junit.jupiter.api.DisplayName;

import java.io.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LMSTest {
	//ArrayList<Book> TestLibrary = new ArrayList<Book>();

	ByteArrayOutputStream output_string = new ByteArrayOutputStream();
	PrintStream testOutput = new PrintStream(output_string);
	PrintStream old = System.out;
	String[] output_array = null;
	void outStreamB(){ // clears/makes new out stream for checking that the program tells the user
		output_string = new ByteArrayOutputStream();
		testOutput = new PrintStream(output_string);
		System.setOut(testOutput);
	}
	void outStreamA(){// splits output to an array to see what is said when
		System.out.flush();
		System.setOut(old);
		output_array = output_string.toString().split("\\r\\n", 0);

	}
	void changeIn(String infile) throws Exception {//changes input file for each test
		File path = new File(LMSTest.class.getResource("/" + infile).getPath());
		LMS.reader = new BufferedReader(new FileReader(path));
	}

	@org.junit.jupiter.api.BeforeEach
	void setUp() {

		try {
			LMS.Library.add(new Book("New World Order", "Evan Tatavitto", "Fiction", LMS.Library));
			LMS.Library.add(new Book("Hello World", "John Doe", "History", LMS.Library));
			LMS.Library.add(new Book("Working Hard", "McKennly", "Crime", LMS.Library));
		}catch (BadData e){
			System.out.println(e.getMessage() + " is not valid try again");
		}
	}
	@org.junit.jupiter.api.AfterEach
	void BreakDown() {
		LMS.Library.clear();
	}

	@org.junit.jupiter.api.Test
	@DisplayName("Make New Book Test")
	void newBook() throws Exception {
		changeIn("newBookTest.txt");

		String toStringTest = "New World, John Doe, History, NULL, false, 4";
		try{
			LMS.newBook();
		}catch (BadData e) {
			System.out.println(e.getMessage() + " is not valid try again");
		}

		assertEquals(toStringTest,LMS.Library.get(3).toString(),"Failed to make valid new book");

		try {
			LMS.newBook();// testing Missing vars
			fail( "newBook didn't throw BadData" );
		} catch (BadData expectedException) {
		}
		try {
			LMS.newBook();// testing testing to big data in vars
			fail( "newBook didn't throw BadData" );
		} catch (BadData expectedException) {
		}
		LMS.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@org.junit.jupiter.api.Test
	@DisplayName("Check Out Book Test")
	void checkOut() throws Exception {
		changeIn("checkOutTest.txt");

		outStreamB();
		LMS.checkOut(LMS.bvt());//test barcode with barcode 1 - should check out book successfully
		outStreamA();

		//checks if the book (with barcode 1 - index 0) has been checked out
		assertTrue(LMS.Library.get(0).getStatus(),"Program should have updated book 0 (barcode 1) to checked out");
		assertNotEquals(LMS.Library.get(0).getDueDate(),"NULL","Program should have updated book 0 (barcode 1) to a date"); // can only be a date or NULL - checks for none NULL value

		outStreamB();
		LMS.checkOut(LMS.bvt());//test barcode with barcode 1 - should not check out book - was already checked out and should say so
		outStreamA();

		//checks if the book (with barcode 1 - index 0) has already been checked out
		assertEquals(output_array[2].toString(),"Sorry Thats been checked out please confirm if you wanted to check this in!","Book 0 (barcode 1) should have already been checked out - program says it isn't");

		LMS.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@org.junit.jupiter.api.Test
	@DisplayName("Check In Book Test")
	void checkIn() throws Exception {
		changeIn("checkInTest.txt");

		outStreamB();
		LMS.checkOut(LMS.bvt());// checks out barcode 1 to be able to check in next successfully
		//checks to make sure the book get checked out so that when we check status of the book and it shows false we know it was true and now false
		assertTrue(LMS.Library.get(0).getStatus(),"Program should have updated book 0 (barcode 1) to checked out");
		LMS.checkIn(LMS.bvt());//test barcode with barcode 1 - should check in book successfully
		assertFalse(LMS.Library.get(0).getStatus(),"Program should have updated book 0 (barcode 1) to checked in");
		assertEquals(LMS.Library.get(0).getDueDate(),"NULL","Program should have updated book 0 (barcode 1) to NULL"); // can only be a date or NULL - checks for NULL value
		outStreamA();

		outStreamB();
		LMS.checkIn(LMS.bvt());//test barcode with barcode 1 - should already be check in - should tell user not possible
		outStreamA();
		assertEquals(output_array[2].toString(),"Sorry Thats been checked in please confirm if you wanted to check this out!","Book 0 (barcode 1) should have already been checked in - program says it isn't");

		LMS.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@org.junit.jupiter.api.Test
	@DisplayName("Remove Old Book Test")
	void rmBook() throws Exception {

		changeIn("rmBookTest.txt");
		LMS.rmBook(LMS.bvt()); //removes book 0 (barcode 1)
		for (int i = 0; i < LMS.Library.size(); i++) {
			if (1 == LMS.Library.get(i).getBarcode()) {
				fail( "book was not removed" ); // tests if book is still in the database - and if so throws fail test
			}
		}
		outStreamB();
		LMS.rmBook(LMS.bvt());// tries to remove book 0 (again) (barcode 1) - uses output from method to confirm it did not remove another book and identified the book was not in the database
		outStreamA();
		assertEquals(output_array[2].toString(),"Sorry Couldn't find this barcode to remove try again","Book 0 (barcode 1) Was already removed but the program identified a book with this barcode!");

		LMS.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@org.junit.jupiter.api.Test
	@DisplayName("Barcode Vs Title Test")
	void bvt() throws Exception {// asks for barcodes and sends number - if the user has a title finds any barcodes linked to this title - does't validate barcode only but needs a valid title to continue with title selection
		changeIn("bvtTest.txt");

		assertEquals(LMS.bvt(),1,"Barcode returned was not 1 - should be 2");// check for ability to read and give valid user given barcode

		int testbarcode;
		outStreamB();
		testbarcode =LMS.bvt();
		outStreamA();
		assertEquals(output_array[2].toString(),"That's not a barcodes, try again!","Identified a barcode from a letter - some how got past int filter");// check if user gives a letter for a barcode selection

		assertEquals(LMS.bvt(),2,"Barcode returned was not 2 - should be 2");// check for ability to read and give valid barcode for user given title

		outStreamB();
		testbarcode =LMS.bvt();
		outStreamA();
		assertEquals(output_array[2].toString(),"Nothing fits that sorry try again!","Identified a an invalid title - some how got past title search");// check for invalid title

		outStreamB();
		testbarcode =LMS.bvt();
		outStreamA();
		assertEquals(output_array[5].toString(),"That's not on the list, try again!","Identified a barcode from a letter - some how got past int filter");// check for invalid barcode for valid title

		LMS.reader = new BufferedReader(new InputStreamReader(System.in));
	}

}
