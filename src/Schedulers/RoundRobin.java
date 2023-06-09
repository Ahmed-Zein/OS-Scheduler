package Schedulers;

import process.MyProcess;

import java.util.LinkedList;
import java.util.Queue;

public class RoundRobin implements Scheduler {
    Queue<MyProcess> q;

    public RoundRobin() {
        q = new LinkedList<>();
    }

    @Override
    public MyProcess pop() {
        return q.poll();
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public void addProcess(MyProcess p) {
        q.add(p);
    }

    @Override
    public MyProcess peek() {
        return q.peek();
    }

    @Override
    public Queue<MyProcess> getProcesses() {
        return q;
    }

    @Override
    public int size() {
        return q.size();
    }

}
