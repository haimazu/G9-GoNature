package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

	@FXML
	void go(ActionEvent event) throws IOException {
		String orderNum = txtOrderNum.getText();
		//if(checkValidOrederNum(orderNum))
		//{
		Stage stage = (Stage) btnOrderNumber.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/EditOrder.fxml"));
		stage.setScene(new Scene(root));
	//	}
		
	}

	private boolean checkValidOrederNum(String orderNum) {
		
		return false;
	}

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
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
