package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import javafx.event.ActionEvent;
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
	 * By clicking button go the function will check if the number is not empty and
	 * valid An alert will pop up if the field is empty we will check the returm
	 * value from server - if the order exist proceed to next screen for update if
	 * not -return to the main welcome screen
	 */
	/*
	 * msg is ArrayList of objects -> [0] -> name of the procedure that will be
	 * executed [1] -> orderNumber to check Object
	 **/
	@FXML
	void go(ActionEvent event) throws IOException {
		String orderNum = txtOrderNum.getText().toString();

		
		if (orderNum.isEmpty())
			alert.setAlert("Cannot leave this field empty! \nPlease insert Valid order Number.");
		else {
			ArrayList<Object> msg = new ArrayList<>();
			ArrayList<String> data = new ArrayList<>();
			msg.add("checkValidOrderNum");
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
//			System.out.println("after5");
			
			if (orderDetails == null) {
				alert.setAlert("Failed, No such order.");
				btnOrderNumber.setVisible(true);
				btnGo.setVisible(false);
				txtOrderNum.setVisible(false);
				return;
			} else {
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

	public static void recievedFromServerValidOrder(Object orderDetails) {
		System.out.println("MADE IT ALIVE");
		if (orderDetails instanceof Order)
			setOrderDetails((Order)orderDetails);
		else
			setOrderDetails(null);
		
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ParkEmployee.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("GoNature");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Object> parkNamesArr = new ArrayList<>();
		parkNamesArr.add("orderParksNameList");
		ClientUI.sentToChatClient(parkNamesArr);
	}



}
