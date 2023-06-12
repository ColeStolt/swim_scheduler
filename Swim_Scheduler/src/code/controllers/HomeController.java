package code.controllers;

import code.datapersistance_dao.ClientChartData;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;

public class HomeController {

	@FXML LineChart<String, Integer> clientChart;
	@FXML LineChart<String, Double> moneyChart;
	
	final NumberAxis xAxisClient = new NumberAxis(0, 20000, 40000);
	final CategoryAxis yAxisClient = new CategoryAxis();
	private ClientChartData clientChartData = ClientChartData.getInstance();

	
	public void initialize() {
		clientChartSetup();
		moneyChartSetup();
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
	
	private void moneyChartSetup() {
		// chart setup
		XYChart.Series<String, Double> series = new Series<String, Double>();
		series.setName("Money Made");

		// Iterate through tracked months and add them
		if(clientChartData.getMonthlyEntries().size() > 12){
			for(int i = clientChartData.getMonthlyEntries().size() - 12; i < clientChartData.getMonthlyEntries().size(); i++) {
				series.getData().add(new XYChart.Data<String, Double>(clientChartData.getMonthlyEntries().get(i).getMonth().toString(), clientChartData.getMonthlyEntries().get(i).getMoney()));
			}
			// If less then 12 months present then use first 12
		} else {
			for(int i = 0; i < clientChartData.getMonthlyEntries().size(); i++) {
				series.getData().add(new XYChart.Data<String, Double>(clientChartData.getMonthlyEntries().get(i).getMonth().toString(), clientChartData.getMonthlyEntries().get(i).getMoney()));
			}
		}
		
		// Chart coloring
		moneyChart.getData().add(series);
		
		//Used to change legend color
		for(Node node : moneyChart.lookupAll("Label.chart-legend-item"))
		{
		    Label tempLabel = (Label)node;
		    if(tempLabel.getText().equals("Money Made"))
		    {
		        tempLabel.getGraphic().setStyle("-fx-bar-fill: firebrick;");
		    }           
		}
	}
	
}
