package Driver;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class RoundRobin extends SchedulingAlgorithms {
	private ArrayList<Process> processes;
	private ProcessFactory processFactory;
	private ResultSet resultSet;
	private PriorityQueue<Process> arrivalTimeHeap;
	private double tempAWT;
	private double tempATT;
	private final int Q = 4;
	private LinkedList<Process> processList;

	public RoundRobin(ProcessFactory processFactory) {
		this.processFactory = processFactory;
		this.resultSet = new ResultSet();

		this.arrivalTimeHeap = new PriorityQueue<>(new Comparator<Process>() {

			@Override
			public int compare(Process o1, Process o2) {
				return o1.getArrivalTime() - o2.getArrivalTime();
			}
		});
		this.processList = new LinkedList<>();
		initialize();
	}

	private void initialize() {
		this.processes = this.processFactory.getRandomProcesses();
		this.arrivalTimeHeap.clear();
		this.arrivalTimeHeap.addAll(this.processes);
		translateHeapToList();
	}

	private void translateHeapToList() {
		while (!this.arrivalTimeHeap.isEmpty()) {
			this.processList.add(this.arrivalTimeHeap.poll());
		}
	}

	public ResultSet getResultSet() {
		int times = 100;
		double sumAWT = 0;
		double sumATT = 0;
		while (times <= 100000) {
			for (int i = 0; i < times; i++) {
				executeRR();
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

	private void executeRR() {
		int currentTime = processList.get(0).getArrivalTime();
		int i = 0;
		boolean queueIsEmpty = true;
		
		while (!processList.isEmpty()) {
			if (processList.get(i).getArrivalTime() <= currentTime) {
				if (processList.get(i).getReminderCpuBurst() <= Q) {
					currentTime += processList.get(i).getReminderCpuBurst();
					processList.get(i).setReminderCpuBurst(0);
					processList.get(i).setFinishTime(currentTime);
					processList.get(i).setTurnArroundTime(
							processList.get(i).getFinishTime() - processList.get(i).getArrivalTime());
					processList.get(i)
							.setWaitingTime(processList.get(i).getTurnArroundTime() - processList.get(i).getCpuBurst());
					processList.remove(i);
					i--;
				} else {
					currentTime += Q;
					processList.get(i).setReminderCpuBurst(processList.get(i).getReminderCpuBurst() - Q);
				}
				i++;
				queueIsEmpty = false;
			} else {
				if(queueIsEmpty){
					currentTime = getMinArrivalTime(processList);
				}
				i = 0;
				queueIsEmpty = true;
			}
			if(i == processList.size())
				i=0;
		}
		this.tempAWT = getAWT(this.processes);
		this.tempATT = getATT(this.processes);
	}

	private int getMinArrivalTime(LinkedList<Process> processList2) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < processList2.size(); i++) {
			if(processList2.get(i).getArrivalTime() < min){
				min = processList2.get(i).getArrivalTime();
			}
		}
		return min;
	}

}
