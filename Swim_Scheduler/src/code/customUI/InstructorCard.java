package code.customUI;

import code.dataObjects.Instructor;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.scene.layout.BorderPane;

public class InstructorCard extends BorderPane{
	
	private Instructor instructorReference;
	// private 
	
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	
	
	public InstructorCard(Instructor instructorReference) {
		this.instructorReference = instructorReference;
	}
	
	private void setup() {
		
	}
}
