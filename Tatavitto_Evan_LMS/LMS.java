import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LMS {

    static File infile = new File(LMS.class.getResource("input.txt").getPath());
    static List<List<String>> cBooks = new ArrayList<List<String>>();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        try {
            String urequest = "0";
            boolean quit = false;
            readFile();

            while(!quit){
                sOption();
                urequest = reader.readLine();
                switch (urequest) {
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

    public static void readFile() throws Exception {

        Scanner fscan = new Scanner(infile);
        fscan.useDelimiter(",|\\r\\n");
        String sInput = "";

        int row = 0;
        while (fscan.hasNext()) {
            cBooks.add(new ArrayList<String>());
            for (int i=0;i<3;i++){
                sInput = fscan.next();
                cBooks.get(row).add(sInput);
            }
            row++;
        }
        fscan.close();
    }
    public static int newId(){
        int lastR =0;
        lastR = cBooks.size() - 2;
        return Integer.parseInt(cBooks.get(lastR).get(0)) + 1;
    }
    public static void newBook() throws Exception{

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
    public static int findId() throws Exception {
        String rmId = "";
        System.out.println("Which Book are you looking to remove? (ID only)");
        rmId = reader.readLine();
        for (int i=0;cBooks.size()>i; i++){
            if (cBooks.get(i).get(0).equals(rmId)){
                return i;
            }
        }
        return -1;
    }
    public static void rmBook() throws Exception{

        boolean indexReal = false;
        int index = -1;
        while(!indexReal) {
            index = findId();
            if ( index >= 0 && index < cBooks.size()) {
                System.out.println("Removing!");
                cBooks.remove(index);
                indexReal =true;
                Thread.sleep(3000);
            }else {
                System.out.println("Thats not a Real ID Try Again!");
            }
        }
    }
    public static void showBook() throws Exception{
        for (int i = 0; i < cBooks.size(); i++){
            System.out.println(cBooks.get(i).get(0) + ", " + cBooks.get(i).get(1) + ", " + cBooks.get(i).get(2));
        }
        Thread.sleep(3000);
    }

    public static void sOption(){
        System.out.println("\nPlease select what to do!");
        System.out.println("Make a New book (N)");
        System.out.println("Remove a book (R)");
        System.out.println("See All books (A)");
        System.out.println("Quit (Q)");
    }
}
