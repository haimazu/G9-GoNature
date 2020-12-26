package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

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
	private JFXTextField lblMaxcapByorder;

	@FXML
	private Button btnSubmitCapacityByorder;

	@FXML
	private Button btnSetDisc1;

	private static String firstName;
	private static String parkName;
	private AlertController alert = new AlertController();

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
	void setDiscount(ActionEvent event) {
		if (!btnSetMaxByOrder.isVisible()) {
			btnSetMaxByOrder.setVisible(true);
			lblMaxcapByorder.setVisible(false);
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
		lblMaxcapByorder.setVisible(true);
		btnSubmitCapacityByorder.setVisible(true);
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
			lblMaxcapByorder.setVisible(false);
			btnSubmitCapacityByorder.setVisible(false);
		}
		btnSetVisitors.setVisible(false);
		lblSetMax.setVisible(true);
		btnSubmitVisits.setVisible(true);

	}

	@FXML
	void submitPendingDiscount(ActionEvent event) {
		String discount = txtManageDsic.getText();
		if (discount.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid discount.");
	}

	@FXML
	void submitVisitorsCapacityByorder(ActionEvent event) {
		String capacity = lblSetMax.getText();
		if(capacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
	}

	@FXML
	void submitVisitorsCapacity(ActionEvent event) {
		String orderCapacity = lblMaxcapByorder.getText();
		if(orderCapacity.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid capacity.");
	}

	void presentParkData(Park parkDetails) {

	}

}
