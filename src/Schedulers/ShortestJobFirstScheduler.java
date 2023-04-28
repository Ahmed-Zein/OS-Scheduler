package Schedulers;

import process.MyProcess;
import process.ProcessBurstTimeComparator;

import java.util.PriorityQueue;

public class ShortestJobFirstScheduler implements Scheduler {
    PriorityQueue<MyProcess> pq;

    public ShortestJobFirstScheduler() {
        pq = new PriorityQueue<>(new ProcessBurstTimeComparator());
    }

    @Override
    public MyProcess pop() {
        MyProcess p = pq.poll();
        // waitingTime : 1000 * p.getBurstTime() ==> (100 * p.getBurstTime()) *10
        int waitingQuantum = 100 * p.getBurstTime();
        while (waitingQuantum-- >= 0) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return p;
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
    public PriorityQueue<MyProcess> getProcesses() {
        return pq;
    }

    @Override
    public int size() {
        return pq.size();
    }
}
