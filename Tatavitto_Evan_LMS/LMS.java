import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LMS {

    static File infile = new File(LMS.class.getResource("input.txt").getPath());
    static List<List<String>> cBooks = new ArrayList<List<String>>();

    public static void main(String[] args) {

        try {
            readFile();
            newBook();
            showBook();
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

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("What is the Title?");
        String title = reader.readLine();

        System.out.println("What is the Author?");
        String author = reader.readLine();

        System.out.println("Adding title: \""+ title + "\" which was written by: " + author);

        cBooks.add(new ArrayList<String>());
        cBooks.get(cBooks.size() - 1).add(String.valueOf(newId()));
        cBooks.get(cBooks.size() - 1).add(title);
        cBooks.get(cBooks.size() - 1).add(author);
    }
    public static void findId(){

    }
    public static void rmBook(){

    }
    public static void showBook(){
        for (int i = 0; i < cBooks.size(); i++){
            System.out.println(cBooks.get(i).get(0) + "," + cBooks.get(i).get(1) + "," + cBooks.get(i).get(2));
        }
    }
}
