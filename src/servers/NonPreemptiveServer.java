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


    public void serve() {
        while (!super.getScheduler().isEmpty()) {
            execute();
        }
        return;
    }

    private void execute() {
        super.updateCurrentlyExecuting();
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
