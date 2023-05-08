package Schedulers;

import process.MyProcess;
import process.ProcessPriorityComparator;

import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityScheduler implements Scheduler {
    private final PriorityQueue<MyProcess> pq;

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

    @Override
    public Queue<MyProcess> getProcesses() {
        return pq;
    }

    @Override
    public int size() {
        return pq.size();
    }
}
