package code.services;

import code.dataObjects.Client;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;


/**
 * 
 * @author Cole Stoltz
 * Small class that will function as the client cards.
 * A client card will function as a small flash card
 * that displays basic info about a client.
 *
 */

public class ClientCard extends BorderPane{
	
	// Reference to client object
	private Client clientReference;
	
	public ClientCard(Client clientReference) {
		this.clientReference = clientReference;
		setup();
	}
	
	private void setup() {
		

		// BorderPane setup (This)
		this.setId("mainCardPane");
		this.setMaxWidth(Double.MAX_VALUE);
		this.setTop(topLabelBox());
		this.setCenter(centerLabelContainer());
		
	}
	
	/**
	 * 
	 * @return HBox
	 * Returns a custom HBox component. Used to build
	 * the larger client information card.
	 */
	private HBox topLabelBox() {
		HBox topLabels = new HBox();
		
		BorderPane namePane = new BorderPane();
		BorderPane totalAmountPane = new BorderPane();
		
		// Labels ---
		Label nameLabel = new Label(clientReference.getClientName());
		Label totalPriceLabel = new Label("Total: $" + Float.toString(clientReference.getTotalAmountForLessons()));
		
		// Grow properties ---
		HBox.setHgrow(namePane, Priority.ALWAYS);
		HBox.setHgrow(totalAmountPane, Priority.ALWAYS);
		
		// Pane properties ---
		namePane.setCenter(nameLabel);
		totalAmountPane.setCenter(totalPriceLabel);
		
		// Settings ----
		topLabels.setId("topLabelsContainer");
		topLabels.setAlignment(Pos.CENTER);
		topLabels.setMinWidth(0);
		
		// Adding nodes ----
		topLabels.getChildren().add(namePane);
		topLabels.getChildren().add(totalAmountPane);
		
		return topLabels;
	}
	
	private GridPane centerLabelContainer() {
		// Panes ---
		// This a lot of panes but it makes it 
		// all pwetty and centered.
		GridPane centerLabels = new GridPane();
		BorderPane addressPane = new BorderPane();
		BorderPane phoneNumberPane = new BorderPane();
		BorderPane totalKidsPane = new BorderPane();
		BorderPane instructorPane = new BorderPane();
		BorderPane numberOfLessonsPane = new BorderPane();
		BorderPane amountPerLessonPane = new BorderPane();
		
		// Labels ---
		Label addressLabel = new Label("Address Of Lessons: " + clientReference.getAddressOfLessons());
		Label phoneNumberLabel = new Label("Phone Number : " + clientReference.getPhoneNumber());
		Label totalKidsLabel = new Label("Kids: " + clientReference.getNumberOfKids());
		Label instructorLabel = new Label("Instructor: " + clientReference.getInstructor());
		Label numberOfLessonsLabel = new Label("Lessons Per Kid: " + clientReference.getNumberOfLessons());
		Label amountPerLessonLabel = new Label("Amount Per Lesson: " + clientReference.getTotalAmountForLessons());
		
		// Pane settings ---
		GridPane.setHgrow(addressPane, Priority.ALWAYS);
		GridPane.setHgrow(phoneNumberPane, Priority.ALWAYS);
		GridPane.setHgrow(totalKidsPane, Priority.ALWAYS);
		GridPane.setHgrow(instructorPane, Priority.ALWAYS);
		GridPane.setHgrow(numberOfLessonsPane, Priority.ALWAYS);
		GridPane.setHgrow(amountPerLessonPane, Priority.ALWAYS);
		addressPane.setCenter(addressLabel);
		phoneNumberPane.setCenter(phoneNumberLabel);
		totalKidsPane.setCenter(totalKidsLabel);
		instructorPane.setCenter(instructorLabel);
		numberOfLessonsPane.setCenter(numberOfLessonsLabel);
		amountPerLessonPane.setCenter(amountPerLessonLabel);
		
		// Constraints ---
		GridPane.setConstraints(instructorPane, 1, 0);
		GridPane.setConstraints(phoneNumberPane, 0, 0);
		GridPane.setConstraints(totalKidsPane, 1, 1);
		GridPane.setConstraints(addressPane, 0, 1);
		GridPane.setConstraints(numberOfLessonsPane, 1, 2);
		GridPane.setConstraints(amountPerLessonPane, 0, 2);
		
		// Adding the nodes --
		centerLabels.getChildren().add(addressPane);
		centerLabels.getChildren().add(phoneNumberPane);
		centerLabels.getChildren().add(totalKidsPane);
		centerLabels.getChildren().add(instructorPane);
		centerLabels.getChildren().add(numberOfLessonsPane);
		centerLabels.getChildren().add(amountPerLessonPane);
		
		return centerLabels;
	}
	
	// Getters / Setters
	public Client getClientReference() {
		return clientReference;
	}
	
	public void setClientReference(Client clientReference) {
		this.clientReference = clientReference;
	}
	
}
