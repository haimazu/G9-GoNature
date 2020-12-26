package server;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

//input: ArrayList of Objects:
// in cell 0: String "cancelOrder"
// in cell 1: Order class of a given order to cancel
//
// output: NONE.
// send to client: ArrayList of Objects:
// in cell 0: String "cancelOrder"
// in cell 1: boolean ->
// true if entry sucsseful
// false if not
// NOTE: this function calls to the waitlist to check if someone is waiting for the new spots avilable after the cancelation.
public class CancelOrder {
	public static void cancel(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList waitlist = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		answer.add(deleteOrder(data.getOrderNumber()));
		try {
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitlist.pullFromWaitList(recived);
		addToDBCanceledOrder(data);
	}

	/// not working!!
	public static boolean deleteOrder(int orderNum) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("orders");
		query.add("orderNumber");
		query.add(String.valueOf(orderNum));
		return MySQLConnection.delete(query);
	}

	// Nastya
	// input:Order obj
	// inserting cancelled orders into 'canceledorders' table in DB
	// output: nada
	public static void addToDBCanceledOrder(Order ord) {
		ord.setOrderNumber(Counter.getCounter().cancelledCountNum());
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("canceledorders"); // table name
		query.add(ord.toStringForDB()); // values in query format
		if (MySQLConnection.insert(query))
			System.out.println("inserted");
		else
			System.out.println("not");

	}

//	public class CancelOrder {
//		public static void cancel(ArrayList<Object> recived, ConnectionToClient client) {
//			WaitingList waitlist = new WaitingList();
//			ArrayList<Object> answer = new ArrayList<Object>();
//			answer.add(recived.get(0));
//			Order data = (Order) recived.get(1); // order object received
//			ArrayList<String> query = new ArrayList<String>();
//			query.add("delete");
//			query.add("orders");
//			query.add("orderNumber");
//			query.add(String.valueOf(data.getOrderNumber()));
//			answer.add(MySQLConnection.delete(query));
//			try {
//				client.sendToClient(answer);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			waitlist.pullFromWaitList(recived, client);
//		}

}
