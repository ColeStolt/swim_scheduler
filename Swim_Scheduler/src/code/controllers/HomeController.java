package code.controllers;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class HomeController {

	@FXML LineChart<String, Integer> clientChart;
	@FXML LineChart<String, Integer> moneyChart;
	
	final NumberAxis xAxisClient = new NumberAxis(0, 20000, 40000);
	final CategoryAxis yAxisClient = new CategoryAxis();
	
	public void initialize() {
		clientChartSetup();
		moneyChartSetup();
	}
	
	private void clientChartSetup() {
		XYChart.Series<String, Integer> series = new Series<String, Integer>();
		series.setName("Clients Per Month");
		
		
		series.getData().add(new XYChart.Data<String, Integer>("Jan", 23));
		series.getData().add(new XYChart.Data<String, Integer>("Feb", 22));
		series.getData().add(new XYChart.Data<String, Integer>("Mar", 15));
		series.getData().add(new XYChart.Data<String, Integer>("Apr", 31));
		series.getData().add(new XYChart.Data<String, Integer>("May", 18));
		series.getData().add(new XYChart.Data<String, Integer>("Jun", 27));
		
		clientChart.getData().add(series);
	}
	
	private void moneyChartSetup() {
		XYChart.Series<String, Integer> series = new Series<String, Integer>();
		series.setName("Money Made Per Month");
		
		
		series.getData().add(new XYChart.Data<String, Integer>("Jan", 2342));
		series.getData().add(new XYChart.Data<String, Integer>("Feb", 1234));
		series.getData().add(new XYChart.Data<String, Integer>("Mar", 5423));
		series.getData().add(new XYChart.Data<String, Integer>("Apr", 10324));
		series.getData().add(new XYChart.Data<String, Integer>("May", 12452));
		series.getData().add(new XYChart.Data<String, Integer>("Jun", 20312));
		
		moneyChart.getData().add(series);
	}
	
}
