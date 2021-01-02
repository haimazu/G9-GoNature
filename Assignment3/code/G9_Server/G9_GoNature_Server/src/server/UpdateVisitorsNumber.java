package server;

import java.util.ArrayList;
import dataLayer.Park;
import ocsf.server.ConnectionToClient;

public class UpdateVisitorsNumber {

	// input: ArrayList<Object> cell [0]: parkName
	// cell [1]: currentVisitoreAmount ,ConnectionToClient
	// updates the ParkCurrentVisitors in Park table
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T if update succeeded, F if not, Full if we can add more visitors
	@SuppressWarnings("unchecked")
	public static void updateParkCurrentVisitors(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : updateCurrentVisitors
		answer.add(recived.get(0));
		// currentVisitoreAmount to update
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

	// input: cell [0]: case name updateAccessControl
	//        cell [1]: cell 0: orderNumber
	//        			cell 1: parkName
	//        			cell 2: entryTime / exitTime
	//        			cell 3: orderType

	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T if update succeeded, F if not, Full if we can add more visitors
	@SuppressWarnings("unchecked")
	public static void updateAccessControl(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : updateAccessControl
		answer.add(recived.get(0)); 
		// information depending on entry or exit status
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		
		ArrayList<String> query = new ArrayList<String>();
		
		// enter mode: we need to insert all 
		if (!checkIfOrderNumberExists(data.get(0))) {
			query.add("insert"); // command
			query.add("accesscontrol"); // table name
			query.add(data.get(0) + ", '" + data.get(1) + "', '" + data.get(2) + "', " + null + ", '" + data.get(3) + "'");

			answer.add(MySQLConnection.insert(query));
			EchoServer.sendToMyClient(answer, client);
			return;
		}
		
		query.add("update"); // command
		query.add("accesscontrol"); // table name
		query.add("exitTime = '" + data.get(2) + "'"); // columns to update	
		query.add("orderNumber"); // condition
		query.add(data.get(0)); // orderNumber value

		answer.add(MySQLConnection.update(query));
		EchoServer.sendToMyClient(answer, client);
	}
	
	// check if the order number exists in the table access control
	// input: orderNumber
	// output: T / F ==> if the order number exists
	public static boolean checkIfOrderNumberExists(String orderNumber) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("accesscontrol"); // table name
		query.add("orderNumber"); // columns to select from
		query.add("WHERE orderNumber = '" + orderNumber + "'"); // condition
		query.add("1"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			return false;
		} 
		
		return true;
	}

	// input: ArrayList<Object>,ConnectionToClient
	// pulling details of a selected park from DB
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] ArrayList<String> [0] parkName
	// [1] number of visitors to add
	@SuppressWarnings("unchecked")
	public static void getParkDetails(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : getParkDetails
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: parkName
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		int visitorsToAddorRemove = Integer.parseInt(data.get(1));

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE parkName = '" + data.get(0) + "'"); // condition
		query.add("6"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		Park park = new Park(queryData.get(0));

		if (park.getCurrentAmount() + visitorsToAddorRemove > park.getMaximumCapacityInPark()) {
			answer.add("Greater");
		} else if (park.getCurrentAmount() - visitorsToAddorRemove < 0) {
			answer.add("Lower");
		} else if (park.getCurrentAmount() == park.getMaximumCapacityInPark()) {
			answer.add("Full");
		} else {
			answer.add(park);
		}

		EchoServer.sendToMyClient(answer, client);
	}

}
