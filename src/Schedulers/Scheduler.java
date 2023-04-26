package Schedulers;

import process.MyProcess;

public interface Scheduler {
    MyProcess pop();

    boolean isEmpty();

    void addProcess(MyProcess p);

    MyProcess peek();

    int size();
}
