package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.omg.CORBA.Request;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.x.protobuf.MysqlxExpr.Identifier;

import client.ClientUI;
import dataLayer.Park;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import reportData.ManagerRequest;

public class ParkManagerController implements Initializable {
	@FXML
	private Button btnDashboard;

	@FXML
	private Button btnVisits;

	@FXML
	private Button btnUsage;

	@FXML
	private Button btnMonthlyRevenue;

	@FXML
	private Button btnSettings;

	@FXML
	private Label lblTitle;

	@FXML
	private Button btnLogout;

	@FXML
	private Label lblFirstNameTitle;

	@FXML
	private Label lblParkName;

	@FXML
	private Pane pnSettings;

	@FXML
	private Pane pnMonthlyRev;

	@FXML
	private Label lblTitle1112;

	@FXML
	private Label lblTitle11111;

	@FXML
	private Button btnReportView11;

	@FXML
	private Pane pnUsage;

	@FXML
	private Label lblTitle111;

	@FXML
	private Label lblTitle1111;

	@FXML
	private Button btnReportView1;

	@FXML
	private Pane pnVisits;

	@FXML
	private Label lblTitle11;

	@FXML
	private Label lblTitle112;

	@FXML
	private TableView<?> tblHoursPerTypes;

	@FXML
	private Button btnReportView;

	@FXML
	private Pane pnVisits1;

	@FXML
	private JFXTextField lblSetMax;

	@FXML
	private JFXTextField txtManageDsic;

	@FXML
	private Button btnSubmitVisits;

	@FXML
	private Label lblTitle1;

	@FXML
	private Button btnSubmitDisc;

	@FXML
	private JFXDatePicker txtDateFrom;

	@FXML
	private JFXDatePicker txtDateTo;

	@FXML
	private Button btnSetDisc;

	@FXML
	private Button btnSetVisitors;
	@FXML
	private Button btnSetMaxByOrder;
	@FXML
	private Label lblPresentDisc;

	@FXML
	private Label lblPresentMaxVis;

	@FXML
	private Label lblPresentReservationCap;

	@FXML
	private JFXTextField txtMaxcapByorder;

	@FXML
	private Button btnSubmitCapacityByorder;

	@FXML
	private Button btnSetDisc1;
	private static ManagerRequest Req = new ManagerRequest(0, "", 0, 0, "", "", "", "");
	private static String firstName;
	private static String parkName;
	private AlertController alert = new AlertController();
	private static boolean discountAnswerFromServer;
	private static int empID;
	

	public static int getEmpID() {
		return empID;
	}

	public static void setEmpID(int empID) {
		ParkManagerController.empID = empID;
	}

	public static boolean isDiscountAnswerFromServer() {
		return discountAnswerFromServer;
	}

	public static void setDiscountAnswerFromServer(boolean discountAnswerFromServer) {
		ParkManagerController.discountAnswerFromServer = discountAnswerFromServer;
	}

	public static String getParkName() {
		return parkName;
	}

