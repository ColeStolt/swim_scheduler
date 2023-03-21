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
		this.setBottom(cardBottomLabels());
		
	}
	
	/**
	 * 
	 * @return HBox
	 * Returns a custom HBox component. Used to build
	 * the larger client information card.
	 */
	private HBox topLabelBox() {
		HBox topLabels = new HBox();
		
		BorderPane phoneNumberPane = new BorderPane();
		BorderPane namePane = new BorderPane();
		BorderPane addressPane = new BorderPane();
		
		// Labels ---
		Label phoneNumberLabel = new Label(clientReference.getPhoneNumber());
		Label nameLabel = new Label(clientReference.getClientName());
		Label addressLabel = new Label(clientReference.getAddressOfLessons());
		
		// Label IDs
		phoneNumberLabel.setId("phoneAndAddress");
		addressLabel.setId("phoneAndAddress");
		
		// Grow properties ---
		HBox.setHgrow(phoneNumberPane, Priority.ALWAYS);
		HBox.setHgrow(namePane, Priority.ALWAYS);
		HBox.setHgrow(addressPane, Priority.ALWAYS);
		
		// Pane properties ---
		phoneNumberPane.setCenter(phoneNumberLabel);
		namePane.setCenter(nameLabel);
		addressPane.setCenter(addressLabel);
		
		// Settings ----
		topLabels.setId("topLabelsContainer");
		topLabels.setAlignment(Pos.CENTER);
		topLabels.setMinWidth(0);
		
		if(clientReference.isPaidInFull()) {
			topLabels.setStyle("-fx-background-color: #234736;");
		} else {
			topLabels.setStyle("-fx-background-color: #442727;");
		}
		
		// Adding nodes ----
		topLabels.getChildren().add(phoneNumberPane);
		topLabels.getChildren().add(namePane);
		topLabels.getChildren().add(addressPane);
		
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
		BorderPane totalAmountPane = new BorderPane();
		
		// Labels ---
		Label totalKidsLabelTop = new Label(Integer.toString(clientReference.getNumberOfKids()));
		Label totalKidsLabelBottom = new Label("KIDS");
		Label numberOfLessonsLabelTop = new Label(Short.toString(clientReference.getNumberOfLessons()));
		Label numberOfLessonsLabelBottom = new Label("LESSONS");
		Label amountPerLessonLabelTop = new Label("$ " + String.format("%.2f", clientReference.getAmountPerLesson()));
		Label amountPerLessonLabelBottom = new Label("$ PER LESSON");
		Label totalAmountLabelTop = new Label("$ " + String.format("%.2f", clientReference.getTotalAmountForLessons()));
		Label totalAmountLabelBottom = new Label("$ TOTAL");
	
		// Pane IDS
		totalKidsPane.setId("kidsPane");
		totalKidsLabelBottom.setId("bottomLabels");
		numberOfLessonsLabelBottom.setId("bottomLabels");
		amountPerLessonLabelBottom.setId("bottomLabels");
		totalAmountLabelBottom.setId("bottomLabels");
		BorderPane.setAlignment(totalKidsLabelBottom, Pos.CENTER);
		BorderPane.setAlignment(numberOfLessonsLabelBottom, Pos.CENTER);
		BorderPane.setAlignment(amountPerLessonLabelBottom, Pos.CENTER);
		BorderPane.setAlignment(totalAmountLabelBottom, Pos.CENTER);
		
		// Pane settings ---
		GridPane.setHgrow(addressPane, Priority.ALWAYS);
		GridPane.setHgrow(phoneNumberPane, Priority.ALWAYS);
		GridPane.setHgrow(totalKidsPane, Priority.ALWAYS);
		GridPane.setHgrow(instructorPane, Priority.ALWAYS);
		GridPane.setHgrow(numberOfLessonsPane, Priority.ALWAYS);
		GridPane.setHgrow(amountPerLessonPane, Priority.ALWAYS);
		GridPane.setHgrow(totalAmountPane, Priority.ALWAYS);
	
		totalKidsPane.setCenter(totalKidsLabelTop);
		totalKidsPane.setBottom(totalKidsLabelBottom);
		numberOfLessonsPane.setCenter(numberOfLessonsLabelTop);
		numberOfLessonsPane.setBottom(numberOfLessonsLabelBottom);
		amountPerLessonPane.setCenter(amountPerLessonLabelTop);
		amountPerLessonPane.setBottom(amountPerLessonLabelBottom);
		totalAmountPane.setCenter(totalAmountLabelTop);
		totalAmountPane.setBottom(totalAmountLabelBottom);

		
		// Constraints ---
		GridPane.setConstraints(totalKidsPane, 0, 0);
		GridPane.setConstraints(numberOfLessonsPane, 1, 0);
		GridPane.setConstraints(amountPerLessonPane, 2, 0);
		GridPane.setConstraints(totalAmountPane, 3, 0);
	
		
		// Adding the nodes --
		centerLabels.getChildren().add(totalKidsPane);
		centerLabels.getChildren().add(numberOfLessonsPane);
		centerLabels.getChildren().add(amountPerLessonPane);
		centerLabels.getChildren().add(totalAmountPane);
		
		return centerLabels;
	}
	
	private BorderPane cardBottomLabels() {
		BorderPane instructorPane = new BorderPane();
		
		// Labels
		Label instructorLabel = new Label(clientReference.getInstructor());
		
		// Pane settings
		instructorPane.setId("cardBottomLabels");
		instructorPane.setCenter(instructorLabel);
		
		return instructorPane;
	}
	
	// Getters / Setters
	public Client getClientReference() {
		return clientReference;
	}
	
	public void setClientReference(Client clientReference) {
		this.clientReference = clientReference;
	}
	
}
