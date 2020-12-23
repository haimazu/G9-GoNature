package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import orderData.Order;
import userData.Member;

public class ServiceRepresentativeController implements Initializable {
	
	private AlertController alert = new AlertController();
	
	
	@FXML
	private Label lblFirstNameTitle;
	@FXML
	private Button btnLogout;

	private static String firstName;

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
	    private JFXCheckBox cbFamilyMember;
	    @FXML
	    private JFXTextField txtFamilyMemberAmount;
	    @FXML
	    private JFXCheckBox cbGuideMember;

	  
	    
	    @FXML
	    void addMember(ActionEvent event) {
	   
	    	String id=txtId.getText();
	    	String firstName= txtFirstName.getText();
	    	String lastName= txtLastName.getText();;
	    	String email=txtEmail.getText();
	    	String phoneNumber= txtPhoneNumber.getText();
	    	
	    	if ( id.isEmpty() || lastName.isEmpty() ||  email.isEmpty() ||phoneNumber.isEmpty()
					||  firstName.isEmpty()) 
				alert.setAlert("One or more of the fields are empty.\n Please fill them in and try again.");
	    	
			 if(validIdInput()&&validFirstLastNameInput()&&validPhoneNumberInput())
			
			 if(!(txtEmail.getText().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$")))
					alert.setAlert("The filed has to be in this pattern foobar@gmail.com.");
			
			//single member
			// if(!cbFamilyMember.isSelected()&&!cbGuideMember.isSelected())
			//	membership= new Member(id,firstName,lastName,email,phoneNumber,'1',)
			
	    	
	    	//newMember = new Member(getParkName(), dateAndTimeFormat, memberId, id, 
					//Integer.parseInt(txtRandomVisitorsAmount.getText()));
			// check the random visitor type and calculate the price
			// Query
			//ArrayList<Object>  = new ArrayList<Object>();	
			//msg.add("newMembershipInsert");
			// Data fields
			//msg.add(newMember);	
			// set up all the order details and the payment method
			//ClientUI.sentToChatClient(msg);
	    }
	    //check valid input for first and last name 
	public boolean validFirstLastNameInput() {
		
    	if(txtFirstName.getText().length()<2|| txtLastName.getText().length()<2)
    		{
				alert.setAlert("A name must have at least 2 letters.");
				return false;
			}
 
		return true;	
	}
	 //check valid input for id 
	public boolean validIdInput() {
		
		if(txtId.getText().length()!=9)
			{
				alert.setAlert("Id must have 9 digits.");
				return false;
		
			}
		
		return true;
	}
	//check valid input for phoneNumber
	public boolean validPhoneNumberInput() {
		
    	if(txtPhoneNumber.getText().length()!=10) 
    		{
				alert.setAlert("PhoneNumber must have 10 digits.");
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

}
