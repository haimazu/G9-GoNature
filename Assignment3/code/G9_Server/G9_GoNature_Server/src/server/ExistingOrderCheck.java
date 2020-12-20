package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class ExistingOrderCheck {

	public static void getOrderDetailsByIdOrMemberId(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();

		// the service name : ordersByIdOrMemberId
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: ID / memberId
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("orders"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE ID = '" + data.get(0) + "' OR memberId = '" + data.get(0) + "'"); // condition
		query.add("12"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			answer.add(new ArrayList<String>(Arrays.asList("No such order")));
		} else {
			answer.add(queryData.get(0));
		}

		EchoServer.sendToMyClient(answer, client);
	}

	public static void getOrderDetailsByOrderNumber(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : orderByOrderNumber
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: orderNumber
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("orders"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE orderNumber = '" + data.get(0) + "'"); // condition
		query.add("12"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			answer.add(new ArrayList<String>(Arrays.asList("No such order")));
		} else {
			answer.add(queryData.get(0));
		}

		EchoServer.sendToMyClient(answer, client);
	}

}
