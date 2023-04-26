package Schedulers;

import process.MyProcess;

import static java.lang.Math.ceil;

public class PreemptiveServer extends Server {

    public PreemptiveServer(Scheduler scheduler) {
        setScheduler(scheduler);
    }

    private PreemptiveServer() {
    }

    public void serve() {
        if (super.getScheduler().isEmpty()) return;
        MyProcess p = super.getScheduler().peek();
        // waitingTime : 1000 * p.getBurstTime() ==> (100 * p.getBurstTime()) *10
        int waitingQuantum = 100 * p.getBurstTime();
        while (waitingQuantum-- >= 0) {
            if (p.getPid().equals(super.getScheduler().peek().getPid()))
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            else {
                System.out.println("55555 a higher process has come" + "last process remaining time " + waitingQuantum / 100);
                p.setBurstTime((int) ceil(waitingQuantum / 100));
                p = super.getScheduler().peek();
                waitingQuantum = 100 * p.getBurstTime();
            }
        }
        System.out.println("FINISHED");
        super.getScheduler().pop();
        return;
    }

    @Override
    public void run() {
        this.serve();
        super.run();
    }

}
