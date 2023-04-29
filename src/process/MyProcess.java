package process;

import javafx.scene.paint.Color;

import java.util.UUID;

public class MyProcess {
    private UUID pid;
    private long arriveTime;
    private long completationTime;
    private final UUID pid;
    private final long arriveTime;
    private long startTime;
    private long finishTime;
    private int remainingTime; //

    private int burstTime; //
    private int orignalBurstTime;
    private int priority;   // 1 : 5
    private ProcessState state;
    private final Color color;


    public MyProcess() {
        this.pid = UUID.randomUUID();
        this.arriveTime = System.currentTimeMillis();
        this.remainingTime = 1;
        this.priority = 5;
        this.orignalBurstTime = 0;
        this.startTime = -1;
        this.finishTime = -1;
        this.state = ProcessState.ready;
        this.color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public long getCompletationTime() {
		return completationTime;
	}

	public void setCompletationTime(long completationTime) {
		this.completationTime = completationTime;
	}

	public int getOrignalBurstTime() {
		return orignalBurstTime;
	}

	public void setOrignalBurstTime(int orignalBurstTime) {
		this.orignalBurstTime = orignalBurstTime;
	}

	public UUID getPid() {
        return pid;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        if (this.startTime == -1)
            this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        if (this.finishTime == -1)
            this.finishTime = finishTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
    	if(this.orignalBurstTime == 0) {
    		this.orignalBurstTime = burstTime;
    	}
        this.remainingTime = burstTime;
        this.burstTime = burstTime;
    }

    public Color getColor() {
        return color;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
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

}
