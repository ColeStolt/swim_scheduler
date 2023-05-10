package code.controllers;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class HomeController {

	@FXML LineChart clientChart;
	
	final NumberAxis xAxisClient = new NumberAxis(0, 20000, 40000);
	final CategoryAxis yAxisClient = new CategoryAxis();
	
	public void initialize() {
		clientChartSetup();
	}
	
	private void clientChartSetup() {
		XYChart.Series series = new XYChart.Series();
		series.setName("Clients Per Month");
		
		
		series.getData().add(new XYChart.Data("Jan", 23));
		series.getData().add(new XYChart.Data("Feb", 22));
		series.getData().add(new XYChart.Data("Mar", 15));
		series.getData().add(new XYChart.Data("Apr", 31));
		series.getData().add(new XYChart.Data("May", 18));
		series.getData().add(new XYChart.Data("Jun", 27));
		
		clientChart.getData().add(series);
	}
	
	private void moneyChartSetup() {
		
	}
	
}
