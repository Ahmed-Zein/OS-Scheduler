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
        int speed =GrantChart.instance().getSpeed();
        super.updateCurrentlyExecuting();
        MyProcess p = super.getCurrentlyExecuting();
        System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
        p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime());
        p.setTurnAround((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime() + p.getBurstTime());
        try {
            int burstTime = super.getCurrentlyExecuting().getRemainingTime();
            while (burstTime-- > 0) {
                Thread.sleep(speed);
                Platform.runLater(() -> {
                    GrantChart.instance().addRectangleManually();
                });
                super.getCurrentlyExecuting().setRemainingTime(burstTime);
                getObservers().update();
            }
            super.pop();
            System.out.println("FINISHED");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.start();
        this.serve();
        super.shutdown();
    }
}
