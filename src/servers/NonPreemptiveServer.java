package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;

public class NonPreemptiveServer extends Server {

    public NonPreemptiveServer(Scheduler scheduler) {
        super.setScheduler(scheduler);
    }

    private NonPreemptiveServer() {
    }

    public void serve() {
        while (!super.getScheduler().isEmpty()) {
            super.updateCurrentlyExecuting();
            try {
                int burstTime = super.getCurrentlyExecuting().getBurstTime();
                while (burstTime-- > 0) {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        GrantChart.instance().addRectangleManually();
                    });
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("FINISHED");
            super.getScheduler().pop();
        }
        return;
    }

    @Override
    public void run() {
        this.serve();
    }
}
