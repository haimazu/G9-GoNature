package controllers;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import orderData.Order;

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
	private JFXComboBox<String> cbxParkName;

	@FXML
	private JFXTextField txtVisitorsNumber;
	@FXML
	private JFXTextField txtInvitingEmail;
	@FXML
	private JFXTextField txtmemberID;
	// @FXML
	// private JFXTextField txtParkName;
	@FXML
	private ImageView imgOrder;

	@FXML
	private Button information;

	private Image imgOrderEmpty = new Image("/gui/cart-removebg-80.png");
	private Image imgOrderFull = new Image("/gui/cartfull-removebg-80.png");

	private static ArrayList<String> ParksNames = new ArrayList<>();
	private static Order orderSuccess;
	private Order order;
	private static String status = "not";
	private String memberId = null;
	private String ID = null;
	private AlertController alert = new AlertController();
	
	private PaymentController payStatus;

	public static String getStatus() {
		return status;
	}

	public static void setStatus(String status) {
		OrderController.status = status;
	}

	public static Order getOrderSuccess() {
		return orderSuccess;
	}

	public static void setOrderSuccess(Order orderSuccess) {
		OrderController.orderSuccess = orderSuccess;
	}

	public static void setParksNames(ArrayList<String> parksNames) {
		ParksNames = parksNames;
		System.out.println(ParksNames);
	}

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
		txtdate.getEditor().clear();
		txtmemberID.clear();
	}

	/*
	 * msgForServer is ArrayList of objects -> [0] -> name of the class [1] -> order
	 * Object
	 * 
	 * By clicking button next the function will check it the values in the fields
	 * are correct - if so, send them to server
	 * 
	 **/
	@FXML
	void next(ActionEvent event) throws IOException {
		ArrayList<Object> msgForServer = new ArrayList<>();

		// continue only if the fields are correct
		if (checkNotEmptyFields() && checkCorrectEmail() && checkCorrectAmountVisitor() && checkCorrectMemberId()
				&& checkCurrentTime()) {
			msgForServer.add("order");
			String strDateTime = txtdate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " "
					+ cbxArrivelTime.getValue().toString();

			this.order = new Order(Integer.parseInt(txtVisitorsNumber.getText()), txtInvitingEmail.getText(),
					"0549991234", cbxParkName.getValue().toString(), strDateTime, this.memberId, this.ID);
			msgForServer.add(order);
			imgOrder.setImage(imgOrderFull);

			if (btnNext == event.getSource()) {

				ClientUI.sentToChatClient(msgForServer);

				if (this.status.equals("Faild")) {
					Pane pane = FXMLLoader.load(getClass().getResource("/gui/WaitingList.fxml.fxml"));
					pnOrder.getChildren().removeAll();
					pnOrder.getChildren().setAll(pane);
				} else {
					payStatus = new PaymentController(orderSuccess);
					pnPayment.toFront();
				}
			} else if (btnContinue == event.getSource()) {
			
				if (payStatus.checkNotEmptyFields())
					pnConfirmation.toFront();
			}
		} else {
			this.memberId = null;
			this.ID = null;
		}
	}

	// home button
	@FXML
	void home(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnHome.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}

	/*
	 * recived the answer if the reservation success string if it faild Order Object
	 * if success
	 */
	public static void recivedFromServer(Object newOrder) {
		if (newOrder instanceof String) {
			String status = (String) newOrder;
			setStatus(status);
		} else {
			Order myOrder = (Order) newOrder;
			setOrderSuccess(myOrder);
		}
	}

	// return list of all the parks names
	public static void recivedFromServerParksNames(ArrayList<String> parks) {
		setParksNames(parks);
	}

	// check that the user fill all the fields
	public boolean checkNotEmptyFields() {
		String visitorsNumber = txtVisitorsNumber.getText();
		String email = txtInvitingEmail.getText();
		String parkNum = cbxParkName.getValue();
		String memberId = txtmemberID.getText();
		if (visitorsNumber.isEmpty() || email.isEmpty() || parkNum.isEmpty() || memberId.isEmpty()) {
			alert.setAlert("One or more of the fields are empty.\n Please fill them in and try again.");
			return false;
		}
		return true;
	}

	// check only for reservation for today
	public boolean checkCurrentTime() {
		LocalDate date = txtdate.getValue();
		String[] arrSplit = cbxArrivelTime.getValue().toString().split("-");
		String[] hour = arrSplit[0].split(":");
		LocalTime arrivalTime = LocalTime.of(Integer.parseInt(hour[0]), 00, 00);

		LocalTime now = LocalTime.now();
		if (date.compareTo(LocalDate.now()) == 0 && now.compareTo(arrivalTime) >= 0) {
			alert.setAlert("You're trying to book for a time that has already passed. Please select a future time\r\n");
//			if(now.compareTo(arrivalTime)>=)
//			cbxArrivelTime.setItems(FXCollections.observableArrayList("8:00-12:00", "12:00-16:00", "16:00-20:00"));
//			cbxArrivelTime.getSelectionModel().selectFirst();
			return false;
		}
		return true;
	}

	// check correct email
	public boolean checkCorrectEmail() {
		String email = txtInvitingEmail.getText();
		String nameMethod = "email";
		if (!validInput(nameMethod, email)) {
			alert.setAlert("Invalid email address");
			return false;
		}
		return true;
	}

	// check if the user put correct number
	public boolean checkCorrectAmountVisitor() {
		String AmountVisitor = txtVisitorsNumber.getText();
		String nameMethod = "AmountVisitor";
		if (!validInput(nameMethod, AmountVisitor) || AmountVisitor.equals("0")) {
			alert.setAlert("Invalid amount of visitors");
			return false;
		}

		return true;
	}

	// start with letter except from g= member
	// start with letter g = guid
	// id - traveler / member
	public boolean checkCorrectMemberId() {
		String memberId = txtmemberID.getText();
		String nameMethod = "memberId";
		if (validInput(nameMethod, memberId)) {
			this.memberId = memberId;
			return true;
		} else if (validInput("ID", memberId)) {
			this.ID = memberId;
			return true;
		}
		alert.setAlert("Invalid member-ID / ID ");
		return false;
	}

	// pattern for check
	public static final Pattern VALIDEMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern VALIDAmountVisito = Pattern.compile("^[0-9]{0,3}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALIDMemberId = Pattern.compile("^[f,g]{1}[0-9]{5}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALIDID = Pattern.compile("^[0-9]{9}$", Pattern.CASE_INSENSITIVE);

	// check valid input
	public static boolean validInput(String nameMathod, String txt) {
		Matcher matcher = null;
		if (nameMathod.equals("email")) {
			matcher = VALIDEMAIL.matcher(txt);

		} else if (nameMathod.equals("AmountVisitor")) {
			matcher = VALIDAmountVisito.matcher(txt);
		} else if (nameMathod.equals("memberId")) {
			matcher = VALIDMemberId.matcher(txt);
		} else if (nameMathod.equals("ID")) {
			matcher = VALIDID.matcher(txt);
		}
		return matcher.find();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbxArrivelTime.setItems(FXCollections.observableArrayList("8:00-12:00", "12:00-16:00", "16:00-20:00"));
		cbxArrivelTime.getSelectionModel().selectFirst();

		// user can choose to date only date today until next year.

		txtdate.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate nextYear = LocalDate.of(today.getYear() + 1, today.getMonth(), today.getDayOfMonth());
				setDisable(empty || (date.compareTo(nextYear) > 0 || date.compareTo(today) < 0));
			}
		});
		// txtdate = new JFXDatePicker(LocalDate.now());
		txtdate.setValue(LocalDate.now());

		cbxParkName.setItems(FXCollections.observableArrayList(ParksNames));
		cbxParkName.getSelectionModel().selectFirst();

		information.setTooltip(new Tooltip(
				"In order to get a discount insert member ID or ID number\nof the person that made the order"));

	}

}
