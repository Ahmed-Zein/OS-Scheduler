package process;

import java.util.Comparator;

public class ProcessBurstTimeComparator implements Comparator<MyProcess> {
    @Override
    public int compare(MyProcess o1, MyProcess o2) {
        if (o1.getBurstTime() > o2.getBurstTime()) return 1;
        else if (o1.getBurstTime() < o2.getBurstTime()) return -1;
        return 0;
    }
}
