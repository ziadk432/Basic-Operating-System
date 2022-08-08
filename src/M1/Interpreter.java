// package M1;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.util.ArrayList;

// import java.util.concurrent.Semaphore;
// import java.util.Scanner;



// public class Interpreter {
//     int userInput_permit = 1;
//     int userOutput_permit = 1;
//     int file_permit = 1;
//     Semaphore userInput;
//     Semaphore userOutput;
//     Semaphore fileSem;



//     ArrayList<String> program1;
//     ArrayList<String> program2;
//     ArrayList<String> program3;
//     ArrayList<ArrayList<String>> myFiles;


//     int Instrcount_p1;
//     int Instrcount_p2;
//     int Instrcount_p3;

//     boolean process1_blocked;
//     boolean process2_blocked;
//     boolean process3_blocked;
//     public Interpreter(String path1, String path2, String path3) throws IOException {
//         this.process1_blocked = false;
//         this.process2_blocked = false;
//         this.process3_blocked = false;
//         this.Instrcount_p1 = 0;
//         this.Instrcount_p2 = 0;
//         this.Instrcount_p3 = 0;
//         userInput = new Semaphore(userInput_permit, true);
//         userOutput = new Semaphore(userOutput_permit, true);
//         fileSem = new Semaphore(file_permit, true);
        
//         myFiles = new ArrayList<ArrayList<String>>();
//         program1 = new ArrayList<String>();
//         program2 = new ArrayList<String>();
//         program3 = new ArrayList<String>();
//         fileReader file1 = new fileReader(path1);
//         fileReader file2 = new fileReader(path2);
//         fileReader file3 = new fileReader(path3);
//         file1.getFileStr();
//         file2.getFileStr();
//         file3.getFileStr();
//         program1.addAll(file1.str);
//         program2.addAll(file2.str);
//         program3.addAll(file3.str);
//         //to be used in process3 method...
//         myFiles.add(program1);
//         myFiles.add(program2);
//         myFiles.add(program3);
//     }

//     public void printer() {
//         System.out.println("Program1:- " + program1);
//         System.out.println("Program2:- " + program2);
//         System.out.println("Program3:- " + program3);
//         System.out.println("======================================================");
//         System.out.println(program1.get(1));

//     }
//     //process 1 operations.....
//     public int takeInp() {
//         Scanner sc= new Scanner(System.in); 
//         System.out.print("please enter the first value:- ");
//         int a= sc.nextInt(); 
//         return a;
//     }
//     public void outputing(int a, int b) {
//         for(int i = a + 1; i < b; i++) {
//             System.out.print(i + " ");
//         }
//         System.out.println();
//     }

//     //process 2 operations...
//     public String takeInpStr() {
//         Scanner sc= new Scanner(System.in);
//         System.out.print("please enter a data:- ");
//         String a= sc.nextLine(); 
//         return a;
//     }
//     public File createFile(String path, String fileName) throws IOException {
//         File newFile = new File(path + fileName);
//         if(newFile.createNewFile()) {
//             System.out.println("file has been created successfuly");
//         }
//         else {
//             System.out.println("Sorry, something went wrong");
//         }
//         return newFile;
//     }


//     public void process1() throws InterruptedException {
//         int a = 0;
//         int b = 0;
//         for(String instruction: program1) {
//             switch (instruction) {
//                 case "semWait userInput" : 
//                     if(!userInput.tryAcquire()) {
//                         process1_blocked = true;
//                     }
//                     else {
//                         userInput.acquire();
//                         System.out.println("Semaphore Now:- "+ userInput );
//                         Instrcount_p1++;
//                     }
//                     break;
//                 case "assign a input" : 
//                     a  = takeInp();
//                     Instrcount_p1++;
//                     break;
//                 case "assign b input" :
//                     b = takeInp(); 
//                     Instrcount_p1++;
//                     break;
//                 case "semSignal userInput" :
//                     userInput.release();
//                     Instrcount_p1++;
//                     break;
//                 case "semWait userOutput" :
//                     if(!userOutput.tryAcquire()) {
//                         process1_blocked = true;
//                     }
//                     else {
//                         userOutput.acquire();
//                         Instrcount_p1++;
//                     }
//                     break;
//                 case "printFromTo a b" :
//                     if(b > a) {
//                         outputing(a, b);
//                         Instrcount_p1++;
//                     }
//                     else {
//                         System.out.println("Sorry, wrong input values");
//                     }
//                     break;
//                 case "semSignal userOutput" :
//                         userOutput.release();
//                         Instrcount_p1++;
//                         System.out.println("Semaphore then:- "+ userInput );
//                         break;
//             }
            
