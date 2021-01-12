package tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import com.itextpdf.text.log.SysoLogger;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.xml.simpleparser.NewLineHandler;

import controllers.IPassword;
import controllers.IRecievedFromSreverForLogin;
import controllers.IUserName;
import controllers.LoginController;
import javafx.event.ActionEvent;

public class LoginControllerTest {

	// Inside class
	public class StubUserName implements IUserName {

		@Override
		public boolean checkLoginUserName() {

			// TODO Auto-generated method stub
			return value;
		}
	}

	// Inside class
	public class StubPassword implements IPassword {

		@Override
		public boolean checkLoginPassword() {
			// TODO Auto-generated method stub
			return value;
		}
	}

	// Inside class
	public class StubRecivedFromServer implements IRecievedFromSreverForLogin {

		@Override
		public void receivedFromServerUserStatus(Object msgReceived) {
			// TODO Auto-generated method stub
			if (msgReceived instanceof String) {
				LoginController.setError("Failed");
			} else if (msgReceived instanceof ArrayList) {
				// ArrayList<String> msgReceived;
				LoginController.setError("");
				LoginController.setStatus("connected");
				LoginController.setFirstName("hodaya");
				LoginController.setParkName("disney");
			}
		}
//stub2

		@Override
		public void receivedFromServerLoggedInStatus(boolean msgReceived) {
			// TODO Auto-generated method stub
			System.out.println("Recieved ststus logged in/out");
		}

		@Override
		public void sendToServerArrayListLogin(String type, ArrayList<String> dbColumns) {
			// TODO Auto-generated method stub
			System.out.println("Send to server Arraylist");
		}

	}

	private IUserName iUserName;
	private IPassword iPassword;
	private IRecievedFromSreverForLogin iRecievedFromSreverForLogin;

	private LoginController logintest;

	private boolean value;
	private String expectedResult;
	private String actualResult;
	
	private ActionEvent event;

	@BeforeEach
	void setUp() throws Exception {

		iUserName = new StubUserName();
		iPassword = new StubPassword();
		iRecievedFromSreverForLogin = new StubRecivedFromServer();
		logintest = new LoginController(iUserName, iPassword, iRecievedFromSreverForLogin);
		value = true;
		expectedResult = null;
		actualResult = null;
	}

	@Test
	void userNameExist() throws IOException {
		value = true;
		iRecievedFromSreverForLogin.receivedFromServerUserStatus(new ArrayList<String>());
		logintest.login(event);
		expectedResult = "hodaya";
		actualResult = LoginController.getFirstName();
		assertEquals(expectedResult, actualResult);

	}

	@Test
	void userNameNotCorrect() throws IOException {

		value = false;
		logintest.login(event);
		expectedResult = null;
		actualResult = LoginController.getFirstName();
		assertEquals(expectedResult, actualResult);

	}

	@Test
	void parkNameExist() throws IOException {
		value = true;
		iRecievedFromSreverForLogin.receivedFromServerUserStatus(new ArrayList<String>());
		logintest.login(event);
		expectedResult = "disney";
		actualResult = LoginController.getParkName();
		assertEquals(expectedResult, actualResult);

	}

	@Test
	void failedTest() throws IOException {
		
		value = true;
		iRecievedFromSreverForLogin.receivedFromServerUserStatus(new String());
		logintest.login(event);
		expectedResult = "disney";
		actualResult = LoginController.getParkName();
		assertEquals(expectedResult, actualResult);
	}
}
