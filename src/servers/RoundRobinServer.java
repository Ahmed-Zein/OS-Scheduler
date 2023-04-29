package servers;

import Schedulers.Scheduler;
import UI.GrantChart;
import javafx.application.Platform;
import process.MyProcess;
import process.ProcessState;

public class RoundRobinServer extends Server {

    public RoundRobinServer(Scheduler scheduler) {
        super(scheduler);
    }

    private RoundRobinServer() {
        super();
    }


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
        MyProcess p = super.getCurrentlyExecuting();
        p.setState(ProcessState.running);
        int quantum = 1000; //quantum = 1 sec
        try {
            Thread.sleep(quantum);
            Platform.runLater(() -> {
                GrantChart.instance().addRectangleManually();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setRemainingTime(p.getRemainingTime() - 1);
        super.pop();

        if (p.getRemainingTime() != 0) {
            p.setState(ProcessState.ready);
            sleep(10);
            super.push(p);

        }
    }

    void sleep(int n) {
        try {
            Thread.sleep(n);
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
        System.out.println("server shutdown");
        super.setRunning(false);
    }
}
