package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import reportData.ManagerRequest;
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

	/***** Cancel Report *****/
	@FXML
	private BarChart<String, Double> bcCancells;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private NumberAxis yAxis;
	@FXML
    private JFXDatePicker dpCancelFrom;
    @FXML
    private JFXDatePicker dpCancelTo;
	@FXML
	private Button btnCancelShow;
	@FXML
	private Button btnExport;
	@FXML
	private Button btnLogout;
	
	/***** Visits Report *****/
	@FXML
    private JFXDatePicker dpVisitorsFrom;
    @FXML
    private JFXDatePicker dpVisitorsTo;
    @FXML
    private Button btnVisitorsShow;
	@FXML
    private PieChart pieRegular;
    @FXML
    private PieChart pieMember;
    @FXML
    private PieChart pieGroup;
    @FXML
    private Label lblRegular;
    @FXML
    private Label lblMember;
    @FXML
    private Label lblGroup;


	/***** Global Variables *****/
	private static String firstName;
	private AlertController alert = new AlertController();
	// private static Park parkDetails;

	/***** Visits Report Variables *****/
	private static ArrayList<Double> regularVisitors = new ArrayList<>();
	private static ArrayList<Double> memberVisitors = new ArrayList<>();
	private static ArrayList<Double> groupVisitors = new ArrayList<>();

	/***** Cancel Report Variables *****/
	private static ArrayList<ArrayList<String>> cancelledOrders = new ArrayList<>();
	private static boolean error = false;
	private int index = 0;

	/***** Dashboard Variables *****/
	private ArrayList<Object> data = new ArrayList<>();	
	private static ArrayList<ArrayList<String>> DBList = new ArrayList<>();
	private static boolean status;
	private int count = 0;

	// this function managed the side bar
	// input: button source that pressed
	// output: switch to the relevant pane
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

	// checks if the date is correct and displays the chart if so
	// input: from -> start date
	// to -> end date
	// output: display the chart depending on the dates entered
	@FXML
	void show(ActionEvent event) {
		ArrayList<String> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		// the dates are correct
		if (checkDate(dpCancelFrom.getValue(), dpCancelTo.getValue().plusDays(1))) {
			String fromFormat = dateTimeFormatter.format(dpCancelFrom.getValue());
			String toFormat = dateTimeFormatter.format(dpCancelTo.getValue().plusDays(1));
			data.add(fromFormat);
			data.add(toFormat);			
			sendToServerArrayList("getCancellationReports" ,data);

			if (!getError()) {
				// show all data
				barChart();
			} else {
				alert.failedAlert("Failed", "There is no information on the dates you requested.");
				bcCancells.getData().clear();
			}
		} else {
			alert.failedAlert("Failed", "Start date cannot be greater than end date.");
			bcCancells.getData().clear();
		}
	}

	// creates a PDF file based on data retrieved from the DB
	// input: from -> start date
	// to -> end date
	// ArrayList<ArrayList<String>> cancelledOrders, cells:
	// cell [i][0]: parkName
	// cell [i][1]: date of canceled/dismissed
	// cell [i][2]: amount
	// 0 <= i <= cancelledOrders.size() - 1
	// output: PDF report with all the data shown in the chart
	@FXML
	void export(ActionEvent event) {
		// call the function to fill the cancelledOrders data
		show(event);

		if (getError()) {
			return;
		}

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		DateTimeFormatter fileNameFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String fromFormat = dateFormatter.format(dpCancelFrom.getValue());
		String toFormat = dateFormatter.format(dpCancelTo.getValue());
		// the date it was created
		String fileNameDate = fileNameFormatter.format(LocalDate.now());

		Font titleFont = FontFactory.getFont(FontFactory.COURIER, 18, Font.BOLD, new BaseColor(46, 139, 87));
		try {
			Document document = new Document();
			// creates a report with the name ==> CanceledReport 'yyyy-MM-dd'.pdf
			// the 'yyyy-MM-dd' is the date it was created
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream("CanceledReport " + fileNameDate + ".pdf"));
			document.open();
			Image logo = Image.getInstance(
					"E:\\Documents\\GitHub\\G9-GoNature\\Assignment3\\code\\G9_Client\\G9_GoNature_Client\\src\\gui\\logo_small.png");
			logo.setAlignment(Element.ALIGN_CENTER);
			document.add(logo);

			Paragraph title = new Paragraph("Cancellation/Dismissed Report\n", titleFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			Paragraph date = new Paragraph(new Date().toString() + "\n\n");
			date.setAlignment(Element.ALIGN_CENTER);
			document.add(date);

			PdfPTable table = new PdfPTable(3);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell titleCell = new PdfPCell(new Paragraph(fromFormat + " - " + toFormat));
			titleCell.setColspan(3);
			titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			titleCell.setBackgroundColor(BaseColor.GRAY);
			// the title of the table
			table.addCell(titleCell);

			PdfPCell parkName = new PdfPCell(new Paragraph("Park Name"));
			parkName.setColspan(1);
			parkName.setHorizontalAlignment(Element.ALIGN_CENTER);
			parkName.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(parkName);

			PdfPCell missedTime = new PdfPCell(new Paragraph("Missed Time"));
			missedTime.setColspan(1);
			missedTime.setHorizontalAlignment(Element.ALIGN_CENTER);
			missedTime.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(missedTime);

			PdfPCell amount = new PdfPCell(new Paragraph("Amount"));
			amount.setColspan(1);
			amount.setHorizontalAlignment(Element.ALIGN_CENTER);
			amount.setBackgroundColor(BaseColor.LIGHT_GRAY);
			table.addCell(amount);

			for (int i = 0; i < cancelledOrders.size(); i++) {
				table.addCell(cancelledOrders.get(i).get(0));
				table.addCell(cancelledOrders.get(i).get(1));
				table.addCell(cancelledOrders.get(i).get(2));
			}
			document.add(table);

			alert.successAlert("Success", "The report was created successfully.");

			Desktop.getDesktop().open(new File("CanceledReport " + fileNameDate + ".pdf"));

			document.close();
			writer.close();

		} catch (Exception e) {
			alert.failedAlert("Failed", "It looks like the file is already open, close it and try again.");
		}
	}
	
	@FXML
	void showPieChart() {
		ArrayList<String> data = new ArrayList<>();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		// the dates are correct
		if (checkDate(dpVisitorsFrom.getValue(), dpVisitorsTo.getValue().plusDays(1))) {
			String fromFormat = dateTimeFormatter.format(dpVisitorsFrom.getValue());
			String toFormat = dateTimeFormatter.format(dpVisitorsTo.getValue().plusDays(1));
			data.add(fromFormat);
			data.add(toFormat);
			data.add("regular");
			sendToServerArrayList("getRegularsVisitorsData", data);
						
			data.clear();
			data.add(fromFormat);
			data.add(toFormat);
			data.add("member");
			sendToServerArrayList("getMembersVisitorsData", data);
			
			data.clear();
			data.add(fromFormat);
			data.add(toFormat);
			data.add("group");
			sendToServerArrayList("getGroupsVisitorsData", data);
		}
		
		addPieChart(pieRegular, "Regular");
		addPieChart(pieMember, "Member");
		addPieChart(pieGroup, "Group");
	}
	
	public boolean checkDate(LocalDate from, LocalDate to) {

		// the dates are correct
		if (from.isBefore(to) || from.isEqual(to)) {
			return true;
		}
		
		return false;
	}
	
	public void addPieChart(PieChart currentPie, String title) {
		currentPie.getData().clear();
		// setting the length of the label line 
		currentPie.setLabelLineLength(5);
		currentPie.setClockwise(false);
		currentPie.setAnimated(false);
		currentPie.setLegendVisible(false);
		
		ObservableList<PieChart.Data> currentVisitorsData = null;

		switch (title) {
			case "Regular":
				lblRegular.setText("Regular");
				currentVisitorsData = FXCollections.observableArrayList(
		                new PieChart.Data("0-1 hours, " + String.valueOf(regularVisitors.get(0) + "%"), regularVisitors.get(0)),
		                new PieChart.Data("1-2 hours, " + String.valueOf(regularVisitors.get(1) + "%"), regularVisitors.get(1)),
		                new PieChart.Data("2-3 hours, " + String.valueOf(regularVisitors.get(2) + "%"), regularVisitors.get(2)),
		                new PieChart.Data("3-4 hours, " + String.valueOf(regularVisitors.get(3) + "%"), regularVisitors.get(3)));			
				break;
			case "Member":
				lblMember.setText("Member");
				currentVisitorsData = FXCollections.observableArrayList(
		                new PieChart.Data("0-1 hours, " + String.valueOf(memberVisitors.get(0) + "%"), memberVisitors.get(0)),
		                new PieChart.Data("1-2 hours, " + String.valueOf(memberVisitors.get(1) + "%"), memberVisitors.get(1)),
		                new PieChart.Data("2-3 hours, " + String.valueOf(memberVisitors.get(2) + "%"), memberVisitors.get(2)),
		                new PieChart.Data("3-4 hours, " + String.valueOf(memberVisitors.get(3) + "%"), memberVisitors.get(3)));
				break;
			case "Group":
				lblGroup.setText("Group");
				currentVisitorsData = FXCollections.observableArrayList(
		                new PieChart.Data("0-1 hours, " + String.valueOf(groupVisitors.get(0) + "%"), groupVisitors.get(0)),
		                new PieChart.Data("1-2 hours, " + String.valueOf(groupVisitors.get(1) + "%"), groupVisitors.get(1)),
		                new PieChart.Data("2-3 hours, " + String.valueOf(groupVisitors.get(2) + "%"), groupVisitors.get(2)),
		                new PieChart.Data("3-4 hours, " + String.valueOf(groupVisitors.get(3) + "%"), groupVisitors.get(3)));
				break;
			default:
				return;
		}

		currentPie.setData(currentVisitorsData);
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		DepartmentManagerController.firstName = firstName;
	}

	public static boolean getStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		DepartmentManagerController.status = status;
	}

	public static void setData(boolean status) {
		setStatus(status);
	}
	
