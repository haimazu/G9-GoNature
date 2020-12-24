package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orderData.Order;

public class ParkEmployeeController implements Initializable {
	/***** Top/Side Panels Details *****/
	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnSettings;

	/***** Current Park Details *****/
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblCurrentVisitors;

	@FXML
	private Pane pnSettings;
	@FXML
	private Pane pnDashboard;

	/***** Print Details *****/
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

	/***** Payment Details *****/
	@FXML
	private Label lblVisitorsAmount;
	@FXML
	private Label lblPrice;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblTotalPrice;

	/***** BarcodeScan *****/
	@FXML
	private Button btnBarcodeScan;

	/***** Show Details - Regular Entry *****/
	@FXML
	private JFXTextField txtOrderNumber;
	@FXML
	private JFXTextField txtVisitorsAmount;
	@FXML
	private Button btnShowDetails;

	/***** Random Entry *****/
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
	private JFXTextField txtIdOrMemberId;
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

	private AlertController alert = new AlertController();
	private boolean informationExists = false;
	private String radVisitorStatusText;
	private String dateAndTimeFormat = "";
	private static String firstName;
	private static String parkName;
	private static Order orderDetails;
	private static Order randomVisitorFakeOrderDetails;
	private static Park parkDetails;
	private static String error = "";

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

