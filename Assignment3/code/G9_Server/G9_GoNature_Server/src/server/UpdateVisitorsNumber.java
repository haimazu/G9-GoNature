package server;

import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;

public class UpdateVisitorsNumber {

	// updates the MaxVisitorsNumber in Park table
	public static void MaxVisitorsNumberUpdate(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Park data = (Park) recived.get(1); // Park object received

		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("park"); // table name
		String value = "maxVisitorAmount='" + data.getMaximumCapacityInPark() + "'";
		query.add(value);
		query.add("parkName");
		query.add(data.getName());

		if (MySQLConnection.insert(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);

	}

	// updates the MaxAmountOrders in Park table
	public static void MaxAmountOrdersUpdate(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Park data = (Park) recived.get(1); // Park object received

		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("park"); // table name
		String value = "maxAmountOrders='" + data.getMaxAmountOrders() + "'";
		query.add(value);
		query.add("parkName");
		query.add(data.getName());

		if (MySQLConnection.insert(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);

	}

}
