package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import client.ClientUI;
//import common.Status;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
*
*controller that gets the user login details
*
* @author Haim Azulay Hodaya Mekonen
*/

public class LoginController implements Initializable {
	@FXML
	private Pane pnLogin;
	@FXML
	private Button btnBack;

	@FXML
	private JFXTextField txtUsername;
	@FXML
	private FontAwesomeIconView userIcon;
	@FXML
	private JFXPasswordField txtPassword;
	@FXML
	private FontAwesomeIconView passIcon;

	@FXML
	private Hyperlink hplForgotPassword;
	@FXML
	private Button btnLogin;

	private static String status, firstName;
	public static String password;
	public static String username;
	private static String parkName;
	private static String error;
	private boolean userStatus = false, passStatus = false;
	private AlertController alert = new AlertController();

	/**
	 * Switch screens: Login -> Welcome
	 **/

	@FXML
	void back(ActionEvent event) throws IOException {			
		Stage stage = (Stage) btnBack.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Welcome.fxml"));
		stage.setScene(new Scene(root));
	}
		
	 /**
	 *
	 * msg is ArrayList of objects -> [0] -> the function who calling to service
	 * from the server "login" [1] -> ArrayList of String -> [0] -> username [1] ->
	 * password The function waiting to response from the server and can get: if
	 * success (the user exists) -> we getting the role of the user else (the user
	 * doesn't exists) -> getting "Failed" 
	 * @param ActionEvent event
	 * @exception IOException
	 * 
	 */
	
