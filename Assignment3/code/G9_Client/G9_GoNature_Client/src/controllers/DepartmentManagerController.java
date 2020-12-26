package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

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



	@FXML
    private Button btnDashboard;

    @FXML
    private Button btnVisitsReport;

    @FXML
    private Button btnCancelsReport;

    @FXML
    private Button btnSettings;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblFirstNameTitle;

    @FXML
    private StackPane pnStack;

    @FXML
    private Pane pnSettings;

    @FXML
    private Pane pnCancels;

    @FXML
    private Pane pnVisits;

    @FXML
    private BarChart<?, ?> visitsChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Button btnReload;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnPrevPark;

    @FXML
    private Button btnNextPark;

    @FXML
    private Pane pnDashboard;

    @FXML
    private TableView<TableViewSet> tableDep;
    
    @FXML
    private TableColumn<TableViewSet, String> parkName;

    @FXML
    private TableColumn<TableViewSet, String> requestType;

    @FXML
    private TableColumn<TableViewSet, String> requestDetails;

    @FXML
    private TableColumn<TableViewSet, JFXCheckBox> checkBox;

    @FXML
    private Label LabelCount;

    @FXML
    private Button btnApprove;

    @FXML
    private Button btnDisaprove;


	@FXML
	private JFXCheckBox agree;
	
	
	private static String firstName;
	
	private static ArrayList<ArrayList<String>> DBList=new ArrayList<>();

	private int count=0;

	public static ArrayList<ArrayList<String>> getDBList() {
		return DBList;
	}

	public static void setDBList(ArrayList<ArrayList<String>> dBList) {
		DBList = dBList;
	}

	@FXML
	void logout(ActionEvent event) throws IOException {
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
			if(arrayList.get(1).equals("discount")) {
				String str= "Discount : " + arrayList.get(4) + "%"+ " in the following dates: "+ arrayList.get(5) + " - "+arrayList.get(6);
				listForTable.add(str);
			}
			else if(arrayList.get(1).equals("max_c")) {
				String str = "Visitors Capacity : " + arrayList.get(2);
				listForTable.add(str);
			}
			else if(arrayList.get(1).equals("max_o")) {
				String str = "Visitors Order Capacity : " + arrayList.get(3);
				listForTable.add(str);
			}
			
			listForTable.add(agree);
			addRow(listForTable);
			
			listForTable.clear();
		}
	}
	
	public void addRow(ArrayList<Object> al) {
		tableDep.getItems().add(new TableViewSet(al));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());
		
		parkName.setCellValueFactory(new PropertyValueFactory<TableViewSet,String>("parkName"));
		requestType.setCellValueFactory(new PropertyValueFactory<TableViewSet,String>("requestType"));
		requestDetails.setCellValueFactory(new PropertyValueFactory<TableViewSet,String>("requestDetails"));
		checkBox.setCellValueFactory(new PropertyValueFactory<TableViewSet,JFXCheckBox>(""));
		
		ArrayList<String> msg = new ArrayList<>();
		//msg.add("PemdingManagerRequests");
		//ClientUI.sentToChatClient(msg);
		
		msg.add("1234");
		msg.add("discount");
		msg.add("0");
		msg.add("0");
		msg.add("15");
		msg.add("2020-12-30");
		msg.add("2020-01-30");
		msg.add("kuku");
		
		DBList.add(msg);
		
		count=DBList.size();
		LabelCount.setText(String.valueOf(count));
		addData(DBList);
	}
}
