package server;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import gui.ServerController;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	private static boolean DBup = false;
	private static boolean serverUP = false;
	private static ServerController control;
	private static EchoServer echoServ;

	public static void main(String args[]) throws Exception {
		launch(args);
	} 

	@Override
	public void start(Stage primaryStage) throws Exception {
		control = new ServerController();
		control.start(primaryStage);
	}

	public static boolean runServer(EchoServer sv)
	{
		if (DBup) {
			ServerUI.echoServ = sv;
	        try {
	          sv.listen();
	        } catch (Exception ex) {
	        	return false;
	        	//setMsg("ERROR - Could not listen for clients!");
	        }
	        serverUP=true;
	        return true;
		}
		else {
			return false;
			//setMsg("Please Start DB first");
		}
	}
	
	public static boolean stopServer() {
		try {
			echoServ.close();
			//System.out.println("covefee");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		serverUP=false;
		return true;
	}


	public static boolean connectToDB(ArrayList<String> data) {
		if(MySQLConnection.connectToDB(data)) {
			DBup=true;
			return true;
			}
		return false;
	}
	
	public static boolean disconnectFromDB() {
		if (serverUP) {
			return false;
			//SERVER UP FAILURE MSG
		}
		if(MySQLConnection.disconnectFromDB()) {
			DBup=false;
			return true;
			}
		return false;
	}
	
	public static boolean isDBup() {
		return DBup;
	}

	public static boolean isServerUP() {
		return serverUP;
	}


}