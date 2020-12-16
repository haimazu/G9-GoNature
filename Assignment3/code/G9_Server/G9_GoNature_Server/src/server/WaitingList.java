package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.DateAndTime;
import orderData.Order;

public class WaitingList {
	
	//input: ArrayList of Objects:
	//				in cell 0: String "enterTheWaitList"
	//				in cell 1: Order class of a given order to put in the wait list
	//
	//output: NONE. send to client: ArrayList of Objects:
	//									in cell 0: String ""
	//									in cell 1: boolean ->
	//													true if entry sucsseful
	//													false if not
	public void enterTheWaitList(ArrayList<Object> recived, ConnectionToClient client){
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received
		
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("waitingList"); // table name
		query.add(toStringForReservation(data)); // values in query format


		if (MySQLConnection.insert(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
	}
	
	//input: park name as String, arrived Time as DateAndTime, visitorsNumber as int.
	//		all the following could be provided from the relevant Order Class
	//
	//output: True -> if there are available spots at the given park at the given time
	//			false -> if there are no spots available
	public boolean checkForAvailableSpots(String parkName, DateAndTime arrivedTime, int visitorsNumber) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("orders");
		query.add("visitorsNumber");
		query.add("WHERE parkName = '" + parkName + "' AND arrivedTime ='" + arrivedTime + "'");
		query.add("1");
		
		int totalAmount = 0;
		ArrayList<ArrayList<String>> amountArr = MySQLConnection.select(query);
		for (ArrayList<String> row : amountArr) {
			totalAmount += Integer.parseInt(row.get(0));
		}
		
		query = new ArrayList<String>();
		query.add("select");
		query.add("park");
		query.add("maxAmountOrders");
		query.add("WHERE parkName = '" + parkName + "'");
		query.add("1");
		ArrayList<ArrayList<String>> maxAmount = MySQLConnection.select(query);
		
		if (totalAmount + visitorsNumber > Integer.parseInt(maxAmount.get(0).get(0)))
			return false;
		return true;
	}
	
	//input: 
	//
	//output:
	public void pullFromWaitList() { //send mail and sms and wait for hour
		
	}
	
	//input: 
	//
	//output:
	public void confirmAndOrder() { //get mail or sms confirmation
		
	}
	
	//input: 
	//
	//output:
	public void nextInLine() { //go to next in line
		
	}
	
	public static String toStringForReservation(Order data) {
		return "'" + data.getOrderNumber() + "','" + data.getVisitorsNumber() + "','" + data.getOrderEmail() + "','"
				+ data.getOrderType() + "','" + data.getPrice() + "','" + data.getParkname() + "','"
				+ data.getArrivedTime() + "','" + data.getMemberId() + "'";
	}
}
