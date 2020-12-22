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

	private AlertController alert = new AlertController();

	private static boolean status = false;
	private static String msg = "not";

	public static String getMsg() {
		return msg;
	}

	public static void setMsg(String msg) {
		WaitingListConfirmContoller.msg = msg;
	}

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

			if (WaitingListConfirmContoller.status) {
				WaitingListConfirmContoller.status = false;
				alert.ensureAlert("Success!",
						"you are in our wait list we will send you an Email and SMS notification as soon as a spot is available."
								+ "\nbe sure to answer within one hour or you will lose your spot in the line.");
			} else if (msg.equals("alreadyExist")) {
				alert.ensureAlert("information", "You are already in the waiting list");
			} else
				alert.failedAlert("Failed!",
						"something went wrong, our code monkey has been notified and will work on the error, please try again shortly. ");
			
			Stage stage = (Stage) btnListMe.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
			stage.setScene(new Scene(root));
		}

	}

	public static void recivedfromWaitListServer(Object status) {
		if (status instanceof Boolean) {
			Boolean flag1 = (boolean) status;
			setStatus(flag1);
		} else {
			String flag2 = (String) status;
			setMsg(flag2);
		}

	}

	public boolean checkAgreeRadioBtn() {
		if (agree.isSelected()) {
			return true;
		}
		alert.setAlert("you need to aprove the terms");
		return false;
	}

}
