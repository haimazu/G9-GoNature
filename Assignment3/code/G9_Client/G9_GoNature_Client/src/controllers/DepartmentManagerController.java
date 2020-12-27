package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import reportData.TableViewSet;

public class DepartmentManagerController implements Initializable {
	/***** Side Bar *****/
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnVisitsReport;
	@FXML
	private Button btnCancelsReport;
	@FXML
	private Button btnSettings;

	/***** Top Title *****/
	@FXML
	private Label lblTitle;
	@FXML
	private Label lblFirstNameTitle;

	/***** Panels *****/
	@FXML
	private StackPane pnStack;
	@FXML
	private Pane pnDashboard;
	@FXML
	private Pane pnVisits;
	@FXML
	private Pane pnCancels;
	@FXML
	private Pane pnSettings;

	/***** Dashboard *****/
	@FXML
	private TableView<TableViewSet> tableDep;
	@FXML
	private TableColumn<TableViewSet, String> parkName;
	@FXML
	private TableColumn<TableViewSet, String> requestType;
	@FXML
	private TableColumn<TableViewSet, String> requestDetails;
	@FXML
	private TableColumn<TableViewSet, CheckBox> checkBox;
	@FXML
	private Label LabelCount;
	@FXML
	private Button btnApprove;
	@FXML
	private Button btnDisaprove;

	/***** Cancel Reports *****/
	@FXML
	private BarChart<?, ?> bcCancells;
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
	@FXML
	private Button btnLogout;

	private static String firstName;

	/***** Cancel Reports Variables *****/
	private static ArrayList<Object> cancelReportsDetails = new ArrayList<>();

	/***** Dashboard Variables *****/
	private static ArrayList<ArrayList<String>> DBList = new ArrayList<>();
	private int count = 0;

	public static ArrayList<ArrayList<String>> getDBList() {
		return DBList;
	}

	public static void setDBList(ArrayList<ArrayList<String>> dBList) {
		DBList = dBList;
	}

	@FXML
	public void handleSideBar(ActionEvent event) {
		if (event.getSource() == btnDashboard) {
			lblTitle.setText("Dashboard");
			pnDashboard.toFront();
			setButtonPressed(btnDashboard);
			setButtonReleased(btnVisitsReport, btnCancelsReport, btnSettings);
		} else if (event.getSource() == btnVisitsReport) {
			lblTitle.setText("Visits Report");
			pnVisits.toFront();
			setButtonPressed(btnVisitsReport);
			setButtonReleased(btnDashboard, btnCancelsReport, btnSettings);
		} else if (event.getSource() == btnCancelsReport) {
			lblTitle.setText("Cancels Report");
			pnCancels.toFront();
			setButtonPressed(btnCancelsReport);
			setButtonReleased(btnDashboard, btnVisitsReport, btnSettings);
		} else if (event.getSource() == btnSettings) {
			lblTitle.setText("Settings");
			pnSettings.toFront();
			setButtonPressed(btnSettings);
			setButtonReleased(btnVisitsReport, btnCancelsReport, btnCancelsReport);
		}
	}

	@FXML
	void show(ActionEvent event) {
		checkDate();
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
		DepartmentManagerController.firstName = firstName;
	}

	@FXML
	void approve(ActionEvent event) {

	}

	@FXML
	void disapprove(ActionEvent event) {

	}

	public void addData(ArrayList<ArrayList<String>> al) {
		ArrayList<Object> listForTable = new ArrayList<>();
		for (ArrayList<String> arrayList : al) {
			listForTable.add(arrayList.get(7));
			listForTable.add(arrayList.get(1));
			if (arrayList.get(1).equals("discount")) {
				String str = "Discount : " + arrayList.get(4) + "%" + " in the following dates: " + arrayList.get(5)
						+ " - " + arrayList.get(6);
				listForTable.add(str);
			} else if (arrayList.get(1).equals("max_c")) {
				String str = "Visitors Capacity : " + arrayList.get(2);
				listForTable.add(str);
			} else if (arrayList.get(1).equals("max_o")) {
				String str = "Visitors Order Capacity : " + arrayList.get(3);
				listForTable.add(str);
			}

			listForTable.add(new CheckBox());
			addRow(listForTable);
			listForTable.clear();
		}
	}

	public void addRow(ArrayList<Object> al) {
		tableDep.getItems().add(new TableViewSet(al));
	}

	public void setButtonPressed(Button button) {
		button.setStyle("-fx-background-color: transparent;\r\n" + "	-fx-border-color: brown;\r\n"
				+ "	-fx-border-width: 0px 0px 0px 3px;");
	}

	public void setButtonReleased(Button button, Button button1, Button button2) {
		button.setStyle("-fx-background-color: transparent;");
		button1.setStyle("-fx-background-color: transparent;");
		button2.setStyle("-fx-background-color: transparent;");
	}

	public void checkDate() {
		ArrayList<String> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate from = dpFrom.getValue();
		String fromFormat = dateTimeFormatter.format(from);

		LocalDate to = dpTo.getValue();
		String toFormat = dateTimeFormatter.format(to);

		System.out.println(fromFormat);
		System.out.println(toFormat);

		// good
		if (from.isBefore(to)) {
			data.add(fromFormat);
			data.add(toFormat);
			sendToServerArrayList(data);
		}
	}

	// ArrayList<String> data, sending to the server to get data
	// input: [0] case name
	// [1] month
	// [2] year
	// output: none
	public void sendToServerArrayList(ArrayList<String> date) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add("getCancellationReports");
		// add date
		msg.add(date);
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
	}

	// getting information from the server
	// input: none
	// output: list of cancelled orders:
	// ArrayList<Object>: cell[0] case name
	// cell[1] list of cancelled orders
	// cell[2] list of dismissed orders
	public static void receivedFromServerVisitorsPrice(ArrayList<Object> msgReceived) {
		DepartmentManagerController.cancelReportsDetails = msgReceived;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());

		parkName.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("ParkName"));
		requestType.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqType"));
		requestDetails.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqDetails"));
		checkBox.setCellValueFactory(new PropertyValueFactory<TableViewSet, CheckBox>("mark"));

		ArrayList<String> msg = new ArrayList<>();
		// msg.add("PendingManagerRequests");
		// ClientUI.sentToChatClient(msg);

		msg.add("1234");
		msg.add("discount");
		msg.add("0");
		msg.add("0");
		msg.add("15");
		msg.add("2020-12-30");
		msg.add("2020-01-30");
		msg.add("kuku");

		DBList.add(msg);

		count = DBList.size();
		LabelCount.setText(String.valueOf(count));
		addData(DBList);
		
		/***** Cancel Reports *****/
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
}
