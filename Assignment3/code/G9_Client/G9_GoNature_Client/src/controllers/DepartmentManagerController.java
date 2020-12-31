package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;

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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
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
	private ArrayList<Object> data = new ArrayList<>();
	private static String status;
	private AlertController alert = new AlertController();

	/********* Useage report *********/
	@FXML
	private LineChart<?, ?> lineChart;

	@FXML
	private CategoryAxis lineX;

	@FXML
	private NumberAxis lineY;

	@FXML
	private JFXDatePicker dateFrom;

	@FXML
	private JFXDatePicker dateTo;

	/***** Cancel Reports Variables *****/
//	private static ArrayList<CanceledReport> cancelledOrders = new ArrayList<>();
//	private static ArrayList<Order> dismissedOrders = new ArrayList<>();
	private static ArrayList<ArrayList<String>> cancelledOrders = new ArrayList<>();
	private int index = 0;

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
			setDatePickerInitialValues();
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

	public static String getStatus() {
		return status;
	}

	public static void setStatus(String status) {
		DepartmentManagerController.status = status;
	}

	public static void setData(String status) {
		setStatus(status);
	}

	/**
	 * button approve will remove the row and will update the table from DB will
	 * send to server [0] - name mathod [1] - approve [2] - TabelViewSet
	 * 
	 * @param event
	 */
	@FXML
	void approve(ActionEvent event) {
		for (int i = 0; i < TableDep.getItems().size(); i++) {
			if (TableDep.getItems().get(i).getMarkCh().isSelected()) {
				data.add("removePendingsManagerReq");
				data.add("approve");
				data.add(TableDep.getItems().get(i));
//				data.add(TableDep.getItems().get(i).getParkName());
//				data.add(TableDep.getItems().get(i).getReqType());
//				ClientUI.sentToChatClient(data);

//				if(status.equals("Success"))
				TableDep.getItems().remove(TableDep.getItems().get(i));
//				else
//					alert.failedAlert("Failed", "Something went wrong, can't delete from DB - please try later");
			}
			data.clear();
		}
		iniailTabel();
	}
	
	@FXML
    void approveUsage(ActionEvent event) {

    }

	/**
	 * button disapprove will not remove the row and will update the server will
	 * send to server [0] - name method [1] - disapprove [2] - TabelViewSet
	 * 
	 * @param event
	 */
	@FXML
	void disapprove(ActionEvent event) {

		for (int i = 0; i < TableDep.getItems().size(); i++) {
			if (TableDep.getItems().get(i).getMarkCh().isSelected()) {
				data.add("removePendingsManagerReq");
				data.add("disapprove");
				data.add(TableDep.getItems().get(i));
//				data.add(TableDep.getItems().get(i).getParkName());
//				data.add(TableDep.getItems().get(i).getReqType());
//				ClientUI.sentToChatClient(data);

//				if(!status.equals("Success"))
//					alert.failedAlert("information", "We pass your decision to the park manager");

			}
			data.clear();
		}
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
			TableViewSet TVS = new TableViewSet(arrayList.get(7), arrayList.get(1), str);
			TVS.setIdEmp(arrayList.get(0));
			listForTable.add(TVS);
		}

		TableDep.setItems(listForTable);
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

	public void checkDate() {
		ArrayList<String> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate from = dpFrom.getValue();
		String fromFormat = dateTimeFormatter.format(from);

		LocalDate to = dpTo.getValue();
		String toFormat = dateTimeFormatter.format(to);

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
		int i, j = 0;
		int disneyDate = 0;
		int jurasicDate = 0;
		int universalDate = 0;
		xAxis = new CategoryAxis();
		yAxis = new NumberAxis(0, 1000, 50);

		bcCancells.getData().clear();
		bcCancells.setAnimated(false);
		bcCancells.setBarGap(0d);
		bcCancells.setCategoryGap(4.0);

		bcCancells.setTitle("Cancellation/Dismissed Reports");

		Series<String, Double> disney = new Series<>();
		Series<String, Double> jurasic = new Series<>();
		Series<String, Double> universal = new Series<>();
		
		disney.setName("Disney");
		jurasic.setName("Jurasic");
		universal.setName("Universal");
		
		//for (LocalDate date = dpFrom.getValue(); date.isBefore(dpTo.getValue().plusDays(1)); date = date.plusDays(1)) {
		//	i = date.getDayOfMonth();
		String minDateFormat = cancelledOrders.get(0).get(1).substring(0, 10);
		String maxDateFormat = cancelledOrders.get(cancelledOrders.size() - 1).get(1).substring(0, 10);
		LocalDate minDate = LocalDate.parse(minDateFormat);
		LocalDate maxDate = LocalDate.parse(maxDateFormat);
		int count = 0;
		
		for (LocalDate date = minDate; date.isBefore(maxDate.plusDays(1)); date = date.plusDays(1)) {
			
			count = checkIfExists(date);
			if (count == 0) {
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
				continue;
			}
			
			disneyDate = jurasicDate = universalDate = 0;
			i = 0;
				
			while (i < count) {
				if (cancelledOrders.get(index).get(0).equals("disney")) {
					disneyDate = index;
				} else if (cancelledOrders.get(index).get(0).equals("jurasic")) {
					jurasicDate = index;
				} else if (cancelledOrders.get(index).get(0).equals("universal")) {
					universalDate = index;
				}
				index++;
				i++;
			}
					
			// parkName = Disney && same day in the month
			if (cancelledOrders.get(j).get(0).equals("disney") && disneyDate == 0) {
				// x = day of the month, y = sum of cancel
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(j).get(2))));
			} else if (disneyDate != 0) {
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(disneyDate).get(2))));
				disneyDate = 0;
			} else {
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}
			
			// parkName = Jurasic && same day in the month
			if (cancelledOrders.get(j).get(0).equals("jurasic") && jurasicDate == 0) {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(j).get(2))));
			} else if (jurasicDate != 0) {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(jurasicDate).get(2))));
				jurasicDate = 0;
			} else {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}
				
			// parkName = Universal && same day in the month
			if (cancelledOrders.get(j).get(0).equals("universal") && universalDate == 0) {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(j).get(2))));
			} else if (universalDate != 0) {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), Double.parseDouble(cancelledOrders.get(universalDate).get(2))));
				universalDate = 0;
			} else {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}
		}

		bcCancells.getData().addAll(disney, jurasic, universal);
	}
	
	public int checkIfExists(LocalDate date) {
		int count = 0;
		index = 0;
		
		for (int i = 0; i < cancelledOrders.size(); i++) {
			String DateFormat = cancelledOrders.get(i).get(1).substring(0, 10);
			LocalDate checkDate = LocalDate.parse(DateFormat);
			if (date.equals(checkDate)) {
				if (index != 0) {
					index = i;
				}
				count++;
			}
		}
		
		return count;
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
	public static void receivedFromServerCancelReportsData(ArrayList<ArrayList<String>> cancelData) {
		DepartmentManagerController.cancelledOrders = cancelData;
	}
//	public static void receivedFromServerCancelReportsData(ArrayList<ArrayList<String>> cancelData,
//			ArrayList<ArrayList<String>> dismissData) {
//		for (ArrayList<String> cancel : cancelData) {
//			cancelledOrders.add(new CanceledReport(cancel));
//		}
//
//		for (ArrayList<String> dismiss : dismissData) {
//			dismissedOrders.add(new Order(dismiss));
//		}
//	}

	public static ArrayList<ArrayList<String>> getDBList() {
		return DBList;
	}

	public static void setDBList(ArrayList<ArrayList<String>> dBList) {
		DBList = dBList;
	}
	
	public void setDatePickerInitialValues() {
		dateFrom.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		//txtDateFrom.setValue(LocalDate.now());
		dateTo.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		//txtDateTo.setValue(LocalDate.now());

	}

	public void iniailTabel() {
		DBList.clear();
		ArrayList<Object> msg = new ArrayList<>();
		msg.add("PendingManagerRequests");
		ClientUI.sentToChatClient(msg);

		count = DBList.size();
		LabelCount.setText(String.valueOf(count));
		addData(DBList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());

		iniailTabel();

		parkName.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("ParkName"));
		requestType.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqType"));
		requestDetails.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("reqDetails"));
		mark.setCellValueFactory(new PropertyValueFactory<TableViewSet, String>("MarkCh"));

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
