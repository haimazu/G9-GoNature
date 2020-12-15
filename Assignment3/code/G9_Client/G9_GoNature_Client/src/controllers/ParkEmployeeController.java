package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
	private Label lblDate;
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
	private boolean entryStatus = false;
	private LocalDateTime arrivelTime;
	private int hour, minutes;
	private int day, mongth, year;

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void barcodeScan(ActionEvent event) {
		// get order number from the server
		// call showDetails() function to set up all the order details
	}

	@FXML
	void showDetails(ActionEvent event) {
		// set up all the order details and the payment method
	}

	@FXML
	void approve(ActionEvent event) {
		if (entryStatus) {
			Alert("Success", "Thank you, hope you enjoy your time in the park.");
		// wrong date
		// wrong time
		} else {
			Alert("Failed", "wrong.");
		}
	}
	
	public boolean checkDate() {	
		arrivelTime = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("mm/dd/yyyy");	
		String date = lblDate.getText();

		System.out.println(arrivelTime.format(dateFormat));
		
		return entryStatus;
	}
	
	public boolean checkTime() {
		arrivelTime = LocalDateTime.now();
		String time = lblTime.getText();
		
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		String[] split = time.split(":"); 
		
		System.out.println(arrivelTime.format(timeFormat));
		
		return entryStatus;		
	}
	
	// showing alert message
	public void Alert(String title, String msg) {
		if (title == "Success") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		} else if (title == "Failed"){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setHeaderText(null);
			alert.setContentText(msg);
			alert.showAndWait();
		}
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
