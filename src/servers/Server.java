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
        currentlyExecuting.setStartTime(Math.max(serverStartTime, currentlyExecuting.getArriveTime()));
        GrantChart.instance().changeColor(currentlyExecuting.getColor());
    }

    public MyProcess getCurrentlyExecuting() {
        return currentlyExecuting;
    }

    public void pop() {
        MyProcess p = scheduler.pop();
        p.setFinishTime(System.currentTimeMillis());
        observers.update();
    }

    public void push(MyProcess p) {
        scheduler.addProcess(p);
        if (!finishedList.contains(p))
            finishedList.add(p);
        observers.update();
    }

    public void pushFinished(MyProcess p) {
        finishedList.add(p);
    }

    public long getServerStartTime() {
        return serverStartTime;
    }

    public void setServerStartTime(long serverStartTime) {
        this.serverStartTime = serverStartTime;
    }

    public abstract void serve();

    public float calcAvgWaitingTime() {
//        todo try the log list (arriveTime, finishTime, burstTime)
//        long avgWaitingTime = 0;
//        for (MyProcess p : finishedList) {
//            long fTime = (p.getFinishTime() - serverStartTime) / 1000;
//            long sTime;
//            if (p.getStartTime() < serverStartTime) sTime = 0;
//            else sTime = (p.getStartTime() - serverStartTime) / 1000;
//            long bTime = p.getBurstTime();
//            System.out.println(sTime + "\t" + fTime + "\t" + bTime);
//            avgWaitingTime += fTime - (sTime + bTime);
//        }
//        if (finishedList.size() > 0)
//            return avgWaitingTime / finishedList.size();
        return 0;

    }

    public float calcTurnAroundTime() {
        //        todo try the log list (arriveTime, finishTime, burstTime)
//        long avgTurnAroundTime = 0;
//        for (MyProcess p : finishedList) {
//            long fTime = (p.getFinishTime() - serverStartTime) / 1000;
//            long sTime = (p.getStartTime() - serverStartTime) / 1000;
//            long bTime = p.getBurstTime();
//            avgTurnAroundTime += fTime - sTime;
//        }
//        if (finishedList.size() > 0)
//            return avgTurnAroundTime / finishedList.size();
        return 0;
    }

    @Override
    public void run() {
    }
}
