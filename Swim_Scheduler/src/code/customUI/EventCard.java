package code.customUI;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Cole Stoltz
 *
 * Purpose: Small UI card to display basic information about an event on the home screen.
 *
 */

public class EventCard extends BorderPane{
	
	private String eventSummary;
	private String eventLocation;
	private String eventDescription;
	
	// Panes
	private HBox borderPaneTop;
	
	public EventCard(String eventSummary, String eventLocation, String eventDescription) {
		this.eventSummary = eventSummary;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
	}

	private void UISetup() {
		
	}
	
	// Getters / Setters
	
	public String getEventSummary() {
		return eventSummary;
	}

	public void setEventSummary(String eventSummary) {
		this.eventSummary = eventSummary;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	
	
}
