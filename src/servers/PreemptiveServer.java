package servers;

import Schedulers.Scheduler;

import static java.lang.Math.ceil;

public class PreemptiveServer extends Server {

    public PreemptiveServer(Scheduler scheduler) {
        setScheduler(scheduler);
    }

    private PreemptiveServer() {
    }

    @Override
    public void serve() {
        while (!super.getScheduler().isEmpty()) {
            super.updateCurrentlyExecuting();
            System.out.println("I am executing: " + super.getCurrentlyExecuting().getPid());
            // waitingTime : 1000 * p.getBurstTime() ==> (100 * p.getBurstTime()) *10
            int waitingQuantum = 100 * super.getCurrentlyExecuting().getBurstTime();
            while (waitingQuantum-- >= 0) {
                if (super.getCurrentlyExecuting().getPid().equals(super.getScheduler().peek().getPid()))
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else {
                    System.out.println("A higher process has come" + "last process remaining time " + waitingQuantum / 100);
                    super.getCurrentlyExecuting().setBurstTime((int) ceil(waitingQuantum / 100));
                    super.updateCurrentlyExecuting();
                    waitingQuantum = 100 * super.getCurrentlyExecuting().getBurstTime();
                }
            }
            System.out.println("FINISHED");
            super.getScheduler().pop();
        }
        return;
    }

    @Override
    public void run() {
        System.out.println("I am running");
        this.serve();

    }

}
