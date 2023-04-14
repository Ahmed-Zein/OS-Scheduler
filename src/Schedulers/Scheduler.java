package Schedulers;

import process.MyProcess;

public interface Scheduler {
    MyProcess serve();

    boolean isEmpty();

    void addProcess(MyProcess p);
}
