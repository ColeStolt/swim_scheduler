package code.datapersistance_dao;

import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Cole Stoltz
 * This class is made with the purpose of allowing for reference to
 * the main screen of the application to be made to multiple controllers.
 * This will prevent the holding of data such as panes and other objects
 * not removed when scenes are switched.
 *
 */

public class MainScreenSingleton {

	private static final MainScreenSingleton instance = new MainScreenSingleton();
	
	private BorderPane screenReference;
	
	private MainScreenSingleton() {
		
	}
	
	
	public static MainScreenSingleton getInstance() {
		return instance;
	}
	
	
	public BorderPane getPane() {
		return screenReference;
	}
	
	public void setPane(BorderPane screenReference) {
		this.screenReference = screenReference;
	}
	
}
