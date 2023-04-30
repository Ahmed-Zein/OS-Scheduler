package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;
import process.MyProcess;

public class NonPreemptiveServer extends Server {

    public NonPreemptiveServer(Scheduler scheduler) {
        super(scheduler);
    }

    private NonPreemptiveServer() {
        super();
    }

    @Override
    public void serve() {
        while (!super.getScheduler().isEmpty()) {
            execute();
        }
        return;
    }

    private void execute() {
        super.updateCurrentlyExecuting();
        MyProcess p = super.getCurrentlyExecuting();
        System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
//        if (p.getCreationTime() < super.getServerStartTime()) {
//            p.setArriveTime(0);
//        } else {
//            p.setArriveTime((p.getCreationTime() - super.getServerStartTime()) / 1000);
//        }
        p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime())/1000 - p.getArriveTime());
        p.setTurnAround((System.currentTimeMillis() - super.getServerStartTime())/1000 - p.getArriveTime() + p.getBurstTime());

        try {
            int burstTime = super.getCurrentlyExecuting().getRemainingTime();
            while (burstTime-- > 0) {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    GrantChart.instance().addRectangleManually();
                });
                super.getCurrentlyExecuting().setRemainingTime(burstTime);
                getObservers().update();
            }
            
//            p.setFinishTime(System.currentTimeMillis());
////            long offset;
////            
////            if(p.getCreationTime() < super.getServerStartTime()) {
////            	offset = super.getServerStartTime() - p.getCreationTime() ;
////            }
////            else {
////            	offset = 0;
////            }
////            
//////            long turnAroundTime = p.getFinishTime() - p.getArriveTime() - offset;
//////            long waitingTime=  p.getFinishTime() - p.getArriveTime() - p.getBurstTime() * 1000 - offset;
////////            p.setFinishTime(p.getFinishTime() - offset);
//////
//////            System.out.println("Turnaround time: " + turnAroundTime);
//////            System.out.println("Waiting time: " + waitingTime);
//////            
//////            System.out.println("Creation time: " + p.getCreationTime());
//////            System.out.println("Start time: " + super.getServerStartTime());
//////            System.out.println("Arrival time: " + p.getArriveTime());
//////            System.out.println("Finish time: " + p.getFinishTime());



            super.pop();
            System.out.println("FINISHED");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("server starting");
        setServerStartTime(System.currentTimeMillis());
        System.out.println("server start Time" + super.getServerStartTime());

        super.setRunning(true);
        this.serve();
        System.out.println(calcAvgWaitingTime());
        System.out.println(calcTurnAroundTime());
        super.finishedList.clear();
        System.out.println("server shutdown");
        super.setServerStartTime(-1);
        super.setRunning(false);
    }
}
