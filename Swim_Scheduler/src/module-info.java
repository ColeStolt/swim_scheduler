module Swim_Scheduler {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires twilio;
	requires com.google.api.client;
	requires com.google.api.client.json.gson;
	requires com.google.api.client.auth;
	requires google.api.client;
	requires com.google.api.client.extensions.java6.auth;
	requires com.google.api.client.extensions.jetty.auth;
	requires com.google.api.services.calendar;
	requires google.http.client.jackson2;



	
	opens main to javafx.graphics, javafx.fxml;
	opens code.controllers to javafx.graphics, javafx.fxml;
}
