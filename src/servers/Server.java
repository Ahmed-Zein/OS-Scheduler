package servers;

import Schedulers.Scheduler;
import process.MyProcess;

public abstract class Server implements Runnable {
    private Scheduler scheduler;
     public MyProcess currentlyExecuting;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void serve() {
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void updateCurrentlyExecuting() {
        this.currentlyExecuting =  scheduler.peek();
    }

    public MyProcess getCurrentlyExecuting() {
        return currentlyExecuting;
    }

    @Override
    public void run() {
    }
}
