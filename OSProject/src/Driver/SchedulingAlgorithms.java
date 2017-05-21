package Driver;

import java.util.ArrayList;

public abstract class SchedulingAlgorithms {

	public double getAWT(ArrayList<Process> processes) {
		double sum = 0;
		double size = processes.size();

		for (int i = 0; i < size; i++) {
			sum += processes.get(i).getWaitingTime();
		}

		return sum / size;
	}

	public double getATT(ArrayList<Process> processes) {
		double sum = 0;
		double size = processes.size();

		for (int i = 0; i < size; i++) {
			sum += processes.get(i).getTurnArroundTime();
		}

		return sum / size;
	}
}
