package process;

import javafx.scene.paint.Color;

import java.util.UUID;

public class MyProcess {
    //Turnaround time = Exit time - Arrival time
    //Waiting time = Turnaround time - Burst time
    private final UUID pid;
    private final long creationTime;
    private long waitingTime = 0;
    private long turnAround = 0;
    private long arriveTime;
    private long finishTime;
    private int remainingTime; //
    private int burstTime; //
    private int priority;   // 1 : 5
    private int numberOfInterrupts;
    private ProcessState state;
    private final Color color;


    public MyProcess() {
        this.pid = UUID.randomUUID();
        this.creationTime = System.currentTimeMillis();
        this.priority = 5;
        this.finishTime = 0;
        this.arriveTime = -1;
        this.numberOfInterrupts = 0; 
        this.state = ProcessState.ready;
        this.color = Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }

    public UUID getPid() {
        return pid;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime += waitingTime;
    }

    public long getTurnAround() {
        return turnAround;
    }

    public int getNumberOfInterrupts() {
		return numberOfInterrupts;
	}

	public void setNumberOfInterrupts(int numberOfInterrupts) {
		this.numberOfInterrupts = numberOfInterrupts;
	}

	public void setTurnAround(long turnAround) {
        this.turnAround = turnAround;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
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