		sendToServerArrayList("ordersByIdOrMemberId", new ArrayList<String>(Arrays.asList(String.valueOf(memberId))));

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
		 Random rand = new Random();
		// generate random integers in range 0 to 3
		 return rand.nextInt(4);
		//return 2;
	}

	private int getVisitorsEnteredFromBarcode() {
		return 2;
	}

	// input: order number
	// output: order details
	@FXML
	void showDetails(ActionEvent event) {
		setRandomModeOff();

		if (txtOrderNumber.getText().isEmpty() || txtVisitorsAmount.getText().isEmpty()) {
			alert.failedAlert("Failed", "All fields required.");
			return;
		}

		// will not enter if already has information from the barcodeScan
		if (!informationExists) {
			sendToServerArrayList("ordersByOrderNumber", new ArrayList<String>(Arrays.asList(txtOrderNumber.getText())));

			if (getError().equals("No such order")) {
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


		printOrderDetails();
		/****** calculate discount ******/
		setPrice();

		informationExists = false;
		btnApprove.setDisable(false);
	}

	// input: none
	// output: number of visitor/s that enter / leave the park
	@FXML
	void approve(ActionEvent event) {
		// update park status
		updateParkStatus(0);
		
		// checking for places in the park
		if (!getError().equals("Full")) {
			// random mode
			if (!btnRandomVisitor.isVisible()) {
				if (txtIdOrMemberId.getText().isEmpty() || txtRandomVisitorsAmount.getText().isEmpty()) {
					alert.failedAlert("Failed", "All fields required.");
					return;
				} else {
					/*** Enter ***/
					if (radVisitorStatusText.equals("Enter")) {
						execRandomVisitor(Integer.parseInt(txtRandomVisitorsAmount.getText()));
					/*** Exit ***/
					} else {
						execExit();
					}
				}
			// barcode / regular entry
			} else {
				if (txtOrderNumber.getText().isEmpty() || txtVisitorsAmount.getText().isEmpty()) {
					alert.failedAlert("Failed", "All fields required.");
					return;
				}
				
				// if the order is for another park
				if (!orderDetails.getParkName().equals(getParkName())) {
					alert.failedAlert("Failed", "The order is for the park " + orderDetails.getParkName() + ".");
					clearAllFields();
					return;
				}
				
				// check date and time
				if (checkDate() && checkTime()) {
					/*** Enter ***/
					if (radVisitorStatusText.equals("Enter")) {
						
						// if this order is already listed in "Used", asked to make a purchase for everyone 
						if (orderDetails.getAmountArrived() != 0) {
							alert.ensureAlert("Ensure", "Visitors to this order have already entered the park.\n"
									+ "Are you sure you want to approve the purchase for everyone?");
							if (alert.getResult().equals("OK")) {
								execRandomVisitor(Integer.parseInt(txtVisitorsAmount.getText()));
								clearAllFields();
								return;
							}
						}
						
						execEnter();
						
					/*** Exit ***/
					} else {
						// update park current visitors
						execExit();
					}
				}
			}
		} else {
			lblCurrentVisitors.setText("[" + getParkName() + "]: is full right now ");
			theParkIsOff();
			alert.failedAlert("Failed", "We're sorry, the park is full.\nPlease try again later.");
			return;
		}
		
		clearAllFields();
	}
	
	// enter control to the park
	// input: none
	// output: updating the current visitors in the park 
	public void execEnter() {
		int tooManyVisitors = 0;
		
		// check if the amount of "visitorsEntered" greater than the invitation.
		if (Integer.parseInt(txtVisitorsAmount.getText()) > 
	    	Integer.parseInt(lblVisitorsNumber.getText())) {
				tooManyVisitors = Integer.parseInt(txtVisitorsAmount.getText()) -
						Integer.parseInt(lblVisitorsNumber.getText());
			
			// check the available places in the park
			updateParkStatus(tooManyVisitors);
			
			if (!getError().equals("Full")) {
				// alert to ensure that the employee didn't get typing wrong
				alert.ensureAlert("Ensure", "Are you sure you want to approve the purchase?\n"
						+ "You can check if the additional visitors have a membership in the random part,"
						+ " otherwise they will pay the full price for the entrance to the park.");
				if (alert.getResult().equals("OK")) {	
					// update the current visitors in the park after the entry
					updateCurrentVisitors(tooManyVisitors);
					// updates the number of visitors who came for a specific order
					updateAmountArrived();
					// make an automatic purchase for the additional visitors
					execRandomVisitor(tooManyVisitors);				
				} 
			} else {
				alert.failedAlert("Failed", "The amount of visitors is greater than the available places in the park.");
				return;
			}
		// amount of visitors is less than or equal to what is on the order
		} else {
			// check the available places in the park
			updateParkStatus(tooManyVisitors);
				
			if (!getError().equals("Full")) {
				// update the current visitors in the park after the entry
				updateCurrentVisitors(Integer.parseInt(txtVisitorsAmount.getText()));
				// updates the number of visitors who came for a specific order
				updateAmountArrived();		
				alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s entered.");
				
			} else {
				alert.failedAlert("Failed", "The amount of visitors is greater than the available places in the park.");
				return;
			}
		}
	}
	
	// exit control from the park
	// input: none
	// output: updating the current visitors in the park 
	public void execExit() {
		int updateCurrentVisitors = 0;
		// random mode
		if (!btnRandomVisitor.isVisible()) {
			alert.successAlert("Success", txtRandomVisitorsAmount.getText() + " visitor/s leaved.");
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtRandomVisitorsAmount.getText());
		// barcode / regular mode
		} else {
			alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s leaved.");	
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtVisitorsAmount.getText());
		}
		// update current visitors
		updateCurrentVisitors(updateCurrentVisitors);
	}

	// checking the entrance and exit of the random visitor
	// ArrayList<String> data, sending to the server to update the current visitors amount
	// input: cell 0: parkName
	//        cell 1: new current visitors (updated one)
	// output: updating the current visitors in the park 
	public void execRandomVisitor(int visitorsAmount) {
		//TODO update park visitors amount
		int maxVisitors = parkDetails.getMaximumCapacityInPark();
		int currentVisitors = parkDetails.getCurrentAmount();
		int updateCurrentVisitors = 0;
		int freePlace = maxVisitors - currentVisitors;

		// check if the amount of "visitorsEntered" greater than the invitation.
		if (visitorsAmount > freePlace) {
			alert.failedAlert("Failed", "The amount of visitors is greater than the available places in the park.");
			return;
		} 
		
		// update prices
		setPrice();
		
		// in case without order
		if (!lblVisitorsNumber.getText().isEmpty()) {
			alert.successAlert("Success", 
					Integer.parseInt(lblVisitorsNumber.getText()) + " visitor/s with order.\n"
					+ String.valueOf(visitorsAmount) + " casual visitor/s, entered.");
			updateCurrentVisitors = visitorsAmount + Integer.parseInt(lblVisitorsNumber.getText()) + currentVisitors;
		// in case with order
		} else {
			alert.successAlert("Success", String.valueOf(visitorsAmount) + " visitor/s entered.");			
			updateCurrentVisitors = visitorsAmount + currentVisitors;
		}
		
		// update current visitors
		updateCurrentVisitors(updateCurrentVisitors);
	}
	
	// updates prices
	// input: none
	// output: prints the latest data for payment
	public void setPrice() {	
		Order fakeOrder;
		String id = null;
		String memberId = null;
		String tempDate = dateAndTimeFormat;
		double discount = 0;
		int difference = 0;

		// format time
		tempDate += roundingTime();
		// after calling the function, dateAndTimeFormat = "yyyy-MM-dd HH:mm:ss"
				
		// case 2 or 4 -> single/family OR group
		/***** Random *****/
		if (!btnRandomVisitor.isVisible()) {		
			if (txtIdOrMemberId.getText().length() == 9) {
				id = txtIdOrMemberId.getText();
			} else {
				memberId = txtIdOrMemberId.getText();
			}
		
			fakeOrder = new Order(getParkName(), tempDate, memberId, id, 
					Integer.parseInt(txtRandomVisitorsAmount.getText()));
			
			sendToServerObject("randomVisitorFakeOrder", fakeOrder);
			
			lblPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getPrice()) + "₪");
			discount = (1 - (randomVisitorFakeOrderDetails.getTotalPrice() / randomVisitorFakeOrderDetails.getPrice())) * 100;
			lblDiscount.setText(String.format("%.1f", discount) + "%");	
			lblTotalPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getTotalPrice()) + "₪");
			
		// if added to visitors in the order, making fake order
		} else {
			difference = Integer.parseInt(txtVisitorsAmount.getText()) - Integer.parseInt(lblVisitorsNumber.getText());
				
			if (difference > 0) {
				double price = difference * parkDetails.getEnteryPrice();
				// set price
				lblPrice.setText(String.format("%.1f", orderDetails.getPrice()) + "₪ , " 
							   + String.format("%.1f", price) + "₪");
				// set discount
				discount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", discount) + "% , "
								  + String.format("%.1f", (double) parkDetails.getMangerDiscount()) + "%");	
				// set total price
				double totalPrice = price * (((100 - (double) parkDetails.getMangerDiscount()) / 100));
				price += orderDetails.getTotalPrice();
				lblTotalPrice.setText(String.format("%.1f", orderDetails.getTotalPrice()) + "₪ + " 
							   + String.format("%.1f", totalPrice) + "₪ = " 
							   + String.format("%.1f", price) + "₪");
			}
		}	
	}
	
	// takes the current time and adjusts it to the system
	// input: 'today's time' 
	// output: return String -> with the appropriate time for the system
	public String roundingTime() {
		LocalDateTime arrivelTime = LocalDateTime.now();
		// currentHour = hh
		int currentHour = arrivelTime.getHour();
		
		// currentHour = 08:00 | 12:00 | 16:00
		if (currentHour >= 8 && currentHour < 12) {
			return " 08:00:00";
		} else if (currentHour >= 12 && currentHour < 16) {
				return " 12:00:00";
		} else if (currentHour >= 16 && currentHour < 20) {
				return " 16:00:00";
		}

		return "";
	}

	// check for valid date in the order
	// input: [0] 'today's date' 
	//        [1] order date
	// output: return true if that's today's date
	//         otherwise false
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

	// check for correct visit time in the order
	// input: [0] 'the time now' 
	//        [1] order time range
	// output: return true if the visitor arrived at the right hours
	//         otherwise false
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

		// currentHour = 08:00 | 12:00 | 16:00
		// arrivelHour = 08:00 - 12:00 | 12:00 - 16:00 | 16:00 - 20:00
		if (arrivelHour == 8) {
			if (currentHour >= 8 && currentHour < 12) {
				return true;
			}
		} else if (arrivelHour == 12) {
			if (currentHour >= 12 && currentHour < 16) {
				return true;
			}
		} else if (arrivelHour == 16) {
			if (currentHour >= 16 && currentHour < 20) {
				return true;
			}
		}

		alert.failedAlert("Failed", "Arrival time doesn't match the time on order.");
		return false;
	}
	
	// input: number of visitor to update
	// output: updates the number of visitors who came for a specific order
	public void updateAmountArrived() {
		// ArrayList<String> data
		// 		  cell 0: orderNumber
		//        cell 1: new arrived amount (updated one)
		ArrayList<String> data = new ArrayList<String>();
		data.add(String.valueOf(orderDetails.getOrderNumber()));
		data.add(lblVisitorsNumber.getText());
		//sendToServerArrayList("updateAmountArrived", data);
		
		// check if the update failed and showing alert
		if (getError().equals("false")) {
			alert.failedAlert("Failed", "Sorry, we couldn't do the update.");
			return;
		}
	}
	
	// updates the current amount of visitors in the appropriate park table
	// input: number of visitor to update
	// output: updated number of visitors in DB
	public void updateCurrentVisitors(int updateCurrentVisitors) {
		// ArrayList<String> data, sending to the server to update the current visitors amount
		// input: cell 0: parkName
		//        cell 1: new current visitors (updated one)
		// output: message with the result of the update: true if success
		//                                                false, otherwise
		ArrayList<String> data = new ArrayList<String>();
		data.add(getParkName());
		data.add(String.valueOf(updateCurrentVisitors));
		sendToServerArrayList("updateCurrentVisitors", data);
		
		// check if the update failed and showing alert
		if (getError().equals("false")) {
			alert.failedAlert("Failed", "Sorry, we couldn't do the update.");
			return;
		}
	}
	
	// input: none
	// output: update the park title with the current visitors
	public void updateParkStatus(int addToEnter) {
		sendToServerArrayList("getParkDetails", new ArrayList<String>(Arrays.asList(getParkName(), 
				String.valueOf(addToEnter))));

		if (!getError().equals("Full")) {
			lblCurrentVisitors.setText("[" + getParkName() + "]:  " 
									+ String.valueOf(parkDetails.getCurrentAmount()) + "/" 
									+ parkDetails.getMaximumCapacityInPark());
		} else {
			lblCurrentVisitors.setText("[" + getParkName() + "]: is full right now ");
			theParkIsOff();
		}
	}
	
	// prints order data
	// input: order
	// output: prints the order data 
	public void printOrderDetails() {
		// 2021-01-01 08:00:00
		double discount;
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
			discount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
			lblDiscount.setText(String.format("%.1f", discount) + "%");
			lblTotalPrice.setText(orderDetails.getTotalPrice() + "₪");

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	// String type, the case we dealing with
	// Object obj, sending to the server to get data
	// input: object
	// output: none
	public void sendToServerObject(String type, Object obj) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
			
		msg.add(type);
		// Data fields
		if (obj instanceof Order) {
			msg.add((Order) obj);			
		}
			
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
	}
	
	// String type, the case we dealing with
	// ArrayList<String> dbColumns, sending to the server to get data
	// input: cells, depending on the case
	// output: none
	public void sendToServerArrayList(String type, ArrayList<String> dbColumns) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		
		msg.add(type);
		// Data fields
		msg.add(dbColumns);
		
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
	}
	
	// getting information from the server
	// input: if the order number exists in the system: 
	// 		  		1. ArrayList<String> order with all the order data
	//        otherwise 2. string of "No such order"
	// output: for case 1. we create new order with all the received details
	//         for case 2. we set the error message
	public static void receivedFromServerOrderDetails(Object order) {
		if (order instanceof Order) {
			ParkEmployeeController.orderDetails = (Order) order;
		} else {
			setError((String) order);
		}
	}

	// getting information from the server
	// input: ArrayList<String> park with all the park data
	// output: new park
	public static void receivedFromServerParkDetails(Object park) {
		if (park instanceof Park) {
			ParkEmployeeController.parkDetails = (Park) park;			
		} else {
			setError("Full");
		}
	}
	
	// getting information from the server
	// input: if the member exists in the system: 
	// 		  		1. ArrayList<String> member with all the member data
	//        otherwise 2. string of "Not a member"
	// output: for case 1. we create new member with all the received details
	//         for case 2. we set the error message
	public static void receivedFromServerVisitorsPrice(Object order) {
		if (order instanceof Order) {
			ParkEmployeeController.randomVisitorFakeOrderDetails = (Order) order;
		} else {
			setError("Failed");
		}
	}

	// getting information from the server
	// input: boolean status
	// output: set error message with the following return
	public static void receivedFromServerCurrentVisitorsUpdateStatus(boolean status) {
		if (status) {
			setError("true");
		} else {
			setError("false");
		}
	}
	
	// getting information from the server
	// input: boolean status
	// output: set error message with the following return
	public static void receivedFromServerAmountArrivedStatus(boolean status) {
		if (status) {
			setError("true");
		} else {
			setError("false");
		}
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

	public static String getParkName() {
		return parkName;
	}

	public static void setParkName(String parkName) {
		ParkEmployeeController.parkName = parkName;
	}
	
	// enter random mode
	// input: random button has been pressed
	// output: screen changes
	@FXML
	void randomVisitor(ActionEvent event) {
		btnRandomVisitor.setVisible(false);
		lblDateTitle.setVisible(true);
		lblRandomDate.setVisible(true);
		lblTimeTitle.setVisible(true);
		lblRandomTime.setVisible(true);
		txtRandomVisitorsAmount.setVisible(true);
		txtIdOrMemberId.setVisible(true);
		
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime arrivelTime = LocalDateTime.now();
		lblRandomTime.setText(arrivelTime.format(time));
	}
	
	// turns off random mode
	// input: none
	// output: screen changes
	public void theParkIsOff() {
		btnApprove.setDisable(true);
		radExit.setSelected(true);
		radEnter.setDisable(true);
	}
	
	// turns off random mode
	// input: none
	// output: screen changes
	public void setRandomModeOff() {
		btnRandomVisitor.setVisible(true);
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		txtRandomVisitorsAmount.setVisible(false);
		txtIdOrMemberId.setVisible(false);
	}

	// clear the screen fields
	// input: none
	// output: none
	public void clearAllFields() {
		alert.setResult("");
		setError("");
		txtIdOrMemberId.clear();
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
		lblTotalPrice.setText("");
		txtRandomVisitorsAmount.clear();;
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		txtRandomVisitorsAmount.setVisible(false);
		btnApprove.setDisable(true);
		btnRandomVisitor.setVisible(true);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnApprove.setDisable(true);
		radEnter.setSelected(true);
		setRandomModeOff();
		radVisitorStatusText = "Enter";
		
		//setParkName(LoginController.getParkName());
		setParkName("jurasic");
		updateParkStatus(0);
		
		/***** Random *****/
		LocalDateTime arrivelDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		dateAndTimeFormat = arrivelDate.format(formatter);
		
		lblRandomDate.setText(arrivelDate.getDayOfMonth() 
				+ "-" + arrivelDate.getMonthValue() 
				+ "-" + arrivelDate.getYear());

		/***** Barcode / Regular *****/
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
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		/***** Random *****/
		// force the field to be numeric only
		txtRandomVisitorsAmount.textProperty().addListener((obs, oldValue, newValue) -> {
			btnApprove.setDisable(false);
			lblVisitorsAmount.setText(newValue);
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtRandomVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
				lblVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
		// force the field to be numeric only
		txtIdOrMemberId.textProperty().addListener((obs, oldValue, newValue) -> {
			btnApprove.setDisable(false);
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtIdOrMemberId.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		/***** Approve *****/
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
