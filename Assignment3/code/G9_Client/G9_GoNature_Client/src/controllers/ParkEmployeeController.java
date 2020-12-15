package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
	private Label lblPrice;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblPayment;
	@FXML
	private Label lblTotalPrice;

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
	private Button btnBarcodeScan;

	@FXML
	private JFXTextField txtOrderNumber;
	@FXML
	private Button btnShowDetails;

	@FXML
	private Button btnRndonVisitor;

	@FXML
	private Button btnApprove;

	private static String firstName;
	private static ArrayList<String> orderDetails;

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void barcodeScan(ActionEvent event) {
		// get order number from the server
		// call showDetails() function to set up all the order details
		// SELECT * FROM orders ORDER BY orderNumber LIMIT 1;
		txtOrderNumber.setText(String.valueOf(getOrderNumberFromBarcode()));
		showDetails(event);
	}

	private int getOrderNumberFromBarcode() {	
		return 1111;
	}

	@FXML
	void showDetails(ActionEvent event) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		// Data fields
		ArrayList<String> data = new ArrayList<String>();
		
		msg.add("ParkEmployee");
		data.add(txtOrderNumber.getText().toString());
		msg.add(data);
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
		
		if (orderDetails.get(0).equals("No such order")) {
			Alert("Failed", "No such order");
			return;
		}
		
		// 2021-01-01 08:00:00
		String DateAndTime = orderDetails.get(2);
		String[] splitDateAndTime = DateAndTime.split(" "); 
		// 2021-01-01
		String date = splitDateAndTime[0];
		
		// 08:00:00 -> 08:00
		String time = (String) splitDateAndTime[1].subSequence(0, 5);
		
		lblOrderNumber.setText(orderDetails.get(0));
		lblParkName.setText(orderDetails.get(1));
		lblDate.setText(date);
		lblTime.setText(time);
		lblVisitorsNumber.setText(orderDetails.get(3));
		lblEmail.setText(orderDetails.get(4));
		
		lblPrice.setText(orderDetails.get(5));
		lblDiscount.setText(orderDetails.get(6));
		lblPayment.setText(orderDetails.get(7));
	}

	@FXML
	void approve(ActionEvent event) {
		// check date and then time
		if (checkDate() && checkTime()) {
			Alert("Success", "Thank you, hope you enjoy your time in the park.");
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
		} else if (title == "Failed"){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		}
	}
	
	public boolean checkDate() {	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = lblDate.getText();
		
		if (currentDate.equals(dateFormat.format(new Date()))) {
			return true;
		}
			
		Alert("Failed", "Invalid date.");
		return false;
	}
	
	public boolean checkTime() {
		LocalDateTime arrivelTime = LocalDateTime.now();
		int currentHour = arrivelTime.getHour();
		// periodTime = hh:mm-hh:mm
		String periodTime = lblTime.getText();
		String[] splitTime = periodTime.split("-"); 
		// startTime = hh:mm
		String startTime = splitTime[0];
		// endTime = hh:mm
		String endTime = splitTime[1];
		
		splitTime = startTime.split(":"); 
		// tmpStartHour = hh
		String tmpStartHour = splitTime[0];
		// startHour = (int)hh
		int startHour = Integer.parseInt(tmpStartHour);
		
		splitTime = endTime.split(":"); 
		// tmpEndHour = hh
		String tmpEndHour = splitTime[0];
		// endHour = (int)hh
		int endHour = Integer.parseInt(tmpEndHour);
		
		if (currentHour >= startHour && currentHour < endHour) {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// force the field to be numeric only
		txtOrderNumber.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtOrderNumber.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}
}
