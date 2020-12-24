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
import orderData.Order;
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
	private JFXCheckBox cbGuideMember;
	@FXML
	private JFXTextField txtGroupMembersAmount;
	@FXML
	private JFXCheckBox cbCreditCard;

	
    @FXML
    private JFXTextField txtCardNumber;
    @FXML
    private JFXComboBox<?> cbxExpiryMonth;
    @FXML
    private JFXComboBox<?> cbxExpiryYear;
    @FXML
    private JFXTextField txtHolderName;
    @FXML
    private JFXTextField txtCVV;
    @FXML
    private Button btnSave;

    

    
	@FXML
	//add a new groupMember to the db
	void addMember(ActionEvent event) {

		String id = txtId.getText();
		String firstName = txtFirstName.getText();
		String lastName = txtLastName.getText();
		//String groupMemberAmount=txtGroupMembersAmount.getText();
		String groupMemberAmount="5";
		String email = txtEmail.getText();
		String phoneNumber = txtPhoneNumber.getText();

		//txtGroupMembersAmount.setEditable(cbGuideMember.isSelected());
		
		if (id.isEmpty() || lastName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() 
				|| firstName.isEmpty()|| !cbGuideMember.isSelected())
			alert.setAlert("One or more of the fields are empty.\n Please fill them in and try again.");

		if (validIdInput() && validFirstLastNameInput() && validPhoneNumberInput()&&validEmailInput())
		{

			//	if(txtGroupMembersAmount.getText().matches("[0-9]+") && Integer.parseInt(groupMemberAmount)<=15
					//	&&Integer.parseInt(groupMemberAmount)>0)
				// alert.setAlert("You need to choose amount between 1-15.");
				
			
				Member newMember = new Member(id,firstName,lastName,null,phoneNumber,email,null,groupMemberAmount);
			 	// check the random visitor type and calculate the price
				// Query
				ArrayList<Object> msg = new ArrayList<Object>();
				msg.add("newMembershipInsert");
				// Data fields
				msg.add(newMember);
				// set up all the order details and the payment method
				ClientUI.sentToChatClient(msg);
				
				if(getStatus())
					alert.successAlert("Succesful", "A member add succesfuly");
				else alert.setAlert("Failed to add your member.");
				
		}
		
	}

	@FXML
	void crditCard(ActionEvent event) throws IOException {
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
				cbCreditCard.setSelected(false);
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

		if (!(txtEmail.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) 
			{
				alert.setAlert("The filed has to be in this pattern foobar@gmail.com.");
				return false;
			}
		return true;
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

		// force the field to be with
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
		
	}
	public static boolean getStatus() {
		return status;
	}

	public static void setStatuss(boolean status) {
		ServiceRepresentativeController.status = status;
	}
	
	public static void receivedFromServerAddMemberStatus(boolean status) {
		if (status) {
			setStatuss(status);//status=true
		} else {
			setStatuss(status);//status=false
		}
	}

}
