package code.controllers;

import java.io.IOException;
import java.util.ArrayList;

import code.customUI.KidEntry;
import code.customUI.MaskedTextField;
import code.dataObjects.Client;
import code.dataObjects.Instructor;
import code.dataObjects.Kid;
import code.datapersistance_dao.ClientCardReferenceSingleton;
import code.datapersistance_dao.ClientChartData;
import code.datapersistance_dao.ClientDataDB;
import code.datapersistance_dao.InstructorDataDB;
import code.datapersistance_dao.MainScreenSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Cole Stoltz Probably the dirtiest way to switch scenes. It works but
 *         will keep holding onto to border panes which is a memory leak. Goes
 *         away when user switches scenes in the navigation though.
 *
 */

public class ClientEntryController {

	private ClientDataDB clientDB = ClientDataDB.getInstance();
	private MainScreenSingleton mainScreen = MainScreenSingleton.getInstance();
	private ClientCardReferenceSingleton clientTempReference = ClientCardReferenceSingleton.getInstance();
	private ClientChartData clientChartData = ClientChartData.getInstance();
	private InstructorDataDB instructorDB = InstructorDataDB.getInstance();

	@FXML
	private BorderPane mainScenePane;
	@FXML
	private Button cancelButton;
	@FXML
	private Button saveButton;
	@FXML
	private VBox clientInfoEntriesVBox;
	@FXML
	private VBox clientLessonInfoEntryVBox;
	@FXML
	private HBox buttonHBox;

	// Fields
	private TextField nameField;
	private MaskedTextField phoneNumberField;
	private TextField addressField;
	private ChoiceBox<Instructor> instructorChoice;
	private Spinner<Integer> numberOfLessonsField;
	private Spinner<Double> amountPerLessonField;
	private Spinner<Double> amountPaidField;
	private CheckBox contractRecieved;

	// Arrays for holding multiple instructor and kid fields
	private ArrayList<ChoiceBox<Instructor>> instructorChoiceBoxes;
	private ArrayList<KidEntry> kidInformationFields;

