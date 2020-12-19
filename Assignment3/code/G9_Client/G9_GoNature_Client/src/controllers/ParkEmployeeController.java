package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import dataLayer.Park;
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
import orderData.Order;

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
	private Label lblCurrentVisitors;

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
	private Label lblVisitorsAmount;
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
	private JFXTextField txtVisitorsAmount;
	@FXML
	private Button btnShowDetails;

	@FXML
	private Button btnRandomVisitor;
	@FXML
	private Label lblDateTitle;
	@FXML
	private Label lblRandomDate;
	@FXML
	private Label lblTimeTitle;
	@FXML
	private Label lblRandomTime;
	@FXML
	private JFXTextField txtRandomVisitorsAmount;

	@FXML
	private JFXRadioButton radEnter;
	@FXML
	private JFXRadioButton radExit;
	@FXML
	private ToggleGroup radGroupStatus;
	@FXML
	private Button btnApprove;

	// check if we already was is the DB
	private AlertController alert = new AlertController();
	private boolean informationExists = false;
	private String radVisitorStatusText;
	private static String firstName;
	private static Order orderDetails;
	private static Park parkDetails;
	private static String error = "";
	// private Map<String, Integer> orderDocumentation = new HashMap<String,
	// Integer>();

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	// input: Id / memberId
	// output: order details
	@FXML
	void barcodeScan(ActionEvent event) {
		informationExists = true;
		// get order number from the server
		// call showDetails() function to set up all the order details

		int id = getOrderNumberFromBarcodeByID();
		int memberId = getOrderNumberFromBarcodeByMemberId();
		System.out.println("MemberId: " + memberId);

		sendToServer("ordersByIdOrMemberId", String.valueOf(memberId));

		if (getError().equals("No such order")) {
			alert.failedAlert("Failed", "No such order.");
			clearAllFields();
			return;
		}

		txtOrderNumber.setText(String.valueOf(orderDetails.getOrderNumber()));
		txtVisitorsAmount.setText(String.valueOf(getVisitorsEnteredFromBarcode()));

		showDetails(event);
	}

	private int getOrderNumberFromBarcodeByID() {
		return 123456;
	}

	private int getOrderNumberFromBarcodeByMemberId() {
		// Random rand = new Random();
		// generate random integers in range 0 to 3
		// return rand.nextInt(4);
		return 2;
	}

	private int getVisitorsEnteredFromBarcode() {
		return 2;
	}

	@FXML
	void showDetails(ActionEvent event) {

		if (txtOrderNumber.getText().isEmpty() || txtVisitorsAmount.getText().isEmpty()) {
			alert.failedAlert("Failed", "All fields required.");
			return;
		}

		// will not enter if already has information from the barcodeScan
		if (!informationExists) {
			sendToServer("ordersByOrderNumber", txtOrderNumber.getText());

			if (error.equals("No such order")) {
				alert.failedAlert("Failed", "No such order.");
				clearAllFields();
				return;
			}
		}

		if (!(String.valueOf(orderDetails.getOrderNumber()).equals(txtOrderNumber.getText()))) {
			alert.failedAlert("Failed", "No such order.");
			clearAllFields();
			return;
		}

		sendToServer("getParkDetails", orderDetails.getParkName());

		lblCurrentVisitors.setText(String.valueOf(parkDetails.getCurrentAmount()));

		/****** calculate discount ******/
		// float price = Float.parseFloat(orderDetails.get(5));
		// float discount = Float.parseFloat(orderDetails.get(6));

		// lblTotalPrice.setText(String.valueOf(totalPrice) + "₪");

		printOrderDetails();

		informationExists = false;
		btnApprove.setDisable(false);
	}

	@FXML
	void randomVisitor(ActionEvent event) {
		btnApprove.setDisable(false);
		btnRandomVisitor.setVisible(false);
		lblDateTitle.setVisible(true);
		lblRandomDate.setVisible(true);
		lblTimeTitle.setVisible(true);
		lblRandomTime.setVisible(true);
		txtRandomVisitorsAmount.setVisible(true);
	}

	@FXML
	void approve(ActionEvent event) {

		// random mode
		if (btnRandomVisitor.isVisible()) {
			int maxVisitors = 10;
			int currentVisitors = 1;
			int freePlace = maxVisitors - currentVisitors;
			// check if the amount of "visitorsEntered" greater than the invitation.
			if (!txtRandomVisitorsAmount.getText().isEmpty()
					&& (Integer.parseInt(txtRandomVisitorsAmount.getText()) < freePlace)) {
				alert.failedAlert("Failed", "The amount of visitors doesn't match the invitation.");
				return;
			} else {
				// update current visitors
			}
			// barcode / regular entry
		} else {
			// check if the amount of "visitorsEntered" greater than the invitation.
			if (!lblVisitorsNumber.getText().isEmpty() && (Integer.parseInt(txtVisitorsAmount.getText()) > Integer
					.parseInt(lblVisitorsNumber.getText()))) {
				alert.failedAlert("Failed", "The amount of visitors doesn't match the invitation.");
				return;
			}

			if (!(String.valueOf(orderDetails.getOrderNumber()).equals(txtOrderNumber.getText()))) {
				alert.failedAlert("Failed", "No such order.");
				clearAllFields();
				return;
			}

			// check date and time
			if (checkDate() && checkTime()) {

				// doesn't exists in the list -> entering the park
				if (radVisitorStatusText.equals("Enter")) {

					alert.successAlert("Success", orderDetails.getAmountArrived() + " entered.");
				} else {
					/*** Enter ***/
					// they are in the list, but they didn't take advantage of all the visits
					if (radVisitorStatusText.equals("Enter")) {

						/*** Exit ***/
					} else {

					}
				}
			}
		}

		informationExists = false;
		clearAllFields();

		btnApprove.setDisable(false);
	}

	public void printOrderDetails() {
		// 2021-01-01 08:00:00
		String DateAndTime = orderDetails.getArrivedTime();
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

			lblOrderNumber.setText(String.valueOf(orderDetails.getOrderNumber()));
			lblParkName.setText(orderDetails.getParkName());
			lblDate.setText(strDateTime);
			lblTime.setText(time);
			lblVisitorsNumber.setText(String.valueOf(orderDetails.getVisitorsNumber()));
			lblEmail.setText(orderDetails.getOrderEmail());

			lblVisitorsAmount.setText(txtVisitorsAmount.getText());
			lblPrice.setText(orderDetails.getPrice() + "₪");

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

	public boolean checkDate() {
		LocalDateTime arrivelDate = LocalDateTime.now();
		// "dd-MM-yyyy"
		String currentDate = arrivelDate.getDayOfMonth() + "-" + arrivelDate.getMonthValue() + "-"
				+ arrivelDate.getYear();
		String orderDate = lblDate.getText();

		if (currentDate.equals(orderDate)) {
			return true;
		}

		alert.failedAlert("Failed", "Invalid date.");
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

		alert.failedAlert("Failed", "Invalid time.");
		return false;
	}
	
	public boolean checkFreePlaces() {
		if (parkDetails.getCurrentAmount() < parkDetails.getMaximumCapacityInPark()) {
			return true;
		}
		return false;
	}

	public void sendToServer(String type, String dbColumns) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		// Data fields
		ArrayList<String> data = new ArrayList<String>();

		msg.add("getParkDetails");
		data.add(orderDetails.getParkName());
		msg.add(data);
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		ParkEmployeeController.firstName = firstName;
	}

	public static String getError() {
		return error;
	}

	public static void setError(String error) {
		ParkEmployeeController.error = error;
	}

	public static Order getOrderDetails() {
		return orderDetails;
	}

	public static void receivedFromServerOrderDetails(ArrayList<String> order) {
		if (order.get(0).equals("No such order")) {
			setError("No such order");
		} else {
			ParkEmployeeController.orderDetails = new Order(order);
		}
	}

	public static void receivedFromServerParkDetails(ArrayList<String> park) {
		ParkEmployeeController.parkDetails = new Park(park);
	}

	public void clearAllFields() {
		txtOrderNumber.clear();
		lblOrderNumber.setText("");
		txtVisitorsAmount.clear();
		lblVisitorsAmount.setText("");
		lblParkName.setText("");
		lblDate.setText("");
		lblTime.setText("");
		lblVisitorsNumber.setText("");
		lblEmail.setText("");
		lblPrice.setText("");
		lblDiscount.setText("");
		lblPayment.setText("");
		lblTotalPrice.setText("");
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		txtRandomVisitorsAmount.setVisible(false);
		btnApprove.setDisable(false);
		btnRandomVisitor.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnApprove.setDisable(true);
		radEnter.setSelected(true);
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		txtRandomVisitorsAmount.setVisible(false);
		radVisitorStatusText = "Enter";

		LocalDateTime arrivelTime = LocalDateTime.now();
		lblRandomTime.setText(arrivelTime.getHour() + ":" + arrivelTime.getMinute());

		LocalDateTime arrivelDate = LocalDateTime.now();
		lblRandomDate
				.setText(arrivelDate.getDayOfMonth() + "-" + arrivelDate.getMonthValue() + "-" + arrivelDate.getYear());

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
		txtVisitorsAmount.textProperty().addListener((obs, oldValue, newValue) -> {
			lblVisitorsAmount.setText(newValue);
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
				lblVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		// force the field to be numeric only
		txtRandomVisitorsAmount.textProperty().addListener((obs, oldValue, newValue) -> {
			lblVisitorsAmount.setText(newValue);
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtRandomVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
				lblVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
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
