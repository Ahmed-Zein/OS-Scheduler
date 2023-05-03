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
        System.out.println("Executing: " + super.getCurrentlyExecuting().getPid());

        if (p.getFinishTime() == 0)
            p.setWaitingTime((System.currentTimeMillis() - super.getServerStartTime()) / 1000 - p.getArriveTime());
        else
            p.setWaitingTime((System.currentTimeMillis() - p.getFinishTime()) / 1000);

        p.setState(ProcessState.running);
        int quantum = GrantChart.instance().getSpeed();; //quantum = 1 sec
        try {
            Thread.sleep(quantum);
            Platform.runLater(() -> {
                GrantChart.instance().addRectangleManually();
            });
            Thread.sleep(30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        p.setRemainingTime(p.getRemainingTime() - 1);
        p.setFinishTime(p.getArriveTime() + p.getBurstTime());
        p.setWaitingTime(p.getArriveTime());
        p.setTurnAround(p.getFinishTime() - p.getArriveTime());
        super.pop();

        if (p.getRemainingTime() != 0) {
            p.setState(ProcessState.ready);
            System.out.println(p.getWaitingTime());
            p.setTurnAround(p.getWaitingTime() + 1);
            p.setFinishTime(System.currentTimeMillis());
            sleep(30);
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
        super.start();
        this.serve();
        super.shutdown();
    }
}
