package Driver;

import java.util.ArrayList;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class CustomAreaChart extends StackPane {
	public CustomAreaChart(ArrayList<ResultPair> resultPairs, double key, String type) {
		int size = 100;
		if(key == 1000){
			size = 300;
		}
		else if(key == 10000){
			size = 1000;
		}
		else if(key == 100000){
			size = 2000;
		}
		final NumberAxis xAxis = new NumberAxis(1, size , 1);
		final NumberAxis yAxis = new NumberAxis();
		final AreaChart<Number, Number> ac = new AreaChart<>(xAxis, yAxis);
		ac.setTitle(type);

		XYChart.Series seriesATT = new XYChart.Series();
		seriesATT.setName(type);


		int j = 0;
		for (int i = 0; j < size; i++) {
			if (resultPairs.get(i).getTimes() == key) {
				if(type == "AWT"){
					seriesATT.getData().add(new XYChart.Data<>(++j, resultPairs.get(i).getATT()));
				}else{
					seriesATT.getData().add(new XYChart.Data<>(++j, resultPairs.get(i).getAWT()));
				}
			}
		}
		
		ac.getData().addAll(seriesATT);
		getChildren().add(ac);
	}
}