package test;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import ocsf.server.ConnectionToClient;
import server.Login;

public class LoginTest {
	private ArrayList<Object> received;
	
	private String expected = "Falied";
	
	@Mock
	private Login mockLogin;
	@Mock
	private ConnectionToClient client;
	
	// Executed before each test. It is used to prepare the test environment
	@Before
	public void setUp() throws Exception {
		received = new ArrayList<Object>();
		
		mockLogin = Mockito.mock(Login.class);
		client = Mockito.mock(ConnectionToClient.class);
		
		mockLogin.login(received, client);
		
		//Mockito.when(Login.login(received, null));
	}

	// invalid username and password
	
	// valid username and invalid password
	
	// invalid username and valid password
	
	// valid username and password
	
	// the user is already logged in (the user must be exists in the system)
	@Test
	public void test() {
		
		// username
		received.add("Haim");
		// password
		received.add("12345679");	
		expected = "Falied";
		
		//assertEquals(expected, MockLoginService.Login(received));
		received.clear();
	}
}
