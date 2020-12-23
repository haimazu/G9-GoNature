package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import orderData.Order;
import javafx.scene.control.Label;

public class ManageOrderController implements Initializable {
	@FXML
	private Label lblPrice;
	@FXML
	private Label lblParkName;

	@FXML
	private Label lblDate;
	@FXML
	private Label lblOrderNum;
	@FXML
	private Label lblTime;

	@FXML
	private Label lblDiscount;

	@FXML
	private Label lblPayment;

	@FXML
	private Label lblTotal;
	@FXML
	private Label lblVisitors;
	@FXML
	private Label lblMail;
	@FXML
	private Hyperlink lnkSwitch;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnCancel;
	@FXML
	private JFXTextField txtVisitorsNumber;

	@FXML
	private JFXDatePicker txtdate;

	@FXML
	private JFXComboBox<String> cbxArriveTime;

	private AlertController alert = new AlertController();
	private static Order order = null;
	private static boolean updated = false;

	public static Order getOrder() {
		return order;
	}

	public static void setOrder(Order order) {
		ManageOrderController.order = order;
	}

	/*
	 * input : received order object from server Output : non present on screen:
	 * order details
	 * 
	 */
	void presentOrderdetails(Order details) {

		String DateAndTime = details.getArrivedTime();
		String[] splitDateAndTime = DateAndTime.split(" ");

		String date = splitDateAndTime[0];

		DateFormat iFormatter = new SimpleDateFormat("yyyy-MM-dd");

		DateFormat oFormatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			String strDateTime = oFormatter.format(iFormatter.parse(date));

			String time = (String) splitDateAndTime[1].subSequence(0, 5);

			lblOrderNum.setText(String.valueOf(details.getOrderNumber()));
			lblParkName.setText(details.getParkName());
			lblDate.setText(strDateTime);
			lblTime.setText(time);
			lblVisitors.setText(String.valueOf(details.getVisitorsNumber()));
			lblMail.setText(details.getOrderEmail());
			lblPrice.setText(details.getPrice() + "₪");
			this.lblTotal.setText(String.valueOf(details.getTotalPrice()));
			double discValue = (1 - (details.getTotalPrice() / details.getPrice())) * 100;
			this.lblDiscount.setText(String.format("%.1f", discValue) + "%");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxArriveTime.setItems(FXCollections.observableArrayList("8:00-12:00", "12:00-16:00", "16:00-20:00"));
		txtVisitorsNumber.setText(String.valueOf(WelcomeController.getOrderDetails().getVisitorsNumber()));
		cbxArriveTime.getSelectionModel().selectFirst();
		setOrder(WelcomeController.getOrderDetails());
		presentOrderdetails(order);

		txtdate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		txtdate.setValue(LocalDate.now());
	}

	@FXML
	void back(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}

	/*
	 * input : non output : non send to server : Array list of objects : [0]->
	 * string for server : editOrder , [1]-> updated order object , [2]-> old order
	 * object
	 */
	@FXML
	void update(ActionEvent event) {
		Order sentOrder = order; // maybe a pointer ???
		if (checkNotEmptyVisitorsField() && checkCurrentTime()) {
			String[] timeString = cbxArriveTime.getValue().toString().split("-");
			System.out.println(timeString[0]);
			String clientDateTime = (txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " "
					+ timeString[0]);
			String orderDateTime = sentOrder.getArrivedTime();
			int clientVisitorsNumber = Integer.parseInt(txtVisitorsNumber.getText());
			int orderVisitorsNumber = sentOrder.getVisitorsNumber();
			if ((!clientDateTime.equals(orderDateTime)) || (clientVisitorsNumber != orderVisitorsNumber)) {
				sentOrder.setArrivedTime(clientDateTime);
				sentOrder.setVisitorsNumber(clientVisitorsNumber);
				ArrayList<Object> msgForServer = new ArrayList<>();
				msgForServer.add("editOrder");
				msgForServer.add(sentOrder);
				msgForServer.add(order);
				ClientUI.sentToChatClient(msgForServer);
				if (updated) {
					alert.setAlert("Updated succesful");
				} else
					// alertFailed
					alert.setAlert("Updated failed");
			} else
				// notify user
				alert.setAlert("no chages made! Please try again");
		}
		updated = false;
	}

	public static void updatedOrderFromServer(Object received) {
		if (received instanceof Order) {
			updated = true;
		}
		return;
	}

	@FXML
    void cancelOrder(ActionEvent event) {
		ArrayList<Object> msgForServer = new ArrayList<>();
		msgForServer.add("cancelOrder");
		msgForServer.add(order);
		ClientUI.sentToChatClient(msgForServer);
	}

	/*
	 * Do not allow to book a trip for a time that already passed today
	 */
	public boolean checkCurrentTime() {
		LocalDate date = txtdate.getValue();
		String[] arrSplit = cbxArriveTime.getValue().toString().split("-");
		String[] hour = arrSplit[0].split(":");
		LocalTime arrivalTime = LocalTime.of(Integer.parseInt(hour[0]), 00, 00);
		LocalTime now = LocalTime.now();
		if (date.compareTo(LocalDate.now()) == 0 && now.compareTo(arrivalTime) >= 0) {
			alert.setAlert("You're trying to book for a time that has already passed. Please select a future time\r\n");
			return false;
		}
		return true;
	}
	/*
	 * Check that the user didn't leave field empty
	 */

	public boolean checkNotEmptyVisitorsField() { // check not 0, chek not 999 etc.
		String visitorsNumber = txtVisitorsNumber.getText();
		if (visitorsNumber.isEmpty()) {
			alert.setAlert("Cannot leave 'Visitors Amount' field empty");
			return false;
		}
		return true;
	}

	public static void canceledOrderFromServer(boolean returned) {
		// TODO Auto-generated method stub
		if (returned)
			order = null;
	}

	@FXML
	/*
	 * NICE TO HAVE!!!!!!
	 */
	void switchToMember(ActionEvent event) throws IOException {
//		Stage stage = (Stage)lnkSwitch.getScene().getWindow();
//		Parent root = FXMLLoader.load(getClass().getResource("/gui/EditMemberOrder.fxml"));
//		stage.setScene(new Scene(root));
	}
}