	@FXML
	void login(ActionEvent event) throws IOException {
		// Data fields
		ArrayList<String> data = new ArrayList<String>();

		if (checkUsername() && checkPassword()) {
			data.add(txtUsername.getText());
			data.add(txtPassword.getText());
			sendToServerArrayList("login", data);
			data.clear();

			// Username and password doesn't match / the user is already logged in
			if (getError().equals("Failed")) {
				alert.failedAlert("Failed", "Username / password doesn't match or the user is already logged in.");
			} else {
				// needed for check in parkManager
				setPassword(txtPassword.getText());
				setUsername(txtUsername.getText());
				
				// update as loggedin
				data.add(getUsername());
				data.add(String.valueOf(1));
				sendToServerArrayList("updateLoggedIn", data);

				// Check the employee type
				// Switch to the screen
				Stage stage = (Stage) btnLogin.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("/gui/" + getStatus() + ".fxml"));
				stage.setScene(new Scene(root));
			}
		}
	}

	/**
	 * Check Username input before sending to DB     
	 * @return  true if the input is correct false otherwise
	 */
	
	public boolean checkUsername() {
		String username = txtUsername.getText();
		// Username consists a letter then letters or numbers [length of 3-20
		// characters]
		String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{3,20}$";

		if (username.isEmpty()) {
			alert.failedAlert("Failed", "All fields required.");
		} else if (!username.matches(pattern)) {
			alert.failedAlert("Failed", "Wrong pattern.\n" + "Username consists a letter, then letters or numbers.\n"
					+ "[length of 4-20 characters]");
		}

		return userStatus;
	}

	/**
	 * Check password input before sending to DB     
	 * @return  true if the input is correct false otherwise
	 */
	
	public boolean checkPassword() {
		String password = txtPassword.getText();

		if (password.isEmpty()) {
			alert.failedAlert("Failed", "All fields required.");
		} else if (password.length() < 8) {
			alert.failedAlert("Failed", "Password too short.");
		} else if (password.length() > 30) {
			alert.failedAlert("Failed", "Password too long.");
		}

		return passStatus;
	}
	
	/**
	 *  sends the case we dealing with and dbColumns to the server to get data
	 *  
	 * @param type String depending on the case         
	 * @param   dbColumns ArrayList of String
	 */

	public void sendToServerArrayList(String type, ArrayList<String> dbColumns) {
		// Query
		ArrayList<Object> msg = new ArrayList<Object>();	
		msg.add(type);
		// Data fields
		msg.add(dbColumns);	
		// set up all the order details and the payment method
		ClientUI.sentToChatClient(msg);
	}

	/**
	 * @param msgReceived-data from the server
	 *                
	 * @return cell 0: the 'role' of the user
	 *	       cell 1: the first name of the user
	 *		   cell 2: name of the park where the employee works
	 */

	@SuppressWarnings("unchecked")
	public static void receivedFromServerUserStatus(Object msgReceived) {
		if (msgReceived instanceof String) {
			setError("Failed");
		} else if (msgReceived instanceof ArrayList) {
			// ArrayList<String> msgReceived;
			setError("");
			setStatus(((ArrayList<String>) msgReceived).get(0));
			setFirstName(((ArrayList<String>) msgReceived).get(1));
			setParkName(((ArrayList<String>) msgReceived).get(2));
		}
	}

	/**
	 * @param msgReceived-boolean value with the result of the action Status 
	 *                
	 * @return T / F, 
	 *	       T ==> Update logged in status success
	 *		   F ==> Update logged in status failed
	 */
	
	public static void receivedFromServerLoggedInStatus(boolean msgReceived) {
		if (msgReceived) {
			System.out.println("Update logged in / out status success.");
		} else {
			System.out.println("Update logged in / out status failed.");
		}
	}

	// get the 'role' of the user
	public static String getStatus() {
		return status;
	}

	// set the 'role' of the user
	public static void setStatus(String status) {
		LoginController.status = status;
	}

	// get the 'firstName' of the user
	public static String getFirstName() {
		return firstName;
	}

	// set the 'firstName' of the user
	public static void setFirstName(String firstName) {
		LoginController.firstName = firstName;
	}

	// get the 'parkName'
	public static String getParkName() {
		return parkName;
	}

	// set the 'parkName'
	public static void setParkName(String parkName) {
		LoginController.parkName = parkName;
	}

	public static String getError() {
		return error;
	}

	public static void setError(String error) {
		LoginController.error = error;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		LoginController.password = password;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		LoginController.username = username;
	}

	/**
	 * Initializing and force each of the fields according to the Required templates 
	 */
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// active listener for the username text field
		txtUsername.textProperty().addListener((obs, oldValue, newValue) -> {
			userStatus = false;
			// Username consists a letter then letters or numbers [length of 4-20
			// characters]
			String pattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{3,20}$";

			// the text field painted red if doesn't match the conditions
			userIcon.setFill(Color.RED);
			txtUsername.setStyle("-jfx-unfocus-color: red; " + "-fx-text-fill: red; " + "-fx-prompt-text-fill: red;");

			// otherwise -> match the conditions, painted green
			if (!newValue.isEmpty() && newValue.matches(pattern)) {
				userStatus = true;
				userIcon.setFill(Color.GREEN);
				txtUsername.setStyle(
						"-jfx-unfocus-color: green; " + "-fx-text-fill: green; " + "-fx-prompt-text-fill: green;");
			}
		});

		// active listener for the password text field
		txtPassword.textProperty().addListener((obs, oldValue, newValue) -> {
			passStatus = false;
			// Character range between 8 and 30
			String pattern = "^.{8,30}$";

			// the password field painted red if doesn't match the conditions
			passIcon.setFill(Color.RED);
			txtPassword.setStyle("-jfx-unfocus-color: red; " + "-fx-text-fill: red; " + "-fx-prompt-text-fill: red;");

			// otherwise -> match the conditions, painted green
			if (!newValue.isEmpty() && newValue.matches(pattern)) {
				passStatus = true;
				passIcon.setFill(Color.GREEN);
				txtPassword.setStyle(
						"-jfx-unfocus-color: green; " + "-fx-text-fill: green; " + "-fx-prompt-text-fill: green;");
			}
		});
	}
}
