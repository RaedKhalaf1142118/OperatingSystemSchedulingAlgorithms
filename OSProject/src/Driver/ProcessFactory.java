package Driver;

import java.util.ArrayList;

public class ProcessFactory {
	private ArrayList<Process> processes;
	private int numberOfProcesses = 8;
	
	public ProcessFactory() {
		this.processes = new ArrayList<>();
	}
	
	 
	
	public ArrayList<Process> getRandomProcesses(){
		this.processes.clear();
		for (int i = 0; i < this.numberOfProcesses; i++) {
			this.processes.add(generateRandomProcess(i));
		}
		return this.processes;
	}
	
	private Process generateRandomProcess(int i){
		String name = "P"+(i+1);
		int priority = (int) (Math.random()*20); // Priority ==> [0 - 20]
		int arrivalTime = (int) (Math.random()*4000);
		int cpuBurst = (int)(Math.random()*989)+10;
		return new Process(name, priority, arrivalTime, cpuBurst);
	}
}
