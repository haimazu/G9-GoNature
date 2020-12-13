package server;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ServerController extends AbstractServer {

	public ServerController(int port) {
		super(port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ArrayList<Object> recived = (ArrayList<Object>)msg;
		switch ((String)recived.get(0)) {
		case "login":
			Login.login((ArrayList<Object>)msg, client);
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub
	}
	
	public static void sendToMyClient(ArrayList<Object>msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
