package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private Pane pnLogin;
	@FXML
	private Button btnBack;

	@FXML
	private JFXTextField txtUsername;
	@FXML
	private FontAwesomeIconView userIcon;
	@FXML
	private JFXPasswordField txtPassword;
	@FXML
	private FontAwesomeIconView passIcon;

	@FXML
	private Hyperlink hplForgotPassword;
	@FXML
	private Button btnLogin;

	private static String status, firstName;

	private boolean userStatus = false, passStatus = false;

	// Switch screens: Login -> Welcome
	@FXML
	void back(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}

	/*
	 * msg is ArrayList of objects -> 
	 * 		[0] -> the function who calling to service from the server "login" 
	 * 		[1] -> ArrayList of String -> 
	 * 							[0] -> username 
	 * 						    [1] -> password 
	 * The function waiting to response from the server and can get: if
	 * success (the user exists) -> we getting the role of the user else (the user
	 * doesn't exists) -> getting "Failed"
	 **/
	@FXML
	void login(ActionEvent event) throws IOException {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();
		// Data fields
		ArrayList<String> data = new ArrayList<String>();

		if (checkUsername() && checkPassword()) {
			msg.add("login");
			data.add(txtUsername.getText());
			data.add(txtPassword.getText());
			msg.add(data);
			
			ClientUI.sentToChatClient(msg);

			// Username and password doesn't match
			if (getStatus().equals("Failed")) {
				Alert("Username or password doesn't match.");
				return;
			}

			// Check the employee type
			// Switch to the screen
			Stage stage = (Stage) btnLogin.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/" + getStatus() + ".fxml"));
			stage.setScene(new Scene(root));
		}
	}

	// Check password input before sending to DB
	public boolean checkUsername() {
		String username = txtUsername.getText();
		// Username consists a letter then letters or numbers [length of 3-20 characters]
		String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{3,20}$";

		if (username.isEmpty()) {
			Alert("All fields required.");
		} else if (!username.matches(pattern)) {
			Alert("Wrong pattern.\n"
					+ "Username consists a letter, then letters or numbers.\n"
					+ "[length of 4-20 characters]");
		} 
		
		return userStatus;
	}

	// Check password input before sending to DB
	public boolean checkPassword() {
		String password = txtPassword.getText();

		if (password.isEmpty()) {
			Alert("All fields required.");
		} else if (password.length() < 8) {
			Alert("Password too short.");
		} else if (password.length() > 30) {
			Alert("Password too long.");
		} 
		
		return passStatus;
	}

	// showing alert message
	public void Alert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Failed");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// received data from the server
	public static void receivedFromServer(ArrayList<String> msgReceived) {
		setStatus(msgReceived.get(0));
		setFirstName(msgReceived.get(1));
	}

	// get the 'role' of the user
	public static String getStatus() {
		return status;
	}

	// set the 'role' of the user
	public static void setStatus(String status) {
		LoginController.status = status;
	}
	
	// get the 'firstName' of the user
	public static String getFirstName() {
		return firstName;
	}

	// set the 'firstName' of the user
	public static void setFirstName(String firstName) {
		LoginController.firstName = firstName;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// active listener for the username text field
		txtUsername.textProperty().addListener((obs, oldValue, newValue) -> {
			userStatus = false;
			// Username consists a letter then letters or numbers [length of 3-20 characters]
			String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{3,20}$";

			// the text field painted red if doesn't match the conditions
			userIcon.setFill(Color.RED);
			txtUsername.setStyle("-jfx-unfocus-color: red; " 
							       + "-fx-text-fill: red; " 
								   + "-fx-prompt-text-fill: red;");

		    // otherwise -> match the conditions, painted green
			if (!newValue.isEmpty() && newValue.matches(pattern)) {
				userStatus = true;
				userIcon.setFill(Color.GREEN);
				txtUsername.setStyle("-jfx-unfocus-color: green; " 
								+ "-fx-text-fill: green; " 
									+ "-fx-prompt-text-fill: green;");
			}
		});

		// active listener for the password text field
		txtPassword.textProperty().addListener((obs, oldValue, newValue) -> {
			passStatus = false;
			// Character range between 8 and 30
			String pattern = "^.{8,30}$";

			// the password field painted red if doesn't match the conditions
			passIcon.setFill(Color.RED);
			txtPassword.setStyle("-jfx-unfocus-color: red; " 
							   + "-fx-text-fill: red; " 
						       + "-fx-prompt-text-fill: red;");

			// otherwise -> match the conditions, painted green
			if (!newValue.isEmpty() && newValue.matches(pattern)) {
				passStatus = true;				
				passIcon.setFill(Color.GREEN);
				txtPassword.setStyle("-jfx-unfocus-color: green; " 
								   + "-fx-text-fill: green; " 
								   + "-fx-prompt-text-fill: green;");
			}
		});
	}
}
