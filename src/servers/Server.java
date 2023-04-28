package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import oserver.Observable;
import process.MyProcess;
import process.ProcessState;

public abstract class Server implements Runnable {
    private Scheduler scheduler;
    private MyProcess currentlyExecuting;
    private Observable observers;

    public Observable getObservers() {
        return observers;
    }

    public Server(Scheduler scheduler) {
        this.scheduler = scheduler;
        observers = new Observable();
    }

    Server() {
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
        currentlyExecuting.setState(ProcessState.running);
        GrantChart.instance().changeColor(currentlyExecuting.getColor());
    }

    public MyProcess getCurrentlyExecuting() {
        return currentlyExecuting;
    }

    public void pop() {
        scheduler.pop();
        observers.update();
    }

    public void push(MyProcess p) {
        scheduler.addProcess(p);
        observers.update();
    }

    @Override
    public void run() {
    }
}