//         }     
//     }

//     public void process2() throws IOException, InterruptedException {
//         String a = "";
//         String b = "";
//         userInput.acquire();
//         String fileName = takeInpStr();
//         userInput.release();
//         for(String instruction: program2) {
//             switch (instruction) {
//                 case "semWait userInput" : 
//                     if(!userInput.tryAcquire()) {
//                         process2_blocked = true;
//                     }
//                     else {
//                         userInput.acquire();
//                         Instrcount_p2++;
//                         System.out.println("Semaphore Now:- "+ userInput );
//                     }
//                     break;
//                 case "assign a input" : 
//                     a  = takeInpStr();
//                     Instrcount_p2++;
//                     break;
//                 case "assign b input" :
//                     b = takeInpStr(); 
//                     Instrcount_p2++;
//                     break;
//                 case "semSignal userInput" :
//                     userInput.release();
//                     Instrcount_p2++;
//                     break;
//                 case "semWait file" :
//                     if(!fileSem.tryAcquire()) {
//                         process2_blocked = true;
//                     }
//                     else {
//                         fileSem.acquire();
//                         Instrcount_p2++;
//                     }
//                      break;
//                 case "writeFile a b" :
//                     File newFile = createFile("C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\" , fileName);
//                     FileWriter myWriter = new FileWriter("C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\" + fileName);
//                     myWriter.write(a + "\n");
//                     myWriter.write(b + "\n");
//                     myWriter.close();
//                     Instrcount_p2++;
//                     break;
//                 case "semSignal file": 
//                     fileSem.release();
//                     Instrcount_p2++;
//                     break;
                

                
//             }
//         }    
//         //taking the file name as an input from the user.
//         System.out.println("Successfully wrote to the file.");
//     }

//     public void process3() throws FileNotFoundException, InterruptedException {
//        String fileName = "";
//        Scanner myReader = null;
//        File myObj;
       
//         for(String instruction: program3) {
//             switch(instruction) {
//                 case "semWait userInput" : 
//                     if(!userInput.tryAcquire()) {
//                         process3_blocked = true;
//                     }
//                     else {
//                         userInput.acquire();
//                         Instrcount_p3++;
//                         System.out.println("Semaphore Now:- "+ userInput );
//                     }
//                     break;
//                 case "assign a input" : 
//                     fileName = takeInpStr();
//                     Instrcount_p3++;
//                     break;
//                 case "semSignal userInput" :
//                     userInput.release();
//                     Instrcount_p3++;
//                     break;
//                 case "semWait file" :
//                 if(!fileSem.tryAcquire()) {
//                     process3_blocked = true;
//                 }
//                 else {
//                     fileSem.acquire();
//                     Instrcount_p3++;
//                 }
//                     break;
//                 case "assign b readFile a":
//                     myObj = new File("C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\" + fileName);
//                     myReader = new Scanner(myObj);
//                     Instrcount_p3++;
//                     break;
//                 case "semSignal file":
//                     fileSem.release();
//                     Instrcount_p3++;
//                 case "semWait userOutput":
//                     if(userOutput.tryAcquire()) {
//                         process3_blocked = true;
//                     }
//                     else {
//                         userOutput.acquire();
//                         Instrcount_p3++;
//                     }
//                 case "print b":
//                 if(myReader != null) {
//                     while(myReader.hasNextLine()) {
//                         System.out.println(myReader.nextLine());
//                     }
//                     Instrcount_p3++;
//                 }
//                 case "semSignal userOutput":
//                     userOutput.release();
//                     Instrcount_p3++;
//                     break;
//             }
//         }
//     }
   

//     public static void main(String[]args) throws IOException, InterruptedException {
//         Interpreter test = new Interpreter(
//         "C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\Program_1.txt", 
//         "C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\Program_2.txt", 
//         "C:\\Users\\Omar\\OneDrive\\Desktop\\GUC\\8th Semester\\Operating Systems\\Project\\MileStone 1\\MileStone 1\\Program_3.txt"
//         );
//       //  test.printer();
//         //test.process1();
//         //test.process2();
//       //  test.process3();
//     }
// }
