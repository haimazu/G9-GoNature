package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class OrderController implements Initializable {
	@FXML
	private StackPane pnStackOrder;

	@FXML
	private Pane pnOrder;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnClear;
	@FXML
	private Button btnNext;

	@FXML
	private Pane pnPayment;
	@FXML
	private Button btnContinue;

	@FXML
	private Pane pnConfirmation;
	@FXML
	private Button btnHome;
	
	@FXML
    private ImageView imgOrder;
	
	private Image imgOrderEmpty = new Image("/gui/cart-removebg-80.png");
	private Image imgOrderFull = new Image("/gui/cartfull-removebg-80.png");

	@FXML
	void back(ActionEvent event) throws IOException {
		ObservableList<Node> stackPanels = this.pnStackOrder.getChildren();
				
		if (stackPanels.size() > 1) {
			Node topNode = stackPanels.get(stackPanels.size() - 1);

			if (topNode.getId().equals("pnOrder")) {
				Stage stage = (Stage) btnBack.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
				stage.setScene(new Scene(root));
			} else if (topNode.getId().equals("pnPayment")) {
				imgOrder.setImage(imgOrderEmpty);
				pnOrder.toFront();
			} else if (topNode.getId().equals("pnConfirmation")) {
				pnPayment.toFront();
			}
		}
	}

	@FXML
	void clear(ActionEvent event) {

	}

	@FXML
	void next(ActionEvent event) {
		imgOrder.setImage(imgOrderFull);
		
		if (btnNext == event.getSource()) {
			pnPayment.toFront();
		} else if (btnContinue == event.getSource()) {
			pnConfirmation.toFront();
		}
	}

	@FXML
	void home(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnHome.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
