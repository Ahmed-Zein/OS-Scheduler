import Schedulers.PriorityNonPreemptive;
import Schedulers.Scheduler;
import Schedulers.ShortestJobFirstNonPreemptive;
import process.MyProcess;

import java.util.Random;

public class T4test {
    public static void main(String[] args) {
//        Scheduler s = new FirstComeFirstServed();
//        Scheduler s = new PriorityNonPreemptive();
        Scheduler s = new ShortestJobFirstNonPreemptive();

        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            int t = 10000000;
            while (t-- > 0) ;
            MyProcess p = new MyProcess();
            p.setPriority(rd.nextInt(1, 5));
            p.setBurstTime(rd.nextInt(1, 10));
            s.addProcess(p);
        }
        while (!s.isEmpty())
//            System.out.println(s.serve().getArriveTime());
//            System.out.println(s.serve().getPriority());
            System.out.println(s.serve().getBurstTime());

    }
}
