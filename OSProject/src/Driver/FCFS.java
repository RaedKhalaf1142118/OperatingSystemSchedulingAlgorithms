package Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FCFS extends SchedulingAlgorithms {
	private ArrayList<Process> processes;
	private ProcessFactory processFactory;
	private ResultSet resultSet;
	private PriorityQueue<Process> priorityQueue;
	private double tempAWT;
	private double tempATT;
	private ArrayList<ResultPair> results = new ArrayList<>();

	public FCFS(ProcessFactory processFactory) {
		this.processFactory = processFactory;
		this.resultSet = new ResultSet();

		this.priorityQueue = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		});
		initialize();
	}

	public ArrayList<ResultPair> getResults() {
		return results;
	}
	
	public ResultSet getResultSet() {
		int times = 100;
		double sumAWT = 0;
		double sumATT = 0;
		while (times <= 100000) {
			for (int i = 0; i < times; i++) {
				executeFCFS();
				sumATT += this.tempATT;
				sumAWT += this.tempAWT;
				this.results.add(new ResultPair(this.tempATT, this.tempAWT, times));
				initialize();
			}
			this.resultSet.addData("ATT" + times, sumATT / times);
			this.resultSet.addData("AWT" + times, sumAWT / times);
			times *= 10;
			sumAWT = sumATT = 0;
		}
		return resultSet;
	}

	private void initialize() {
		this.processes = this.processFactory.getRandomProcesses();
		this.priorityQueue.clear();
		this.priorityQueue.addAll(this.processes);
	}

	private void executeFCFS() {
		int currentTime = 0;
		while (!this.priorityQueue.isEmpty()) {
			Process tempProcess = this.priorityQueue.poll();
			if (tempProcess.getArrivalTime() > currentTime) {
				currentTime = tempProcess.getArrivalTime();
			}
			currentTime += tempProcess.getCpuBurst();
			tempProcess.setFinishTime(currentTime);
			tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
			tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
		}
		this.tempAWT = getAWT(this.processes);
		this.tempATT = getATT(this.processes);
	}

	public double[] executeFCFS(ArrayList<Process> processes) {
		int currentTime = 0;
		PriorityQueue<Process> priorityQueue = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		});

		priorityQueue.addAll(processes);

		while (!priorityQueue.isEmpty()) {
			Process tempProcess = priorityQueue.poll();
			if (tempProcess.getArrivalTime() > currentTime) {
				currentTime = tempProcess.getArrivalTime();
			}
			currentTime += tempProcess.getCpuBurst();
			tempProcess.setFinishTime(currentTime);
			tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
			tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
		}

		return new double[] { getAWT(processes), getATT(processes) };
	}

}
