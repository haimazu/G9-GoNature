package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
    private Label lblPresentDisc;

    @FXML
    private Label lblPresentMaxVis;

    

	private static String firstName;
	private static String parkName;
	

	public static String getParkName() {
		return parkName;
	}

	public static void setParkName(String parkName) {
		ParkManagerController.parkName = parkName;
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
		ParkManagerController.firstName = firstName;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());
		setParkName(LoginController.getParkName());
		lblParkName.setText(getParkName());
	}

	@FXML
	void setDiscount(ActionEvent event) {
		if(!btnSetVisitors.isVisible())
		{
			lblSetMax.setVisible(false);
			btnSubmitVisits.setVisible(false);
			btnSetVisitors.setVisible(true);
		}
		btnSetDisc.setVisible(false);
		txtManageDsic.setVisible(true);
		txtDateFrom.setVisible(true);
		txtDateTo.setVisible(true);
		btnSubmitDisc.setVisible(true);

	}

	@FXML
	void setMaxCapacity(ActionEvent event) {
		if(!btnSetDisc.isVisible())
		{
			btnSetDisc.setVisible(true);
			txtManageDsic.setVisible(false);
			txtDateFrom.setVisible(false);
			txtDateTo.setVisible(false);
			btnSubmitDisc.setVisible(false);
		}
		btnSetVisitors.setVisible(false);
		lblSetMax.setVisible(true);
		btnSubmitVisits.setVisible(true);

	}

}
