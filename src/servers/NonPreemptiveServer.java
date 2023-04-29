package servers;

import Schedulers.RoundRobin;
import Schedulers.Scheduler;
import UI.GrantChart;
import UI.SayMyName;
import javafx.application.Platform;
import process.MyProcess;
import process.ProcessState;

public class NonPreemptiveServer extends Server {

    public NonPreemptiveServer(Scheduler scheduler) {
        super(scheduler);
    }

    private NonPreemptiveServer() {
        super();
    }


    public void serve() {
        while (!super.getScheduler().isEmpty()) {
            super.updateCurrentlyExecuting();
            System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
            if (super.getScheduler() instanceof RoundRobin)
                roundRobinExecute();
            else execute();
        }
        return;
    }

    private void roundRobinExecute() {
        MyProcess p = super.getCurrentlyExecuting();
        p.setState(ProcessState.running);
        int quantum = 1000; //quantum = 1 sec
        try {
            Thread.sleep(quantum);
            Platform.runLater(() -> {
            	GrantChart.instance().addRectangleManually();
            });
            Thread.sleep(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setBurstTime(p.getBurstTime() - 1);
        super.pop();
        if (p.getBurstTime() != 0) {
            p.setState(ProcessState.ready);
            super.push(p);
        }
        else {
            p.setCompletationTime(System.currentTimeMillis());
            long offset;
            
            if(p.getArriveTime() < SayMyName.getStartTime()) {
            	offset = SayMyName.getStartTime() - p.getArriveTime() ;
            }else {
            	offset = 0;
            }
            
            long turnAroundTime = p.getCompletationTime() - p.getArriveTime() - offset;
            long waitingTime=  p.getCompletationTime() - p.getArriveTime() - p.getOrignalBurstTime() * 1000 - offset;
            
            System.out.println("Turnaround time: " + turnAroundTime);
            System.out.println("Waiting time: " + waitingTime);
        }
    }

    private void execute() {
        System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
        try {
            int burstTime = super.getCurrentlyExecuting().getBurstTime();
            while (burstTime-- > 0) {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    GrantChart.instance().addRectangleManually();
                });
            }
            super.pop();
            System.out.println("FINISHED");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.serve();
    }
}