//	/**
//	 * for CheckBox
//	 */
//	public void groupRadioButton() {
//		ButtonGroup  group = new ButtonGroup();
//		
//		for (int i = 0; i < TableDep.getItems().size(); i++) {
//			group.add(TableDep.getItems().get(i).getMarkCh());
//				
//			}
//		}
//		radioCash.setToggleGroup(group);
//		radioCreditCard.setToggleGroup(group);
//		radioPayPal.setToggleGroup(group);
//	}


	/**
	 * button approve will remove the row and will update the table from DB will
	 * send to server array list of object [0] - name method [1] - string "yes" [2]
	 * -object of ManagerRequest
	 * 
	 * @param event
	 */
	@FXML
	void approve(ActionEvent event) {
		for (int i = 0; i < TableDep.getItems().size(); i++) {
			if (TableDep.getItems().get(i).getMarkCh().isSelected()) {
				TableViewSet s = TableDep.getItems().get(i);
				ManagerRequest mnData = new ManagerRequest(s.getParkName(), Integer.parseInt(s.getIdEmp()),
						s.getReqType());
				data.add("removePendingsManagerReq");
				data.add(mnData);
				data.add("yes");
				System.out.println("before server");
				ClientUI.sentToChatClient(data);
				
				System.out.println("pass server");
				if (status)
					TableDep.getItems().remove(TableDep.getItems().get(i));
				else {
					if (TableDep.getItems().get(i).getReqType().equals("discount")) {
						alert.failedAlert("Failed", "Already has discount on these dates!");
					} else {
						alert.failedAlert("Failed", "something went wrong, please try later again!");
					}
				}

			}
			data.clear();
		}
		iniailTabel();
	}

	/**
	 * button disapprove will not remove the row and will update the server will
	 * send to server array list of Object : [0] - name method [1] - string "no" [2]
	 * - Object of ManagerRequest
	 * 
	 * @param event
	 */
	@FXML
	void disapprove(ActionEvent event) {

		for (int i = 0; i < TableDep.getItems().size(); i++) {
			if (TableDep.getItems().get(i).getMarkCh().isSelected()) {
				TableViewSet s = TableDep.getItems().get(i);
				ManagerRequest mnData = new ManagerRequest(s.getParkName(), Integer.parseInt(s.getIdEmp()),
						s.getReqType());
				data.add("removePendingsManagerReq");
				data.add(mnData);
				data.add("no");

				ClientUI.sentToChatClient(data);

				if (status) {
					TableDep.getItems().remove(TableDep.getItems().get(i));
					alert.setAlert("The request dissaprove");
				}
				

			}
			data.clear();
		}
		iniailTabel();
	}

	public void addData(ArrayList<ArrayList<String>> al) {
		ObservableList<TableViewSet> listForTable = FXCollections.observableArrayList();
		String str = null;
		for (ArrayList<String> arrayList : al) {

			if (arrayList.get(1).equals("discount")) {
				String [] FromDate = arrayList.get(5).split(" ");
				String [] ToDate = arrayList.get(6).split(" ");
				String discString = arrayList.get(4);
				double disc =Double.parseDouble(discString);
				disc = (1-disc)*100;
				//int discount =(1 - Integer.parseInt())*100;
				str = "Discount : " + String.format("%.1f", disc) + "%" + " in the following dates: " + FromDate[0] + " - "
						+ ToDate[0];
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

	// set style for the pressed button
	// input: the active button
	// output: new style
	public void setButtonPressed(Button button) {
		button.setStyle("-fx-background-color: transparent;" + "-fx-border-color: brown;"
				+ "-fx-border-width: 0px 0px 0px 3px;");
	}

	// set style for the released buttons
	// input: the inactive buttons
	// output: removing the previous style
	public void setButtonReleased(Button button, Button button1, Button button2) {
		button.setStyle("-fx-background-color: transparent;");
		button1.setStyle("-fx-background-color: transparent;");
		button2.setStyle("-fx-background-color: transparent;");
	}

	// displays the chart for the information retrieved from DB
	// input: from -> start date
	// to -> end date
	// ArrayList<ArrayList<String>> cancelledOrders, cells:
	// cell [0]: parkName
	// cell [1]: date of canceled/dismissed
	// cell [2]: amount
	// output: displays the data
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void barChart() {
		int i;
		int disneyDate = 0;
		int jurasicDate = 0;
		int universalDate = 0;
		int count = 0;
		String minDateFormat = cancelledOrders.get(0).get(1).substring(0, 10);
		String maxDateFormat = cancelledOrders.get(cancelledOrders.size() - 1).get(1).substring(0, 10);
		LocalDate minDate = LocalDate.parse(minDateFormat);
		LocalDate maxDate = LocalDate.parse(maxDateFormat);

		xAxis = new CategoryAxis();
		yAxis = new NumberAxis(0, 1000, 50);

		bcCancells.getData().clear();
		bcCancells.setAnimated(false);
		bcCancells.setBarGap(0d);
		bcCancells.setCategoryGap(4.0);

		bcCancells.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		bcCancells.setPrefSize(613, 430);
		bcCancells.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

		bcCancells.setTitle("Cancellation/Dismissed Reports");

		Series<String, Double> disney = new Series<>();
		Series<String, Double> jurasic = new Series<>();
		Series<String, Double> universal = new Series<>();

		disney.setName("Disney");
		jurasic.setName("Jurasic");
		universal.setName("Universal");

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
			if (cancelledOrders.get(0).get(0).equals("disney") && disneyDate == 0) {
				// x = day of the month, y = sum of cancel
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(0).get(2))));
			} else if (disneyDate != 0) {
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(disneyDate).get(2))));
				disneyDate = 0;
			} else {
				disney.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}

			// parkName = Jurasic && same day in the month
			if (cancelledOrders.get(0).get(0).equals("jurasic") && jurasicDate == 0) {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(0).get(2))));
			} else if (jurasicDate != 0) {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(jurasicDate).get(2))));
				jurasicDate = 0;
			} else {
				jurasic.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}

			// parkName = Universal && same day in the month
			if (cancelledOrders.get(0).get(0).equals("universal") && universalDate == 0) {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(0).get(2))));
			} else if (universalDate != 0) {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(),
						Double.parseDouble(cancelledOrders.get(universalDate).get(2))));
				universalDate = 0;
			} else {
				universal.getData().add(new XYChart.Data(date.getDayOfMonth() + "/" + date.getMonthValue(), 0));
			}
		}

		bcCancells.getData().addAll(disney, jurasic, universal);
	}

	// checks if the date exists and saves the first position where it appears
	// input: date to check
	// output: - return the number of times he appears
	// - saves the first index where he appeared
	public int checkIfExists(LocalDate date) {
		int count = 0;
		index = 0;

		for (int i = 0; i < cancelledOrders.size(); i++) {
			String DateFormat = cancelledOrders.get(i).get(1).substring(0, 10);
			LocalDate checkDate = LocalDate.parse(DateFormat);
			if (date.equals(checkDate)) {
				if (index == 0) {
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
	public void sendToServerArrayList(String caseName, ArrayList<String> date) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		msg.add(caseName);
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
		if (cancelData.isEmpty()) {
			setError(true);
		} else {
			setError(false);
			DepartmentManagerController.cancelledOrders = cancelData;
		}
	}
	
	// getting information from the server
	// input: none
	// output: list of regular visitors:
	// ArrayList<Object>: cell[0] 0-1 hour
	// 					  cell[1] 1-2 hours
	//	 				  cell[2] 2-3 hours
	//	  				  cell[3] 3-4 hours
	public static void receivedFromServerRegularsVisitorsData(double one, double two, double three, double four) {
		DepartmentManagerController.regularVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(one)));
		DepartmentManagerController.regularVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(two)));
		DepartmentManagerController.regularVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(three)));
		DepartmentManagerController.regularVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(four)));
	}
	
	// getting information from the server
	// input: none
	// output: list of members visitors:
	// ArrayList<Object>: cell[0] 0-1 hour
	// 					  cell[1] 1-2 hours
	//	 				  cell[2] 2-3 hours
	//	  				  cell[3] 3-4 hours
	public static void receivedFromServerMembersVisitorsData(double one, double two, double three, double four) {
		DepartmentManagerController.memberVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(one)));
		DepartmentManagerController.memberVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(two)));
		DepartmentManagerController.memberVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(three)));
		DepartmentManagerController.memberVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(four)));
	}
	
	// getting information from the server
	// input: none
	// output: list of group visitors:
	// ArrayList<Object>: cell[0] 0-1 hour
	// 					  cell[1] 1-2 hours
	//	 				  cell[2] 2-3 hours
	//	  				  cell[3] 3-4 hours
	public static void receivedFromServerGroupsVisitorsData(double one, double two, double three, double four) {
		DepartmentManagerController.groupVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(one)));
		DepartmentManagerController.groupVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(two)));
		DepartmentManagerController.groupVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(three)));
		DepartmentManagerController.groupVisitors.add(Double.parseDouble(new DecimalFormat("##.##").format(four)));
	}

	public static boolean getError() {
		return error;
	}

	public static void setError(boolean error) {
		DepartmentManagerController.error = error;
	}

	public static ArrayList<ArrayList<String>> getDBList() {
		return DBList;
	}

	public static void setDBList(ArrayList<ArrayList<String>> dBList) {
		DBList = dBList;
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

		/***** Visitors Reports *****/
		dpVisitorsFrom.setValue(LocalDate.now().withDayOfMonth(1));
		// listener for updating the date
		dpVisitorsFrom.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpVisitorsFrom.setValue(newValue);
		});

		// plusMonths(1) to get the next month
		// withDayOfMonth(1) to get the first day
		dpVisitorsTo.setValue(dpVisitorsFrom.getValue().plusMonths(1).withDayOfMonth(1));
		// listener for updating the date
		dpVisitorsTo.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpVisitorsTo.setValue(newValue);
		});
		
		/***** Cancel Reports *****/
		dpCancelFrom.setValue(LocalDate.now().withDayOfMonth(1));
		// listener for updating the date
		dpCancelFrom.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpCancelFrom.setValue(newValue);
		});

		// plusMonths(1) to get the next month
		// withDayOfMonth(1) to get the first day
		dpCancelTo.setValue(dpCancelFrom.getValue().plusMonths(1).withDayOfMonth(1));
		// listener for updating the date
		dpCancelTo.valueProperty().addListener((ov, oldValue, newValue) -> {
			dpCancelTo.setValue(newValue);
		});
	}
}
