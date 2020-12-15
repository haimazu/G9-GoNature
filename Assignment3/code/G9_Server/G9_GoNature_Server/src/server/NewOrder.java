package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;
//executed by Nastya
public class NewOrder {

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

	public static boolean availableCapacity4Order(int visitorsNumber) {
		
		return true;
	}

	public static String toStringForReservation(Order data) {
		return "'" + data.getOrderNumber() + "','" + data.getVisitorsNumber() + "','" + data.getOrderEmail() + "','"
				+ data.getOrderType() + "','" + data.getPrice() + "','" + data.getParkname() + "','"
				+ data.getArrivedTime() + "','" + data.getMemberId() + "'";
	}
}
