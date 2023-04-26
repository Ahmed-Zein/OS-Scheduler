package process;

import javafx.scene.paint.Color;

import java.util.UUID;

public class MyProcess {
    private UUID pid;
    private long arriveTime;
    private int burstTime; //
    private int priority;   // 1 : 5
    private ProcessState state;
    private Color color;


    public MyProcess() {
        this.pid = UUID.randomUUID();
        this.arriveTime = System.currentTimeMillis();
        this.burstTime = 1;
        this.priority = 5;
        this.state = ProcessState.ready;
        this.color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public UUID getPid() {
        return pid;
    }

    public void setPid(UUID pid) {
        this.pid = pid;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Color getColor() {
        return color;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
