package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.stage.Stage;
import orderData.Order;

public class ManageOrderController implements Initializable {
	private Order orderFromServer;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnUpdate;

	@FXML
	private JFXTextField txtVisitorsNumber;

	@FXML
	private JFXDatePicker txtdate;

	@FXML
	private JFXComboBox<String> cbxArriveTime;

	private AlertController alert = new AlertController();

	private static void setOrderdetails(Order order) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxArriveTime.setItems(FXCollections.observableArrayList("8:00-12:00", "12:00-16:00", "16:00-20:00"));
		cbxArriveTime.getSelectionModel().selectFirst();

		// initialize date value with today
		// The user can pick a date only from today until this day next year
		txtdate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});

		txtdate.setValue(LocalDate.now());
	}

	public static void recievedOrderFromServer(Order order) {
		setOrderdetails(order);
	}

	@FXML
	void back(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void update(ActionEvent event) {
		ArrayList<Object> msgForServer = new ArrayList<>();
		if (checkNotEmptyVisitorsField() && checkCurrentTime()) {
			System.out.println("hihi");

			// ClientUI.sentToChatClient(msgForServer);

		}
	}

	// do not allow to reserve for passed hour today
	public boolean checkCurrentTime() {
		LocalDate date = txtdate.getValue();
		String[] arrSplit = cbxArriveTime.getValue().toString().split("-");
		String[] hour = arrSplit[0].split(":");
		LocalTime arrivalTime = LocalTime.of(Integer.parseInt(hour[0]), 00, 00);
		LocalTime now = LocalTime.now();
		if (date.compareTo(LocalDate.now()) == 0 && now.compareTo(arrivalTime) >= 0) {
			alert.setAlert("You're trying to book for a time that has already passed. Please select a future time\r\n");
			return false;
		}
		return true;
	}

	// check that the user fill all the fields
	public boolean checkNotEmptyVisitorsField() {
		String visitorsNumber = txtVisitorsNumber.getText();
		if (visitorsNumber.isEmpty()) {
			alert.setAlert("Cannot leave 'Visitors Amount' field empty");
			return false;
		}
		return true;
	}

}
