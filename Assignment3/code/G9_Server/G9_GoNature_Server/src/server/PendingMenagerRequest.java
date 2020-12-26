package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import reportData.ManagerRequest;

public class PendingMenagerRequest {

	// input: ArrayList<Object>: cell[0] name
	// cell[1] ManagerRequest object
	// 
	// output:
	public static void OverallVisitorsReport(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ??
		answer.add(recived.get(0));
		ManagerRequest mr = (ManagerRequest) recived.get(1);
		// select pending requests and to check if not more than 1 in every type
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("pendingmanagerrequests"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE requesttype='" + mr.getRequesttype() + "' AND employeeID='" + mr.getEmployeeID() + "'");

		if (!query.isEmpty()) {
			System.out.println("already has this type of request");
			answer.add(false);
		}
		// insert if possible
		else {

		}

	}

	public static String toStringForDB(ManagerRequest mr) {
		return "'" + mr.getEmployeeID() + "','" 
				+ mr.getRequesttype() + "','" 
				+ mr.getMaxCapacity() + "','"
				+ mr.getOrdersCapacity() + "','" 
				+ mr.getDiscount() + "','" 
				+ mr.getFromDate() + "','"
				+ mr.getToDate() + "','" 
				+ mr.getParkName() + "'";
	}

}
