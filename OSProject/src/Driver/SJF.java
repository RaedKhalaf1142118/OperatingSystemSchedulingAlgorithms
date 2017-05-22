package Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF extends SchedulingAlgorithms {

	private ArrayList<Process> processes;
	private ProcessFactory processFactory;
	private ResultSet resultSet;
	private PriorityQueue<Process> arrivalTimeHeap;
	private PriorityQueue<Process> cpuBurtsHeap;
	private double tempAWT;
	private double tempATT;
	private ArrayList<ResultPair> results = new ArrayList<>();
	
	public SJF(ProcessFactory processFactory) {
		this.processFactory = processFactory;
		this.resultSet = new ResultSet();
		this.cpuBurtsHeap = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return o1.getCpuBurst() - o2.getCpuBurst();
			}
		});
		this.arrivalTimeHeap = new PriorityQueue<>(new Comparator<Process>() {
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

	private void initialize() {
		this.processes = this.processFactory.getRandomProcesses();
		this.arrivalTimeHeap.clear();
		this.arrivalTimeHeap.addAll(this.processes);
		this.cpuBurtsHeap.clear();
	}

	public ResultSet getResultSet() {
		int times = 100;
		double sumAWT = 0;
		double sumATT = 0;
		while (times <= 100000) {
			for (int i = 0; i < times; i++) {
				executeSJF();
				sumATT += this.tempATT;
				sumAWT += this.tempAWT;
				results.add(new ResultPair(this.tempATT, this.tempAWT, times));
				initialize();
			}
			this.resultSet.addData("ATT" + times, sumATT / times);
			this.resultSet.addData("AWT" + times, sumAWT / times);
			times *= 10;
			sumAWT = sumATT = 0;
		}
		return resultSet;
	}

	public void executeSJF() {
		int currentTime = 0;
		while (!this.arrivalTimeHeap.isEmpty() || !this.cpuBurtsHeap.isEmpty()) {

			if (this.cpuBurtsHeap.isEmpty()) {
				cpuBurtsHeap.add(this.arrivalTimeHeap.poll());
			}
			Process tempProcess = this.cpuBurtsHeap.poll();

			if (tempProcess.getArrivalTime() > currentTime) {
				currentTime = tempProcess.getArrivalTime();
			}

			currentTime += tempProcess.getCpuBurst();
			tempProcess.setFinishTime(currentTime);
			tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
			tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
			while (!this.arrivalTimeHeap.isEmpty() && this.arrivalTimeHeap.peek().getArrivalTime() < currentTime) {
				this.cpuBurtsHeap.add(this.arrivalTimeHeap.poll());
			}
		}
		this.tempAWT = getAWT(this.processes);
		this.tempATT = getATT(this.processes);
	}
}
