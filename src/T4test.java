import Schedulers.PriorityScheduler;
import Schedulers.Scheduler;
import process.MyProcess;
import servers.PreemptiveServer;

import java.util.Random;

public class T4test {
    public static void main(String[] args)  {
//        Scheduler s = new PriorityPreemptive();
        Scheduler s = new PriorityScheduler();
        Runnable serverRunnable = new PreemptiveServer(s);
        Thread thread = new Thread(serverRunnable);
        for (int i = 0; i < 3; i++) {
            int t = 10000000;
            while (t-- > 0) ;
            MyProcess p = new MyProcess();
            p.setPriority(3);
            p.setBurstTime(10);
            s.addProcess(p);
        }
        thread.start();
        MyProcess p = new MyProcess();
        p.setPriority(1);
        p.setBurstTime(9);
        System.out.println("thread starts");
        System.out.println("process added");
        try {
            Thread.sleep(2000);
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
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
