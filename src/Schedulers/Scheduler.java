package Schedulers;

import process.MyProcess;

import java.util.Queue;

public interface Scheduler {
    MyProcess pop();

    boolean isEmpty();

    void addProcess(MyProcess p);

    MyProcess peek();

    Queue<MyProcess> getProcesses();

    int size();
}
