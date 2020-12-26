package server;

import java.util.ArrayList;

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

		if (!query1.isEmpty()) {
			System.out.println("already has this type of request for this employee");
			answer.add(false);
		}
		// insert if possible
		else {
			ArrayList<String> query2 = new ArrayList<String>();
			query2.add("insert"); // command
			query2.add("orders"); // table name
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