	public static void setParkName(String parkName) {
		ParkManagerController.parkName = parkName;
	}

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

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		ParkManagerController.firstName = firstName;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());
		setParkName(LoginController.getParkName());
		lblParkName.setText(getParkName());

		txtDateFrom.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		txtDateFrom.setValue(LocalDate.now());
		txtDateTo.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		txtDateTo.setValue(LocalDate.now());
		//RequestForEmployeeID();
	}

	@FXML
	void setDiscount(ActionEvent event) {
		if (!btnSetMaxByOrder.isVisible()) {
			btnSetMaxByOrder.setVisible(true);
			txtMaxcapByorder.setVisible(false);
			btnSubmitCapacityByorder.setVisible(false);
		}
		if (!btnSetVisitors.isVisible()) {
			lblSetMax.setVisible(false);
			btnSubmitVisits.setVisible(false);
			btnSetVisitors.setVisible(true);
		}
		btnSetDisc.setVisible(false);
		txtManageDsic.setVisible(true);
		txtDateFrom.setVisible(true);
		txtDateTo.setVisible(true);
		btnSubmitDisc.setVisible(true);

		txtManageDsic.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtManageDsic.setText(newValue.replaceAll("[^\\d]", ""));

			}
		});
	}

	@FXML
	void setVisitorsCapacityByorder(ActionEvent event) {
		if (!btnSetDisc.isVisible()) {
			btnSetDisc.setVisible(true);
			txtManageDsic.setVisible(false);
			txtDateFrom.setVisible(false);
			txtDateTo.setVisible(false);
			btnSubmitDisc.setVisible(false);
		}
		if (!btnSetVisitors.isVisible()) {
			lblSetMax.setVisible(false);
			btnSubmitVisits.setVisible(false);
			btnSetVisitors.setVisible(true);
		}
		btnSetMaxByOrder.setVisible(false);
		txtMaxcapByorder.setVisible(true);
		btnSubmitCapacityByorder.setVisible(true);

		txtMaxcapByorder.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtMaxcapByorder.setText(newValue.replaceAll("[^\\d]", ""));

			}
		});
	}

	@FXML
	void setMaxCapacity(ActionEvent event) {
		if (!btnSetDisc.isVisible()) {
			btnSetDisc.setVisible(true);
			txtManageDsic.setVisible(false);
			txtDateFrom.setVisible(false);
			txtDateTo.setVisible(false);
			btnSubmitDisc.setVisible(false);
		}
		if (!btnSetMaxByOrder.isVisible()) {
			btnSetMaxByOrder.setVisible(true);
			txtMaxcapByorder.setVisible(false);
			btnSubmitCapacityByorder.setVisible(false);
		}
		btnSetVisitors.setVisible(false);
		lblSetMax.setVisible(true);
		btnSubmitVisits.setVisible(true);

		lblSetMax.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				lblSetMax.setText(newValue.replaceAll("[^\\d]", ""));

			}
		});

	}

	@FXML
	void submitPendingDiscount(ActionEvent event) throws ParseException {

		String discount = txtManageDsic.getText();
		
		
		if(DatesNotCorresponding())
			alert.setAlert("You are trying to set incorrect dated!\nPlease try again");
		if (discount.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid discount.");
		else {
			int integerDisc = Integer.parseInt(discount);
			double discountInPrecents = integerDisc / 100.0;
			if (integerDisc > 100)
				alert.setAlert(
						"You are trying to set illegal discount!\nDiscount value should be in a range of 0%-100%");
			else {
				ArrayList<Object> msg = new ArrayList<>();
				msg.add("parkManagerRequest");
				Req.setDiscount(String.valueOf(1 - discountInPrecents));
				Req.setFromDate(txtDateFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				Req.setToDate(txtDateTo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				Req.setParkName(getParkName());
				Req.setEmployeeID(0);// ??????
				Req.setRequesttype("discount");
				msg.add(Req);
				System.out.println(msg);
				ClientUI.sentToChatClient(msg);
			
			
			if (discountAnswerFromServer) {
				alert.successAlert("Request Info", "Your request was sent and pending for deapartment manager approval.");
				btnSetDisc.setVisible(true);
				txtManageDsic.setVisible(false);
				txtDateFrom.setVisible(false);
				txtDateTo.setVisible(false);
				btnSubmitDisc.setVisible(false);
				
			}
			else {
				alert.setAlert("You already reached maximum number of requests.\nIt is possible to have only one request of a type in a time.\nContact your department manager or try again later.");
				btnSetDisc.setVisible(true);
				txtManageDsic.setVisible(false);
				txtDateFrom.setVisible(false);
				txtDateTo.setVisible(false);
				btnSubmitDisc.setVisible(false);
			}
			
			
		}
		}
		Req.setDiscount("");
		Req.setFromDate("");
		Req.setToDate("");
		Req.setParkName("");
		Req.setEmployeeID(0);// ??????
		Req.setRequesttype("");
	}

	@FXML
	void submitVisitorsCapacityByorder(ActionEvent event) {
		String capacity = lblSetMax.getText();
		if (capacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
		else 
		{
			ArrayList<Object> msg = new ArrayList<>();
			msg.add("parkManagerRequest");
			//Req.setDiscount(String.valueOf(1 - discountInPrecents));
			Req.setParkName(getParkName());
			Req.setEmployeeID(ParkManagerController.getEmpID());// ??????
			Req.setRequesttype("discount");
			msg.add(Req);
			System.out.println(msg);
			ClientUI.sentToChatClient(msg);
			
		}
			
	}

	@FXML
	void submitVisitorsCapacity(ActionEvent event) {
		String orderCapacity = txtMaxcapByorder.getText();
		if (orderCapacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
	}

	void presentParkData(Park parkDetails) {
		
	}

	public static void recivedFromserver(boolean answer) {
		setDiscountAnswerFromServer(answer);
		
	}
	
	public static void recivedFromserverEmployeeID(String answer) {
		setEmpID(Integer.parseInt(answer));
		System.out.println("Employee" +answer);
		
	}
	public void RequestForEmployeeID() {

		ArrayList<Object> msg = new ArrayList<>();
		msg.add("requestForEmployeeID");
		msg.add(LoginController.getPassword());
		msg.add(LoginController.getUsername());
		ClientUI.sentToChatClient(msg);
	}
	public boolean DatesNotCorresponding() throws ParseException
	{
		LocalDate from ;
		LocalDate to ;
		
		String [] fromarrsplitStrings = txtDateFrom.toString().split(" ");
		String [] fromdatesplit =fromarrsplitStrings[0].split("-");
		String [] toarrsplitStrings = txtDateTo.toString().split(" ");
		String [] todatesplit =toarrsplitStrings[0].split("-");	
		//[0]->year , [1]->month , [2]->day
		from = LocalDate.of(Integer.parseInt(fromdatesplit[0]), Integer.parseInt(fromdatesplit[1]), Integer.parseInt(fromdatesplit[2]));
		to = LocalDate.of(Integer.parseInt(todatesplit[0]), Integer.parseInt(todatesplit[1]), Integer.parseInt(todatesplit[2]));
		if(to.compareTo(from) <0)
			return true;
		
		return false;
	}

}
