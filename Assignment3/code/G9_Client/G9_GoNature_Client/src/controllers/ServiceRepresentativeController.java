package controllers;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import dataLayer.CreditCard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import orderData.OrderType;
import userData.Member;

public class ServiceRepresentativeController implements Initializable {

	private AlertController alert = new AlertController();

	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;

	private static String firstName;
	private static boolean status;

	@FXML
	private JFXTextField txtId;
	@FXML
	private JFXTextField txtFirstName;
	@FXML
	private JFXTextField txtLastName;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextField txtPhoneNumber;
	@FXML
	private JFXTextField txtMembersAmount;
	
	
	@FXML
	private JFXCheckBox cbGuideMember;


	@FXML
	private Button btnCreditCard;


	@FXML
	// add a new Member data into the db
	void addMember(ActionEvent event) {

		String id = txtId.getText();
		// txtId.setText("111222333");
		String firstName = txtFirstName.getText();
		// txtFirstName.setText("hodaya");
		String lastName = txtLastName.getText();
		// txtLastName.setText("mekonen");
		String memberAmount = txtMembersAmount.getText();
		// txtMembersAmount.setText("5");
		String email = txtEmail.getText();
		// txtEmail.setText("mor@gmail.com");
		String phoneNumber = txtPhoneNumber.getText();
		// txtPhoneNumber.setText("0536754898");

		if (id.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || firstName.isEmpty()) {
			alert.setAlert("One or more of the fields are empty.\n Please fill them in and try again.");
			return;
		}
		if (validIdInput() && validFirstLastNameInput() && validPhoneNumberInput() && validEmailInput()) {
			
			//gets the member credit card details
			CreditCard creditCard = CreditCardController.getDetails();
			
			
			// member= member/family
			if (!cbGuideMember.isSelected()) {

				if (txtMembersAmount.getText().matches("[0-9]+") && Integer.parseInt(memberAmount) > 15
						&& Integer.parseInt(memberAmount) < 0)
					alert.setAlert("You need to choose amount between 1-15.");

				Member newMember = new Member(id, firstName, lastName, phoneNumber, email, OrderType.MEMBER,
						memberAmount);
				System.out.println(newMember.toString());
				// check the member type and insert him to the db
				// Query
				ArrayList<Object> msg = new ArrayList<Object>();
				msg.add("newMembershipInsert");
				// Data fields
				msg.add(newMember);

				 if(creditCard!=null)
					 msg.add(creditCard);//insert credit card details
				 else msg.add(null);

				// set up all the member details of the new member
				ClientUI.sentToChatClient(msg);

				if (getStatus()) {
					alert.successAlert("Succesful", "A member add succesfuly");
					ClearFields();
				} else
					alert.setAlert("Failed! the member u trying to insert allready exist.");
			}

			// member=guide
			else {
				Member newMember = new Member(id, firstName, lastName, phoneNumber, email, OrderType.GROUP, "0");
				// check the member type and insert him to the db
				// Query
				ArrayList<Object> msg = new ArrayList<Object>();
				msg.add("newMembershipInsert");
				// Data fields
				msg.add(newMember);
				
				 if(creditCard!=null)
					 msg.add(creditCard);//insert credit card details
				 else msg.add(null);
				 
				// set up all the member details of the new member
				ClientUI.sentToChatClient(msg);

				if (getStatus()) {
					alert.successAlert("Succesful", "A member add succesfuly");
					ClearFields();
				} else
					alert.setAlert("Failed! the member u trying to insert allready exist.");
			}

		}

	}

	@FXML
	void creditCard(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Pane root = FXMLLoader.load(getClass().getResource("/gui/CreditCard.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				btnCreditCard.isPressed();
			}
		});

	}

	// check valid input for first and last name
	public boolean validFirstLastNameInput() {

		if (txtFirstName.getText().length() < 3 || txtLastName.getText().length() < 3) {
			alert.setAlert("A name must have at least 2 letters.");
			return false;
		}

		return true;
	}

	// check valid input for id
	public boolean validIdInput() {

		if (txtId.getText().length() != 9) {
			alert.setAlert("Id must have 9 digits.");
			return false;

		}

		return true;
	}

	// check valid input for phoneNumber
	public boolean validPhoneNumberInput() {

		if (txtPhoneNumber.getText().length() != 10) {
			alert.setAlert("PhoneNumber must have 10 digits.");
			return false;
		}
		return true;
	}

	// check valid input for email
	public boolean validEmailInput() {

		if (!(txtEmail.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
			alert.setAlert("The filed has to be in this pattern foobar@gmail.com.");
			return false;
		}
		return true;
	}

	public void ClearFields() {

		txtId.clear();
		txtFirstName.clear();
		txtLastName.clear();
		txtEmail.clear();
		txtMembersAmount.clear();
		txtPhoneNumber.clear();

	}

	@FXML
	void logout(ActionEvent event) throws IOException {
		Stage stage = (Stage) btnLogout.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));
		stage.setScene(new Scene(root));
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		ServiceRepresentativeController.firstName = firstName;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		setFirstName(LoginController.getFirstName());
		lblFirstNameTitle.setText(getFirstName());

		// force the field to be numeric only
		txtId.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtId.setText(newValue.replaceAll("[^\\d]", ""));

			}
		});

		// force the field to be letters only
		txtFirstName.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\sa-zA-Z*")) {
				txtFirstName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}

		});

		// force the field to be letters only
		txtLastName.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\sa-zA-Z*")) {
				txtLastName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}

		});

		// force the field to be with letters only
		txtLastName.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\sa-zA-Z*")) {
				txtLastName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
			}

		});

		// force the field to be numeric only
		txtPhoneNumber.textProperty().addListener((obs, oldValue, newValue) -> {

			// \\d -> only digits
			// * -> escaped special characters
			if (!newValue.matches("\\d")) {
				// ^\\d -> everything that not a digit
				txtPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));

			}
		});
		
			//check if the group member type has been selected
			//if true set disable the member amount text field
		cbGuideMember.selectedProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue)
				txtMembersAmount.setDisable(true);
			else {
				txtMembersAmount.setDisable(false);
			}
		});

	}

	public static boolean getStatus() {
		return status;
	}

	public static void setStatuss(boolean status) {
		ServiceRepresentativeController.status = status;
	}

	public static void receivedFromServerAddMemberStatus(boolean status) {
		if (status) {
			setStatuss(status);// status=true
		} else {
			setStatuss(status);// status=false
		}
	}

}
