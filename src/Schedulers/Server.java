package Schedulers;

public class Server implements Runnable {
    private Scheduler scheduler;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void serve() {

    }

    public Scheduler getScheduler() {return scheduler;
    }

    @Override
    public void run() {

    }
}
