package Driver;

public class ResultPair {
	private double times;
	private double ATT;
	private double AWT;
	public ResultPair(double aTT, double aWT, double times) {
		super();
		ATT = aTT;
		AWT = aWT;
		this.setTimes(times);
	}
	public double getATT() {
		return ATT;
	}
	public void setATT(double aTT) {
		ATT = aTT;
	}
	public double getAWT() {
		return AWT;
	}
	public void setAWT(double aWT) {
		AWT = aWT;
	}
	public double getTimes() {
		return times;
	}
	public void setTimes(double times) {
		this.times = times;
	}
	
}
