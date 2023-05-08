package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;
import process.MyProcess;
import process.ProcessState;

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
            MyProcess p = super.getCurrentlyExecuting();
            if (p.getFinishTime() == 0)
                p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime());
            else
                p.setWaitingTime((System.currentTimeMillis() - p.getFinishTime()) / 1000);
            int counter = 0;
            int temp = 0;
            while (p.getRemainingTime() > 0) {
                int speed = GrantChart.instance().getSpeed();
                if (super.getCurrentlyExecuting().getPid().equals(super.getScheduler().peek().getPid())) {
                    try {
                        counter++;
                        Thread.sleep(10);
                        if (counter >= speed / 10) {
                            counter = 0;
                            temp++;
                            getCurrentlyExecuting().setRemainingTime(getCurrentlyExecuting().getRemainingTime() - 1);
                            getObservers().update();
                            Platform.runLater(() -> {
                                GrantChart.instance().addRectangleManually();
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("A higher process has come " + "last process remaining time " + p.getRemainingTime());
                    p.setFinishTime(System.currentTimeMillis());
                    p.setState(ProcessState.ready);

                    // reset inner while-loop
                    super.updateCurrentlyExecuting();
                    System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
                    p = super.getCurrentlyExecuting();
                    if (p.getFinishTime() == 0)
                        p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime());
                    else
                        p.setWaitingTime((System.currentTimeMillis() - p.getFinishTime()) / 1000);
                    p.setTurnAround(p.getWaitingTime() + temp);
                    counter = 0;
                    temp = 0;
                }
            }
            System.out.println("FINISHED");
            p.setTurnAround(p.getWaitingTime() + temp);
            super.pop();
            if (super.getScheduler().isEmpty())
                return;
        }
    }

    @Override
    public void run() {
        super.start();
        this.serve();
        super.shutdown();
    }

}
