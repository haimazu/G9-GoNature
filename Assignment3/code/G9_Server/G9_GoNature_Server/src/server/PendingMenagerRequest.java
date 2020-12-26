package server;

import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;
import reportData.ManagerRequest;

public class PendingMenagerRequest {

	// input: ArrayList<Object>: cell[0] name
	// cell[1] ManagerRequest object
	//
	// output:T/F
	public static void InsertToPending(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		answer.add(recived.get(0));
		ManagerRequest mr = (ManagerRequest) recived.get(1);
		// select pending requests and to check if not more than 1 in every type
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("pendingmanagerrequests"); // table name
		query1.add("*"); // columns to select from
		query1.add("WHERE requesttype='" + mr.getRequesttype() + "' AND employeeID='" + mr.getEmployeeID() + "'");
		query1.add("8");
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query1);
		System.out.println(query1);
		System.out.println(queryData);
		if (!queryData.isEmpty()) {
			System.out.println("already has this type of request for this employee");
			answer.add(false);
		}
		// insert if possible
		else {
			ArrayList<String> query2 = new ArrayList<String>();
			query2.add("insert"); // command
			query2.add("pendingmanagerrequests"); // table name
			query2.add(toStringForDB(mr)); // values in query format
			if (!MySQLConnection.insert(query2)) {
				answer.add(false);
				System.out.println("failed to insert");
			} else {
				answer.add(true);

			}

		}
		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList<Object>: cell[0] name
	// cell[1] user_name
	// cell[2] password
	// select the emloyeeID by his user name and password
	// output:F if no such employee OR employeeID
	public static void employeeNumberSet(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		String username = (String) recived.get(1);
		String password = (String) recived.get(2);
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("employee"); // table name
		query.add("employeeNumber"); // columns to select from
		query.add("WHERE username='" + username + "' AND password='" + password + "'"); // condition
		query.add("1"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			System.out.println("no such employee");
			answer.add(false);
		} else {
			System.out.println(queryData.get(0));
			answer.add(queryData.get(0));
		}

		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList<Object>: cell[0] func_name
	// cell[1]: ArrayList<String> cell[0] park name
	// ,ConnectionToClient
	// pulling details of a selected park from DB
	// output:ArrayList<Object> cell[0] func_name
	// cell[1] Park Object
	public static void getParkDetails(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : getParkDetails
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: parkName
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE parkName = '" + data.get(0) + "'"); // condition
		query.add("6"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		Park park = new Park(queryData.get(0));
		answer.add(park);
		EchoServer.sendToMyClient(answer, client);
	}

	
	//for bar with love
	// input: ArrayList<Object>: cell[0] name
	//
	// output:ArrayList<ArrayList<String>> of all Items in pending manager request table
	public static void pendingManagerRequestAllItems(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		answer.add(recived.get(0));
		// select pending requests and to check if not more than 1 in every type
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("pendingmanagerrequests"); // table name
		query1.add("*"); // columns to select from
		query1.add("");
		query1.add("8");
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query1);
		answer.add(queryData);
		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList<Object>: cell[0] name
	// cell[1] ManagerRequest object
	//
	// output:
	public static void deleteFromPending(ArrayList<Object> recived, ConnectionToClient client) {

	}

	public static String toStringForDB(ManagerRequest mr) {
		return "'" + mr.getEmployeeID() + "','" + mr.getRequesttype() + "','" + mr.getMaxCapacity() + "','"
				+ mr.getOrdersCapacity() + "','" + mr.getDiscount() + "','" + mr.getFromDate() + "','" + mr.getToDate()
				+ "','" + mr.getParkName() + "'";
	}

}
