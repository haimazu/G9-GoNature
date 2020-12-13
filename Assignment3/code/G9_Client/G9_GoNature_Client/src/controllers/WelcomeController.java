package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
	void login(ActionEvent event) throws IOException {
		Pane pane = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		pnWelcomeRight.getChildren().removeAll();
		pnWelcomeRight.getChildren().setAll(pane);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("GoNature");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
