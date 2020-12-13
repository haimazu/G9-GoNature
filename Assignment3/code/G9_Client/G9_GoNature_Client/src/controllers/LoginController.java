package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Alert.AlertType;
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

	@FXML
	void back(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
        stage.setScene(new Scene(root));
	}

	@FXML
	void login(ActionEvent event) {
		if (txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
			Alert("Failed", "You've loser.");
		} else {
			Alert("Success", "You've logged in successfully.");
		}
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
}
