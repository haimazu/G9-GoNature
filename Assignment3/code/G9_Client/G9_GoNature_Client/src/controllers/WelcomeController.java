package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXLabelFloatControl;

import client.ClientUI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.LocalDateTimeStringConverter;
import orderData.Order;

public class WelcomeController implements Initializable {
	@FXML
	private StackPane pnWelcomeStack;
	@FXML
	private Pane pnWelcomeRight;
	@FXML
	private Button btnOrderNow;
	@FXML
	private Button btnOrderNumber;
	@FXML
	private JFXTextField txtOrderNum;
	@FXML
	private Button btnGo;

	private AlertController alert = new AlertController();
	private static Order orderDetails;
	private static boolean ispending;

	public static boolean getisIspending() {
		return ispending;
	}

	public static void setIspending(boolean ispending) {
		WelcomeController.ispending = ispending;
	}
	@FXML
	void login(ActionEvent event) throws IOException {
		// switch scene to login
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		Pane root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	@FXML
	void orderNow(ActionEvent event) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/gui/Order.fxml"));
		pnWelcomeRight.getChildren().removeAll();
		pnWelcomeRight.getChildren().setAll(pane);
	}

	@FXML
	void orderNumber(ActionEvent event) {
		btnOrderNumber.setVisible(false);
		btnGo.setVisible(true);
		txtOrderNum.setVisible(true);
	}

	/*
	 input : non 
	 output : non 
	 send to server : array list of object : [0]->string :checkValidOrderNum [1]->array list of string with order number
	 returned from server : in case of success order object with order details , in case of failure - null
	 check if order number inserted by user exist , and it arrival time didn't already pass
	 present info alerts accordingly
	 
	 **/
	@FXML
	void go(ActionEvent event) throws IOException, ParseException {
		String orderNum = txtOrderNum.getText().toString();

		
		if (orderNum.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid order Number.");
		else {
			
			ArrayList<Object> msg = new ArrayList<>();
			ArrayList<String> data = new ArrayList<>();
			msg.add("checkOrderForGo");
			data.add(orderNum);
			msg.add(data);
			ClientUI.sentToChatClient(msg);
			
//			for (int i=0;i<5;i++) {
//				try {
//					TimeUnit.SECONDS.sleep(1);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			//System.out.println("after2");
			
			
			
			
			if (orderDetails == null) {
				alert.setAlert("Failed, No such order.");
				txtOrderNum.clear();
				btnOrderNumber.setVisible(true);
				btnGo.setVisible(false);
				txtOrderNum.setVisible(false);
				return;
			}
		
			if(arrivalTimePassed()) {
				alert.setAlert("It is not possible to manage an order with a time that already passed !");
				btnOrderNumber.setVisible(true);
				btnGo.setVisible(false);
				txtOrderNum.setVisible(false);
				return;
			}
			
			 else {
				Stage stage = (Stage) btnGo.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/gui/EditOrder.fxml"));
				setOrderDetails(orderDetails);
				stage.setScene(new Scene(root));
			}
			WelcomeController.setOrderDetails(null);
		}
	}

	public static Order getOrderDetails() {
		return orderDetails;
	}

	public static void setOrderDetails(Order orderDetails2) {
		orderDetails = orderDetails2;
	}

	public static void recievedFromServerValidOrderAndPending(ArrayList<Object> orderDetails) {
		if (orderDetails.get(1) instanceof Order) {
			setOrderDetails((Order)orderDetails.get(1));
			setIspending((boolean)orderDetails.get(2));
		}
		else
			setOrderDetails(null);
		
	}
	

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("GoNature");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				// Data fields
				ArrayList<String> data = new ArrayList<String>();
				// Query
				ArrayList<Object> msg = new ArrayList<Object>();

				if (LoginController.getUsername() != null) {
					msg.add("updateLoggedIn");
					// update as loggedin as logged out
					data.add(LoginController.getUsername());
					data.add(String.valueOf(0));
					// Data fields
					msg.add(data);
					// set up all the order details and the payment method
					ClientUI.sentToChatClient(msg);
					
					System.out.println("emergency exit");
					System.out.println(LoginController.getUsername() + " have been disconnected.");
					LoginController.setUsername(null);
				}
				Platform.exit();
				System.exit(0);
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Object> parkNamesArr = new ArrayList<>();
		parkNamesArr.add("orderParksNameList");
		ClientUI.sentToChatClient(parkNamesArr);
	}
	
	
	public boolean arrivalTimePassed() throws ParseException
	{
		LocalDate now = LocalDate.now();
		String [] arrsplitStrings = orderDetails.getArrivedTime().split(" ");
		String [] datesplit =arrsplitStrings[0].split("-");
		String[] hour = arrsplitStrings[1].split(":");
		LocalTime arrivalTime = LocalTime.of(Integer.parseInt(hour[0]), 00, 00);
		//[0]->year , [1]->month , [2]->day
		LocalDate arrivedate =LocalDate.of(Integer.parseInt(datesplit[0]), Integer.parseInt(datesplit[1]), Integer.parseInt(datesplit[2]));
		if(arrivedate.compareTo(now) <0)
			return true;
		if(arrivedate.compareTo(now) == 0 && arrivalTime.compareTo(LocalTime.now())<0)
			return true;
		return false;
	}



}
