package server;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import userData.Member;

public class WaitingList {
	
	// input: ArrayList of Objects:
	// in cell 0: String "enterTheWaitList"
	// in cell 1: Order class of a given order to put in the wait list
	//
	// output: NONE. send to client: ArrayList of Objects:
	// in cell 0: String ""
	// in cell 1: boolean ->
	// true if entry sucsseful
	// false if not
	public static void enterTheWaitList(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received
		ArrayList<String> query = new ArrayList<String>();
		Member mem = NewOrder.MemerCheck(data);
		if (mem!=null)
			data.setMemberId(mem.getMemberID());
		data = NewOrder.totalPrice(data, mem, false);
		data.setOrderNumber(Counter.getCounter().waitlistNum());
		query.add("select"); // command
		query.add("waitingList"); // table name
		query.add("*");
		query.add("WHERE ID='" + data.getID() + "' AND arrivedTime='" + data.getArrivedTime() + "' And parkName='" + data.getParkName() + "'");
		query.add("12");
		ArrayList<ArrayList<String>> exist = MySQLConnection.select(query);
		if (exist.isEmpty()) { //if there is no another matching in the wait list
			query.clear();
			query.add("insert"); // command
			query.add("waitingList"); // table name
			query.add(data.toStringForDB()); // values in query format
			answer.add(MySQLConnection.insert(query)); //true if ok false if not
		}
		else
			answer.add("alreadyExist"); //alreadyExist if already in the list
		EchoServer.sendToMyClient(answer, client);
	}

	// input: ArrayList of Objects:
	// in cell 0: String "checkForAvailableSpots"
	// in cell 1: Order class of a given order to check if it can fit
	//
	// output: True -> if there are available spots at the given park at the given time
	// False -> if there are no spots available
	public boolean checkForAvailableSpots(ArrayList<Object> recived, ConnectionToClient client) {
		Order order = (Order) recived.get(1);
		String parkName = order.getParkName();
		String arrivedTime = order.getArrivedTime();
		int visitorsNumber = order.getVisitorsNumber();
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("orders");
		query.add("visitorsNumber");
		query.add("WHERE parkName = '" + parkName + "' AND arrivedTime ='" + arrivedTime + "'");
		query.add("1");

		int totalAmount = 0;
		ArrayList<ArrayList<String>> amountArr = MySQLConnection.select(query);
		for (ArrayList<String> row : amountArr) {
			totalAmount += Integer.parseInt(row.get(0));
		}

		query = new ArrayList<String>();
		query.add("select");
		query.add("park");
		if(order.isOccasional())
			query.add("maxVisitorAmount");
		else
			query.add("maxOrderVisitorsAmount");
		query.add("WHERE parkName = '" + parkName + "'");
		query.add("1");
		ArrayList<ArrayList<String>> maxAmount = MySQLConnection.select(query);

		if (totalAmount + visitorsNumber > Integer.parseInt(maxAmount.get(0).get(0)))
			return false;
		return true;
	}

	// NOTE: this function should be called after an order has been canceled by a
	// user
	// input: ArrayList of Objects:
	// 			in cell 0: String "pullFromWaitList"
	// 			in cell 1: Order class of a given order that has been canceled
	//
	// output:
	public boolean pullFromWaitList(ArrayList<Object> recived, ConnectionToClient client) {
		Order order = (Order) recived.get(1); //been deleted
		String parkName = order.getParkName();
		String arrivedTime = order.getArrivedTime();
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("waitingList");
		query.add("*");
		query.add("WHERE parkName='" + parkName + "' AND arrivedTime='" + arrivedTime + "' ORDER BY waitlistID");
		query.add("12");
		ArrayList<ArrayList<String>> waitingInLineArr = MySQLConnection.select(query);
		if (waitingInLineArr.get(0).isEmpty())
			return true; // no one in line
		ArrayList<String> firstInLine = waitingInLineArr.get(0); // first in line
		Order firstInLineOrder = new Order(firstInLine);
		ArrayList<Object> arrayForSpots = new ArrayList<Object>();
		arrayForSpots.add("");//fix

		arrayForSpots.add(firstInLineOrder);
		if (!checkForAvailableSpots(arrayForSpots, client))
			return false; // no space for the first in line
		//if we have avilable spots
		firstInLineOrder.setOrderNumber(Counter.getCounter().orderNum());
		NewOrder.insertNewOrder(firstInLineOrder); //add to regular orders
		WaitListSingelton.CancelWaitlist(firstInLineOrder);//delete from the wait list
		
		//send notification
		
		pullFromWaitList(recived, client); // try next one in line recursively
		return true;
	}
}
