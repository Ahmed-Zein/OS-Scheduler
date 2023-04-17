package Schedulers;

import process.MyProcess;

import static java.lang.Math.ceil;

public class PreemptiveServer extends Thread {
    Scheduler scheduler;

    public PreemptiveServer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private PreemptiveServer() {
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void serve() {
        if (scheduler.isEmpty()) return;
        MyProcess p = scheduler.peek();
        // waitingTime : 1000 * p.getBurstTime() ==> (100 * p.getBurstTime()) *10
        int waitingQuantum = 100 * p.getBurstTime();
        while (waitingQuantum-- >= 0) {
            if (p.getPid().equals(scheduler.peek().getPid()))
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            else {
                System.out.println("55555 a higher process has come" + "last process remaining time "+waitingQuantum / 100 );
                p.setBurstTime((int) ceil(waitingQuantum / 100));
                p = scheduler.peek();
                waitingQuantum = 100 * p.getBurstTime();
            }
        }
        System.out.println("FINISHED");
        scheduler.pop();
        return;
    }

    @Override
    public void run() {
        this.serve();
        super.run();
    }

}
