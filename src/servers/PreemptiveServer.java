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

            int waitingQuantum = 100 * super.getCurrentlyExecuting().getRemainingTime();
            int counter = 0;
            int temp = 0;
            while (waitingQuantum-- > 0) {
                if (super.getCurrentlyExecuting().getPid().equals(super.getScheduler().peek().getPid()))
                    try {
                        counter++;
                        Thread.sleep(10);
                        if (counter == 100) {
                            counter = 0;
                            temp++;
                            getCurrentlyExecuting().setRemainingTime(getCurrentlyExecuting().getRemainingTime() - 1);
                            getObservers().update();
                            Platform.runLater(() -> {
                                GrantChart.instance().addRectangleManually();
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else {
                    System.out.println("A higher process has come " + "last process remaining time " + waitingQuantum / 100);
                    p.setFinishTime(System.currentTimeMillis());
                    p.setState(ProcessState.ready);
                    super.updateCurrentlyExecuting();
                    System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());
                    p = super.getCurrentlyExecuting();
                    if (p.getFinishTime() == 0)
                        p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime());
                    else
                        p.setWaitingTime((System.currentTimeMillis() - p.getFinishTime()) / 1000);
                    p.setTurnAround(p.getWaitingTime() + temp);
                    waitingQuantum = 100 * super.getCurrentlyExecuting().getRemainingTime();
                    counter = 0;

                }
            }
            System.out.println("FINISHED");
            p.setTurnAround(p.getWaitingTime() + temp);
            super.pop();
            if (super.getScheduler().isEmpty())
                return;
        }
        return;
    }
//
//    @Override
//    public float calcTurnAroundTime() {
//        return 0;
//    }
//
//    @Override
//    public float calcAvgWaitingTime() {
//        return 0;
//    }

    @Override
    public void run() {
        System.out.println("server starting");
        setServerStartTime(System.currentTimeMillis());
        super.setRunning(true);
        this.serve();
        System.out.println(calcAvgWaitingTime());
        System.out.println(calcTurnAroundTime());
        System.out.println("server shutdown");
        super.finishedList.clear();
        super.setServerStartTime(-1);
        super.setRunning(false);
    }

}
