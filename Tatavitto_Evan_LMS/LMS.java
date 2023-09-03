import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LMS {
/*
*           Name:       Evan Tatavitto
*           Course:     Dev1
*           Date:       9/2/2023
*           Class:      LMS
*           For:        This class is the main (and complete) code for the module 2 project requirements which includes > Reading all books from the starting file | Adding new books | Removing old books | Showing current books
*
*/
    static File infile = new File(LMS.class.getResource("input.txt").getPath());
    static List<List<String>> cBooks = new ArrayList<List<String>>();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {//Main that runs the different selections and loops until the user is done

        try {
            String urequest = "0"; // urequest = user's requested selection
            boolean quit = false; // determines when the user selects to quit and ends loop
            readFile();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFile() throws Exception { //reads the beginning file and puts everything in a 2d array (like a database)

        Scanner fscan = new Scanner(infile);
        fscan.useDelimiter(",|\\r\\n"); // allows to pull each section of the file correctly (file is CSV and endline is new book)
        String sInput = "";

        int row = 0;
        while (fscan.hasNext()) { // loops until full file is read
            cBooks.add(new ArrayList<String>()); // makes a new book entry for next step to fill
            for (int i=0;i<3;i++){ // only loops 3 times for ID, Title, Author
                sInput = fscan.next();
                cBooks.get(row).add(sInput);
            }
            row++;
        }
        fscan.close();
    }
    public static int newId(){ // finds last item in array > finds ID > adds one to ID > submits new ID for calling parent
        int lastR =0;
        lastR = cBooks.size() - 2;
        return Integer.parseInt(cBooks.get(lastR).get(0)) + 1;
    }
    public static void newBook() throws Exception{// grabs ID from newId and ask user for Title and Author > adds this as a new book entry to the list

        System.out.println("What is the Title?");
        String title = reader.readLine();

        System.out.println("What is the Author?");
        String author = reader.readLine();

        System.out.println("Adding title: \""+ title + "\" which was written by: " + author);

        cBooks.add(new ArrayList<String>());
        cBooks.get(cBooks.size() - 1).add(String.valueOf(newId()));
        cBooks.get(cBooks.size() - 1).add(title);
        cBooks.get(cBooks.size() - 1).add(author);

        Thread.sleep(3000);
    }
    public static int findId() throws Exception {//request an ID from user > checks this agents current ID's and if one is found sends this to calling parent else tells parent it is not valid (-1 > invalid array index)
        String rmId = "";
        System.out.println("Which Book are you looking to remove? (ID only)");
        rmId = reader.readLine();
        for (int i=0;cBooks.size()>i; i++){//loops through full array or an ID is found
            if (cBooks.get(i).get(0).equals(rmId)){//checks ID
                return i;
            }
        }
        return -1;
    }
    public static void rmBook() throws Exception{// removes book at pulled index from findId - and if invalid requests a new one

        boolean indexReal = false;
        int index = -1;
        while(!indexReal) {// looped to keep asking for an index until one is found
            index = findId();
            if ( index >= 0 && index < cBooks.size()) {//used to check ID is valid
                System.out.println("Removing!");
                cBooks.remove(index);
                indexReal =true;
                Thread.sleep(3000);
            }else {
                System.out.println("Thats not a Real ID Try Again!");
            }
        }
    }
    public static void showBook() throws Exception{//lists all books in books array correctly formatted
        for (int i = 0; i < cBooks.size(); i++){
            System.out.println(cBooks.get(i).get(0) + ", " + cBooks.get(i).get(1) + ", " + cBooks.get(i).get(2));
        }
        Thread.sleep(3000);
    }
    public static void sOption(){//each time a selection finishes it loops back main - reduced cluder of main
        System.out.println("\nPlease select what to do!");
        System.out.println("Make a New book (N)");
        System.out.println("Remove a book (R)");
        System.out.println("See All books (A)");
        System.out.println("Quit (Q)");
    }
}
