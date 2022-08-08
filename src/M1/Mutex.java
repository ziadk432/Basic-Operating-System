package M1;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.ArrayList;

public class Mutex {
    int id = -1;
    boolean MutexFlag;

    public Mutex(){
        MutexFlag = false;
    }

    public void semWait(int Pid){
        if(MutexFlag == false){
            id = Pid;
            MutexFlag = true;
                }
        else if(MutexFlag == true && this.id == Pid){
        }
        else{
            System.out.print("Action Blocked, Processor " + Pid + " moved to BlockedQueue");
        }
    }

    public void semSignal(int Pid){
        if(id == Pid){
            MutexFlag = false;
            id = -1;
        }
        else{
            System.out.print("Action Blocked, Processor " + Pid + " moved to BlockedQueue");
        }
 
    }
}


