package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;

import static java.lang.Math.floor;

public class PreemptiveServer extends Server {

    public PreemptiveServer(Scheduler scheduler) {
        super(scheduler);
    }

    private PreemptiveServer() {
        super();
    }

    @Override
    public void serve() {

        while (!super.getScheduler().isEmpty()) {
            super.updateCurrentlyExecuting();
            System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
            int waitingQuantum = 100 * super.getCurrentlyExecuting().getBurstTime();
            int counter = 0;

            while (waitingQuantum-- >= 0) {
                if (super.getCurrentlyExecuting().getPid().equals(super.getScheduler().peek().getPid()))
                    try {
                        counter++;
                        Thread.sleep(10);
                        if (counter == 100) {
                            counter = 0;
                            Platform.runLater(() -> {
                                GrantChart.instance().addRectangleManually();
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else {
                    System.out.println("A higher process has come " + "last process remaining time " + waitingQuantum / 100);
                    super.getCurrentlyExecuting().setBurstTime((int) floor(waitingQuantum / 100));
                    super.updateCurrentlyExecuting();
                    waitingQuantum = 100 * super.getCurrentlyExecuting().getBurstTime();
                    counter = 0;
                }
            }
            System.out.println("FINISHED");
            super.getScheduler().pop();
            if (super.getScheduler().isEmpty())
                return;
        }
        return;
    }

    @Override
    public void run() {
        System.out.println("server starting");
        this.serve();
        System.out.println("server shutdown");
    }

}
