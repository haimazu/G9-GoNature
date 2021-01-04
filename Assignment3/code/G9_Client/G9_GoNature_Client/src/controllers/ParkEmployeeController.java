package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import orderData.Order;
import orderData.OrderType;
import sim.BarcodeSimulation;

public class ParkEmployeeController implements Initializable {
	/***** Top/Side Panels Details *****/
	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnAccessControl;
	@FXML
	private Button btnSettings;
	@FXML
    private Pane pnSettings;
	@FXML
    private Pane pnAccessControl;

	/***** Current Park Details *****/
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblCurrentVisitors;

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
    private Button btnManualAccess;
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
//	@FXML
//	private JFXTextField txtRandomVisitorsAmount;

	/***** Bottom Right Screen *****/
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
	private boolean orderStatus = false;
	private boolean approveIsPressed = false;
	private static String firstName;
	private static String parkName;
	private static Order orderDetails;
	private static Order randomVisitorFakeOrderDetails;
	private static Park parkDetails;
	private static String error = "";

	// input: none
	// output: moving to 'login' screen
	@FXML
	void logout(ActionEvent event) throws IOException {
		// Data fields
		ArrayList<String> data = new ArrayList<String>();
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();	
				
		msg.add("updateLoggedIn");
		// update as loggedin as logged out
		data.add(LoginController.getUsername());
		data.add(String.valueOf(0));
		// Data fields
		msg.add(data);	
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
				
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}
	
	@FXML
	public void handleSideBar(ActionEvent event) {
		if (event.getSource() == btnAccessControl) {
			lblTitle.setText("Access Control");
			pnAccessControl.toFront();
			setButtonPressed(btnAccessControl);
			setButtonReleased(btnSettings);
		} else if (event.getSource() == btnSettings) {
			lblTitle.setText("Visits Report");
			pnSettings.toFront();
			setButtonPressed(btnSettings);
			setButtonReleased(btnAccessControl);
		} 
	}
	
	public void setButtonPressed(Button button) {
		button.setStyle("-fx-background-color: transparent;" 
					  + "-fx-border-color: brown;"
				      + "-fx-border-width: 0px 0px 0px 3px;");
	}

	public void setButtonReleased(Button button) {
		button.setStyle("-fx-background-color: transparent;");
	}

	// input: Id / memberId
	// output: order details
	@FXML
	void barcodeScan(ActionEvent event) {
		informationExists = true;
		// get order number from the server
		// call showDetails() function to set up all the order details

		String fromSimulator = BarcodeSimulation.getSim().read(3);
				
		// memberId case: memberId = G2 ==> memberId = 2
		if (Character.isLetter(fromSimulator.charAt(0))) {
			//fromSimulator = memberId.substring(1);
			fromSimulator.substring(1);
		} 

		sendToServerArrayList("ordersByIdOrMemberId", new ArrayList<String>(Arrays.asList(fromSimulator)));

		if (getError().equals("No such order")) {
			alert.failedAlert("Failed", "No such order.");
			clearAllFields();
			return;
		}

		txtOrderNumber.setText(String.valueOf(orderDetails.getOrderNumber()));
		txtVisitorsAmount.setText(String.valueOf(orderDetails.getVisitorsNumber()));

		showDetails(event);
	}

