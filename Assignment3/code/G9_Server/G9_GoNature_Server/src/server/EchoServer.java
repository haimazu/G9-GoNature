package server;

import java.io.IOException;
//import java.sql.Connection;
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
		@SuppressWarnings("unchecked")
		ArrayList<Object> recived = (ArrayList<Object>) msg;
		switch ((String) recived.get(0)) {
		case "login":
			Login.login(recived, client);
			break;
		case "orderParksNameList":
			NewOrder.ParksNames(recived, client);
			break;
		case "ordersByIdOrMemberId":	
			ExistingOrderCheck.getOrderDetailsByIdOrMemberId(recived, client);
			break;
		case "ordersByOrderNumber":	
			ExistingOrderCheck.getOrderDetailsByOrderNumber(recived, client);
			break;
		case "getParkDetails":
			UpdateVisitorsNumber.getParkDetails(recived, client);
			break;
		case "updateCurrentVisitors":
			UpdateVisitorsNumber.updateParkCurrentVisitors(recived, client);
			break;
		case "order":
			NewOrder.NewReservation(recived, client);
			break;
		case "checkValidOrderNum":
			ExistingOrderCheck.getOrderDetailsByOrderNumber(recived, client);
			break;
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
