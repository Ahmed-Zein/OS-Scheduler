package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import UI.ShowReport;
import javafx.application.Platform;
import oserver.Observable;
import process.MyProcess;
import process.ProcessState;

import java.util.ArrayList;

public abstract class Server implements Runnable {
    private boolean running;
    private long serverStartTime;
    private Scheduler scheduler;
    private final Observable observers = new Observable();
    private MyProcess currentlyExecuting;
    private final ArrayList<MyProcess> finishedList;


    public Server(Scheduler scheduler) {
        this();
        this.scheduler = scheduler;
        this.serverStartTime = -1;
        System.out.println(scheduler.toString());
    }

    protected Server() {
        running = false;
        finishedList = new ArrayList<>();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    public Observable getObservers() {
        return observers;
    }


    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
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
        if (!finishedList.contains(p))
            finishedList.add(p);
        observers.update();
    }

    public long getServerStartTime() {
        return serverStartTime;
    }

    public void setServerStartTime(long serverStartTime) {
        this.serverStartTime = serverStartTime;
    }

    public double calcAvgWaitingTime() {
        double avgWaitingTime = 0;
        for (MyProcess p : finishedList) {
            avgWaitingTime += p.getWaitingTime();
        }
        if (finishedList.size() > 0)
            return avgWaitingTime / finishedList.size();
        return 0;
    }

    public double calcTurnAroundTime() {
        double avgTurnAroundTime = 0;
        for (MyProcess p : finishedList) {
            avgTurnAroundTime += p.getTurnAround();
        }
        if (finishedList.size() > 0)
            return avgTurnAroundTime / finishedList.size();
        return 0;
    }

    public void start() {
        System.out.println("server starting");
        setServerStartTime(System.currentTimeMillis());
        System.out.println("server start Time: " + this.getServerStartTime());
        this.setRunning(true);
    }

    public void shutdown() {
        double wt = calcAvgWaitingTime();
        double tat = calcTurnAroundTime();
        Platform.runLater(() -> {
            ShowReport.getInstance().setWaitingTime(wt);
            ShowReport.getInstance().setTurnAround(tat);
        });
        this.finishedList.clear();
        System.out.println("server shutdown");
        this.setServerStartTime(-1);
        this.setRunning(false);
    }

    public abstract void serve();
    public void delete(){
        while (!scheduler.isEmpty()){
            scheduler.pop();
        }
    }
    @Override
    public void run() {
    }
}
