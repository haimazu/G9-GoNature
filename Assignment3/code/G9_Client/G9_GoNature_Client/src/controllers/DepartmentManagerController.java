package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

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

//    @FXML
//    private BarChart<?, ?> visitsChart;

//    @FXML
//    private CategoryAxis xAxis;
//
//    @FXML
//    private NumberAxis yAxis;

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

//    @FXML
//    private TableView<?> tableDep;

	@FXML
	private Label LabelCount;

	@FXML
	private Button btnReload1;

	@FXML
	private JFXTextField txtDiscount;

	@FXML
	private JFXTextField txtDates;

	@FXML
	private Button btnApproveDis;

	@FXML
	private JFXTextField txtMaxVisitor;

	@FXML
	private JFXTextField txtParkNameVis;

	@FXML
	private JFXTextField txtParkNameDis;

	@FXML
	private Button btnDisaproveDis;

	@FXML
	private Button btnApproveVis;

	@FXML
	private Button btnDisaproveVis;

	private static String firstName;

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
	void seeAll(ActionEvent event) {

	}

	@FXML
	void approve(ActionEvent event) {

	}

	@FXML
	void btnApproveDis(ActionEvent event) {

	}

	@FXML
	void disapprove(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());
	}
}
