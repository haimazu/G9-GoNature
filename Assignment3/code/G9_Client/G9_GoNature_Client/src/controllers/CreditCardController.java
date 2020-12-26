package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import dataLayer.CreditCard;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orderData.Order;


public class CreditCardController implements Initializable {

	@FXML
	private Pane pnVerify;

	@FXML
	private JFXTextField txtCardNumber;

	@FXML
	private JFXComboBox<String> cbxExpiryMonth;

	@FXML
	private JFXComboBox<String> cbxExpiryYear;

	@FXML
	private JFXTextField txtHolderName;

	@FXML
	private JFXTextField txtCVV;

	@FXML
	private Button btnSave;

	private String dateCC;

	private static CreditCard  details;



	private Order myOrder = OrderController.getOrderSuccess();

	private static Boolean paymentStatus = false;

	private AlertController alert = new AlertController();

	@FXML
	void save(ActionEvent event) {
		ArrayList<Object> msgEditPaymentForServer = new ArrayList<>();
		if (checkNotEmptyCardFields() && chechCorrectfeilds()) {
			dateCC = cbxExpiryMonth.getValue().toString() + "/" + cbxExpiryYear.getValue().toString();

			details = new CreditCard(txtCardNumber.getText(), txtHolderName.getText(), dateCC,
					Integer.parseInt(txtCVV.getText()),0);
			
//			msgEditPaymentForServer.add("orderPaymentMathod");
//			msgEditPaymentForServer.add(details);
			
//			ClientUI.sentToChatClient(msgEditPaymentForServer);

//			if (this.getPaymentStatus()) {
				Stage stage = (Stage) btnSave.getScene().getWindow();
			    stage.close();
//			}

		}
	}

//	public static void recivedFromServerSuccessPayment(boolean status) {
//		setPaymentStatus(status);
//	}
//
//	public static boolean getPaymentStatus() {
//		return paymentStatus;
//	}
//
//	public static void setPaymentStatus(boolean paymentStatus) {
//		CreditCardController.paymentStatus = paymentStatus;
//	}

	public static CreditCard getDetails() {
		return details;
	}

	public static void setDetails(CreditCard details) {
		CreditCardController.details = details;
	}
	public static final Pattern VALIDCVV = Pattern.compile("^[0-9]{3}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALIDCardNumber = Pattern.compile("^[0-9]{16}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALIDName = Pattern.compile("^[A-Za-z]{1,10}+ [A-Za-z]{1,10}$",
			Pattern.CASE_INSENSITIVE);

	public boolean checkNotEmptyCardFields() {
		String CardNum = txtCardNumber.getText();
		String CVC = txtCVV.getText();
		String CardName = txtHolderName.getText();
//		String mount = cbxExpiryMonth.getValue().toString();
//		String year = cbxExpiryYear.getValue().toString();
		if (CardName.isEmpty() || CVC.isEmpty() || CardName.isEmpty() ) {
			alert.setAlert("One or more of the fields are empty.\n Please fill them in and try again.");
			return false;
		}

		return true;
	}

	// check valid input
	public static boolean validInput(String nameMathod, String txt) {
		Matcher matcher = null;
		if (nameMathod.equals("CardNumber")) {
			matcher = VALIDCardNumber.matcher(txt);
		} else if (nameMathod.equals("CVV")) {
			matcher = VALIDCVV.matcher(txt);
		} else if (nameMathod.equals("CardName")) {
			matcher = VALIDName.matcher(txt);
		}
		return matcher.find();
	}

	public boolean chechCorrectfeilds() {
		if (!validInput("CardNumber", txtCardNumber.getText())) {
			alert.setAlert("Invalid card number");
			return false;
		} else if (!validInput("CVV", txtCVV.getText())) {
			alert.setAlert("Invalid CVV");
			return false;
		} else if (!validInput("CardName", txtHolderName.getText())) {
			alert.setAlert("Invalid card holder name");
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> mounthArr = new ArrayList<>();
		
		for (int i = 1; i <= 12; i++) {
			mounthArr.add(String.valueOf(i));
		}
		cbxExpiryMonth.setItems(FXCollections.observableArrayList(mounthArr));
		cbxExpiryMonth.getSelectionModel().selectFirst();
		ArrayList<String> yearArr = new ArrayList<>();
		for (int i = 2021; i <= 2031; i++) {
			yearArr.add(String.valueOf(i));
		}
		cbxExpiryYear.setItems(FXCollections.observableArrayList(yearArr));
		cbxExpiryYear.getSelectionModel().selectFirst();
	}

}
