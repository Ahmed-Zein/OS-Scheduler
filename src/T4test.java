import Schedulers.PreemptiveServer;
import Schedulers.PriorityScheduler;
import Schedulers.Scheduler;
import process.MyProcess;

import java.util.Random;

public class T4test {
    public static void main(String[] args) {
//        Scheduler s = new PriorityPreemptive();
        Scheduler s = new PriorityScheduler();
        Thread threadServer = new PreemptiveServer(s);
        for (int i = 0; i < 100; i++) {
            int t = 10000000;
            while (t-- > 0) ;
            MyProcess p = new MyProcess();
            p.setPriority(3);
            p.setBurstTime(20);
            s.addProcess(p);
        }
        threadServer.start();
        MyProcess p = new MyProcess();
        p.setPriority(1);
        p.setBurstTime(5);
        System.out.println("thread starts");
        System.out.println("process added");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.addProcess(p);

////        Scheduler s = new FirstComeFirstServed();
////        Scheduler s = new PriorityNonPreemptive();
//        Scheduler s = new ShortestJobFirstNonPreemptive();
//
        Random rd = new Random();
//        while (!s.isEmpty())
//            System.out.println(s.serve().getArriveTime());
//            System.out.println(s.serve().getPriority());
//            System.out.println(s.pop().getPriority());

    }
}
