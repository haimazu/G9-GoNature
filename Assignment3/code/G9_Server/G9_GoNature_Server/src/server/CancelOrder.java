package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class CancelOrder {
	public void cancel(ArrayList<Object> recived, ConnectionToClient client){
		WaitingList waitlist = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("orders");
		query.add("orderNumber");
		query.add(String.valueOf(data.getOrderNumber()));
		//sendtowaitlist
	}
	
}
