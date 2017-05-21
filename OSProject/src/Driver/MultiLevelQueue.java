package Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MultiLevelQueue extends SchedulingAlgorithms {
	private ArrayList<Process> processes;
	private ProcessFactory processFactory;
	private ResultSet resultSet;
	private double tempAWT;
	private double tempATT;
	private PriorityQueue<Process> level1;
	private PriorityQueue<Process> level2;
	private PriorityQueue<Process> level3;
	private final int Q1 = 17;
	private final int Q2 = 25;
	private int currentTime = 0;

	public MultiLevelQueue(ProcessFactory processFactory) {
		this.processFactory = processFactory;
		this.resultSet = new ResultSet();
		Comparator<Process> comparator = new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		};
		level1 = new PriorityQueue<>(comparator);
		level2 = new PriorityQueue<>(comparator);
		level3 = new PriorityQueue<>(comparator);
		initialize();
	}

	private void initialize() {
		this.processes = this.processFactory.getRandomProcesses();
		level1.clear();
		level2.clear();
		level3.clear();
		level1.addAll(processes);
		this.currentTime = 0;
	}

	public ResultSet getResultSet() {
		int times = 100;
		double sumAWT = 0;
		double sumATT = 0;
		while (times <= 100000) {
			for (int i = 0; i < times; i++) {
				executeTLFQ();
				sumATT += this.tempATT;
				sumAWT += this.tempAWT;
				this.resultSet.addData("ATT" + times, sumATT / times);
				this.resultSet.addData("AWT" + times, sumAWT / times);
				initialize();
			}
			times *= 10;
		}
		return resultSet;
	}

	private void executeTLFQ() {
		executeFirstQueue();
		executeSecondQueue();
		executeThriedQueue();
	}

	private void executeFirstQueue() {
		while (!this.level1.isEmpty()) {
			Process tempProcess = this.level1.poll();
			if (tempProcess.getArrivalTime() > this.currentTime) {
				this.currentTime = tempProcess.getArrivalTime();
			}
			if (tempProcess.getCpuBurst() <= this.Q1) {
				currentTime += tempProcess.getReminderCpuBurst();
				tempProcess.setCpuBurst(0);
				tempProcess.setFinishTime(currentTime);
				tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
				tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
			} else {
				currentTime += Q1;
				tempProcess.setReminderCpuBurst(tempProcess.getReminderCpuBurst() - this.Q1);
				this.level2.add(tempProcess);
			}

		}
	}

	private void executeSecondQueue() {
		while (!this.level2.isEmpty()) {
			Process tempProcess = this.level2.poll();
			if (tempProcess.getReminderCpuBurst() <= this.Q2) {
				currentTime += tempProcess.getReminderCpuBurst();
				tempProcess.setCpuBurst(0);
				tempProcess.setFinishTime(currentTime);
				tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
				tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
			} else {
				currentTime += Q2;
				tempProcess.setReminderCpuBurst(tempProcess.getReminderCpuBurst() - this.Q2);
				this.level3.add(tempProcess);
			}
		}
	}

	private void executeThriedQueue() {
		while (!this.level3.isEmpty()) {
			Process tempProcess = this.level3.poll();
			currentTime += tempProcess.getCpuBurst();
			tempProcess.setFinishTime(currentTime);
			tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
			tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
		}
		this.tempAWT = getAWT(this.processes);
		this.tempATT = getATT(this.processes);
	}
}
