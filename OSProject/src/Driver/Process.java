package Driver;

public class Process {
	private String name;
	private int priority;
	private int arrivalTime;
	private int finishTime;
	private int cpuBurst;
	private int turnArroundTime;
	private int waitingTime;
	private int reminderCpuBurst;

	public Process(String name, int priority, int arrivalTime, int cpuBurst) {
		super();
		this.name = name;
		this.priority = priority;
		this.arrivalTime = arrivalTime;
		this.cpuBurst = this.setReminderCpuBurst(cpuBurst);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public int getCpuBurst() {
		return cpuBurst;
	}

	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}

	public int getTurnArroundTime() {
		return turnArroundTime;
	}

	public void setTurnArroundTime(int turnArroundTime) {
		this.turnArroundTime = turnArroundTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	@Override
	public String toString() {
		return this.name + " , start " + this.arrivalTime + " , burst " + this.reminderCpuBurst;
	}

	public int getReminderCpuBurst() {
		return reminderCpuBurst;
	}

	public int setReminderCpuBurst(int reminderCpuBurst) {
		this.reminderCpuBurst = reminderCpuBurst;
		return reminderCpuBurst;
	}

}
