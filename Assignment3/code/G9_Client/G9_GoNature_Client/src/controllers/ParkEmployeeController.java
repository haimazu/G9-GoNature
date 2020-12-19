package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ParkEmployeeController implements Initializable {
	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnSettings;

	@FXML
	private Label lblTitle;

	@FXML
	private Pane pnSettings;
	@FXML
	private Pane pnDashboard;

	@FXML
	private Label lblOrderNumber;
	@FXML
	private Label lblParkName;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblTime;
	@FXML
	private Label lblVisitorsNumber;
	@FXML
	private Label lblEmail;

	@FXML
	private Label lblVisitorsEntered;
	@FXML
	private Label lblPrice;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblPayment;
	@FXML
	private Label lblTotalPrice;

	@FXML
	private Button btnBarcodeScan;

	@FXML
	private JFXTextField txtOrderNumber;
	@FXML
	private JFXTextField txtVisitorsEntered;
	@FXML
	private Button btnShowDetails;

	@FXML
	private Button btnRndonVisitor;

	@FXML
	private JFXRadioButton radEnter;
	@FXML
	private JFXRadioButton radExit;
	@FXML
	private ToggleGroup radGroupStatus;
	@FXML
	private Button btnApprove;

	// discount by types of visitors
	// single / family -> pre-booked
	public static final float SINGLE_OR_FAMILY_PRE_BOOKED = 10;
	public static final float SINGLE_OR_FAMILY_PRE_BOOKED_AND_MEMBERS = 20;
	// single / family -> random visitor/s
	public static final float SINGLE_OR_FAMILY_AND_MEMBERS = 20;
	// group -> pre-booked (guide not pay)
	public static final float GROUP_PRE_BOOKED = 25;
	public static final float GROUP_PRE_BOOKED_AND_ALREADY_PAID = 12;
	// group -> random visit (guide pay)
	public static final float GROUP_PRE_BOOKED_RANDOM_VISIT = 10;

	// check if we already was is the DB
	private boolean informationExists = false;
	private String radVisitorStatusText;
	private static String firstName;
	private static ArrayList<String> orderDetails;
	private Map<String, Integer> orderDocumentation = new HashMap<String, Integer>();

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void barcodeScan(ActionEvent event) {
		informationExists = true;
		// get order number from the server
		// call showDetails() function to set up all the order details
		// SELECT * FROM orders ORDER BY orderNumber LIMIT 1;
		// txtOrderNumber.setText(String.valueOf(getOrderNumberFromBarcodeByID()));
		txtVisitorsEntered.setText(String.valueOf(getVisitorsEnteredFromBarcode()));

		int id = getOrderNumberFromBarcodeByID();
		int memberId = getOrderNumberFromBarcodeByMemberId();

		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		// Data fields
		ArrayList<String> data = new ArrayList<String>();

		msg.add("ordersByIdOrMemberId");
		data.add(String.valueOf(id));
		msg.add(data);
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);

		txtOrderNumber.setText(orderDetails.get(0));

		showDetails(event);
	}

	private int getOrderNumberFromBarcodeByID() {
		return 123456;
	}

	private int getOrderNumberFromBarcodeByMemberId() {
		return 2;
	}

	private int getVisitorsEnteredFromBarcode() {
		return 2;
	}

	@FXML
	void showDetails(ActionEvent event) {

		if (txtOrderNumber.getText().isEmpty() || txtVisitorsEntered.getText().isEmpty()) {
			Alert("Failed", "All fields required.");
			return;
		}

		// will not enter if already has information from the barcodeScan
		if (!informationExists) {
			// Query
			ArrayList<Object> msg = new ArrayList<Object>();
			// Data fields
			ArrayList<String> data = new ArrayList<String>();

			msg.add("ordersByOrderNumber");
			data.add(txtOrderNumber.getText().toString());
			msg.add(data);
			// set up all the order details and the payment method
			ClientUI.sentToChatClient(msg);

			if (orderDetails.get(0).equals("No such order")) {
				Alert("Failed", "No such order.");
				clearAllFields();
				return;
			}
		}

		if (!(orderDetails.get(0).equals(txtOrderNumber.getText()))) {
			Alert("Failed", "No such order.");
			clearAllFields();
			return;
		}

		/****** calculate discount ******/
		float price = Float.parseFloat(orderDetails.get(5));
		// float discount = Float.parseFloat(orderDetails.get(6));

		// lblTotalPrice.setText(String.valueOf(totalPrice) + "₪");

		printOrderDetails();

		informationExists = false;
		btnApprove.setDisable(false);
	}

	@FXML
	void approve(ActionEvent event) {
		// check if the amount of "visitorsEntered" greater than the invitation.
		if (!lblVisitorsNumber.getText().isEmpty()
				&& (Integer.parseInt(txtVisitorsEntered.getText()) > Integer.parseInt(lblVisitorsNumber.getText()))) {
			Alert("Failed", "The amount of visitors doesn't match the invitation.");
			return;
		}

		if (!(orderDetails.get(0).equals(txtOrderNumber.getText()))) {
			Alert("Failed", "No such order.");
			clearAllFields();
			return;
		}

		// check date and time
		if (checkDate() && checkTime()) {

			int orderVisitorsNumber = Integer.parseInt(lblVisitorsNumber.getText());
			int visitorsEntered = Integer.parseInt(txtVisitorsEntered.getText());
			int visitorsLeaved = Integer.parseInt(txtVisitorsEntered.getText());
			int placesLeft = 0;

			// doesn't exists in the list -> entering the park
			if (orderDocumentation.size() < 1 && radVisitorStatusText.equals("Enter")) {
				orderDocumentation.put(txtOrderNumber.getText(), visitorsEntered);
				Alert("Success", visitorsEntered + " entered.");
			} else {
				// check to see if visitors are enter or leave the park
				for (Map.Entry<String, Integer> orderNumber : orderDocumentation.entrySet()) {

					/*** Enter ***/					
					// they are in the list, but they didn't take advantage of all the visits
					if (radVisitorStatusText.equals("Enter")) {
						
						if (orderNumber.getKey().equals(txtOrderNumber.getText())) {
							placesLeft = orderVisitorsNumber - (visitorsEntered + orderNumber.getValue());
							if (placesLeft >= 0) {
								// the method put will replace the value of an existing key
								// and will create it if doesn't exist.
								orderDocumentation.put(orderNumber.getKey(), placesLeft);
								Alert("Success", visitorsEntered + " visitor/s entered."
										+ "\n" + placesLeft + " more places left. ");
								// we need to update the "currentVisitors"
								// "currentVisitors += visitorsEntered"
								// UPDATE.. currentVisitors
							} else {
								Alert("Failed", "You've used all the places.");
							}
						} 
					/*** Exit ***/
					} else {
						placesLeft = orderNumber.getValue() - visitorsLeaved;
						// if exists in the list -> leavening the park
						if (placesLeft > 0) {
							orderDocumentation.remove(orderNumber.getKey());
							Alert("Success", visitorsLeaved + " visitor/s leaved.\n"
									+ placesLeft + " visitor/s are still in the park.");
							// we need to update the "currentVisitors"
							// "currentVisitors -= visitorsEntered"
							// UPDATE.. currentVisitors
						// all the visitors on this order, leaved
						} else if (placesLeft == 0){
							Alert("Failed", "All the visitor/s or this order, have left.");
						// wrong number, not match the orderNumber
						} else {
							Alert("Failed", "The amount of visitors doesn't match the invitation.");
						}
					}
				}
			}

			informationExists = false;
			clearAllFields();
		}
		System.out.println(orderDocumentation);
	}

	public void printOrderDetails() {
		// 2021-01-01 08:00:00
		String DateAndTime = orderDetails.get(8);
		String[] splitDateAndTime = DateAndTime.split(" ");
		// 2021-01-01
		String date = splitDateAndTime[0];

		// changing the date format from "yyyy-MM-dd" to "dd-MM-yyyy"
		// iFormatter -> input format
		DateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd");
		// oFormatter -> output format
		DateFormat oFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			String strDateTime = oFormatter.format(iFormatter.parse(date));

			// 08:00:00 -> 08:00
			String time = (String) splitDateAndTime[1].subSequence(0, 5);

			lblOrderNumber.setText(orderDetails.get(0));
			lblParkName.setText(orderDetails.get(7));
			lblDate.setText(strDateTime);
			lblTime.setText(time);
			lblVisitorsNumber.setText(orderDetails.get(1));
			lblEmail.setText(orderDetails.get(2));

			lblVisitorsEntered.setText(txtVisitorsEntered.getText());
			lblPrice.setText(orderDetails.get(5) + "₪");

			// need to calculate:
			// order type -> random / not random?
			// member / not member?
			// payed / not payed?
			// there is parkManager discount too?
			// lblDiscount.setText(orderDetails.get(6) + "%");
			// lblPayment.setText(orderDetails.get(9));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// showing alert message
	public void Alert(String title, String msg) {
		if (title == "Success") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		} else if (title == "Failed") {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		}
	}

	public boolean checkDate() {
		LocalDateTime arrivelDate = LocalDateTime.now();
		// "dd-MM-yyyy"
		String currentDate = arrivelDate.getDayOfMonth() + "-" + arrivelDate.getMonthValue() + "-"
				+ arrivelDate.getYear();
		String orderDate = lblDate.getText();

		if (currentDate.equals(orderDate)) {
			return true;
		}

		Alert("Failed", "Invalid date.");
		return false;
	}

	public boolean checkTime() {
		LocalDateTime arrivelTime = LocalDateTime.now();
		// currentHour = hh
		int currentHour = arrivelTime.getHour();
		// startTime = hh:mm
		String startTime = lblTime.getText();
		String[] splitStartTime = startTime.split(":");
		// stringArrivelHour = hh
		String stringArrivelHour = splitStartTime[0];
		int arrivelHour = Integer.parseInt(stringArrivelHour);

		// currentHour = 08:00 | 12:00
		// arrivelHour = 08:00 - 12:00 | 12:00 - 16:00
		if ((currentHour >= arrivelHour && currentHour < 12) || (currentHour >= arrivelHour && currentHour < 23)) {
			return true;
		}

		Alert("Failed", "Invalid time.");
		return false;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		ParkEmployeeController.firstName = firstName;
	}

	public static ArrayList<String> getOrderDetails() {
		return orderDetails;
	}

	public static void receivedFromServerOrderDetails(ArrayList<String> orderDetails) {
		ParkEmployeeController.orderDetails = orderDetails;
	}

	public void clearAllFields() {
		txtOrderNumber.clear();
		lblOrderNumber.setText("");
		txtVisitorsEntered.clear();
		lblVisitorsEntered.setText("");
		lblParkName.setText("");
		lblDate.setText("");
		lblTime.setText("");
		lblVisitorsNumber.setText("");
		lblEmail.setText("");
		lblPrice.setText("");
		lblDiscount.setText("");
		lblPayment.setText("");
		lblTotalPrice.setText("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnApprove.setDisable(true);
		radEnter.setSelected(true);
		radVisitorStatusText = "Enter";

		// force the field to be numeric only
		txtOrderNumber.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtOrderNumber.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		// force the field to be numeric only
		txtVisitorsEntered.textProperty().addListener((obs, oldValue, newValue) -> {
			lblVisitorsEntered.setText(newValue);
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtVisitorsEntered.setText(newValue.replaceAll("[^\\d]", ""));
				lblVisitorsEntered.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		// listen to changes in selected toggle
		radGroupStatus.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
			if (newToggle == radEnter) {
				radVisitorStatusText = ((RadioButton) radGroupStatus.getSelectedToggle()).getText();
			} else if (newToggle == radExit) {
				radVisitorStatusText = ((RadioButton) radGroupStatus.getSelectedToggle()).getText();
			}
		});
	}
}
