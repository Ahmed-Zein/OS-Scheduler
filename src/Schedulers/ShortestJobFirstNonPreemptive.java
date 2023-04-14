package Schedulers;

import process.MyProcess;
import process.ProcessBurstTimeComparator;
import process.ProcessPriorityComparator;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestJobFirstNonPreemptive implements Scheduler {
    PriorityQueue<MyProcess> pq;

    public ShortestJobFirstNonPreemptive() {
        pq = new PriorityQueue<>(new ProcessBurstTimeComparator());
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
