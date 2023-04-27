package servers;

import Schedulers.Scheduler;
import oserver.Observable;
import process.MyProcess;

public abstract class Server implements Runnable {
    private Scheduler scheduler;
    private MyProcess currentlyExecuting;

    public Observable getObservable() {
        return observable;
    }

    private Observable observable;

    public Server(Scheduler scheduler) {
        this.scheduler = scheduler;
        this.observable = new Observable();
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
        if(scheduler.isEmpty()) {
            observable.update(null);
            return;
        }
        this.currentlyExecuting = scheduler.peek();
        observable.update(currentlyExecuting);
    }

    public MyProcess getCurrentlyExecuting() {
        return currentlyExecuting;
    }

    @Override
    public void run() {
    }
}
