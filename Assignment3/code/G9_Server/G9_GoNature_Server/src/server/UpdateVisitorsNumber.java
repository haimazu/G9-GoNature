package server;

import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;


public class UpdateVisitorsNumber {

	public static void MaxVisitorsNumberUpdate(ArrayList<Object> recived, ConnectionToClient client) {
		
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Park data = (Park) recived.get(1); // Park object received
		
		
		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("park"); // table name
		query.add("maxVisitorAmount");//column to edit
		String amount=""+data.getMaximumCapacityInPark();
		query.add(amount);
		
		
		
	}
	
	public static void MaxAmountOrdersUpdate(ArrayList<Object> recived, ConnectionToClient client) {
		
	}

}
