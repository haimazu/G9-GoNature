package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ParkEmployeeController implements Initializable {
	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;
	@FXML
	private Button btnDashboard;
	@FXML
	private Button btnSettings;

	@FXML
	private Label lblTitle;

	@FXML
	private Pane pnSettings;
	@FXML
	private Pane pnDashboard;

	@FXML
	private Label lblPrice;
	@FXML
	private Label lblDiscount;
	@FXML
	private Label lblPayment;
	@FXML
	private Label lblTotalPrice;

	@FXML
	private Label lblOrderNumber;
	@FXML
	private Label lblParkName;
	@FXML
	private Label lvlDate;
	@FXML
	private Label lblTime;
	@FXML
	private Label lblVisitorsNumber;
	@FXML
	private Label lblEmail;

	@FXML
	private Button btnBarcodeScan;

	@FXML
	private JFXTextField txtOrderNumber;
	@FXML
	private Button btnShowDetails;

	@FXML
	private Button btnRndonVisitor;

	@FXML
	private Button btnApprove;

	private static String firstName;

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void showDetails(ActionEvent event) {
		
	}

	@FXML
	void approve(ActionEvent event) {

	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		ParkEmployeeController.firstName = firstName;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// get the user firstname from the login 
		setFirstName(LoginController.getFirstName());
		// add the user first name to say "Welcome, firstname"
		lblFirstNameTitle.setText(getFirstName());
		
		// force the field to be numeric only
		txtOrderNumber.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters	
			if (!newValue.matches("\\d")) {	
				// ^\\d -> everything that not a digit
				txtOrderNumber.setText(newValue.replaceAll("[^\\d]", ""));
			} 
		});
	}
}
