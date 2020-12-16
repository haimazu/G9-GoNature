package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import orderData.OrderType;

//executed by Nastya
public class NewOrder {

	/*
	 * * Received is ArrayList of objects -> [0] -> the function who calling to
	 * service from the server "order" [1] -> ArrayList of String :
	 * [0] -> visitors amount 
	 * [1] -> email 
	 * [2] -> park name 
	 * [3] -> arrival date 
	 * [4] -> arrival time
	 * [5] -> member id (optional)
	 * 
	 * answer is array List of object -> [0] -> the function who calling to service
	 * from the server "order" [1] -> ArrayList of String ->[0]true / false in
	 * string if success new order [1] object order [2] price after discount [3]  discount 
	 * 
	 */
	public static void NewReservation(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received

		// check if the capacity of orders is full
		if (!availableCapacity4Order(data.getVisitorsNumber())) {
			answer.add(false);
			EchoServer.sendToMyClient(answer, client);
		}

		else {
			
			
//			switch (data.) {
//			case value:
//				
//				break;
//
//			default:
//				break;
//			}
//			
			
			
			ArrayList<String> query = new ArrayList<String>();
			query.add("insert"); // command
			query.add("orders"); // table name
			query.add(toStringForReservation(data)); // values in query format

			if (MySQLConnection.insert(query))
				answer.add(true);
			else
				answer.add(false);

			EchoServer.sendToMyClient(answer, client);
		}

	}
	
	public static double totalPrice(int visitorsAmount,OrderType ot) {
		
		
		
		return 10;
	}

	// check if needed to be sent null or empty in order number
	public static String toStringForReservation(Order data) {
		return "'" + "" + "','"
				+ data.getVisitorsNumber() + "','" 
				+ data.getOrderEmail() + "','" 
				+ data.getOrderType() + "','"
				+ data.getPrice() + "','" 
				+ data.getParkname() + "','" 
				+ data.getArrivedTime() + "','"
				+ data.getMemberId() + "','" 
				+ data.getID() + "'";
	}

//*******************************************************************
	public static boolean availableCapacity4Order(int visitorsNumber) {

		return true;
	}
	// *******************************************************************

	// func that returns all parks names
	public static void ParksNames(ArrayList<Object> recived, ConnectionToClient client) {

		// the returned values stored here
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ParksNames
		answer.add(recived.get(0));
		// cell 0: recivedFromServerParksNames

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("parkName"); // columns to select from
		query.add(""); // condition - non -> all parks names required
		query.add("1"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.get(0).isEmpty()) {
			// no parks in DB
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			///******************
			//ArrayList<String> parkNames = new ArrayList<String>();
			answer.add(queryData.get(0));
		}
		EchoServer.sendToMyClient(answer, client);

	}

}
