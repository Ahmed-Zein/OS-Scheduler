package servers;

import Schedulers.Scheduler;
import process.MyProcess;

public class NonPreemptiveServer extends Server {

    public NonPreemptiveServer(Scheduler scheduler) {
        super.setScheduler(scheduler);
    }

    private NonPreemptiveServer() {
    }

    public void serve() {
         if (super.getScheduler().isEmpty()) return;
        MyProcess p = super.getScheduler().peek();
        try {
            Thread.sleep(p.getBurstTime() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FINISHED");
        super.getScheduler().pop();
        return;
    }

    @Override
    public void run() {
        this.serve();
    }
}
