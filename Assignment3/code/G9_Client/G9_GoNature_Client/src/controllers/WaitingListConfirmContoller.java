package controllers;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXCheckBox;
import com.mysql.cj.xdevapi.Client;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WaitingListConfirmContoller {

	@FXML
	private Pane pnResetPassword;

	@FXML
	private Button btnListMe;

	@FXML
	private JFXCheckBox agree;

	private AlertController alert= new AlertController();

	private static boolean status;

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		WaitingListConfirmContoller.status = status;
	}

	@FXML
	void listMe(ActionEvent event) throws IOException {
		ArrayList<Object> msgWaitList = new ArrayList<>();
		msgWaitList.add("enterTheWaitList");
		msgWaitList.add(OrderController.getOrder());
		if (checkAgreeRadioBtn()) {
			ClientUI.sentToChatClient(msgWaitList);

			if (WaitingListConfirmContoller.status)
				alert.ensureAlert("Listed",
						"You enter to the waiting lise\n you will get notify when you will have available spot");
			else
				alert.failedAlert("Faild", "The waiting list is full\nPlease try another date ");
			Stage stage = (Stage) btnListMe.getScene().getWindow();
    	    stage.close();
    		Stage stage2 = (Stage) btnListMe.getScene().getWindow();
    		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
    		stage2.setScene(new Scene(root));
		}
		
	}

	public static void recivedfromWaitListServer(boolean status) {
		setStatus(status);
	}

	public boolean checkAgreeRadioBtn() {
		if (agree.isSelected()) {
			return true;
		}
		alert.setAlert("you need to aprove the terms");
		return false;
	}

}
