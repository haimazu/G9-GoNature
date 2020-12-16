package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class WaitingList {
	
	//input: 
	//
	//output:
	public void enterTheWaitList(ArrayList<Object> recived, ConnectionToClient client){
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received
		
		ArrayList<String> query = new ArrayList<String>();
		//query.add("insert"); // command
	//	query.add("orders"); // table name
		//query.add(toStringForReservation(data)); // values in query format

		if (MySQLConnection.insert(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
	}
	
	//input: 
	//
	//output:
	public boolean checkForAvilableSpots() {
		return true;
	}
	
	//input: 
	//
	//output:
	public void pullFromWaitList() {
		
	}
	
	//input: 
	//
	//output:
	public void confirmAndOrder() {
		
	}
	
	//input: 
	//
	//output:
	public void nextInLine() {
		
	}
}
