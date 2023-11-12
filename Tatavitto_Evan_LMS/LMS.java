import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class LMS {
/*
*   Name:               Evan Tatavitto
*   Course:             Dev1
*   Date (updated):     9/26/2023
*   Class:              LMS
*   For:                This class is the main code for the module 6 project requirements which includes > requesting database file from user (must be comma delimited) | Reading all books from the starting file | Adding new books (validate book on creation) | Removing old books | Showing current books | Check in and out books |
*
*/

    static List<Book> Library = new ArrayList<Book>(); // list of all books
    static List<Book> Barcodes = new ArrayList<Book>();//list of books (barcodes) of a specified title
    static Iterator booksWithName = Barcodes.iterator();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String dbfile ="";
    static File infile = new File("");

    public static void main(String[] args) {//Main that runs the different selections and loops until the user is done

        try {
            newFile();
            slcOption();
        }
        catch (BadData e){// received from newBook passed through methods
            System.out.println(e.getMessage() + " is not valid try again");
        }catch (Exception e) {// in case of issue with newFile
            e.printStackTrace();
        }
    }
    public static int bvt() throws Exception{//Barcode Vs Title - asks if they have a barcode or a title - if title continues to findBarcode which will return a barcode of the selected book
        int barcode = 0;
        String uRequest ="";

        boolean type = false; // incase they want to quit from adding a barcode or title
        while(!type) {
            System.out.println("Are you using a Barcode (B) or a Title (T) (Q to Quit)?");
            uRequest = reader.readLine();
            switch (uRequest) {

                case ("B"):
                case ("b"): {// for barcodes
                    type = true;
                    System.out.println("So you have a Barcode - what is it?");
                    uRequest = reader.readLine();
                    try {
                        barcode = Integer.parseInt(uRequest);
                    }catch (NumberFormatException x) {// checks that user requested a number
                        System.out.println("That's not a barcodes, try again!");
                        bvt();
                    }
                    return barcode;
                }
                case ("T"):
                case ("t"): { // for title
                    type =true;
                    System.out.println("So you have a Title - what is it?");
                    uRequest = reader.readLine();
                    barcode = findBarcode(uRequest); // finds the requested barcodes liked to this title, displays them and then receives the requested barcode and passes it along - title must be exact case
                    return barcode;
                }
                case ("Q"):
                case ("q"): {
                    type =true;
                    System.out.println("So you want to try something else!");
                    break;
                }
                default: {
                    System.out.println("Sorry That's Not A Valid Selection! Try Again!");
                    Thread.sleep(2000);
                    break;
                }
            }
        }
        return -1;
    }
    public static void readFile() throws Exception { //reads the beginning file and puts everything in a List of Books
        System.out.println("Reading File!");
        Scanner fscan = new Scanner(infile);
        fscan.useDelimiter(",|\\r\\n"); // allows to pull each section of the file correctly (file is CSV and endline is new book)

        while (fscan.hasNext()) { // loops until full file is read
            String title ="";
            String author ="";
            String genre ="";
            for (int i=0;i<3;i++){ // only loops 3 times for Title, Author, Genre
                title =fscan.next();
                author =fscan.next();
                genre =fscan.next();
                //Library.add(new Book(title,author,genre,Library));
            }
        }
        fscan.close();
    }
    public static void newFile() throws Exception{//requests database file and then runs readFile to make List
        System.out.println("What is the database file name?");
        try {
            dbfile = reader.readLine();
            infile = new File(LMS.class.getResource(dbfile).getPath());
            readFile();
        }catch (IOException | NullPointerException i){// validates file and if not lets the user know - this is only ran at the beginning so must be completed so loops until done
            System.out.println("Sorry \"" + dbfile + "\" is not a valid File!");
            System.out.println("Try Again!");
            newFile();
        }
    }
    public static void newBook() throws Exception{//Ask user for Title, Author, and Genre receives new barcode from Book constructor > adds this as a new book entry to the list

        System.out.println("What is the Title?");
        String title = reader.readLine();

        System.out.println("What is the Author?");
        String author = reader.readLine();

        System.out.println("What is the Genre?");
        String genre = reader.readLine();

        //Library.add(new Book(title,author,genre,Library));

        System.out.println("Making New Book!");
        Thread.sleep(3000);
    }
    public static void checkOut(int barcode) {// checks out book - receives barcode to check out - note that if the user selects title, it will pull the barcode from the listed titles
        if(barcode > 0){
            try {
                int s = 0;
                for (int i = 0; i < Library.size(); i++) {// looks for barcode in List
                    if (barcode == Library.get(i).getBarcode()) {// once found does...
                        if (Library.get(i).getStatus()) {// if checked out already - lets the user know - no action taken
                            System.out.println("Sorry Thats been checked out please confirm if you wanted to check this in!");
                        } else {// book is not checked out - checks the book out - also sets due date
                            System.out.println("Checking Out Book!");
                            Library.get(i).setStatus(true);
                            LocalDate dueDate = LocalDate.now();
                            dueDate = dueDate.plus(4, ChronoUnit.WEEKS);
                            Library.get(i).setDueDate(String.valueOf(dueDate));
                            System.out.println(Library.get(i).toString());
                            Thread.sleep(3000);
                        }
                    }
                }
            } catch (InterruptedException e) {

            }
        }

    }
    public static void checkIn(int barcode) {// checks in book - receives barcode to check in - note that if the user selects title, it will pull the barcode from the listed titles
        if(barcode > 0) {
            try {
                for (int i = 0; i < Library.size(); i++) {// looks for barcode in List
                    if (barcode == Library.get(i).getBarcode()) {// once found does...
                        if (!Library.get(i).getStatus()) {// if checked in already - lets the user know - no action taken
                            System.out.println("Sorry Thats been checked in please confirm if you wanted to check this out!");
                        } else {// book is not checked in - checks the book in - also removes due date
                            System.out.println("Checking In Book!");
                            Library.get(i).setStatus(false);
                            Library.get(i).setDueDate("NULL");
                            System.out.println(Library.get(i).toString());
                            Thread.sleep(3000);
                        }
                    }
                }
            } catch (InterruptedException e) {

            }
        }
    }
    public static int findBarcode(String title) throws InterruptedException{// used to find and list all books with a set title
        Barcodes.clear(); // incase title was used before - removes everything
        for (int i = 0; i < Library.size(); i++){
            if (Library.get(i).getTitle().equals(title)){// finds and adds all books with selected title
                Barcodes.add(Library.get(i));
            }
        }
        if (Barcodes.size() == 0) {// if nothing is found with this title - let user know
            System.out.println("Nothing fits that sorry try again!");
            Thread.sleep(2000);
        }else{// lists all barcodes with title
            System.out.println("Barcodes with this Title >");
            for (int i = 0; i < Barcodes.size(); i++) {
                System.out.println(Barcodes.get(i).getBarcode());
            }
            Thread.sleep(500);
            return slcBarcode(Barcodes,title);// goes to slcBarcode which uses the above listed barcodes and reads which barcode is being requested
        }
        return -1;
    }
    public static int slcBarcode(List<Book> barcodes, String title) throws InterruptedException{// requests which barcode from findBarcode user is looking to change, returns barcode
        String uBarcode = "";
        System.out.println("Now please select the barcode you need");
        try {
            uBarcode = reader.readLine();
            int s = 0;
            for (int i = 0; i < Barcodes.size(); i++) {
                if (Barcodes.get(i).getBarcode() == Integer.parseInt(uBarcode)) {// finds and sends barcode from user request
                    return Barcodes.get(i).getBarcode();
                } else {
                    s++;
                }
            }
            if(Barcodes.size() == s){// confirming if barcodes list finishes and didn't find a barcode to let the user know and try again
                System.out.println("That's not on the list, try again!");
                Thread.sleep(2000);
                return slcBarcode(barcodes,title);
            }
        }catch (NumberFormatException x){
            System.out.println("That's not a barcodes, try again!");
            Thread.sleep(2000);
            return findBarcode(title);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    public static void rmBook(int barcode) throws Exception{// removes book at barcode requested
        if(barcode > 0) {
            first:// used to break try case
            try {
                for (int i = 0; i < Library.size(); i++) {
                    if (barcode == Library.get(i).getBarcode()) {// removes book at found barcode - barcode is unique so only need to find one book
                        System.out.println("Removing Book!");
                        Library.remove(i);
                        Thread.sleep(3000);
                        break first;
                    }
                }
                System.out.println("Sorry Couldn't find this barcode to remove try again");
                Thread.sleep(3000);
            } catch (InterruptedException e) {

            }
        }
    }
    public static void showBook() throws Exception{//lists all books in books List correctly formatted
        for (int i = 0; i < Library.size(); i++){
            System.out.println(Library.get(i).toString());
        }
        Thread.sleep(3000);
    }
    public static void showOption(){//each time a selection finishes it loops back main - reduced cluttered of main
        System.out.println("\nPlease select what to do!");
        System.out.println("Make a New book (N)");
        System.out.println("Remove a book (R)");
        System.out.println("Check Out a book (O)");
        System.out.println("Check In a book (I)");
        System.out.println("See All books (A)");
        System.out.println("Quit (Q)");
    }
    public static void slcOption(){

        try{
            String urequest = "0"; // urequest = user's requested selection
            boolean quit = false; // determines when the user selects to quit and ends loop

            while(!quit){ // loops until user wants the program to end
                showOption();
                urequest = reader.readLine();

                switch (urequest) { // option N/n = new book > R/r = remove book > O/o = Checks Out Books > I/i = Checks In Book > A/a = shows all books > Q/q = quits program > default = tells user that's not a real selection and has them pick again
                    case ("N"):
                    case ("n"): {
                        System.out.println("Starting New Book Selection!");
                        newBook();
                        break;
                    }
                    case ("R"):
                    case ("r"): {
                        System.out.println("Starting Remove Book Selection!");
                        rmBook(bvt());
                        break;
                    }
                    case ("O"):
                    case ("o"): {
                        System.out.println("Starting Check Out Book!");
                        checkOut(bvt());
                        break;
                    }
                    case ("I"):
                    case ("i"): {
                        System.out.println("Starting Check In Book!");
                        checkIn(bvt());
                        break;
                    }
                    case ("A"):
                    case ("a"): {
                        System.out.println("Starting View Book Collection!");
                        showBook();
                        break;
                    }
                    case ("Q"):
                    case ("q"):{
                        System.out.println("Quitting now!");
                        quit = true;
                        break;
                    }
                    default:{
                        System.out.println("Sorry That's Not A Valid Selection! Try Again!");
                        Thread.sleep(3000);
                        break;
                    }
                }
            }
        } catch (BadData e){
            System.out.println(e.getMessage() + " is not valid try again");
            slcOption();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
