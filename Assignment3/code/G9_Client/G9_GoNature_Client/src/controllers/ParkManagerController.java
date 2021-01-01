package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import org.omg.CORBA.Request;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.x.protobuf.MysqlxExpr.Identifier;
//import com.sun.prism.shader.Mask_TextureRGB_AlphaTest_Loader;

import client.ClientUI;
import dataLayer.Park;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import orderData.Order;
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
	private Pane pnDashboard;
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

	/* ------for visitors chart----- */
	@FXML
	private BarChart<String, Double> bcVisitorsChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private JFXDatePicker dpFrom;
	@FXML
	private JFXDatePicker dpTo;
	@FXML
	private Button btnShow;
	private static ArrayList<ArrayList<String>> visitorsReport = new ArrayList<>();
	private double[] weekDaysCounter = new double[8];

	/*------------------------*/

	/* ----for usage chart----- */
	
	
	
	/*-------------------------*/
	
	private static ManagerRequest Req = new ManagerRequest(0, "", 0, 0, "", "", "", "");
	private static String firstName;
	private static String parkName;
	private static AlertController alert = new AlertController();
	private static boolean requestAnswerFromServer;
	private static int empID = 0;
	private static Park park;

	public static ArrayList<ArrayList<String>> getVisitorsReport() {
		return visitorsReport;
	}

	public static void setVisitorsReport(ArrayList<ArrayList<String>> visitorsReport) {
		ParkManagerController.visitorsReport = visitorsReport;
	}

	public static Park getPark() {
		return park;
	}

	public static void setPark(Park park) {
		ParkManagerController.park = park;
	}

	public static int getEmpID() {
		return empID;
	}

	public static void setEmpID(int empID) {
		ParkManagerController.empID = empID;
	}

	public static boolean getRequestAnswerFromServer() {
		return requestAnswerFromServer;
	}

	public static void setRequestAnswerFromServer(boolean discountAnswerFromServer) {
		ParkManagerController.requestAnswerFromServer = discountAnswerFromServer;
	}

	public static String getParkName() {
		return parkName;
	}

	public static void setParkName(String parkName) {
		ParkManagerController.parkName = parkName;
	}

	@FXML
	void handleSideBarParkManager(ActionEvent event) {
		if (event.getSource() == btnDashboard) {
			lblTitle.setText("Dashboard");
			pnDashboard.toFront();
			setButtonPressed(btnDashboard);
			setButtonReleased(btnVisits, btnUsage, btnMonthlyRevenue);
		} else if (event.getSource() == btnVisits) {
			lblTitle.setText("Visits Report");
			pnVisits.toFront();
			setButtonPressed(btnVisits);
			setButtonReleased(btnDashboard, btnUsage, btnMonthlyRevenue);
			setDatePickerInitialValues();
		} else if (event.getSource() == btnUsage) {
			lblTitle.setText("Usage Report");
			pnUsage.toFront();
			setButtonPressed(btnUsage);
			setButtonReleased(btnDashboard, btnVisits, btnMonthlyRevenue);
		} else if (event.getSource() == btnMonthlyRevenue) {
			lblTitle.setText("Revenew report");
			pnMonthlyRev.toFront();
			setButtonPressed(btnMonthlyRevenue);
			setButtonReleased(btnVisits, btnDashboard, btnUsage);

		}
	}

	public void setButtonPressed(Button button) {
		button.setStyle("-fx-background-color: transparent;" + "-fx-border-color: brown;"
				+ "-fx-border-width: 0px 0px 0px 3px;");
	}

	public void setButtonReleased(Button button, Button button1, Button button2) {
		button.setStyle("-fx-background-color: transparent;");
		button1.setStyle("-fx-background-color: transparent;");
		button2.setStyle("-fx-background-color: transparent;");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setParkName(LoginController.getParkName());
		RequestForParkDetails();
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());
		lblParkName.setText(getParkName());

		setDatePickerInitialValues();
		RequestForEmployeeID();
		presentParkDetails(park);

		/*** visitors reports ***/
		dpFrom.setValue(LocalDate.now().withDayOfMonth(1));
		// listener for updating the date
		dpFrom.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpFrom.setValue(newValue);
		});

		// plusMonths(1) to get the next month
		// withDayOfMonth(1) to get the first day
		dpTo.setValue(dpFrom.getValue().plusMonths(1).withDayOfMonth(1));
		// listener for updating the date
		dpTo.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpTo.setValue(newValue);
		});
	}

	// dates method
	public void setDatePickerInitialValues() {
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

		if (!illegalValuesForSubmitingDiscount(discount))

		{
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
				Req.setEmployeeID(getEmpID());
				Req.setRequestType("discount");
				msg.add(Req);
				ClientUI.sentToChatClient(msg);

				if (requestAnswerFromServer) {
					alert.successAlert("Request Info",
							"Your request was sent and pending for deapartment manager approval.");
					btnSetDisc.setVisible(true);
					txtManageDsic.setVisible(false);
					txtDateFrom.setVisible(false);
					txtDateTo.setVisible(false);
					btnSubmitDisc.setVisible(false);

				} else {
					alert.setAlert(
							"You already reached maximum number of requests.\nIt is possible to have only one request of a type in a time.\nContact your department manager or try again later.");
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
		Req.setEmployeeID(getEmpID());
		Req.setRequestType("");
		txtManageDsic.clear();
		setDatePickerInitialValues();
	}

	public boolean illegalValuesForSubmitingDiscount(String discount) throws ParseException {
		String datefrom = txtDateFrom.getValue().toString();
		String dateto = txtDateTo.getValue().toString();
		if (DatesNotCorresponding(datefrom, dateto)) {
			alert.setAlert("You are trying to set incorrect dates!\nPlease try again");
			return true;
		}

		if (discount.isEmpty()) {
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid discount.");
			return true;
		}
		return false;
	}

	@FXML
	void submitVisitorsCapacityByorder(ActionEvent event) {
		String capacity = txtMaxcapByorder.getText();
		if (capacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
		// check that capacity for orders is less than overall capacity
		if (Integer.parseInt(capacity) >= park.getMaximumCapacityInPark())
			alert.setAlert(
					"You are trying to determine illegal capacity !\nCapacity for ordered visits should be less than maximum capacity in park ");
		else {
			ArrayList<Object> msg = new ArrayList<>();
			msg.add("parkManagerRequest");
			Req.setParkName(getParkName());
			Req.setEmployeeID(getEmpID());
			Req.setRequestType("max_o");
			Req.setOrdersCapacity(Integer.parseInt(txtMaxcapByorder.getText()));
			msg.add(Req);
			ClientUI.sentToChatClient(msg);

			if (requestAnswerFromServer) {
				alert.successAlert("Request Info",
						"Your request was sent and pending for deapartment manager approval.");
				btnSetMaxByOrder.setVisible(true);
				txtMaxcapByorder.setVisible(false);
				btnSubmitCapacityByorder.setVisible(false);
			} else {
				alert.setAlert(
						"You already reached maximum number of requests.\nIt is possible to have only one request of a type in a time.\nContact your department manager or try again later.");
				btnSetMaxByOrder.setVisible(true);
				txtMaxcapByorder.setVisible(false);
				btnSubmitCapacityByorder.setVisible(false);
			}

		}
		Req.setDiscount("");
		Req.setFromDate("");
		Req.setToDate("");
		Req.setParkName("");
		Req.setEmployeeID(getEmpID());
		Req.setRequestType("");
		txtMaxcapByorder.clear();

	}

	@FXML
	void submitVisitorsCapacity(ActionEvent event) {
		String orderCapacity = lblSetMax.getText();
		if (orderCapacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
		else {
			ArrayList<Object> msg = new ArrayList<>();
			msg.add("parkManagerRequest");
			Req.setParkName(getParkName());
			Req.setEmployeeID(getEmpID());
			Req.setRequestType("max_c");
			Req.setMaxCapacity(Integer.parseInt(lblSetMax.getText()));
			msg.add(Req);
			ClientUI.sentToChatClient(msg);

			if (requestAnswerFromServer) {
				alert.successAlert("Request Info",
						"Your request was sent and pending for deapartment manager approval.");
				btnSetVisitors.setVisible(true);
				lblSetMax.setVisible(false);
				btnSubmitVisits.setVisible(false);
			} else {
				alert.setAlert(
						"You already reached maximum number of requests.\nIt is possible to have only one request of a type in a time.\nContact your department manager or try again later.");
				btnSetVisitors.setVisible(true);
				lblSetMax.setVisible(false);
				btnSubmitVisits.setVisible(false);
			}

		}
		Req.setDiscount("");
		Req.setFromDate("");
		Req.setToDate("");
		Req.setParkName("");
		Req.setEmployeeID(getEmpID());
		Req.setRequestType("");
		lblSetMax.clear();
	}

	void presentParkDetails(Park parkDetails) {
		double disc = (parkDetails.getMangerDiscount()) * 100;
		lblPresentDisc.setText(String.format("%.1f", disc) + "%");
		lblPresentMaxVis.setText(String.valueOf(parkDetails.getMaximumCapacityInPark()));
		lblPresentReservationCap.setText(String.valueOf(parkDetails.getMaxAmountOrders()));
	}

	public void RequestForEmployeeID() {

		ArrayList<Object> msg = new ArrayList<>();
		msg.add("requestForEmployeeID");
		msg.add(LoginController.getUsername());
		msg.add(LoginController.getPassword());
		ClientUI.sentToChatClient(msg);
	}

	public void RequestForParkDetails() {
		ArrayList<Object> msg = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		msg.add("requestForParkDetails");
		data.add(getParkName());
		msg.add(data);
		ClientUI.sentToChatClient(msg);
	}

	public boolean DatesNotCorresponding(String datefrom, String dateto) throws ParseException {
		LocalDate from;
		LocalDate to;

		String[] fromarrsplitStrings = datefrom.split(" ");
		String[] fromdatesplit = fromarrsplitStrings[0].split("-");
		String[] toarrsplitStrings = dateto.split(" ");
		String[] todatesplit = toarrsplitStrings[0].split("-");
		// [0]->year , [1]->month , [2]->day
		from = LocalDate.of(Integer.parseInt(fromdatesplit[0]), Integer.parseInt(fromdatesplit[1]),
				Integer.parseInt(fromdatesplit[2]));

		to = LocalDate.of(Integer.parseInt(todatesplit[0]), Integer.parseInt(todatesplit[1]),
				Integer.parseInt(todatesplit[2]));
		if (to.compareTo(from) < 0)
			return true;

		return false;
	}
		/*------- visitors chart------------------*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void chart() throws ParseException {
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis(0, 20, 2);
		bcVisitorsChart.getData().clear();
		bcVisitorsChart.setAnimated(false);
		bcVisitorsChart.setBarGap(0d);
		bcVisitorsChart.setCategoryGap(4.0);

		bcVisitorsChart.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		bcVisitorsChart.setPrefSize(613, 430);
		bcVisitorsChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		bcVisitorsChart.setTitle("Visitors segmentation by type");
		xAxis.setCategories(FXCollections.<String>observableArrayList(
				Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")));

		Series<String, Double> regular = new Series<>();
		Series<String, Double> member = new Series<>();
		Series<String, Double> group = new Series<>();

		regular.setName("regular");
		checkWeekDays("regular");

		regular.getData().add(new XYChart.Data("Sunday", weekDaysCounter[1]));
		regular.getData().add(new XYChart.Data("Monday", weekDaysCounter[2]));
		regular.getData().add(new XYChart.Data("Tuesday", weekDaysCounter[3]));
		regular.getData().add(new XYChart.Data("Wednesday", weekDaysCounter[4]));
		regular.getData().add(new XYChart.Data("Thursday", weekDaysCounter[5]));
		regular.getData().add(new XYChart.Data("Friday", weekDaysCounter[6]));
		regular.getData().add(new XYChart.Data("Saturday", weekDaysCounter[7]));

		member.setName("member");
		checkWeekDays("member");

		member.getData().add(new XYChart.Data("Sunday", weekDaysCounter[1]));
		member.getData().add(new XYChart.Data("Monday", weekDaysCounter[2]));
		member.getData().add(new XYChart.Data("Tuesday", weekDaysCounter[3]));
		member.getData().add(new XYChart.Data("Wednesday", weekDaysCounter[4]));
		member.getData().add(new XYChart.Data("Thursday", weekDaysCounter[5]));
		member.getData().add(new XYChart.Data("Friday", weekDaysCounter[6]));
		member.getData().add(new XYChart.Data("Saturday", weekDaysCounter[7]));

		group.setName("group");
		checkWeekDays("group");

		group.getData().add(new XYChart.Data("Sunday", weekDaysCounter[1]));
		group.getData().add(new XYChart.Data("Monday", weekDaysCounter[2]));
		group.getData().add(new XYChart.Data("Tuesday", weekDaysCounter[3]));
		group.getData().add(new XYChart.Data("Wednesday", weekDaysCounter[4]));
		group.getData().add(new XYChart.Data("Thursday", weekDaysCounter[5]));
		group.getData().add(new XYChart.Data("Friday", weekDaysCounter[6]));
		group.getData().add(new XYChart.Data("Saturday", weekDaysCounter[7]));

		bcVisitorsChart.getData().addAll(regular, member, group);
	}

	@FXML
	void showChart(ActionEvent event) throws ParseException {
		ArrayList<Object> msg = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		if (DatesNotCorresponding(dpFrom.getValue().toString(), dpTo.getValue().toString()))
			alert.setAlert("You are trying to set incorrect dates!\nPlease try again");
		else {

			msg.add("overallVisitorsReport");
			data.add(getParkName());
			data.add(dpFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			data.add(dpTo.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			msg.add(data);
			System.out.println("print message: " + msg);
			System.out.println(
					"print date to server : " + dpFrom.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			ClientUI.sentToChatClient(msg);

			chart();
		}
	}

	void checkWeekDays(String type) throws ParseException {
		String someDate;
		Date date1;
		resetWeekDayCounter();
		for (ArrayList<String> arrayList : visitorsReport) {
			someDate = getDate(arrayList.get(2));
			date1 = new SimpleDateFormat("yyyy-MM-dd").parse(someDate);
			if (type.equals(arrayList.get(0))) {
				switch (getDayNumber(date1)) {
				// MON
				case 1:
					weekDaysCounter[2] += Double.parseDouble(arrayList.get(1));
					break;
				// TUE
				case 2:
					weekDaysCounter[3] += Double.parseDouble(arrayList.get(1));
					break;
				// WED
				case 3:
					weekDaysCounter[4] += Double.parseDouble(arrayList.get(1));
					break;
				// THU
				case 4:
					weekDaysCounter[5] += Double.parseDouble(arrayList.get(1));
					break;
				// FRI
				case 5:
					weekDaysCounter[6] += Double.parseDouble(arrayList.get(1));
					break;
				// SAT
				case 6:
					weekDaysCounter[7] += Double.parseDouble(arrayList.get(1));
					break;
				// SUN
				case 7:
					weekDaysCounter[1] += Double.parseDouble(arrayList.get(1));
					break;
				default:
					break;
				}
			}
		}

	}

	public static int getDayNumber(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public String getDate(String date) {
		String[] arrDateAndTime = date.split(" ");
		String[] arrDate = arrDateAndTime[0].split("-");
		return arrDateAndTime[0];
	}

	public void resetWeekDayCounter() {

		for (int i = 0; i < this.weekDaysCounter.length; i++) {
			weekDaysCounter[i] = 0;
		}
	}

	static void noDataTopresent() {
		alert.setAlert("There is no Data to present for selected dates.");
	}
/*-------end of visitors report section --------*/
	
	/*----received from server section ---*/
	public static void recivedFromserver(boolean answer) {
		setRequestAnswerFromServer(answer);

	}

	public static void recivedFromserverParkDetails(Object object) {
		if (object instanceof Park)
			setPark((Park) object);
		else
			setPark(null);
	}

	public static void recivedFromserverEmployeeID(String answer) {
		setEmpID(Integer.parseInt(answer));

	}

	public static void recivedFromserverVisitorsReport(ArrayList<ArrayList<String>> visitorsReportAnswer) {
		if ((Object) visitorsReportAnswer instanceof String)
			noDataTopresent();
		else {
		setVisitorsReport(visitorsReportAnswer);
		System.out.print(visitorsReportAnswer);
		}

	}

}
