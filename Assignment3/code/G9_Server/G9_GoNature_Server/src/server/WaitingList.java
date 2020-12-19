package server;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import ocsf.server.ConnectionToClient;
import orderData.Order;

public class WaitingList {
	private static ArrayList<String> confirmed = new ArrayList<String>();
	private static ArrayList<String> sent = new ArrayList<String>();

	// input: ArrayList of Objects:
	// in cell 0: String "enterTheWaitList"
	// in cell 1: Order class of a given order to put in the wait list
	//
	// output: NONE. send to client: ArrayList of Objects:
	// in cell 0: String ""
	// in cell 1: boolean ->
	// true if entry sucsseful
	// false if not
	public void enterTheWaitList(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received

		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("waitingList"); // table name
		query.add(data.toStringForDB()); // values in query format

		if (MySQLConnection.insert(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
	}

	// TODO: decide if the client can ask that directly
	// input: ArrayList of Objects:
	// in cell 0: String "checkForAvailableSpots"
	// in cell 1: Order class of a given order to check if it can fit
	//
	// output: True -> if there are available spots at the given park at the given
	// time
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
		Order order = (Order) recived.get(1);
		String parkName = order.getParkName();
		String arrivedTime = order.getArrivedTime();
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("waitingList");
		query.add("*");
		query.add("WHERE parkName = '" + parkName + "' AND arrivedTime ='" + arrivedTime + "' SORT BY orderNumber");
		query.add("1");
		ArrayList<ArrayList<String>> waitingInLineArr = MySQLConnection.select(query);
		if (waitingInLineArr.get(0).isEmpty())
			return true; // no one in line
		ArrayList<String> firstInLine = waitingInLineArr.get(0); // first in line
		Order firstInLineOrder = new Order(firstInLine);
		ArrayList<Object> arrayForSpots = new ArrayList<Object>();
		arrayForSpots.add(firstInLineOrder);
		if (!checkForAvailableSpots(arrayForSpots, client))
			return true; // no space for the first in line
		sendWaitlistMailAndSMS(firstInLineOrder);
		for (int i = 0; i < 360; i++) { // wait for confirmation
			if (confirmed.contains(firstInLine.get(0))) {
				confirmed.remove(firstInLine.get(0));
				//TODO: make order
				return true;
			}
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// if the for ended the time of one hour has passed
		sent.remove(firstInLine.get(0));
		CancelWaitlist(firstInLineOrder);
		pullFromWaitList(recived, client); // try next one in line recursively
		return true;
	}

	// input: ArrayList of Objects:
	// 			in cell 0: String "pullFromWaitList"
	// 			in cell 1: Order class of a given order that has been confirmed the mail or sms
	// output:
	public void confirmWaitlistMailOrSMS(ArrayList<Object> recived, ConnectionToClient client) { 
		Order orderConfirm = (Order)recived.get(1);
		int orderNum = orderConfirm.getOrderNumber();
		if (sent.contains(String.valueOf(orderNum))) { //the confirmation was made within the hour
			confirmed.add(String.valueOf(orderNum));
			sent.remove(String.valueOf(orderNum));
		} else { //the confermation was made too late
			System.out.println("you snooze you lose!");
			//TODO: desice how and what to send back to client
		}
	}

	// TODO: decide if the client can ask that directly
	// input: order number as int
	//
	// output: true if found the order on the waitlist and removed it, false if not.
	private boolean CancelWaitlist(Order recived) {
		int orderNum = recived.getOrderNumber();
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("waitingList");
		query.add("orderNumber");
		query.add(String.valueOf(orderNum));
		if (!MySQLConnection.delete(query))
			return false; // error in delete
		return true;
	}
	
	// input: Order class that contains the class that sould get a mail
	//
	// output: true if messeges sent succsesfully
	private boolean sendWaitlistMailAndSMS(Order firstInLine) {
		sent.add(String.valueOf(firstInLine.getOrderNumber())); // add the waitlistID to the sent arraylist
		//TODO: send mail
		//TODO: send sms
		return true;
	}

}