	// Spinner Data
	SpinnerValueFactory<Integer> lessonValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 12);
	SpinnerValueFactory<Double> lessonAmountValues = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 400.00,
			100.00, 5.00);
	SpinnerValueFactory<Double> amountPaidValues = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 50000.00,
			100.00, 10.00);

	// Panes
	private BorderPane titlePane;
	private HBox instructorsAdditionHBox;
	private HBox kidLabelAndButtons;
	private ScrollPane kidScroll;
	private VBox kidScrollVBox;

	// Labels
	private Label nameLabel;
	private Label phoneNumberLabel;
	private Label addressLabel;
	private Label kidsLabel;
	private Label instructorLabel;
	private Label numberOfLessonsLabel;
	private Label amountPerLessonLabel;
	private Label amountPaidLabel;
	private Label clientInfoTitle;
	private Label lessonInfoTitle;

	// Counter for max instructors
	private int instructorMax = 0;

	// Buttons
	private Button addInstructorButton;
	private Button removeInstructorButton;
	private Button addKidButton;
	private Button removeKidButton;

	public void initialize() {
		instructorChoiceBoxes = new ArrayList<ChoiceBox<Instructor>>(); // This first so it can be edited
		kidInformationFields = new ArrayList<KidEntry>();

		clientInfoSetup();
		lessonInfoSetup();
		if (clientTempReference.getClient() != null) {
			populateEntries();
			enableDeleteButton();
		} else {

		}
	}

	public void cancelClientAdd() {
		try {
			clientTempReference.setClientReference(null);
			mainScreen.getPane()
					.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
			// mainScenePane.setBottom(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveClient() {

		if (amountPerLessonField.getValue() == null) {
			Alert sceneAlert = new Alert(AlertType.INFORMATION);
			sceneAlert.setContentText("\"Amount per lesson\" can not be left blank.");
			sceneAlert.show();
			return;
		}

		if (phoneNumberField.getText().contains("_")) {
			Alert sceneAlert = new Alert(AlertType.INFORMATION);
			sceneAlert.setContentText("\"Phone Number\" can not be left blank or contain \"_\".");
			sceneAlert.show();
			return;
		}

		if (instructorDB.getInstructorDB().size() > 0) {
			// Populate an instructor arraylist to add to a client ----
			ArrayList<Instructor> tempList = new ArrayList<Instructor>();
			for (int i = 0; i < instructorChoiceBoxes.size(); i++) {
				tempList.add(instructorChoiceBoxes.get(i).getValue());
			}
			// -------

			if (clientTempReference.getClient() == null) {

				ArrayList<Kid> kids = new ArrayList<Kid>();
				
				// Create kids and add them to the client
				for(int i = 0; i < kidInformationFields.size(); i++) {
					Kid kid = new Kid(kidInformationFields.get(i).getNameInputField().getText());
					kids.add(kid);
				}
				
				// Create client
				Client newClient = new Client(nameField.getText(), addressField.getText(), phoneNumberField.getText(),
						(short) kidInformationFields.size(), tempList, numberOfLessonsField.getValue().shortValue(),
						amountPerLessonField.getValue().floatValue(), false);

				// Set kid arrayList in client to kid arraylist created above
				newClient.setKids(kids);
				
				// Set if client contract has been paid
				newClient.setContractRecieved(contractRecieved.isSelected());
				
				clientDB.getClientDB().add(newClient);
				clientDB.saveData();
				// Add client to chart data
				clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1)
						.setClients(clientChartData.getMonthlyEntries()
								.get(clientChartData.getMonthlyEntries().size() - 1).getClients() + 1);
				clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1).setMoney(
						clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1)
								.getMoney() + newClient.getTotalAmountForLessons());
				clientChartData.saveData();
			} else {
				findAndReplaceClient();
				clientTempReference.setClientReference(null);
				clientDB.saveData();
			}

			try {
				mainScreen.getPane()
						.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
				// mainScenePane.setBottom(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert sceneAlert = new Alert(AlertType.INFORMATION);
			sceneAlert.setContentText("Need to have at least one instructor assigned.");
			sceneAlert.show();
		}

	}

	public String parseData(String data) {

		return data;
	}

	public void populateChoiceBox(ChoiceBox<Instructor> choiceBox) {

		ObservableList<Instructor> items = FXCollections.observableArrayList(instructorDB.getInstructorDB());
		choiceBox.setItems(items);

	}

	public void clientInfoSetup() {

		// Declare fields
		nameField = new TextField();
		phoneNumberField = new MaskedTextField("(###) ###-####");
		addressField = new TextField();
		contractRecieved = new CheckBox("Contract Recieved?");

		// Declare panes
		titlePane = new BorderPane();
		kidLabelAndButtons = new HBox();
		kidScroll = new ScrollPane();
		kidScrollVBox = new VBox();

		// Declare Labels
		nameLabel = new Label("Client Name:");
		phoneNumberLabel = new Label("Phone Number:");
		addressLabel = new Label("Address Of Lessons:");
		kidsLabel = new Label("Kid(s) Information ");
		clientInfoTitle = new Label("Client Info");

		// Declare buttons
		addKidButton = new Button("+");
		removeKidButton = new Button("-");

		// Button settings
		addKidButton.setId("addButton");
		removeKidButton.setId("addButton");

		// Label Settings
		phoneNumberLabel.setId("labels");
		nameLabel.setId("labels");
		addressLabel.setId("labels");
		kidsLabel.setId("labels");
		clientInfoTitle.setId("clientInfoTitle");

		// Field Settings
		nameField.setId("fields");
		phoneNumberField.setId("phoneNumberField");
		addressField.setId("fields");
		contractRecieved.setId("checkBox");

		// Pane settings
		titlePane.setId("titlePane");
		kidScroll.setId("kidScroller");

		// Add to panes
		kidLabelAndButtons.getChildren().add(kidsLabel);
		kidLabelAndButtons.getChildren().add(addKidButton);
		kidLabelAndButtons.getChildren().add(removeKidButton);

		kidScroll.setContent(kidScrollVBox);

		titlePane.setCenter(clientInfoTitle);

		clientInfoEntriesVBox.getChildren().add(titlePane);
		clientInfoEntriesVBox.getChildren().add(nameLabel);
		clientInfoEntriesVBox.getChildren().add(nameField);
		clientInfoEntriesVBox.getChildren().add(phoneNumberLabel);
		clientInfoEntriesVBox.getChildren().add(phoneNumberField);
		clientInfoEntriesVBox.getChildren().add(addressLabel);
		clientInfoEntriesVBox.getChildren().add(addressField);
		clientInfoEntriesVBox.getChildren().add(contractRecieved);
		clientInfoEntriesVBox.getChildren().add(kidLabelAndButtons);
		clientInfoEntriesVBox.getChildren().add(kidScroll);

		// Button functionality
		addKidFieldButton(addKidButton, kidScrollVBox);
		removeKidFieldButton(removeKidButton, kidScrollVBox);
	}

	public void lessonInfoSetup() {

		// Declare Fields
		instructorChoice = new ChoiceBox<Instructor>();
		numberOfLessonsField = new Spinner<Integer>();
		amountPerLessonField = new Spinner<Double>();
		amountPaidField = new Spinner<Double>();
		addInstructorButton = new Button("+");
		removeInstructorButton = new Button("-");

		// Adding listener to a spinner
		amountPerLessonField.setEditable(true);
		amountPaidField.setEditable(true);
		amountPerLessonField.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*") || newValue.length() > 3) {
				amountPerLessonField.getEditor().setText(oldValue);

			}
		});
		amountPaidField.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*") || newValue.length() > 5) {
				amountPaidField.getEditor().setText(oldValue);

			}
		});

		// Adding data to spinners
		amountPerLessonField.setValueFactory(lessonAmountValues);
		numberOfLessonsField.setValueFactory(lessonValues);
		amountPaidField.setValueFactory(amountPaidValues);

		// Declare Labels
		instructorLabel = new Label("Instructor:");
		numberOfLessonsLabel = new Label("Number Of Lessons (Per Kid):");
		amountPerLessonLabel = new Label("Amount Per Lesson: ");
		amountPaidLabel = new Label("Amount Paid:");
		lessonInfoTitle = new Label("Lesson Info");

		// Label Settings
		instructorLabel.setId("labels");
		numberOfLessonsLabel.setId("labels");
		amountPerLessonLabel.setId("labels");
		amountPaidLabel.setId("labels");
		lessonInfoTitle.setId("lessonInfoTitle");

		// Choicebox settings
		instructorChoice.setId("choiceBox");
		populateChoiceBox(instructorChoice);
		instructorChoiceBoxes.add(instructorChoice);
		instructorChoice.getSelectionModel().selectFirst();

		// Field settings
		numberOfLessonsField.setId("spinners");
		amountPerLessonField.setId("spinners");
		amountPaidField.setId("spinners");

		// Button Settings
		addInstructorButton.setId("addButton");
		removeInstructorButton.setId("addButton"); // same css id for same style

		// Declare Panes
		instructorsAdditionHBox = new HBox();
		titlePane = new BorderPane();

		// Pane settings
		titlePane.setId("titlePane");
		addInstructorButton(addInstructorButton, instructorsAdditionHBox);
		removeInstructorButton(removeInstructorButton, instructorsAdditionHBox);

		// Add to panes
		titlePane.setCenter(lessonInfoTitle);
		instructorsAdditionHBox.getChildren().add(instructorChoice);
		instructorsAdditionHBox.getChildren().add(addInstructorButton);
		instructorsAdditionHBox.getChildren().add(removeInstructorButton);

		// Add to vbox
		clientLessonInfoEntryVBox.getChildren().add(titlePane);
		clientLessonInfoEntryVBox.getChildren().add(instructorLabel);
		clientLessonInfoEntryVBox.getChildren().add(instructorsAdditionHBox);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsLabel);
		clientLessonInfoEntryVBox.getChildren().add(numberOfLessonsField);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonLabel);
		clientLessonInfoEntryVBox.getChildren().add(amountPerLessonField);
		clientLessonInfoEntryVBox.getChildren().add(amountPaidLabel);
		clientLessonInfoEntryVBox.getChildren().add(amountPaidField);
	}

	// Fill the fields with data from the client object
	// The client object comes from the client card
	// which is seen on the screen
	private void populateEntries() {

		// Get size of instructor array
		// Create choice box for each
		// Set appropriate value
		if (clientTempReference.getClient().getInstructor().size() > 1) {
			for (int i = 1; i < clientTempReference.getClient().getInstructor().size(); i++) {
				ChoiceBox<Instructor> instructorChoice = new ChoiceBox<Instructor>();
				instructorChoice.setId("choiceBox");
				populateChoiceBox(instructorChoice);
				instructorChoice.setValue(clientTempReference.getClient().getInstructor().get(i));
				instructorChoiceBoxes.add(instructorChoice);
				instructorsAdditionHBox.getChildren().add(instructorsAdditionHBox.getChildren().size() - 2,
						instructorChoice);
				instructorMax++;
			}
		}

		// Populate Kid Fields
		for(int i = 0; i < clientTempReference.getClient().getKids().size(); i++) {
			KidEntry kidEntry = new KidEntry("no information provided", clientTempReference.getClient().getKids().get(i).getKidInformation());
			kidInformationFields.add(kidEntry);
			kidScrollVBox.getChildren().add(kidEntry);
		}
		
		nameField.setText(clientTempReference.getClient().getClientName());
		phoneNumberField.setPlainText(clientTempReference.getClient().getPhoneNumber());
		addressField.setText(clientTempReference.getClient().getAddressOfLessons());
		clientTempReference.getClient().getNumberOfKids();
		numberOfLessonsField.getValueFactory().setValue((int) clientTempReference.getClient().getNumberOfLessons());
		amountPerLessonField.getValueFactory().setValue((double) clientTempReference.getClient().getAmountPerLesson());
		amountPaidField.getValueFactory().setValue(clientTempReference.getClient().getAmountPaid());
		contractRecieved.setSelected(clientTempReference.getClient().isContractRecieved());
	}

	private void enableDeleteButton() {
		// Create button
		Button deleteButton = new Button("Delete");

		// Button settings
		deleteButton.setId("deleteButton");
		deleteButton.setPrefHeight(52);
		deleteButton.setPrefWidth(353);
		deleteButton.setMinHeight(0);
		deleteButton.setMinWidth(0);

		// Delete button handler
		deleteButton.setOnAction((new EventHandler<ActionEvent>() {

			// Temp client name in case of null client object
			String tempName = clientTempReference.getClient().getClientName();

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					deleteClient();
					clientDB.saveData();
					Alert sceneAlert = new Alert(AlertType.INFORMATION);
					sceneAlert.setContentText("Client " + tempName + " has been successfully deleted.");
					sceneAlert.show();
					clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1)
							.setClients(clientChartData.getMonthlyEntries()
									.get(clientChartData.getMonthlyEntries().size() - 1).getClients() - 1);
					clientChartData.saveData();
					mainScreen.getPane()
							.setCenter(FXMLLoader.load(getClass().getResource("/resources/scenes/ClientsScene.fxml")));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));

		// Add button to hbox
		buttonHBox.getChildren().add(deleteButton);

	}

	private void addKidFieldButton(Button addKidButton, VBox kidScrollVBox) {
		addKidButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				KidEntry kid = new KidEntry("Kid " + (kidInformationFields.size() + 1));
				kidInformationFields.add(kid);
				kidScrollVBox.getChildren().add(kid);
			}

		}));
	}

	private void removeKidFieldButton(Button removeKidButton, VBox kidScrollVBox) {
		removeKidButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (kidInformationFields.size() > 0) {
					kidInformationFields.remove(kidInformationFields.size() - 1);
					kidScrollVBox.getChildren().remove(kidScrollVBox.getChildren().size() - 1);
				}
			}

		}));
	}

	private void addInstructorButton(Button addInstructorButton, HBox instructorHBox) {
		addInstructorButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// TODO Auto-generated method stub
				if (instructorMax < 3 && instructorMax > -1) {
					ChoiceBox<Instructor> instructorChoice = new ChoiceBox<Instructor>();
					instructorChoice.setId("choiceBox");
					populateChoiceBox(instructorChoice);
					instructorChoice.setValue(instructorChoice.getItems().get(0));
					instructorChoiceBoxes.add(instructorChoice);
					instructorHBox.getChildren().add(instructorHBox.getChildren().size() - 2, instructorChoice);
					instructorMax++;
				} else {
					return;
				}
			}

		}));
	}

	private void removeInstructorButton(Button removeInstructorButton, HBox instructorHBox) {
		removeInstructorButton.setOnAction((new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (instructorMax > 0) {
					instructorChoiceBoxes.remove(instructorChoiceBoxes.get(instructorChoiceBoxes.size() - 1));
					instructorHBox.getChildren().remove(instructorHBox.getChildren().size() - 3);
					instructorMax--;
				} else {
					return;
				}
			}

		}));
	}

	// Deletes a client from the table
	private void deleteClient() {
		for (int i = 0; i < clientDB.getClientDB().size(); i++) {
			if (clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
				clientDB.getClientDB().remove(i);
				clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1).setMoney(
						clientChartData.getMonthlyEntries().get(clientChartData.getMonthlyEntries().size() - 1)
								.getMoney() - clientTempReference.getClient().getTotalAmountForLessons());
				clientChartData.saveData();
				break;
			}
		}

		// Set client to null for next iteration
		clientTempReference.setClientReference(null);

	}

	// Finds client by ID number and name
	// Then replaces the object data in the array list with the data
	// From the field entries
	private void findAndReplaceClient() {
		for (int i = 0; i < clientDB.getClientDB().size(); i++) {
			if (clientTempReference.getClient().getClientID() == clientDB.getClientDB().get(i).getClientID()) {
				clientDB.getClientDB().get(i).setClientName(nameField.getText());
				clientDB.getClientDB().get(i).setPhoneNumber(phoneNumberField.getText());
				clientDB.getClientDB().get(i).setAddressOfLessons(addressField.getText());
				// clientDB.getClientDB().get(i).setNumberOfKids(kidsField.getValue().shortValue());

				// Populate an instructor arraylist to add to a client ----
				ArrayList<Instructor> tempList = new ArrayList<Instructor>();
				for (int j = 0; j < instructorChoiceBoxes.size(); j++) {
					tempList.add(instructorChoiceBoxes.get(j).getValue());
				}
				// Garbage collection should be able to handle this
				// -------
				ArrayList<Kid> kidsTemp = new ArrayList<Kid>();
				for(int k = 0; k < kidInformationFields.size(); k++) {
					Kid kid = new Kid(kidInformationFields.get(k).getNameInputField().getText());
					kidsTemp.add(kid);
				}

				clientDB.getClientDB().get(i).setKids(kidsTemp);
				clientDB.getClientDB().get(i).setInstructor(tempList);
				clientDB.getClientDB().get(i).setNumberOfLessons(numberOfLessonsField.getValue().shortValue());
				clientDB.getClientDB().get(i).setAmountPerLesson(amountPerLessonField.getValue().floatValue());
				clientDB.getClientDB().get(i).setNumberOfKids((short) kidInformationFields.size());
				clientDB.getClientDB().get(i).setAmountPaid(amountPaidField.getValue().doubleValue());
				clientDB.getClientDB().get(i).setContractRecieved(contractRecieved.isSelected());
				clientDB.getClientDB().get(i).updateTotal();
				break;
			}
		}

	}
}
