package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;

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

//    @Override
//    public float calcTurnAroundTime() {
//        return 0;
//    }
//
//    @Override
//    public float calcAvgWaitingTime() {
//        return 0;
//    }

    private void execute() {
        super.updateCurrentlyExecuting();
        System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
        try {
            int burstTime = super.getCurrentlyExecuting().getRemainingTime();
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
        System.out.println("server starting");
        setServerStartTime(System.currentTimeMillis());
        super.setRunning(true);
        this.serve();
        System.out.println(calcAvgWaitingTime());
        System.out.println(calcTurnAroundTime());
        super.finishedList.clear();
        System.out.println("server shutdown");
        super.setRunning(false);
    }
}
