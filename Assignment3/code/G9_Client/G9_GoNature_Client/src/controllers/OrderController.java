package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import client.ChatClient;
import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	private JFXDatePicker txtdate;
	@FXML
    private JFXComboBox<String> cbxArrivelTime;

	@FXML
	private JFXTextField txtVisitorsNumber;
	@FXML
	private JFXTextField txtInvitingEmail;
	@FXML
	private JFXTextField txtmemberID;
	@FXML
	private JFXTextField txtParkName;
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
		txtVisitorsNumber.clear();
		txtInvitingEmail.clear();
		txtParkName.clear();
		txtdate.getEditor().clear();
		txtmemberID.clear();
	}

	/*
	 * msg is ArrayList of objects -> [0] -> the function who calling to service from the server "order" 
	 * 								  [1] -> ArrayList of String -> 
	 * 															[0] -> visitors number
	 * 															[1] -> email 
	 * 															[2] -> park name 
	 * 															[3] -> arrival date 
	 * 															[4] -> arrival time 
	 * 															[5] ->member id (optional)
	 * 
	 * By clicking button next the function will check it the values in the field are correct - if so, send them to server 
	 * we will receive from the server: 
	 **/
	@FXML
	void next(ActionEvent event) {
		ArrayList<Object> msg = new ArrayList<>();
		ArrayList<String> input = new ArrayList<>();
		
		// continue only if the fields are correct
		if (checkEmptyFields()) {
			
			msg.add("order");
			input.add(txtVisitorsNumber.getText());
			input.add(txtInvitingEmail.getText());
			input.add(txtParkName.getText());
			input.add(txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			input.add(txtmemberID.getText());
			msg.add(input);
			imgOrder.setImage(imgOrderFull);

			if (btnNext == event.getSource()) {
				pnPayment.toFront();
				ClientUI.sentToChatClient(msg);

			} else if (btnContinue == event.getSource()) {
				pnConfirmation.toFront();

			}
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
		cbxArrivelTime.setItems(FXCollections.observableArrayList("8:00-12:00", "12:00-16:00"));
		cbxArrivelTime.getSelectionModel().selectFirst();
		// we wanted to enable to pick only a date from now and on

		/*
		 * txtdate.setDayCellFactory(param -> new DateCell() {
		 * 
		 * @Override public void updateItem(LocalDate date, boolean empty) {
		 * super.updateItem(date, empty); setDisable(empty ||
		 * date.compareTo(LocalDate.now()) > 0 ); } });
		 */

	}

	public static void recivedFromServer(ArrayList<String> msgRecived) {

	}
	/*Not working !!! exception in receiving empty date !!!!!! */	// check if the fields are empty
	public boolean checkEmptyFields() {
		Alert alert = new Alert(AlertType.ERROR);
		String visitorsNumber = txtVisitorsNumber.getText();
		String email = txtInvitingEmail.getText();
		String parkNum = txtParkName.getText();
		String date = txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String memberId = txtmemberID.getText();

		if (visitorsNumber.isEmpty() || email.isEmpty() || parkNum.isEmpty() || date.isEmpty()
				|| memberId.isEmpty()) {
			alert.setTitle("Empty Fields");
			alert.setHeaderText(null);
			alert.setContentText("One or more fields are empty. Please fill in all fields!");
			alert.showAndWait();
			return false;
		}
		return true;

	}

}
