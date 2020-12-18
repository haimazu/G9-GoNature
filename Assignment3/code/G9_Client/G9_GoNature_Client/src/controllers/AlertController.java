package controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertController {
	
	
	// showing alert message
	public void setAlert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Failed");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}
}
