package code.controllers;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import code.datapersistance_dao.ClientChartData;
import code.datapersistance_dao.ClientDataDB;
import javafx.fxml.FXML;
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
	private ClientChartData clientChartData = ClientChartData.getInstance();

	
	public void initialize() {
		clientChartSetup();
		moneyChartSetup();
	}
	
	
	private void moneyChartSetup() {

	}
	
	// Should probably be done in ClientDB
	private void clientChartSetup() {
		// chart setup
		XYChart.Series<String, Integer> series = new Series<String, Integer>();
		series.setName("Clients Per Month");

		// Iterate through tracked months and add them
		if(clientChartData.getMonthlyEntries().size() > 12){
			for(int i = clientChartData.getMonthlyEntries().size() - 12; i < clientChartData.getMonthlyEntries().size(); i++) {
				series.getData().add(new XYChart.Data<String, Integer>(clientChartData.getMonthlyEntries().get(i).getMonth().toString(), clientChartData.getMonthlyEntries().get(i).getClients()));
			}
			// If less then 12 months present then use first 12
		} else {
			for(int i = 0; i < clientChartData.getMonthlyEntries().size(); i++) {
				series.getData().add(new XYChart.Data<String, Integer>(clientChartData.getMonthlyEntries().get(i).getMonth().toString(), clientChartData.getMonthlyEntries().get(i).getClients()));
			}
		}
		
		clientChart.getData().add(series);
	}
	
	private void moneySetup() {
		
	}
	
}
