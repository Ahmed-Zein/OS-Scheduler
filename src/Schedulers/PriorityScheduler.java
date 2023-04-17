package Schedulers;

import process.MyProcess;
import process.ProcessPriorityComparator;

import java.util.PriorityQueue;

public class PriorityScheduler implements Scheduler {
    PriorityQueue<MyProcess> pq;

    public PriorityScheduler() {
        pq = new PriorityQueue<>(new ProcessPriorityComparator());
    }

    @Override
    public MyProcess pop() {
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

    @Override
    public MyProcess peek() {
        return pq.peek();
    }
}