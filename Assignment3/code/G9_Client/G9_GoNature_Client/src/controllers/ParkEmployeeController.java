package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
	private String timeFormat = "";
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
		} else if (txtVisitorsAmount.getText().charAt(0) == '0') {
			alert.failedAlert("Failed", "Number of visitors '0#' is invalid.");
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
		setPrice();
		
		if (radExit.isSelected()) {
			clearPaymentFields();
		}

		informationExists = false;
		btnApprove.setDisable(false);
	}

	// input: none
	// output: number of visitor/s that enter / leave the park
	@FXML
	void approve(ActionEvent event) {
		
		// random mode
		if (!btnRandomVisitor.isVisible()) {
			if (txtIdOrMemberId.getText().isEmpty() || txtRandomVisitorsAmount.getText().isEmpty()) {
				alert.failedAlert("Failed", "All fields required.");
				return;
			} else if (txtRandomVisitorsAmount.getText().charAt(0) == '0') {
				alert.failedAlert("Failed", "Number of visitors '0#' is invalid.");
				return;
			} else {
				/*** Enter ***/
				if (radEnter.isSelected()) {
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
			} else if (txtVisitorsAmount.getText().charAt(0) == '0') {
				alert.failedAlert("Failed", "Number of visitors '0#' is invalid.");
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
				if (radEnter.isSelected()) {		
					execEnter();	
				/*** Exit ***/
				} else {
					execExit();
				}
			}
		}	
		
		// update the number of visitors in the park
		updateParkStatus(0);
		clearAllFields();
	}
	
	// enter control to the park
	// input: none
	// output: updating the current visitors in the park 
	public void execEnter() {
		int tooManyVisitors = Integer.parseInt(txtVisitorsAmount.getText());	
		
		// check if the amount of "visitorsEntered" greater than the invitation.
		if (tooManyVisitors > Integer.parseInt(lblVisitorsNumber.getText())) {
				
			// check the available places in the park
			updateParkStatus(tooManyVisitors);
			
			if (getError().equals("Free")) {
				// the order is already used, need to make new purchase
				if (orderDetails.getAmountArrived() != 0) {
					// purchase as a random visitor
					lblVisitorsNumber.setText("");
					// alert to ensure that the employee didn't get typing wrong
					alert.ensureAlert("Ensure", "Are you sure you want to approve the purchase?\n"
							+ "You can check if the additional visitors have a membership in the random part,"
							+ " otherwise they will pay the full price for the entrance to the park.");
					if (alert.getResult().equals("OK")) {	
						// make an automatic purchase for the additional visitors
						execRandomVisitor(tooManyVisitors);				
					} 
				// the order is not used, just need to pay
				} else {
					tooManyVisitors -= Integer.parseInt(lblVisitorsNumber.getText());
					execRandomVisitor(tooManyVisitors);
					// updates the number of visitors who came for a specific order
					updateAmountArrived(Integer.parseInt(lblVisitorsNumber.getText()));
				}
			} 
		// amount of visitors is less than or equal to what is on the order
		} else {
			// check the available places in the park
			updateParkStatus(Integer.parseInt(txtVisitorsAmount.getText()));
			
			if (getError().equals("Free")) {
				// the order is already used, need to make new purchase
				if (orderDetails.getAmountArrived() != 0) {
					// purchase as a random visitor
					lblVisitorsNumber.setText("");
					// if this order is already listed in "Used", asked to make a purchase for everyone 
					alert.ensureAlert("Ensure", "Visitors to this order have already entered the park.\n"
							+ "Are you sure you want to approve the purchase for everyone?");
					if (alert.getResult().equals("OK")) {
						execRandomVisitor(Integer.parseInt(txtVisitorsAmount.getText()));
					}
				// the order is not used, just need to pay
				} else {
					// updates the number of visitors who came for a specific order
					updateAmountArrived(Integer.parseInt(txtVisitorsAmount.getText()));	
					// update current visitors
					updateCurrentVisitors(parkDetails.getCurrentAmount() + 
										  Integer.parseInt(txtVisitorsAmount.getText()));	
					alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s entered.");	
				}
			}
		}
	}
	
	// checking the entrance of the random visitor
	// sending to the server to update the current visitors amount
	// input: new current visitors 
	// output: updating the current visitors in the park 
	public void execRandomVisitor(int visitorsAmount) {
		int updateCurrentVisitors = visitorsAmount;
		
		// update park status
		updateParkStatus(updateCurrentVisitors);
				
		// in case with order
		if (!lblVisitorsNumber.getText().isEmpty()) {
			updateCurrentVisitors += Integer.parseInt(lblVisitorsNumber.getText());
			// update park status
			updateParkStatus(updateCurrentVisitors);
				
			// checking for places in the park
			if (getError().equals("Free")) {
				// update current visitors
				updateCurrentVisitors(parkDetails.getCurrentAmount() + updateCurrentVisitors);
				createFakeOrder(visitorsAmount);
				alert.successAlert("Success", 
						Integer.parseInt(lblVisitorsNumber.getText()) + " visitor/s with order.\n"
						+ String.valueOf(visitorsAmount) + " casual visitor/s, entered.");
			}
		// in case without order (only random visitors)
		} else {
			// checking for places in the park
			if (getError().equals("Free")) {
				// update current visitors
				updateCurrentVisitors(parkDetails.getCurrentAmount() + updateCurrentVisitors);
				createFakeOrder(visitorsAmount);
				alert.successAlert("Success", String.valueOf(visitorsAmount) + " visitor/s entered.");	
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
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtRandomVisitorsAmount.getText());
			
			if (updateCurrentVisitors < 0) {
				alert.failedAlert("Failed", "The amount of visitors is lower than the number existing in the park.");
			} else {
				// update current visitors
				alert.successAlert("Success", txtRandomVisitorsAmount.getText() + " visitor/s leaved.");
				updateCurrentVisitors(updateCurrentVisitors);		
			}
		// barcode / regular mode
		} else {
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtVisitorsAmount.getText());
			
			if (updateCurrentVisitors < 0) {
				alert.failedAlert("Failed", "The amount of visitors is lower than the number existing in the park.");
			} else {
				// update current visitors
				alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s leaved.");	
				updateCurrentVisitors(updateCurrentVisitors);		
			}
		}	
	}
	public void createFakeOrder(int visitorsAmount) {
		Order fakeOrder;
		String id = null;
		String memberId = null;		
		double discount = 0;	
		String tempDate = dateAndTimeFormat;
		
		// format time
		checkTime();
		tempDate += timeFormat;
		// after calling the function, dateAndTimeFormat = "yyyy-MM-dd HH:mm:ss"
			
		// if there is no order
		if (lblVisitorsNumber.getText().isEmpty()) {
			/***** Random *****/
			if (!btnRandomVisitor.isVisible()) {	
				if (txtIdOrMemberId.getText().length() == 9) {
					id = txtIdOrMemberId.getText();
				} else {
					memberId = txtIdOrMemberId.getText();
				}
				// calculates the prices
				lblPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getPrice()) + "₪");
				discount = (1 - (randomVisitorFakeOrderDetails.getTotalPrice() / randomVisitorFakeOrderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", discount) + "%");			
				lblTotalPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getTotalPrice()) + "₪");
			} 		
		} else {
			setPriceForOrdering();
		}
		
		fakeOrder = new Order(getParkName(), tempDate, memberId, id, visitorsAmount);
			
		// check for a member with fake order
		sendToServerObject("randomVisitorFakeOrder", fakeOrder);
		// create new order for the random visitors
		sendToServerObject("confirmOrder", fakeOrder);
	}
	
	// updates prices
	// input: none
	// output: prints the latest data for payment
	public void setPrice() {	
			
			
		
			
		
	}
	
	// updates prices for ordered visitors
	// input: none
	// output: prints the latest data for payment
	public void setPriceForOrdering() {
		int difference = 0;
		double discount = 0;
		double price = 0;
		double totalPrice = 0;
		
		difference = Integer.parseInt(txtVisitorsAmount.getText()) - Integer.parseInt(lblVisitorsNumber.getText());
		if (difference <= 0) {
			price = Integer.parseInt(txtVisitorsAmount.getText()) * parkDetails.getEnteryPrice();
		} else {
			price = difference * parkDetails.getEnteryPrice();
		}
		totalPrice = price * (((100 - (double) parkDetails.getMangerDiscount()) / 100));	
		
		if (difference > 0) {
			discount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
			
			// the order is not used, pay according to the order details
			if (orderDetails.getAmountArrived() == 0) {
				// set price
				lblPrice.setText(String.format("%.1f", orderDetails.getPrice()) + "₪ , " 
							   + String.format("%.1f", price) + "₪");
				// set discount
				lblDiscount.setText(String.format("%.1f", discount) + "% , "
								  + String.format("%.1f", (double) parkDetails.getMangerDiscount()) + "%");	
				// set total price
				price += orderDetails.getTotalPrice();
				lblTotalPrice.setText(String.format("%.1f", orderDetails.getTotalPrice()) + "₪ + " 
							   + String.format("%.1f", totalPrice) + "₪ = " 
							   + String.format("%.1f", price) + "₪");
				return;
			}
	
		// we check if the order is already "used"
		} else {
			// the order is not used, pay according to the order details
			if (orderDetails.getAmountArrived() == 0) {
				lblPrice.setText(String.format("%.1f", orderDetails.getPrice()) + "₪");
				discount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", discount) + "%");
				lblTotalPrice.setText(String.format("%.1f", orderDetails.getTotalPrice()) + "₪");
				return;
			}
		}
	
		// the order is already used, pay full price
		// set price
		lblPrice.setText(String.format("%.1f", price) + "₪");
		// set discount
		lblDiscount.setText(String.format("%.1f", (double) parkDetails.getMangerDiscount()) + "%");	
		// set total price
		lblTotalPrice.setText(String.format("%.1f", totalPrice) + "₪");
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
		timeFormat = "";
		int arrivelHour;
		LocalDateTime arrivelTime = LocalDateTime.now();
		// currentHour = hh
		int currentHour = arrivelTime.getHour();
		// startTime = hh:mm
		if (!lblTime.getText().isEmpty()) {
			String startTime = lblTime.getText();
			String[] splitStartTime = startTime.split(":");
			// stringArrivelHour = hh
			String stringArrivelHour = splitStartTime[0];
			arrivelHour = Integer.parseInt(stringArrivelHour);
		} else {
			arrivelHour = 0;
		}

		// currentHour = 08:00 | 12:00 | 16:00
		// arrivelHour = 08:00 - 12:00 | 12:00 - 16:00 | 16:00 - 20:00
		if (currentHour >= 8 && currentHour < 12) {
			timeFormat = " 08:00:00";
			if (arrivelHour == 8) {
				return true;
			}
		} else if (currentHour >= 12 && currentHour < 16) {
			timeFormat = " 12:00:00";
			if (arrivelHour == 12) {
				return true;
			}
		} else if (currentHour >= 16 && currentHour < 20) {
			timeFormat = " 16:00:00";
			if (arrivelHour == 16) {
				return true;
			}
		}
		
		if (arrivelHour == 0) {
			return false;
		}

		alert.failedAlert("Failed", "Arrival time doesn't match the time on order.");
		return false;
	}
	
	// input: number of visitor to update
	// output: updates the number of visitors who came for a specific order
	public void updateAmountArrived(int amountArrivedInOrder) {
		// ArrayList<String> data
		// 		  cell 0: orderNumber
		//        cell 1: new arrived amount (updated one)
		ArrayList<String> data = new ArrayList<String>();
		data.add(String.valueOf(orderDetails.getOrderNumber()));
		data.add(String.valueOf(amountArrivedInOrder));
		sendToServerArrayList("updateAmountArrived", data);
		
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
		
		// the park is empty
		if (parkDetails.getCurrentAmount() == 0) {
			theParkIsFullOrEmpty("Empty");
		} else {
			radExit.setDisable(false);
		}
		
		// the park is full
		if (getError().equals("Full")) {
			theParkIsFullOrEmpty("Full");
			return;
		} 
		
		// too many visitors (check after enter)
		if (getError().equals("Greater") && radEnter.isSelected()) {
			alert.failedAlert("Failed", "The amount of visitors is greater than the number existing in the park.");
			return;
		} 
		
		// too few visitors (check after exit)
		if (getError().equals("Lower") && radExit.isSelected()) {
			alert.failedAlert("Failed", "The amount of visitors is lower than the number existing in the park.");
			return;
		} 
		
		setError("Free");
		lblCurrentVisitors.setText("[" + getParkName() + "]:  " 
				+ String.valueOf(parkDetails.getCurrentAmount()) + "/" 
				+ parkDetails.getMaximumCapacityInPark());
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

			if (radEnter.isSelected()) {
				lblVisitorsAmount.setText(txtVisitorsAmount.getText());
				lblPrice.setText(orderDetails.getPrice() + "₪");
				discount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", discount) + "%");
				lblTotalPrice.setText(orderDetails.getTotalPrice() + "₪");
			}

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
		} else if (park instanceof String) {
			if (((String) park).equals("Full")) {
				setError("Full");
			} else if (((String) park).equals("Lower")) {
				setError("Lower");
			} else if (((String) park).equals("Greater")) {
				setError("Greater");
			}
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

	// acceptance status 'CurrentVisitorsUpdate'
	// input: boolean status
	// output: set error message with the following return
	public static void receivedFromServerCurrentVisitorsUpdateStatus(boolean status) {
		if (status) {
			setError("true");
		} else {
			setError("false");
		}
	}
	
	// acceptance status 'amountArrived'
	// input: boolean status
	// output: set error message with the following return
	public static void receivedFromServerAmountArrivedStatus(boolean status) {
		if (status) {
			setError("true");
		} else {
			setError("false");
		}
	}
	
	// acceptance status 'add order'
	// input: boolean status
	// output: set error message with the following return
	public static void receivedFromServerAddFakeOrder(boolean status) {
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
		clearAllOrderFields();
		
		DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime arrivelTime = LocalDateTime.now();
		lblRandomTime.setText(arrivelTime.format(time));
	}
	
	// turns off random mode
	// input: none
	// output: screen changes
	public void theParkIsFullOrEmpty(String status) {
		if (status.equals("Full")) {
			btnApprove.setDisable(true);
			radExit.setSelected(true);
			radEnter.setDisable(true);
		} else if (status.equals("Empty")) {
			btnApprove.setDisable(true);
			radEnter.setSelected(true);
			radExit.setDisable(true);
		}
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
		lblParkName.setText("");
		lblDate.setText("");
		lblTime.setText("");
		lblVisitorsNumber.setText("");
		lblEmail.setText("");
		lblVisitorsAmount.setText("");
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
		txtRandomVisitorsAmount.clear();
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		txtRandomVisitorsAmount.setVisible(false);
		btnApprove.setDisable(true);
		btnRandomVisitor.setVisible(true);
	}
	
	// turns off order mode
	// input: none
	// output: screen changes
	public void clearAllOrderFields() {
		txtOrderNumber.clear();
		lblOrderNumber.setText("");
		txtVisitorsAmount.clear();
		lblParkName.setText("");
		lblDate.setText("");
		lblTime.setText("");
		lblVisitorsNumber.setText("");
		lblEmail.setText("");
		lblVisitorsAmount.setText("");
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
	}
	
	// turns off payment mode (on exit)
	// input: none
	// output: screen changes
	public void clearPaymentFields() {
		lblVisitorsAmount.setText("");
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnApprove.setDisable(true);
		radEnter.setSelected(true);
		setRandomModeOff();
		
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
			
			if (!newValue.isEmpty() && newValue.charAt(0) != '0') {
				lblVisitorsAmount.setText(newValue);
				// update prices
				setPrice();
			}
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtRandomVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
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
				((RadioButton) radGroupStatus.getSelectedToggle()).getText();
			} else if (newToggle == radExit) {
				clearPaymentFields();
				((RadioButton) radGroupStatus.getSelectedToggle()).getText();
			}
		});
	}
}
