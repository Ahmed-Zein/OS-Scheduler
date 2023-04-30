package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
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
    ArrayList<MyProcess> finishedList;


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

    public abstract void serve();

    public double calcAvgWaitingTime() {
        //        todo try the log list (arriveTime, finishTime, burstTime)
        double avgWaitingTime = 0;
        for (MyProcess p : finishedList) {
            System.out.println(p.getArriveTime() + "\t" + p.getFinishTime() + "\t" + p.getBurstTime() + "\t" + p.getWaitingTime() + "\t" + p.getTurnAround());
            avgWaitingTime += p.getWaitingTime();

        }
        if (finishedList.size() > 0)
            return avgWaitingTime / finishedList.size();
        return 0;
    }

    public double calcTurnAroundTime() {
        //        todo try the log list (arriveTime, finishTime, burstTime)
        double avgTurnAroundTime = 0;
        for (MyProcess p : finishedList) {
            avgTurnAroundTime += p.getTurnAround();
        }
        if (finishedList.size() > 0)
            return avgTurnAroundTime / finishedList.size();
        return 0;
    }

    @Override
    public void run() {
    }
}
