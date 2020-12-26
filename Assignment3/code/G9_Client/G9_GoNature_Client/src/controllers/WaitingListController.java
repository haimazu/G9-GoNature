package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class WaitingListController implements Initializable {

	@FXML
	private Pane pnVerify;

	@FXML
	private Button btnCheck;

	@FXML
	private Button btnHere;

	@FXML
	private JFXDatePicker txtdate;

	@FXML
	private JFXComboBox<String> cbxArrivelTime;

	private static ArrayList<String> Dates = new ArrayList<>();
	private static ArrayList<String> time = new ArrayList<>();
	private ArrayList<String> nonReleventDates = new ArrayList<>();
	//private ArrayList<String> ReleventDates = new ArrayList<>();

	public static ArrayList<String> getTime() {
		System.out.println(Dates);
		return Dates;
	}

	public static void setDates(ArrayList<String> date) {
		WaitingListController.Dates = date;
	}

	public void nonReleventDatesForCalender(ArrayList<String> arr) {

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {

						for (String str : arr) {
							super.updateItem(item, empty);
							String[] arrDate = getDate(str, "Date");
							LocalDate thisDate = LocalDate.of(Integer.parseInt(arrDate[0]),
									Integer.parseInt(arrDate[1]), Integer.parseInt(arrDate[2]));
							if (item.compareTo(thisDate) == 0) {
								setDisable(true);
								// setStyle("-fx-background-color: #ffc0cb;");
							}
						}
					}
				};
			}
		};

		txtdate.setDayCellFactory(dayCellFactory);
	}

	public String[] getDate(String date, String what) {
		String[] arrDateAndTime = date.split(" ");
		String[] arrDate = arrDateAndTime[0].split("-");
		if (what.equals("Date"))
			return arrDate;
		else
			return arrDateAndTime;
	}

	public void setArrForDatePicker() {
		ArrayList<String> newArr = new ArrayList<>();
		int count = 0, rounds = 0, flag = 0;
		String[] first = null;
		for (String str : Dates) {
			String[] TimesDates = getDate(str, "all");
			if (count == 0) {
				first = TimesDates;
				count++;
			} else {
				if (TimesDates[0].equals(first[0])) {
					count++;
				} else {
					count = 0;
					rounds = 0;
					flag = 1;
				}
			}
			if (flag != 1)
				rounds++;
			if (rounds == 3) {
				if (count == 3) {
					newArr.add(first[0]);
				}
				rounds = 0;
				count = 0;
			}
			flag = 0;
		}
		this.nonReleventDates = newArr;
		System.out.println(nonReleventDates.toString());
	}

	public void setArrForTime() {

		int length = Dates.size();
		int[] places = new int[length];
		for (int i = 0; i < places.length; i++) {
			places[i] = -1;
		}
		for (String str : nonReleventDates) {
			for (int i = 0; i < length; i++) {
				String[] TimesDates = getDate(Dates.get(i), "all");
				if (TimesDates[0].equals(str)) {
					places[i] = i;
				}
			}
		}
		for (int i = length -1; i >=0 ; i--) {
			if (places[i] != -1)
				Dates.remove(i);
		}
	}

	public void setTimeWithSpecificDate(String date) {
		ArrayList<String> timeTmp = new ArrayList<>(time);
		System.out.println(time);
		for (String str : Dates) {
			String[] TimesDates = getDate(str, "all");
			if (TimesDates[0].equals(date)) {
				for (String start : timeTmp) {
					String[] arr = start.split("-");
					String  [] specificArrTime = arr[0].split(":");
					String [] specificArrDate = TimesDates[1].split(":");
					if (specificArrDate[0].equals(specificArrTime[0])) {
						timeTmp.remove(start);
					}
				}
			}
		}
		System.out.println("look: "+ time);
		cbxArrivelTime.setDisable(false);
		cbxArrivelTime.setItems(FXCollections.observableArrayList(timeTmp));
	}

	@FXML
	void chooseDate(ActionEvent event) {
		try {
			String date = txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if (!date.equals(null)) {
				setTimeWithSpecificDate(date);
			}
		} catch (NullPointerException e) {
			System.out.println("didnt choose date");
		}
	}

	@FXML
	void check(ActionEvent event) {
		
	}
	
	
	

	@FXML
	void here(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Pane root = FXMLLoader.load(getClass().getResource("/gui/WaitingListConfirmation.fxml"));
		Scene scene = new Scene(root);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

		Stage stage2 = (Stage) btnHere.getScene().getWindow();
		stage2.close();
	}

	public static void getListDatesServer(ArrayList<String> arr) {
		setDates(arr);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.time.add("8:00 - 12:00");
		this.time.add("12:00 - 16:00");
		this.time.add("16:00 - 20:00");
		cbxArrivelTime.setDisable(true);

//		Dates.add("2020-12-30 08:00:00");
//		Dates.add("2020-12-30 12:00:00");
//		Dates.add("2020-12-30 16:00:00");
//		Dates.add("2021-01-02 12:00:00");
//		Dates.add("2021-01-10 12:00:00");
//		Dates.add("2021-01-30 08:00:00");
//		Dates.add("2021-01-30 12:00:00");
//		Dates.add("2021-01-30 16:00:00");
//		
		ArrayList<Object> sendServer = new ArrayList<>();
		sendServer.add("checkFullDays");
		sendServer.add(OrderController.getOrder());
		ClientUI.sentToChatClient(sendServer);
		
		setArrForDatePicker();
		nonReleventDatesForCalender(nonReleventDates);
		setArrForTime();
	}

}
