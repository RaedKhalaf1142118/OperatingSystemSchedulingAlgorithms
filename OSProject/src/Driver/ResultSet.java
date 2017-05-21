package Driver;

public class ResultSet {
	
	private double ATT100;
	private double ATT1000;
	private double ATT10000;
	private double ATT100000;

	private double AWT100;
	private double AWT1000;
	private double AWT10000;
	private double AWT100000;
	
	public void addData(String key, double value){
		switch (key) {
		case "ATT100":
			this.ATT100 = value;
			break;
		case "ATT1000":
			this.ATT1000 = value;
			break;
		case "ATT10000":
			this.ATT10000 = value;
			break;
		case "ATT100000":
			this.ATT100000 = value;
			break;
		case "AWT100":
			this.AWT100 = value;
			break;
		case "AWT1000":
			this.AWT1000 = value;
			break;
		case "AWT10000":
			this.AWT10000 = value;
			break;
		case "AWT100000":
			this.AWT100000 = value;
		}
	}
	
	public double getData(String key){
		switch (key) {
		case "ATT100":
			return ATT100;
		case "ATT1000":
			return ATT1000;
		case "ATT10000":
			return ATT10000;
		case "ATT100000":
			 return ATT100000;
		case "AWT100":
			return AWT100;
		case "AWT1000":
			return AWT1000;
		case "AWT10000":
			return AWT10000;
		case "AWT100000":
			return AWT100000;
		}
		return 0;
	}
}
