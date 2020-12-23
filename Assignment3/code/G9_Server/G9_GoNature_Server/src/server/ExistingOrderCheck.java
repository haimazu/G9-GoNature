package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class ExistingOrderCheck {

//	public static void getOrderDetailsByOrderNumber(ArrayList<Object> recived, ConnectionToClient client) {
//		// query
//		ArrayList<Object> answer = new ArrayList<Object>();
//		// the service name : ordersByIdOrMemberId OR ordersByOrderNumber
//		answer.add(recived.get(0));
//		// the data that sent from the client
//		// cell 0: ID / memberId OR orderNumber
//		ArrayList<String> data = (ArrayList<String>) recived.get(1);
//
//		ArrayList<String> query = new ArrayList<String>();
//		query.add("select"); // command
//		query.add("orders"); // table name
//		query.add("*"); // columns to select from
//		
//		// ordersByIdOrMemberId case
//		if (recived.get(0).equals("ordersByIdOrMemberId")) {
//			query.add("WHERE ID = '" + data.get(0) + "' OR memberId = '" + data.get(0) + "'"); // condition
//		} else {
//			// ordersByOrderNumber case
//			query.add("WHERE orderNumber = '" + data.get(0) + "'"); // condition
//		}
//		
//		query.add("12"); // how many columns returned
//
//		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
//		if (queryData.isEmpty()) {
//			answer.add("No such order");
//		} else {
//			Order kuku = new Order(queryData.get(0));
//			answer.add(kuku);
//		}
//		EchoServer.sendToMyClient(answer, client);
//	}
	
	
	//input: array list of object contains:
	//			[0] -> String ordersByIdOrMemberId OR ordersByOrderNumber
	//			[1] -> ArrayList of String contains:
	//							[0] -> orderNumber
	//output: NONE
	//send to client: Order Class if an order found, string "No such order" if not
	public static void getOrderDetailsByOrderNumber(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ArrayList<ArrayList<String>> queryData = fechOrder(recived,"orders","orderNumber");
		if (queryData.isEmpty()) {
			answer.add("No such order");
		} else {
			Order kuku = new Order(queryData.get(0));
			answer.add(kuku);
		}
		EchoServer.sendToMyClient(answer, client);
	}
	
	//input: array list of object contains:
	//			[0] -> String ordersByIdOrMemberId OR ordersByOrderNumber
	//			[1] -> ArrayList of String contains:
	//							[0] -> orderNumber
	//		table name as string
	//		col name as string
	//output: ArrayList<ArrayList<String>> containing the order deatils, empty if no details
	public static ArrayList<ArrayList<String>> fechOrder(ArrayList<Object> recived, String tableName, String colName) {
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add(tableName); // table name
		query.add("*"); // columns to select from
		if (recived.get(0).equals("ordersByIdOrMemberId")) { // ordersByIdOrMemberId case
			query.add("WHERE ID = '" + data.get(0) + "' OR memberId = '" + data.get(0) + "'"); // condition
		} else { // ordersByOrderNumber case
			query.add("WHERE " + colName + "='" + data.get(0) + "'"); // condition
		}
		query.add("12"); // how many columns returned
		return MySQLConnection.select(query);
	}
}
