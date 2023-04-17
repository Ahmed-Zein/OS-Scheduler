package Schedulers;

import process.MyProcess;

public class NonPreemptiveServer extends Thread {
    Scheduler scheduler;

    public NonPreemptiveServer(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private NonPreemptiveServer() {
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void serve() {
        if (scheduler.isEmpty()) return;
        MyProcess p = scheduler.peek();
        try {
            Thread.sleep(p.getBurstTime() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
