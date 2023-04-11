package code.customUI;

import java.io.IOException;

import code.dataObjects.Instructor;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class InstructorCard extends BorderPane{
	
	private Instructor instructorReference;
	// private 
	
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	
	
	public InstructorCard(Instructor instructorReference) {
		this.instructorReference = instructorReference;
		setup();
	}
	
	private void setup() {
		this.setId("instructorCard");
		this.setMaxWidth(Double.MAX_VALUE);
		this.setTop(topLabelBox());
		this.setCenter(centerLabelContainer());
	}
	
	private BorderPane topLabelBox() {
		
		// Panes
		BorderPane namePane = new BorderPane();
		
		// Labels
		Label nameLabel = new Label(instructorReference.getInstructorName());
		
		// Label properties
		nameLabel.setId("instructorName");
		
		// Pane properties
		namePane.setId("nameContainer");
		
		// Adding to pane
		namePane.getChildren().add(nameLabel);
		
		return namePane;
	}
	
	private GridPane centerLabelContainer() {
		
		// Pane
		GridPane centerLabels = new GridPane();
		
		// Borderpanes
		BorderPane emailPane = new BorderPane();
		BorderPane phonePane = new BorderPane();
		
		// Labels
		Label instructorEmailTop = new Label(instructorReference.getInstructorEmail());
		Label instructorEmailBottom = new Label("EMAIL");
		Label instructorPhoneNumberTop = new Label(instructorReference.getInstructorPhoneNumber());
		Label instructorPhoneNumberBottom = new Label("PHONE NUMBER");
		
		// Label settings
		instructorEmailTop.setId("topLabels");
		instructorEmailBottom.setId("bottomLabels");
		instructorPhoneNumberTop.setId("topLabels");
		instructorPhoneNumberBottom.setId("bottomLabels");
		
		// Pane settings
		emailPane.setCenter(instructorEmailTop);
		emailPane.setBottom(instructorEmailBottom);
		phonePane.setCenter(instructorPhoneNumberTop);
		phonePane.setBottom(instructorPhoneNumberBottom);
		
		// Grid constraints
		GridPane.setConstraints(emailPane, 0, 0);
		GridPane.setConstraints(phonePane, 1, 0);
		
		// Adding nodes
		centerLabels.getChildren().add(emailPane);
		centerLabels.getChildren().add(phonePane);
		
		return centerLabels;	
	}
	
}
