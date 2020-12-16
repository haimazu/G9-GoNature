package server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import gui.ServerController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	private static ServerController control;

	public EchoServer(int port, ServerController control) {
		super(port);
		EchoServer.control = control;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		ArrayList<Object> recived = (ArrayList<Object>) msg;
		switch ((String) recived.get(0)) {
		case "login":
			Login.login((ArrayList<Object>) msg, client);
			break;
		case "orderParksNameList":
			NewOrder.ParksNames((ArrayList<Object>) msg, client);
			break;
		case "ordersByIdOrMemberId":	
			ExistingOrderCheck.getOrderDetailsByIdOrMemberId((ArrayList<Object>) msg, client);
			break;
		case "ordersByOrderNumber":	
			ExistingOrderCheck.getOrderDetailsByOrderNumber((ArrayList<Object>) msg, client);
			break;
		case "order":
			NewOrder.NewReservation((ArrayList<Object>) msg, client);
		default:
			break;
		}
		// TODO Auto-generated method stub
	}

	public static void sendToMyClient(ArrayList<Object> msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client) {
		control.logIt("New Client conneted: "+ client.toString() +" "+ this.getPort());
	}

}
