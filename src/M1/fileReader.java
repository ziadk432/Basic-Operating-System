package M1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class fileReader {

    String path;
    ArrayList<String> array;
    public ArrayList<String> str;
    public fileReader(String path) {
        this.path = path;
        this.str = new ArrayList<String>();
    }

    public void getFileStr() throws IOException {
        // BufferedReader sc = new BufferedReader(new FileReader(path));
        Scanner sc = new Scanner(new File(path));
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            str.add(line);
        }
        
    }

    public void getFileArr() throws IOException {
        array = new ArrayList<String>();
        Scanner sc = new Scanner(new File(path));
        while(sc.hasNextLine()) {
            String line = sc.nextLine();
            array.add(line);
        }
        
    }

    public void printer() {
        System.out.println(str);
    }


    public static void main(String[]args) throws IOException {
       // fileReader test = new fileReader("C:\\Users\\Ziad\\Desktop\\MileStone 1\\Program_2.txt");
        
    }
}
