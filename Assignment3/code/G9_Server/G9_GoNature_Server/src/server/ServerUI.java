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
	private static String msg;
	private static EchoServer sv;

	public static void main(String args[]) throws Exception {
		launch(args);
	} 

	@Override
	public void start(Stage primaryStage) throws Exception {
		control = new ServerController();
		control.start(primaryStage);
	}

	public static void runServer(EchoServer sv)
	{
		if (DBup) {
			ServerUI.sv = sv;
			//sv.setCon(MySQLConnection.getDbConn());
	        try {
	          sv.listen();
	        } catch (Exception ex) {
	        	setMsg("ERROR - Could not listen for clients!");
	        }
	        serverUP=true;
		}
		else {
			setMsg("Please Start DB first");
		}
	}
	
	public static void stopServer() {
		try {
			sv.close();
			System.out.println("covefee");
		} catch (IOException e) {
			e.printStackTrace();
		}
		setMsg("Stoped Server succsefuly");
		serverUP=false;
	}


	public static void connectToDB(ArrayList<String> str) {
		MySQLConnection.connectToDB(str);
	}
	
	public static void disconnectFromDB() {
		MySQLConnection.disconnectFromDB();
	}

	public static String getMsg() {
		String ret = msg;
		setMsg("");
		return ret;
	}

	public static void setMsg(String msg) {
		ServerUI.msg = msg;
	}
	
//	public static boolean isDBup() {
//		return DBup;
//	}
//
//	public static boolean isServerUP() {
//		return serverUP;
//	}


}
