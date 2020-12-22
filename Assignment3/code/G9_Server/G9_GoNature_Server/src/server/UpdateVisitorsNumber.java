package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;
import orderData.Order;

public class UpdateVisitorsNumber {

	// input: ArrayList<Object>,ConnectionToClient
	// updates the MaxVisitorsNumber in Park table
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T/F if update succeeded
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

		answer.add(MySQLConnection.insert(query));
		EchoServer.sendToMyClient(answer, client);

	}

	// input: ArrayList<Object>,ConnectionToClient
	// updates the MaxAmountOrders in Park table
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T/F if update succeeded
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

		answer.add(MySQLConnection.insert(query));
		EchoServer.sendToMyClient(answer, client);

	}

	// input: ArrayList<Object> cell [0]: parkName
	// cell [1]: currentVisitoreAmount ,ConnectionToClient
	// updates the ParkCurrentVisitors in Park table
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T/F if update succeeded
	public static void updateParkCurrentVisitors(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : updateCurrentVisitors
		answer.add(recived.get(0));
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("park"); // table name
		query.add("currentVisitoreAmount = '" + data.get(1) + "'"); // columns to update
		query.add("parkName"); // condition
		query.add(data.get(0)); // parkName value

		answer.add(MySQLConnection.update(query));
		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList<Object>,ConnectionToClient
	// pulling details of a selected park from DB
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] details in ArrayList<String> format of the chosen park details
	public static void getParkDetails(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : orderByOrderNumber
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: orderNumber
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE parkName = '" + data.get(0) + "'"); // condition
		query.add("6"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		//Park park = new Park(queryData.get(0));
		answer.add(queryData.get(0));

		EchoServer.sendToMyClient(answer, client);
	}

}