	// input: order number
	// output: order details
	@FXML
	void showDetails(ActionEvent event) {
		if (btnManualAccess.isVisible()) {
			setRandomModeOff();
			return;
		}
		
		if (txtOrderNumber.getText().isEmpty()) {
			alert.failedAlert("Failed", "You must enter order number.");
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
		
		if (radExit.isSelected()) {
			clearPaymentFields();
		}

		informationExists = false;
		btnApprove.setDisable(false);
		orderStatus = true;
		approveIsPressed = false;
	}

	// input: none
	// output: number of visitor/s that enter / leave the park
	@FXML
	void approve(ActionEvent event) {
		approveIsPressed = true;
		
		// random mode
		if (!btnRandomVisitor.isVisible()) {
			if (txtIdOrMemberId.getText().isEmpty()) {
				alert.failedAlert("Failed", "You must enter id/memberid.");
				return; 
			} else if (txtVisitorsAmount.getText().isEmpty()) {
				alert.failedAlert("Failed", "You must enter amount of visitors.");
				return;
			} else if (txtVisitorsAmount.getText().charAt(0) == '0') {
				alert.failedAlert("Failed", "Number of visitors '0#' is invalid.");
				return;
			} else {
				/*** Enter ***/
				if (radEnter.isSelected()) {
					orderStatus = false;
					execRandomVisitor(Integer.parseInt(txtVisitorsAmount.getText()));
				/*** Exit ***/
				} else {
					execExit();
				}
			}
		// barcode / regular entry
		} else {
			if (txtOrderNumber.getText().isEmpty()) {
				alert.failedAlert("Failed", "All fields required.");
				return;
			} else if (txtVisitorsAmount.getText().isEmpty()) {
				alert.failedAlert("Failed", "You must enter amount of visitors.");
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
							
			/*** Enter ***/ // check date and time
			if (radEnter.isSelected() && checkDate() && checkTime("approve")) {	
				orderStatus = true;
				execEnter();	
				/*** Exit ***/
			} else {
				execExit();
			}
		}	
		
		orderStatus = false;
		// update park status
		updateParkStatus(0);
		clearAllFields();
	}
	
	// checking the entrance of the random visitor
	// sending to the server to update the current visitors amount
	// input: new current visitors 
	// output: updating the current visitors in the park 
	public void execRandomVisitor(int visitorsAmount) {
		int updateCurrentVisitors = visitorsAmount;
		
		// update park status (for the 'else' part)
		updateParkStatus(updateCurrentVisitors);
				
		// in case with order (the random visitors are the friends)
		if (orderStatus) {
			updateCurrentVisitors += Integer.parseInt(lblVisitorsNumber.getText());
			// update park status
			updateParkStatus(updateCurrentVisitors);
				
			// checking for places in the park
			if (getError().equals("Free")) {
				// update current visitors
				updateCurrentVisitors(parkDetails.getCurrentAmount() + updateCurrentVisitors);
				createFakeOrder("0", null, visitorsAmount);
				// update the exact entry and exit time, for the ordered visitors && for the random friends
				updateAccessControl(orderDetails.getOrderNumber(), orderDetails.getOrderType().label,
						Integer.parseInt(lblVisitorsNumber.getText()));
				updateAccessControl(randomVisitorFakeOrderDetails.getOrderNumber(), randomVisitorFakeOrderDetails.getOrderType().label,
						visitorsAmount);
				alert.successAlert("Success", 
						Integer.parseInt(lblVisitorsNumber.getText()) + " visitor/s with order.\n"
						+ String.valueOf(visitorsAmount) + " casual visitor/s, entered.");
			}
		// in case without order (only random visitors)
		} else {
			// checking for places in the park
			if (getError().equals("Free")) {
				// check if the random visitor have a fake order 
				if (checkForExistingFakeOrders()) {		
					if (randomVisitorFakeOrderDetails.getOrderType().equals(OrderType.GROUP) && visitorsAmount > 15) {
						alert.failedAlert("Failed", "You are registered as a guide, your group is up to 15 visitors.");
						clearPaymentFields();
						return;
					}
				}
				// update current visitors
				updateCurrentVisitors(parkDetails.getCurrentAmount() + visitorsAmount);
				// update the exact entry and exit time
				updateAccessControl(randomVisitorFakeOrderDetails.getOrderNumber(), randomVisitorFakeOrderDetails.getOrderType().label,
						visitorsAmount);
				alert.successAlert("Success", String.valueOf(visitorsAmount) + " visitor/s entered.");				
			}
		}		
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
					orderStatus = false;
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
					orderStatus = true;
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
					orderStatus = false;
					// if this order is already listed in "Used", asked to make a purchase for everyone 
					alert.ensureAlert("Ensure", "Visitors to this order have already entered the park.\n"
							+ "Are you sure you want to approve the purchase for everyone?");
					if (alert.getResult().equals("OK")) {
						execRandomVisitor(Integer.parseInt(txtVisitorsAmount.getText()));
					}
				// the order is not used, just need to pay
				} else {
					orderStatus = true;
					// updates the number of visitors who came for a specific order
					updateAmountArrived(Integer.parseInt(txtVisitorsAmount.getText()));	
					// update current visitors
					updateCurrentVisitors(parkDetails.getCurrentAmount() + 
										  Integer.parseInt(txtVisitorsAmount.getText()));	
					// update the exact entry and exit time
					updateAccessControl(orderDetails.getOrderNumber(), orderDetails.getOrderType().label,
							Integer.parseInt(txtVisitorsAmount.getText()));
					alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s entered.");	
				}
			}
		}
	}
	
	// exit control from the park
	// input: none
	// output: updating the current visitors in the park 
	public void execExit() {
		int updateCurrentVisitors = 0;
		String memberId = "";
		
		// update places in the park
		updateParkStatus(0);
					
		// random mode
		if (!btnRandomVisitor.isVisible()) {
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtVisitorsAmount.getText());

			// there are not enough visitors to the park
			if (updateCurrentVisitors < 0) {
				alert.failedAlert("Failed", "The amount of visitors is lower than the number existing in the park.");	
				return;
			}
							
			// memberId case
			if (Character.isLetter(txtIdOrMemberId.getText().charAt(0))) {
				memberId = txtIdOrMemberId.getText().substring(1);
				sendToServerArrayList("ordersByIdOrMemberId", new ArrayList<String>(Arrays.asList(memberId)));
			// id case
			} else {
				sendToServerArrayList("ordersByIdOrMemberId", new ArrayList<String>(Arrays.asList(txtIdOrMemberId.getText())));
			}			
					
			// the visitors did not fulfilled the invitation
			if (getError().equals("Failed")) {
				alert.failedAlert("Failed", "The visitor/s did not enter.");
				clearAllFields();
				return;
			}
			
			// number of leavers <= number of entered
			if (randomVisitorFakeOrderDetails.getAmountArrived() != 0) {
				// update the exact entry and exit time
				updateAccessControl(randomVisitorFakeOrderDetails.getOrderNumber(), randomVisitorFakeOrderDetails.getOrderType().label, 0);
				// update current visitors
				updateCurrentVisitors(updateCurrentVisitors);	
				alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s leaved.");	
			} 
		// barcode / regular mode
		} else {
			updateCurrentVisitors = parkDetails.getCurrentAmount() - Integer.parseInt(txtVisitorsAmount.getText());	
			
			// there are not enough visitors to the park
			if (updateCurrentVisitors < 0) {
				alert.failedAlert("Failed", "The amount of visitors is lower than the number existing in the park.");	
				return;
			}
			
			// number of leavers <= number of entered
			if (orderDetails.getAmountArrived() != 0) {
				// update the exact entry and exit time
				updateAccessControl(orderDetails.getOrderNumber(), orderDetails.getOrderType().label, 0);				
				// update current visitors
				updateCurrentVisitors(updateCurrentVisitors);	
				alert.successAlert("Success", txtVisitorsAmount.getText() + " visitor/s leaved.");	
			} 
		} 
	}
	
	// updates fake order 
	// input: none
	// output: T / F ==> if exists return true
	//         			 otherwise creates new 'fake order' and return false
	public boolean checkForExistingFakeOrders() {	
		String id = null;
		String memberId = "0";
		String currentValue = "";
		
		/***** Random *****/
		if (!orderStatus && !btnRandomVisitor.isVisible()) {		
			if (Character.isLetter(txtIdOrMemberId.getText().charAt(0))) {
				memberId = txtIdOrMemberId.getText().substring(1);
				currentValue = memberId;
			} else {
				id = txtIdOrMemberId.getText();
				currentValue = id;
			}
			createFakeOrder(memberId, id, Integer.parseInt(txtVisitorsAmount.getText()));	
		} else {
			createFakeOrder(memberId, id, Integer.parseInt(txtVisitorsAmount.getText()));							
		}
			
		sendToServerArrayList("ordersByIdOrMemberId", new ArrayList<String>(Arrays.asList(currentValue)));

		if (getError().equals("No such order")) {
			return false;
		} 
		return true;
	}
	
	// updates prices for ordered visitors
	// input: none
	// output: prints the latest data for payment
	public void setPriceForOrdering() {
		Order fakeOrder;
		int difference = 0;
		double orderVisitorDiscount = 0;
		double randomVisitorDiscount = 0;
		String tempDate = dateAndTimeFormat;
		
		// format time
		checkTime("setPrice");
		tempDate += timeFormat;
		// after calling the function, dateAndTimeFormat = "yyyy-MM-dd HH:mm:ss"
		
		difference = Integer.parseInt(txtVisitorsAmount.getText()) - Integer.parseInt(lblVisitorsNumber.getText());
		// visitorsAmount <= visitorsNumber in order
		if (difference <= 0) {
			// order not used
			if (orderDetails.getAmountArrived() == 0) {
				lblPrice.setText(String.format("%.1f", orderDetails.getPrice()) + "₪");
				orderVisitorDiscount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", orderVisitorDiscount) + "%");
				lblTotalPrice.setText(String.format("%.1f", orderDetails.getTotalPrice()) + "₪");
			// order used
			} else {
				createFakeOrder("0", null, Integer.parseInt(txtVisitorsAmount.getText()));
			}
		// visitorsAmount > visitorsNumber in order
		} else {
			// order not used
			if (orderDetails.getAmountArrived() == 0) {
				// id = memberId = null
				fakeOrder = new Order(getParkName(), tempDate, "0", null, difference);
				
				// check for a member with fake order
				sendToServerObject("randomVisitorFakeOrder", fakeOrder);
				// create new order for the random visitors (add to DB only if pressed 'Approve')
				if (approveIsPressed) {
					sendToServerObject("addFakeOrder", randomVisitorFakeOrderDetails);
					approveIsPressed = false;
				}
				
				// set price
				lblPrice.setText(String.format("%.1f", orderDetails.getPrice()) + "₪ , " 
							   + String.format("%.1f", randomVisitorFakeOrderDetails.getPrice()) + "₪");
				// set discount
				orderVisitorDiscount = (1 - (orderDetails.getTotalPrice() / orderDetails.getPrice())) * 100;
				randomVisitorDiscount = (1 - (randomVisitorFakeOrderDetails.getTotalPrice() / randomVisitorFakeOrderDetails.getPrice())) * 100;
				lblDiscount.setText(String.format("%.1f", orderVisitorDiscount) + "% , "
								  + String.format("%.1f", randomVisitorDiscount) + "%");	
				// set total price
				lblTotalPrice.setText(String.format("%.1f", orderDetails.getTotalPrice()) + "₪ + " 
							   + String.format("%.1f", randomVisitorFakeOrderDetails.getTotalPrice()) + "₪ = " 
							   + String.format("%.1f", (orderDetails.getTotalPrice() + randomVisitorFakeOrderDetails.getTotalPrice())) + "₪");
			// order used
			} else {
				createFakeOrder("0", null, Integer.parseInt(txtVisitorsAmount.getText()));
			}
		}
	}
	
	// create fake order for the random visitors (for statistics)
	// input: [0] case name 
	//        [1] Order object
	// output: get the new order details and prints the prices on the screen
	public void createFakeOrder(String memberId, String id, int amountArrived) {
		Order fakeOrder;
		double randomVisitorDiscount = 0;
		String tempDate = dateAndTimeFormat;
		
		// format time
		checkTime("setPrice");
		tempDate += timeFormat;
		// after calling the function, dateAndTimeFormat = "yyyy-MM-dd HH:mm:ss"
		
		// id = memberId = null
		fakeOrder = new Order(getParkName(), tempDate, memberId, id, amountArrived);
		
		// check for a member with fake order
		sendToServerObject("randomVisitorFakeOrder", fakeOrder);
		
		if (randomVisitorFakeOrderDetails.getOrderType().equals(OrderType.GROUP) && amountArrived > 15) {
			return;
		}
		
		// create new order for the random visitors (add to DB only if pressed 'Approve')
		if (approveIsPressed) {
			sendToServerObject("addFakeOrder", randomVisitorFakeOrderDetails);
			approveIsPressed = false;
		}
		
		lblPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getPrice()) + "₪");
		randomVisitorDiscount = (1 - (randomVisitorFakeOrderDetails.getTotalPrice() / randomVisitorFakeOrderDetails.getPrice())) * 100;
		lblDiscount.setText(String.format("%.1f", randomVisitorDiscount) + "%");			
		lblTotalPrice.setText(String.format("%.1f", randomVisitorFakeOrderDetails.getTotalPrice()) + "₪");
	}

	// check for valid date in the order
	// input: [0] 'today's date' 
	//        [1] order date
	// output: return true if that's today's date
	//         otherwise false
	public boolean checkDate() {
		LocalDate arrivelDate = LocalDate.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		// "dd-MM-yyyy"
		String currentDate = dateFormatter.format(arrivelDate);
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
	public boolean checkTime(String from) {
		timeFormat = "";
		int arrivelHour = 0;
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
			timeFormat = " 16:00:00";//TODO
			if (arrivelHour == 16) {
				return true;
			}
		}
		
		if (from.equals("setPrice")) {
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
	
	// ArrayList<String> data, sending to the server to update the access control
	// input: on enter: cell 0: orderNumber
	//        			cell 1: entryTime / exitTime
	//        			cell 2: parkName
	//        			cell 3: orderType
	//                  cell 4: amountArrived
	// output: message with the result of the update: true if success
	//                                                false, otherwise
	public void updateAccessControl(int orderNumber, String orderType, int amountArrived) {
		DateTimeFormatter dateAndTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime currentTime = LocalDateTime.now();
		
		ArrayList<String> data = new ArrayList<String>();

		data.add(String.valueOf(orderNumber));
		data.add(currentTime.format(dateAndTime));
		data.add(getParkName());
		data.add(orderType);
		data.add(String.valueOf(amountArrived));

		sendToServerArrayList("updateAccessControl", data);

		// check if the update failed and showing alert
		if (getError().equals("false")) {
			alert.failedAlert("Failed", "Sorry, we couldn't do the update.");
			return;
		}
	}
	
	// input: none
	// output: update the park title with the current visitors
	public void updateParkStatus(int addToEnter) {
		ArrayList<String> data = new ArrayList<String>();
		data.add(getParkName());
		data.add(String.valueOf(addToEnter));
		sendToServerArrayList("getParkDetails", data);
		
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
		if (approveIsPressed && getError().equals("Greater") && radEnter.isSelected()) {
			alert.failedAlert("Failed", "The amount of visitors is greater than the number existing in the park.");
			approveIsPressed = false;
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
			lblEmail.setText(orderDetails.getOrderEmail());
			// random visitor order
			if (orderDetails.getOrderEmail().equals("null")) {
				lblVisitorsNumber.setText(String.valueOf(orderDetails.getAmountArrived()));
			} else {
				lblVisitorsNumber.setText(String.valueOf(orderDetails.getVisitorsNumber()));				
			}

			if (!txtVisitorsAmount.getText().isEmpty()) {
				setPriceForOrdering();				
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
		msg.add(null);
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
	public static void receivedFromServerOrderDetails(Object msg) {
		if (msg instanceof Order) {
			ParkEmployeeController.orderDetails = (Order) msg;
		} else {
			setError((String) msg);
		}
	}

	// getting information from the server
	// input: ArrayList<String> park with all the park data
	// output: new park
	public static void receivedFromServerParkDetails(Object msg) {
		if (msg instanceof Park) {
			ParkEmployeeController.parkDetails = (Park) msg;			
		} else if (msg instanceof String) {
			if (((String) msg).equals("Full")) {
				setError("Full");
			} else if (((String) msg).equals("Lower")) {
				setError("Lower");
			} else if (((String) msg).equals("Greater")) {
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
	public static void receivedFromServerVisitorsPrice(Object msg) {
		if (msg instanceof Order) {
			ParkEmployeeController.randomVisitorFakeOrderDetails = (Order) msg;
		} else {
			setError((String) msg);
		}
	}
	
	// acceptance status
	// input: boolean status
	// output: set error message with the following return
	// T / F ==> T if succeeded
	//           F otherwise
	public static void receivedFromServerUpdateStatus(boolean status) {
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
		btnManualAccess.setVisible(true);
		btnRandomVisitor.setVisible(false);
		lblDateTitle.setVisible(true);
		lblRandomDate.setVisible(true);
		lblTimeTitle.setVisible(true);
		lblRandomTime.setVisible(true);
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
		btnManualAccess.setVisible(false);
		btnRandomVisitor.setVisible(true);
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
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
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
		lblDateTitle.setVisible(false);
		lblRandomDate.setVisible(false);
		lblTimeTitle.setVisible(false);
		lblRandomTime.setVisible(false);
		btnApprove.setDisable(true);
		btnRandomVisitor.setVisible(true);
		orderStatus = false;
		approveIsPressed = false;
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
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
		orderStatus = false;
		approveIsPressed = false;
	}
	
	// turns off payment mode (on exit)
	// input: none
	// output: screen changes
	public void clearPaymentFields() {
		lblPrice.setText("");
		lblDiscount.setText("");
		lblTotalPrice.setText("");
		orderStatus = false;
		approveIsPressed = false;
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
		
		lblRandomDate.setText(arrivelDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

		/***** Barcode / Regular *****/
		// force the field to be numeric only
		txtOrderNumber.textProperty().addListener((obs, oldValue, newValue) -> {
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.isEmpty() && !newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtOrderNumber.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
		// force the field to be numeric only
		txtVisitorsAmount.textProperty().addListener((obs, oldValue, newValue) -> {	
			btnApprove.setDisable(false);
			
			if (!newValue.isEmpty() && newValue.charAt(0) != '0' && !btnRandomVisitor.isVisible()) {
				// update prices
				checkForExistingFakeOrders();
			}
			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.isEmpty() && !newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtVisitorsAmount.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});

		/***** Random *****/	
		// force the field to be numeric only
		txtIdOrMemberId.textProperty().addListener((obs, oldValue, newValue) -> {
			btnApprove.setDisable(false);

			if (!newValue.isEmpty()) {
				txtIdOrMemberId.setText(newValue);
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
