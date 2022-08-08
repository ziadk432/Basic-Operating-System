package M1;
import java.io.File;
import java.io.IOException;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.lang.model.type.NullType;
import javax.print.attribute.standard.RequestingUserName;

public class Scheduler {
    Queue<Process> readyQueue;
    Queue<Process> blockedQueue;
    ArrayList<String> process1;
    ArrayList<String> process2;
    ArrayList<String> process3;

    int p1_arrives;
    int p2_arrives;
    int p3_arrives;
    int IPS;
    int t = 0;

    public Scheduler (int p1_arrives, int p2_arrives, int p3_arrives, int IPS) throws IOException, InterruptedException {
        readyQueue = new LinkedList<Process>();
        blockedQueue = new LinkedList<Process>();
        Mutex userIn = new Mutex();
        Mutex userOut = new Mutex();
        Mutex File = new Mutex();


        Process P1 = new Process("C:\\Users\\Ziad\\Desktop\\MileStone 1\\Program_1.txt", 1);
        Process P2 = new Process("C:\\Users\\Ziad\\Desktop\\MileStone 1\\Program_2.txt", 2);
        Process P3 = new Process("C:\\Users\\Ziad\\Desktop\\MileStone 1\\Program_3.txt", 3);

        boolean p1_done = false;
        boolean p2_done = false;
        boolean p3_done = false;

        //arrival time of each process...
        this.p1_arrives = p1_arrives;
        this.p2_arrives = p2_arrives;
        this.p3_arrives = p3_arrives;

        //instruction per slice...
        this.IPS = IPS;

         int cycles = 0;



         if(0 == p2_arrives){
            readyQueue.add(P2);
        }
        else if(0 == p3_arrives){
            readyQueue.add(P3);
        }
        else if(0 == p1_arrives){
            readyQueue.add(P1);
        }
    

        

        while(!readyQueue.isEmpty() || !blockedQueue.isEmpty()){
            System.out.println("<<Cycle>>  " + cycles);

            if(cycles == p2_arrives && !readyQueue.contains(P2)){
                readyQueue.add(P2);
            }
            else if(cycles == p3_arrives && !readyQueue.contains(P3)){
                readyQueue.add(P3);
            }
            else if(cycles == p1_arrives && !readyQueue.contains(P1)){
                readyQueue.add(P1);
            }
             cycles++;
            ArrayList<Integer> arrR = new ArrayList<Integer>();
            ArrayList<Integer> arrB = new ArrayList<Integer>();


            for(int i = 0;i < readyQueue.size(); i++){
                Process temp = readyQueue.remove();
               arrR.add(temp.id);
               readyQueue.add(temp);
            }
            for(int i = 0;i < blockedQueue.size(); i++){
                Process temp = blockedQueue.remove();
               arrB.add(temp.id);
               blockedQueue.add(temp);
            }
            System.out.println("ReadyQueue is:  " + arrR);
            System.out.println("BlockedQueue is:  " + arrB);

            for(int i = 0; i<IPS; i++){
                if(readyQueue.peek().id == 1 && readyQueue.peek().Instrcount_p1 < readyQueue.peek().instr.size() && !blockedQueue.contains(P1) && P1.P1fin == false){
                    System.out.println("Executing Process: " + readyQueue.peek().id);
                    String x = readyQueue.peek().instr.get(readyQueue.peek().Instrcount_p1).toString();
                    System.out.println(x);
                    System.out.println(readyQueue.peek().Instrcount_p1);
                    if (x.equals("semWait userInput")){
                        userIn.semWait(1);
                        if(userIn.id ==  1 && userIn.MutexFlag == true){
                            P1.run1(x);
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else if (x.equals("semWait userOutput")){
                        userOut.semWait(1);
                        if(userOut.id ==  1 && userOut.MutexFlag == true){
                            P1.run1(x);
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else if (x.equals("semWait File")){
                        File.semWait(1);
                        if(File.id ==  1 && File.MutexFlag == true){
                            P1.run1(x);
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else if(x.equals("semSignal userInput")){
                        userIn.semSignal(1);
                        if(userIn.MutexFlag == false){
                            P1.run1(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else if(x.equals("semSignal userOutput")){
                        userOut.semSignal(1);
                        if(userOut.MutexFlag == false){
                            P1.run1(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else if(x.equals("semSignal File")){
                        File.semSignal(1);
                        if(File.MutexFlag == false){
                            P1.run1(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P1);
                        }
                    }
                    else{
                        P1.run1(x);
                    }
                    System.out.println("==============================================");
                }
                if(readyQueue.peek().id == 2 && readyQueue.peek().Instrcount_p2 < readyQueue.peek().instr.size() && !blockedQueue.contains(P2) && !blockedQueue.contains(P2) && P2.P2fin == false){
                    System.out.println("Executing Process: " + readyQueue.peek().id);
                    String x = readyQueue.peek().instr.get(readyQueue.peek().Instrcount_p2).toString();
                    System.out.println(x);
                    if (x.equals("semWait userInput")){
                        userIn.semWait(2);
                        if(userIn.id == 2 && userIn.MutexFlag == true){
                            P2.run2(x);
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }
                    else if (x.equals("semWait userOutput")){
                        userOut.semWait(2);
                        if(userOut.id ==  2 && userOut.MutexFlag == true){
                            P2.run2(x);
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }
                    else if (x.equals("semWait File")){
                        File.semWait(2);
                        if(File.id ==  2 && File.MutexFlag == true){
                            P2.run2(x);
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }

                    else if(x.equals("semSignal userInput")){
                        userIn.semSignal(2);
                        if(userIn.MutexFlag == false){
                            P2.run2(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }
                    else if(x.equals("semSignal userOutput")){
                        userOut.semSignal(2);
                        if(userOut.MutexFlag == false){
                            P2.run2(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }
                    else if(x.equals("semSignal File")){
                        File.semSignal(2);
                        if(File.MutexFlag == false){
                            P2.run2(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P2);
                        }
                    }
                    else{
                        P2.run2(x);
                    }
                    System.out.println("==============================================");
                }
                else if(readyQueue.peek().id == 3 && readyQueue.peek().Instrcount_p3 < readyQueue.peek().instr.size() && !blockedQueue.contains(P3) && P3.P3fin == false){
                    System.out.println("Executing Process: " + readyQueue.peek().id);
                    String x = readyQueue.peek().instr.get(readyQueue.peek().Instrcount_p3).toString();
                    if (x.equals("semWait userInput")){
                        userIn.semWait(3);
                        if(userIn.id == 3 && userIn.MutexFlag == true){
                            P3.run3(x);
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else if (x.equals("semWait userOutput")){
                        userOut.semWait(3);
                        if(userOut.id ==  3 && userOut.MutexFlag == true){
                            P3.run3(x);
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else if (x.equals("semWait File")){
                        File.semWait(3);
                        if(File.id ==  3 && File.MutexFlag == true){
                            P3.run3(x);
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else if(x.equals("semSignal userInput")){
                        userIn.semSignal(3);
                        if(userIn.MutexFlag == false){
                            P3.run3(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else if(x.equals("semSignal userOutput")){
                        userOut.semSignal(3);
                        if(userOut.MutexFlag == false){
                            P3.run3(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else if(x.equals("semSignal File")){
                        File.semSignal(3);
                        if(File.MutexFlag == false){
                            P3.run3(x);
                            if(!blockedQueue.isEmpty()){
                                blockedQueue.remove();
                            }
                        }
                        else{
                            blockedQueue.add(P3);
                        }
                    }
                    else{
                        P3.run3(x);
                    }
                    System.out.println("==============================================");
                }
                
            }
            // readyQueue.remove();
            // if(readyQueue.peek().Instrcount_p1 < readyQueue.peek().instr.size() && readyQueue.peek().id == 1){
            //     readyQueue.remove();
            // }
            // else{
            //     readyQueue.add(readyQueue.remove());
            // }
            // if(readyQueue.peek().Instrcount_p2 < readyQueue.peek().instr.size() && readyQueue.peek().id == 2){
            //     readyQueue.remove();
            // }
            // else{
            //     readyQueue.add(readyQueue.remove());
            // }
            // if(readyQueue.contains(o))
            // if(readyQueue.peek().Instrcount_p3 < readyQueue.peek().instr.size() && readyQueue.peek().id == 3){
            //     readyQueue.remove();
            // }
            // else{
            //     readyQueue.add(readyQueue.remove());
            // }
            if(readyQueue.peek().id == 1){
                if(readyQueue.peek().Instrcount_p1 == readyQueue.peek().instr.size()){
                    readyQueue.remove();
                }
                else{
                    readyQueue.add(readyQueue.remove());
                }
            }
            else if(readyQueue.peek().id == 2){
                if(readyQueue.peek().Instrcount_p2 == readyQueue.peek().instr.size()){
                    readyQueue.remove();
                }
                else{
                    readyQueue.add(readyQueue.remove());
                }
            }
            else{
                if(readyQueue.peek().Instrcount_p3 == readyQueue.peek().instr.size()){
                    readyQueue.remove();
                }
                else{
                    readyQueue.add(readyQueue.remove());
                }
            }
    }
}
}
