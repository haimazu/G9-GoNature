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
	
	private boolean status;

	@FXML
	void back(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
        stage.setScene(new Scene(root));
	}

	@FXML
	void login(ActionEvent event) {
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
			if (!status) {
				Alert("Failed", "Username or password doesn't match.");
				return;
			}
			// Success
			Alert("Success", "You've logged in successfully.");
			
			// Check the employee type
			// Switch to the screen
		}
	}
	
	public boolean checkUsername() {
		String username = txtUsername.getText();
		// starts with letter, letters or numbers, between 3 to 20
    	String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{3,20}$";
		boolean userStatus = false;
		
		if (username.isEmpty()) {
			Alert("Failed", "All fields required.");
		} else if (!username.matches(pattern)) {
			Alert("Failed", "Wrong pattern.\n"
					+ "Username starts with letter, then letters or numbers\n[between 4 to 20]");
		} else {
			userStatus = true;
		}
		return userStatus;
	}
	
	public boolean checkPassword() {
		String password = txtPassword.getText();
		boolean passStatus = false;
		
		if (password.isEmpty()) {
			Alert("Failed", "All fields required.");
		} else if (password.length() < 8) {
			Alert("Failed", "Password too short.");
		} else if (password.length() > 30) {
			Alert("Failed", "Password too long.");
		} else {
			passStatus = true;
		}
		return passStatus;
	}
	
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
	
	public static void recivedFromServer(boolean status) {
		status = status;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
