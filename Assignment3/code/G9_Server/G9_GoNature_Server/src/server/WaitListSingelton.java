package server;

import java.util.ArrayList;

import orderData.Order;

public class WaitListSingelton {

	// singleton WaitListSingelton
	private static WaitListSingelton obj;
	private static ArrayList<String> sent;
	//private static TIME whenchaecked;

	private WaitListSingelton() {
		sent = new ArrayList<String>();
		// on start up do this
	}

	public static WaitListSingelton getCounter() {
		if (obj == null) {
			if (obj == null) {
				obj = new WaitListSingelton();// instance will be created at request time
			}
		}
		return obj;
	}

	public static void oneHourChance(Order order) {
		// check if exist
		// Make a SET!!!
		// add both orderNumber and time listed to the set
		sent.add("" + order.getOrderNumber());
	}
	
	public static void CheckTheWaitList() {
		//if last check was less than minute ago dont check
		//for each order listed in the SET check if one hour has passed since listed
		// if more than one hour delete the order from the DB send were sorry mail
		// remvoe from waitlist
		// remove from sent
	}

	public static void confirmWaitlistMailOrSMS(int orderNum) {
		ArrayList<Object> stubForOrder = new ArrayList<Object>();

		ArrayList<ArrayList<String>> orderCluster = ExistingOrderCheck.fechOrder(stubForOrder, "waitinglist",
				"waitlistID");
		if (orderCluster.isEmpty()) {// not in waiting list
		} else {
			// MAKE A SET!!
			// search by order number the order
			// if found check the time listed
			// if timeNow is less then onehour from timeListed than keep its order and send
			// confemation mail
			// else if more than one hour delete the order from the DB send were sorry mail
			// remvoe from waitlist
			// remove from sent
			// maybe call for next one in line
			Order orderConfirm = new Order(orderCluster.get(0));

			System.out.println("you snooze you lose!");
		}
	}

	// TODO: decide if the client can ask that directly
	// input: order number as int
	//
	// output: true if found the order on the waitlist and removed it, false if not.
	public static boolean CancelWaitlist(Order recived) {
		int orderNum = recived.getOrderNumber();
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("waitingList");
		query.add("waitlistID");
		query.add(String.valueOf(orderNum));
		if (!MySQLConnection.delete(query))
			return false; // error in delete
		return true;
	}

}
