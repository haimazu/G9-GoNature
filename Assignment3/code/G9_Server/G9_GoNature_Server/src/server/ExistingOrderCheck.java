package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

public class ExistingOrderCheck {
	
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
	@SuppressWarnings("unchecked")
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
	
	//input: order number as string
	//output: boolean true if exist in pendingwaitlist false if not
	private static boolean checkIfPending(String orderNum) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("pendingwaitlist"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE orderNumber='" + orderNum + "'");
		query.add("2");
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		return (!queryData.isEmpty());
	}

	//input: array list of object contains:
	//			[0] -> String checkOrderForGo
	//			[1] -> ArrayList of String contains:
	//							[0] -> orderNumber
	//output: NONE
	//send to client: array list of object contains:
	//					[0] -> Order Class if an order found, string "No such order" if not
	//					[1] -> (if an order is found only) True if the order is in pendingwaitlist table
	public static void checkOrderForGo(ArrayList<Object> recived, ConnectionToClient client) {
		// TODO Auto-generated method stub
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ArrayList<ArrayList<String>> queryData = fechOrder(recived,"orders","orderNumber");
		if (queryData.isEmpty()) {
			answer.add("No such order");
		} else {
			Order kuku = new Order(queryData.get(0));
			answer.add(kuku);
			answer.add(checkIfPending((String)recived.get(1)));
		}
		EchoServer.sendToMyClient(answer, client);
	}
}
