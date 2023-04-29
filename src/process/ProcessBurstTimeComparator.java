package process;

import java.util.Comparator;

public class ProcessBurstTimeComparator implements Comparator<MyProcess> {
    @Override
    public int compare(MyProcess o1, MyProcess o2) {
        if (o1.getRemainingTime() > o2.getRemainingTime()) return 1;
        else if (o1.getRemainingTime() < o2.getRemainingTime()) return -1;
        return 0;
    }
}
