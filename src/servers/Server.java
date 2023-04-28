package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import process.MyProcess;

public abstract class Server implements Runnable {
    private Scheduler scheduler;
    private MyProcess currentlyExecuting;

    public Server(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Server() {
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void serve() {
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void updateCurrentlyExecuting() {
        if (scheduler.isEmpty()) {
            return;
        }
        this.currentlyExecuting = scheduler.peek();
        GrantChart.instance().changeColor(currentlyExecuting.getColor());
    }

    public MyProcess getCurrentlyExecuting() {
        return currentlyExecuting;
    }

    @Override
    public void run() {
    }
}
