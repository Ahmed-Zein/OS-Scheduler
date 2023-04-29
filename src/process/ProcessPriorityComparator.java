package process;

import java.util.Comparator;

public class ProcessPriorityComparator implements Comparator<MyProcess> {
    @Override
    public int compare(MyProcess o1, MyProcess o2) {
        if (o1.getPriority() > o2.getPriority()) return 1;
        else if (o1.getPriority() < o2.getPriority()) return -1;
        return 0;
    }
}
