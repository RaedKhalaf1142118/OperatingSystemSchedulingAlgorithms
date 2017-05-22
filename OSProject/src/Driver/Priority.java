package Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Priority extends SchedulingAlgorithms {

	private ArrayList<Process> processes;
	private ProcessFactory processFactory;
	private ResultSet resultSet;
	private PriorityQueue<Process> arrivalTimeHeap;
	private PriorityQueue<Process> proioritysHeap;
	private double tempAWT;
	private double tempATT;
	private ArrayList<ResultPair> results = new ArrayList<>();
	
	public Priority(ProcessFactory processFactory) {
		this.processFactory = processFactory;
		this.resultSet = new ResultSet();
		this.proioritysHeap = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				return (o2.getPriority() - Integer.MAX_VALUE) - (o1.getPriority() - Integer.MAX_VALUE);
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
		this.proioritysHeap.clear();
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

	private void executeSJF() {
		int currentTime = 0;

		while (!this.arrivalTimeHeap.isEmpty() || !this.proioritysHeap.isEmpty()) {
			if (this.proioritysHeap.isEmpty()) {
				proioritysHeap.add(this.arrivalTimeHeap.poll());
			}
			Process tempProcess = this.proioritysHeap.poll();

			if (tempProcess.getArrivalTime() > currentTime) {
				currentTime = tempProcess.getArrivalTime();
			}

			currentTime += tempProcess.getCpuBurst();
			tempProcess.setFinishTime(currentTime);
			tempProcess.setTurnArroundTime(tempProcess.getFinishTime() - tempProcess.getArrivalTime());
			tempProcess.setWaitingTime(tempProcess.getTurnArroundTime() - tempProcess.getCpuBurst());
			while (!this.arrivalTimeHeap.isEmpty() && this.arrivalTimeHeap.peek().getArrivalTime() < currentTime) {
				this.proioritysHeap.add(this.arrivalTimeHeap.poll());
			}
		}
		this.tempAWT = getAWT(this.processes);
		this.tempATT = getATT(this.processes);
	}
}
