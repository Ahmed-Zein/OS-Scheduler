package Schedulers;

import process.MyProcess;
import process.ProcessPriorityComparator;

import java.util.PriorityQueue;

public class PriorityNonPreemptive implements Scheduler {
    PriorityQueue<MyProcess> pq;

    public PriorityNonPreemptive() {
        pq = new PriorityQueue<>(new ProcessPriorityComparator());
    }

    @Override
    public MyProcess serve() {
        return pq.poll();
    }

    @Override
    public boolean isEmpty() {
        return pq.isEmpty();
    }

    @Override
    public void addProcess(MyProcess p) {
        pq.add(p);
    }
}
