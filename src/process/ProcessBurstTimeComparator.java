package process;

import java.util.Comparator;

public class ProcessBurstTimeComparator implements Comparator<MyProcess> {
    @Override
    public int compare(MyProcess o1, MyProcess o2) {
        return Integer.compare(o1.getRemainingTime(), o2.getRemainingTime());
    }
}
