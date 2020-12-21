package controllers;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertController {
	private String result;
	
	// showing alert message
	public void setAlert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Failed");
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// showing success alert message
	public void successAlert(String title, String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// showing failed alert message
	public void failedAlert(String title, String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		alert.showAndWait();
	}

	// showing alert message
	public void ensureAlert(String title, String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(msg);
		Optional<ButtonType> action = alert.showAndWait();
		
		if (action.get() == ButtonType.OK) {
			setResult("OK");
		}
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
