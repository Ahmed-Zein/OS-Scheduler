package process;

import java.util.Comparator;

public class ProcessPriorityComparator implements Comparator<MyProcess> {
    @Override
    public int compare(MyProcess o1, MyProcess o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }
}
