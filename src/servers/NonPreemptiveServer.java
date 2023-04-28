package servers;

import Schedulers.RoundRobin;
import Schedulers.Scheduler;
import UI.GrantChart;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setBurstTime(p.getBurstTime() - 1);
        super.pop();
        if (p.getBurstTime() != 0) {
            p.setState(ProcessState.ready);
            super.push(p);
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
