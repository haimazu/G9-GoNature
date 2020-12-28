package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.sun.javafx.tk.Toolkit.WritableImageAccessor;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import orderData.Order;
import reportData.CanceledReport;
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
	private TableView<TableViewSet> TableDep;
	@FXML
	private TableColumn<TableViewSet, String> parkName;
	@FXML
	private TableColumn<TableViewSet, String> requestType;
	@FXML
	private TableColumn<TableViewSet, String> requestDetails;
    @FXML
    private TableColumn<TableViewSet, String> mark;

	@FXML
	private Label LabelCount;
	@FXML
	private Button btnApprove;
	@FXML
	private Button btnDisaprove;

	/***** Cancel Reports *****/
	@FXML
	private BarChart<String, Double> bcCancells;
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
	// private static ArrayList<ArrayList<Report>> cancelReportsDetails = new
	// ArrayList<>();
	private static ArrayList<ArrayList<String>> allCancelledOrders = new ArrayList<>();
	private static ArrayList<CanceledReport> cancelledOrders = new ArrayList<>();
	private static ArrayList<Order> dismissedOrders = new ArrayList<>();

	/***** Dashboard Variables *****/
	private static ArrayList<ArrayList<String>> DBList = new ArrayList<>();
	private int count = 0;

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
		ObservableList<TableViewSet> listForTable = FXCollections.observableArrayList();
		String str = null;
		for (ArrayList<String> arrayList : al) {

			if (arrayList.get(1).equals("discount")) {
				str = "Discount : " + arrayList.get(4) + "%" + " in the following dates: " + arrayList.get(5) + " - "
						+ arrayList.get(6);
			} else if (arrayList.get(1).equals("max_c")) {
				str = "Visitors Capacity : " + arrayList.get(2);
			} else if (arrayList.get(1).equals("max_o")) {
				str = "Visitors Order Capacity : " + arrayList.get(3);
			}

			listForTable.add(new TableViewSet(arrayList.get(7), arrayList.get(1), str));

		}

		TableDep.setItems(listForTable);
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

			// show all data
			chart();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void chart() {
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis(0, 1000, 50);

		bcCancells.getData().clear();
		bcCancells.setAnimated(false);
		bcCancells.setBarGap(0d);
		bcCancells.setCategoryGap(2.0);

		bcCancells.setTitle("Cancellation/Dismissed Reports");

		Series<String, Double> disney = new Series<>();
		Series<String, Double> jurasic = new Series<>();
		Series<String, Double> universal = new Series<>();

		for (LocalDate date = dpFrom.getValue(); date.isBefore(dpTo.getValue().plusDays(1)); date = date.plusDays(1)) {
			int i = date.getDayOfMonth();

			disney.setName("Disney");
			// x = day of the month, y = sum of cancel
			disney.getData().add(new XYChart.Data(Integer.toString(i), 601.34));

			jurasic.setName("Jurasic");
			jurasic.getData().add(new XYChart.Data(Integer.toString(i), 401.85));

			universal.setName("Universal");
			universal.getData().add(new XYChart.Data(Integer.toString(i), 450.65));
		}

		bcCancells.getData().addAll(disney, jurasic, universal);
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
	// ArrayList<Object>: cell[0] list of cancelled orders
	// cell[1] list of dismissed orders
	public static void receivedFromServerCancelReportsData(ArrayList<ArrayList<String>> data) {
		allCancelledOrders = data;
		CanceledReport c = new CanceledReport(allCancelledOrders.get(0));
		System.out.println(c);
	}

	public static ArrayList<ArrayList<String>> getDBList() {
		return DBList;
	}

	public static void setDBList(ArrayList<ArrayList<String>> dBList) {
		DBList = dBList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());

		ArrayList<Object> msg = new ArrayList<>();
		msg.add("PendingManagerRequests");
		ClientUI.sentToChatClient(msg);

		count = DBList.size();
		LabelCount.setText(String.valueOf(count));
		addData(DBList);

		parkName.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("ParkName"));
		requestType.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqType"));
		requestDetails.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqDetails"));
		mark.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("mark"));

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
