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


            newFile();

            slcOption();
        }
        catch (BadData e){
            System.out.println(e.getMessage() + " is not valid try again");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int bvt() throws Exception{//Barcode Vs Title
        int barcode = 0;
        String uRequest ="";

        boolean type = false;
        while(!type) {
            System.out.println("Are you using a Barcode (B) or a Title (T) (Q to Quit)?");
            uRequest = reader.readLine();
            switch (uRequest) {

                case ("B"):
                case ("b"): {
                    type = true;
                    System.out.println("So you have a Barcode - what is it?");
                    uRequest = reader.readLine();
                    try {
                        barcode = Integer.parseInt(uRequest);
                    }catch (NumberFormatException x) {
                        System.out.println("That's not a barcodes, try again!");
                        bvt();
                    }
                    return barcode;
                }
                case ("T"):
                case ("t"): {
                    type =true;
                    System.out.println("So you have a Title - what is it?");
                    uRequest = reader.readLine();
                    barcode = findBarcode(uRequest);
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
    public static void readFile() throws Exception { //reads the beginning file and puts everything in a 2d array (like a database)
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
    public static void checkOut(int barcode) {
        if(barcode > 0){
            try {
                int s = 0;
                for (int i = 0; i < Library.size(); i++) {
                    if (barcode == Library.get(i).getBarcode()) {
                        if (Library.get(i).getStatus()) {
                            System.out.println("Sorry Thats been checked out please confirm if you wanted to check this in!");
                        } else {
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
    public static void checkIn(int barcode) {
        if(barcode > 0) {
            try {
                for (int i = 0; i < Library.size(); i++) {
                    if (barcode == Library.get(i).getBarcode()) {
                        if (!Library.get(i).getStatus()) {
                            System.out.println("Sorry Thats been checked in please confirm if you wanted to check this out!");
                        } else {
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


    //need to add to check in and out and remove


    public static int findBarcode(String title) throws InterruptedException{
        Barcodes.clear();
        for (int i = 0; i < Library.size(); i++){
            if (Library.get(i).getTitle().equals(title)){
                Barcodes.add(Library.get(i));
            }
        }
        if (Barcodes.size() == 0) {
            System.out.println("Nothing fits that sorry try again!");
            Thread.sleep(2000);
        }else{
            System.out.println("Barcodes with this Title >");
            for (int i = 0; i < Barcodes.size(); i++) {
                System.out.println(Barcodes.get(i).getBarcode());
            }
            Thread.sleep(500);
            return slcBarcode(Barcodes,title);
        }
        return -1;
    }
    public static int slcBarcode(List<Book> barcodes, String title) throws InterruptedException{
        String uBarcode = "";
        System.out.println("Now please select the barcode you need");
        try {
            uBarcode = reader.readLine();
            int s = 0;
            for (int i = 0; i < Barcodes.size(); i++) {
                if (Barcodes.get(i).getBarcode() == Integer.parseInt(uBarcode)) {
                    return Barcodes.get(i).getBarcode();
                } else {
                    s++;
                }
            }
            if(Barcodes.size() == s){
                System.out.println("That's not on the list, try again!");
                Thread.sleep(2000);
                slcBarcode(barcodes,title);
            }
        }catch (NumberFormatException x){
            System.out.println("That's not a barcodes, try again!");
            Thread.sleep(2000);
            findBarcode(title);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    public static void rmBook(int barcode) throws Exception{// removes book at pulled index from findId - and if invalid requests a new one
        if(barcode > 0) {
            first:
            try {

                for (int i = 0; i < Library.size(); i++) {
                    if (barcode == Library.get(i).getBarcode()) {
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
    public static void showBook() throws Exception{//lists all books in books array correctly formatted
        for (int i = 0; i < Library.size(); i++){
            System.out.println(Library.get(i).toString());
        }
        Thread.sleep(3000);
    }
    public static void showOption(){//each time a selection finishes it loops back main - reduced cluder of main
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
