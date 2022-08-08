package M1;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;

import java.util.concurrent.Semaphore;
import java.util.Scanner;

public class Process {
    int userInput_permit = 1;
    int userOutput_permit = 1;
    int file_permit = 1;
    Semaphore userInput;
    Semaphore userOutput;
    Semaphore fileSem;
    int id;
    int a;
    int b;
    String str_a;
    String str_b;
    String fileName;

    Mutex userIn = new Mutex();
    Mutex userOut = new Mutex();
    Mutex File = new Mutex();

    boolean P1fin, P2fin, P3fin = false;



    ArrayList<String> program;
    //ArrayList<String> program2;
    //ArrayList<String> program3;
    ArrayList<ArrayList<String>> myFiles;
    ArrayList<String> instr;

    Scanner myReader = null;
    File myObj;

    int Instrcount_p1;
    int Instrcount_p2;
    int Instrcount_p3;

    boolean process1_blocked;
    boolean process2_blocked;
    boolean process3_blocked;

    public Process(String path1, int id) throws IOException   {
        this.id = id;
        this.process1_blocked = false;
        this.process2_blocked = false;
        this.process3_blocked = false;
        this.Instrcount_p1 = 0;
        this.Instrcount_p2 = 0;
        this.Instrcount_p3 = 0;
        userInput = new Semaphore(userInput_permit, true);
        userOutput = new Semaphore(userOutput_permit, true);
        fileSem = new Semaphore(file_permit, true);
        
        myFiles = new ArrayList<ArrayList<String>>();
        program = new ArrayList<String>();
        fileReader file1 = new fileReader(path1);
        fileReader file2 = new fileReader(path1);
        file1.getFileStr();
        file2.getFileArr();
        instr = file2.array;
        program.addAll(file1.str);
        myFiles.add(program);
    }
    public void printer() {
        System.out.println("======================================================");
        System.out.println(program.get(1));
    }
     //process 1 operations.....
    public int takeInp() {
        Scanner sc= new Scanner(System.in); 
        System.out.print("Please enter int data:- ");
        int a = sc.nextInt(); 
        return a;
    }
    public void outputing(int a, int b) {
        for(int i = a + 1; i < b; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

     //process 2 operations...
     public String takeInpStr() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter String data:- ");
        String a = sc.nextLine(); 
        return a;
    }
    public File createFile(String path, String fileName) throws IOException {
        File newFile = new File(path + fileName);
        if(newFile.createNewFile()) {
            System.out.println("file has been created successfuly");
        }
        else {
            System.out.println("Sorry, something went wrong");
        }
        return newFile;
    }
    
    public void run1(String instruction) throws InterruptedException {
            switch (instruction) {
                case "semWait userInput" : 
                    Instrcount_p1++;
                    break;
                case "assign a input" : 
                    a  = takeInp();
                    Instrcount_p1++;
                    break;
                case "assign b input" :
                    b = takeInp(); 
                    Instrcount_p1++;
                    break;
                case "semSignal userInput" :
                    Instrcount_p1++;
                    break;
                case "semWait userOutput" :
                        Instrcount_p1++;
                    break;
                case "printFromTo a b" :
                    if(b > a) {
                        outputing(a, b);
                        Instrcount_p1++;
                    }
                    else {
                        System.out.println("Sorry, wrong input values");
                    }
                    break;
                case "semSignal userOutput" :
                        Instrcount_p1++;
                        P1fin = true;
                        break;
            }
    }


    
    


    public void run2(String instruction) throws InterruptedException, IOException {
            switch (instruction) {
                case "semWait userInput" : 
                        Instrcount_p2++;
                    break;
                case "assign a input" : 
                    str_a  = takeInpStr();
                    Instrcount_p2++;
                    break;
                case "assign b input" :
                    str_b = takeInpStr(); 
                    
                    Instrcount_p2++;
                    break;
                case "semSignal userInput" :
                    Instrcount_p2++;
                    break;
                case "semWait file" :
                        Instrcount_p2++;
                     break;
                case "writeFile a b" :
                    File newFile = createFile("C:\\Users\\Ziad\\Desktop\\MileStone 1\\", str_a);
                    FileWriter myWriter = new FileWriter("C:\\Users\\Ziad\\Desktop\\MileStone 1\\"  + str_a);
                    myWriter.write(str_a + "\n");
                    myWriter.write(str_b + "\n");
                    myWriter.close();
                    Instrcount_p2++;
                    break;
                case "semSignal file": 
                    Instrcount_p2++;
                    System.out.println("Successfully wrote to the file.");
                    P2fin = true;
                    break;
            }
        //taking the file name as an input from the user.
        
    }

    public void run3(String instruction) {

       switch(instruction) {
        case "semWait userInput" : 
                try {
                    Instrcount_p3++;
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            break;
        case "assign a input" : 
            fileName = takeInpStr();
            Instrcount_p3++;
            break;
        case "semSignal userInput" :
            Instrcount_p3++;
            break;
        case "semWait file" :
            try {

            }
            catch (Exception e) {
                System.out.println(e);

            }
            Instrcount_p3++;
            break;
        case "assign b readFile a":
            myObj = new File("C:\\Users\\Ziad\\Desktop\\MileStone 1\\" + fileName);
            try {
                myReader = new Scanner(myObj);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            Instrcount_p3++;
            break;
        case "semSignal file":
            Instrcount_p3++;
            break;
        case "semWait userOutput":
                try {
                    Instrcount_p3++;
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println(Instrcount_p3);
            break;
        case "print b":
        if(myReader != null) {
            while(myReader.hasNextLine()) {
                System.out.println(myReader.nextLine());
            }
        }
        Instrcount_p3++;
        break;
        case "semSignal userOutput":
            Instrcount_p3++;
            P3fin = true;
            break;
    }   
    }
    public static void main(String[]args) throws IOException, InterruptedException {
        Process test = new Process(
        "C:\\Users\\Ziad\\Desktop\\MileStone 1\\Program_1.txt", 1);
          Scheduler x = new Scheduler(0,1,5,2);
    }
}
