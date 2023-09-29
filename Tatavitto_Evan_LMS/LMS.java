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
*   For:                This class is the main (and complete) code for the module 2 project requirements which includes > Reading all books from the starting file | Adding new books | Removing old books | Showing current books
*
*/
    //static File infile = new File(LMS.class.getResource("input.txt").getPath());
    static List<Book> Library = new ArrayList<Book>();
    static List<Book> Barcodes = new ArrayList<Book>();
    static Iterator booksWithName = Barcodes.iterator();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String dbfile ="";
    static File infile = new File("");

    public static void main(String[] args) {//Main that runs the different selections and loops until the user is done

        try {
            Library.add(new Book("New World Order", "Evan Tatavitto", "Fiction", Library));
            Library.add(new Book("Hello World", "John Doe", "Mistory", Library));
            Library.add(new Book("Working Hard", "McKennly", "Crime", Library));



            //findBarcode("Hello World");


            /*newFile();

            for (int i = 0; i < Library.size(); i++){
                    System.out.println(Library.get(i).toString());
            }*/

            String urequest = "0"; // urequest = user's requested selection
            boolean quit = false; // determines when the user selects to quit and ends loop

            while(!quit){ // loops until user wants the program to end
                sOption();
                urequest = reader.readLine();

                switch (urequest) { // option N/n = new book > R/r = remove book > A/a = shows all books > Q/q = quits program > default = tells user that's not a real selection and has them pick again
                    case ("N"):
                    case ("n"): {
                        System.out.println("Starting New Book Selection!");
                        newBook();
                        break;
                    }
                    case ("R"):
                    case ("r"): {
                        System.out.println("Starting Remove Book Selection!");
                        rmBook();
                        break;
                    }
                    case ("O"):
                    case ("o"): {
                        System.out.println("Starting Check Out Book!");
                        checkOut();
                        break;
                    }
                    case ("I"):
                    case ("i"): {
                        System.out.println("Starting Check In Book!");
                        checkIn();
                        break;
                    }
                    case ("A"):
                    case ("a"): {
                        System.out.println("Starting View Book Collection!");
                        showBook();
                        break;
                    }
                    case ("Q"):
                    case ("q"):
                        System.out.println("Quitting now!");
                        quit = true;
                        break;
                    default:
                        System.out.println("Sorry That's Not A Valid Selection! Try Again!");
                        Thread.sleep(3000);
                        break;
                }
            }
        } catch (BadData e){
            System.out.println(e.getMessage() + " is not valid try again");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile() throws Exception { //reads the beginning file and puts everything in a 2d array (like a database)

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
                Library.add(new Book(title,author,genre,Library));
            }
        }
        fscan.close();
    }
    public static void newFile() throws Exception{
        System.out.println("What is the database file name?");
        try {
            dbfile = reader.readLine();
            infile = new File(LMS.class.getResource(dbfile).getPath());
            readFile();
        }catch (IOException | NullPointerException i){
            System.out.println("Sorry \"" + dbfile + "\" is not a valid File!");
            System.out.println("Try Again!");
            newFile();
        }
    }
    public static void newBook() throws Exception{// grabs ID from newId and ask user for Title and Author > adds this as a new book entry to the list

        System.out.println("What is the Title?");
        String title = reader.readLine();

        System.out.println("What is the Author?");
        String author = reader.readLine();

        System.out.println("What is the Genre?");
        String genre = reader.readLine();

        Library.add(new Book(title,author,genre,Library));

        System.out.println("Making New Book!");
        Thread.sleep(3000);
    }
    public static void checkOut() {
        System.out.println("Checking Out Book!");
        System.out.println("what is the barcode you need to check out");
        String uBarcode = "";
        try{
            uBarcode = reader.readLine();
            for (int i = 0; i < Library.size(); i++) {
                if (Integer.parseInt(uBarcode) == Library.get(i).getBarcode()){
                    if(Library.get(i).getStatus()){
                        System.out.println("Sorry Thats been checked out please confirm if you wanted to check this in!");
                    }else {
                        Library.get(i).setStatus(true);
                        LocalDate dueDate = LocalDate.now();
                        dueDate = dueDate.plus(4, ChronoUnit.WEEKS);
                        Library.get(i).setDueDate(String.valueOf(dueDate));
                        System.out.println(Library.get(i).toString());
                        Thread.sleep(3000);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (NumberFormatException x) {
            System.out.println("That's not a barcodes, try again!");
            checkOut();
        }catch (InterruptedException e){

        }


    }
    public static void checkIn() {
        System.out.println("Checking In Book!");
        System.out.println("what is the barcode you need to check in");

        String uBarcode = "";
        try{
            uBarcode = reader.readLine();
            for (int i = 0; i < Library.size(); i++) {
                if (Integer.parseInt(uBarcode) == Library.get(i).getBarcode()){
                    if(!Library.get(i).getStatus()){
                        System.out.println("Sorry Thats been checked in please confirm if you wanted to check this out!");
                    }else {
                        Library.get(i).setStatus(false);
                        Library.get(i).setDueDate("NULL");
                        System.out.println(Library.get(i).toString());
                        Thread.sleep(3000);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (NumberFormatException x) {
            System.out.println("That's not a barcodes, try again!");
            checkIn();
        }catch (InterruptedException e){

        }
    }


    //need to add to check in and out and remove


    public static void findBarcode(String title){
        Barcodes.clear();
        for (int i = 0; i < Library.size(); i++){
            if (Library.get(i).getTitle().equals(title)){
                Barcodes.add(Library.get(i));
            }
        }
        if (Barcodes.size() == 0) {
            System.out.println("Nothing fits that sorry try again!");
        }else{
            for (int i = 0; i < Barcodes.size(); i++) {
                System.out.println("Barcodes with this Title >");
                System.out.println(Barcodes.get(i).getBarcode());
                sBarcode(Barcodes,title);
            }
        }
    }
    public static void sBarcode(List<Book> barcodes,String title) {
        String uBarcode = "";
        System.out.println("Now please select the barcode you need");
        try {
            uBarcode = reader.readLine();
            int s = 0;
            for (int i = 0; i < Barcodes.size(); i++) {
                if (Barcodes.get(i).getBarcode() == Integer.parseInt(uBarcode)) {

                } else {
                    s++;
                }
            }
            if(Barcodes.size() == s){
                System.out.println("That's not on the list, try again!");
                sBarcode(barcodes,title);
            }
        }catch (NumberFormatException x){
            System.out.println("That's not a barcodes, try again!");
            findBarcode(title);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public static void rmBook() throws Exception{// removes book at pulled index from findId - and if invalid requests a new one
        System.out.println("What is the barcode of the book you would like to remove");
        String uBarcode = "";
        first:
        try {
            uBarcode = reader.readLine();
            for (int i = 0; i < Library.size(); i++) {
                if (Integer.parseInt(uBarcode) == Library.get(i).getBarcode()){
                    Library.remove(i);
                    Thread.sleep(3000);
                    break first;
                }
            }
            System.out.println("Sorry Couldn't find this barcode to remove try again");
            Thread.sleep(3000);
        }catch (IOException e){
            e.printStackTrace();
        }catch (NumberFormatException x) {
            System.out.println("That's not a barcodes, try again!");
            checkIn();
        }catch (InterruptedException e){

        }
    }
    public static void showBook() throws Exception{//lists all books in books array correctly formatted
        for (int i = 0; i < Library.size(); i++){
            System.out.println(Library.get(i).toString());
        }
        Thread.sleep(3000);
    }
    public static void sOption(){//each time a selection finishes it loops back main - reduced cluder of main
        System.out.println("\nPlease select what to do!");
        System.out.println("Make a New book (N)");
        System.out.println("Remove a book (R)");
        System.out.println("Check Out a book (O)");
        System.out.println("Check In a book (I)");
        System.out.println("See All books (A)");
        System.out.println("Quit (Q)");
    }
}
