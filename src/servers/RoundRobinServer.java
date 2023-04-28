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
        p.setBurstTime(p.getBurstTime() - 1);
        super.pop();

        if (p.getBurstTime() != 0) {
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
        this.serve();
    }
}
