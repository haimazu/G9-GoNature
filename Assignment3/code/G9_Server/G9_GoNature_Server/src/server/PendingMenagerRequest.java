package server;

import java.io.Serializable;
import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;
import reportData.ManagerRequest;
import reportData.TableViewSet;

public class PendingMenagerRequest implements Serializable {

	// input: ArrayList<Object>: cell[0] name
	// cell[1] ManagerRequest object
	//
	// output:T/F
	public static void InsertToPending(ArrayList<Object> recived, ConnectionToClient client) {

		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		answer.add(recived.get(0));

		ManagerRequest mr = (ManagerRequest) recived.get(1);
		System.out.println(toStringForDB(mr));
		if (!mr.getRequestType().equals("discount")) {
			mr.setFromDate("01-01-00 00:00:00");
			mr.setToDate("01-01-00 00:00:00");
		}
		System.out.println(toStringForDB(mr));
		// select pending requests and to check if not more than 1 in every type
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("pendingmanagerrequests"); // table name
		query1.add("*"); // columns to select from
		query1.add("WHERE requesttype='" + mr.getRequestType() + "' AND employeeID='" + mr.getEmployeeID() + "'");
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
		answer.add(recived.get(0));
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
			answer.add(queryData.get(0).get(0));
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

	// for bar with love
	// input: ArrayList<Object>: cell[0] name
	//
	// output:ArrayList<ArrayList<String>> of all Items in pending manager request
	// table
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
	// cell[1] tabelViewSet object
	// cell[2] yes/no
	//
	// output: ArrayList<Object>: cell[0] T/F
	public static void deleteFromPending(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ManagerRequest mr = (ManagerRequest) recived.get(1);
		String yesno = (String) recived.get(2);

		// delete from pendingmanagerrequests
		ArrayList<String> query = new ArrayList<String>();
		query.add("deleteCond");
		query.add("pendingmanagerrequests");
		query.add("employeeID ='" + mr.getEmployeeID() + "' AND requesttype='" + mr.getRequestType() + "'");

		if (yesno.equals("no")) {
			MySQLConnection.deleteCond(query);
			answer.add(true);
			EchoServer.sendToMyClient(answer, client);
			return;
		}

		else {
			ArrayList<String> query1 = new ArrayList<String>();
			query1.add("select"); // command
			query1.add("pendingmanagerrequests"); // table name
			query1.add("*"); // columns to select from
			query1.add("WHERE employeeID ='" + mr.getEmployeeID() + "' AND requesttype='" + mr.getRequestType() + "'");
			query1.add("8");
			ArrayList<ArrayList<String>> queryData1 = MySQLConnection.select(query1);
			mr = new ManagerRequest(queryData1.get(0));
			answer.add(MySQLConnection.deleteCond(query));

			if (mr.getRequestType().equals("discount")) {
				String dateCond = "NOT ( GREATEST('" + mr.getFromDate() + "','" + mr.getToDate() + "') < discounts.from"
						+ "      OR LEAST('" + mr.getFromDate() + "','" + mr.getToDate() + "') > discounts.to" + ")";
				System.out.println(dateCond);
				// search if the dates already exist
				ArrayList<String> query2 = new ArrayList<String>();
				query2.add("select"); // command
				query2.add("discounts"); // table name
				query2.add("*"); // columns returned
				query2.add("WHERE parkName ='" + mr.getParkName() + "' AND '" + dateCond);
				query2.add("5");
				ArrayList<ArrayList<String>> queryData2 = MySQLConnection.select(query2);
				if (!queryData2.isEmpty()) {
					System.out.println("Already has discount on these dates!");
					answer.add(false);
					EchoServer.sendToMyClient(answer, client);
					return;
				} else {

					ArrayList<String> query3 = new ArrayList<String>();
					query3.add("insert"); // command
					query3.add("discounts");
					query3.add(toStringForDBDiscounts(mr));
					answer.add(MySQLConnection.insert(query3));
					EchoServer.sendToMyClient(answer, client);
					return;
				}

			}

			if (mr.getRequestType().equals("max_c")) {
				answer.add(MySQLConnection.deleteCond(query));

				ArrayList<String> query2 = new ArrayList<String>();
				query2.add("update"); // command
				query2.add("park"); // table name
				query2.add("maxVisitorAmount='" + mr.getMaxCapacity() + "'");
				query2.add("parkName");
				query2.add(mr.getParkName());
				answer.add(MySQLConnection.update(query2));
				System.out.println("max capacity updated");
				EchoServer.sendToMyClient(answer, client);
			}

			if (mr.getRequestType().equals("max_o")) {
				answer.add(MySQLConnection.deleteCond(query));
				ArrayList<String> query2 = new ArrayList<String>();
				query2.add("update"); // command
				query2.add("park"); // table name
				query2.add("maxOrderVisitorsAmount='" + mr.getOrdersCapacity() + "'");
				query2.add("parkName");
				query2.add(mr.getParkName());
				answer.add(MySQLConnection.update(query2));
				System.out.println("max orders capacity updated");
				EchoServer.sendToMyClient(answer, client);
			}

		}

	}

	public static String toStringForDB(ManagerRequest mr) {
		return "'" + mr.getEmployeeID() + "','" + mr.getRequestType() + "','" + mr.getMaxCapacity() + "','"
				+ mr.getOrdersCapacity() + "','" + mr.getDiscount() + "','" + mr.getFromDate() + "','" + mr.getToDate()
				+ "','" + mr.getParkName() + "'";
	}

	public static String toStringForDBDiscounts(ManagerRequest mr) {

		return "'" + mr.getParkName() + "','" + mr.getFromDate() + "','" + mr.getToDate() + "','"
				+ Counter.getCounter().discountsIDcountNum() + "','" + mr.getParkName() + "'";
	}

}
