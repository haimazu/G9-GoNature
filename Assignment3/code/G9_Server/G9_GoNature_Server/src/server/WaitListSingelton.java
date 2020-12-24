package server;

import java.util.ArrayList;
import java.util.Date;

import dataLayer.EmailMessege;
import dataLayer.Messege;
import dataLayer.SmsMessege;
import orderData.Order;

public class WaitListSingelton {

	// singleton WaitListSingelton
	private static WaitListSingelton obj;

	private WaitListSingelton() {
		// on start up do this
	}

	public static WaitListSingelton getWaitlist() {
		if (obj == null) {
			if (obj == null) {
				obj = new WaitListSingelton();// instance will be created at request time
			}
		}
		return obj;
	}

	//input: order that is pulled from the waitlist
	//output: none
	//send to client: SMS and Mail notification
	public static void sendWaitlistNotification(Order order) {
		String msg = "WaitlistNotification";
		SmsMessege waitlistSms = new SmsMessege(order.getOrderPhone(), msg, order);
		EmailMessege waitlistMail = new EmailMessege(order.getOrderEmail(), msg, order);
		// send mail and sms notification                 
	}
	
	public static void CheckTheWaitList() {
		//if last check was less than minute ago dont check
		//for each order listed in the SET check if one hour has passed since listed
		// if more than one hour delete the order from the DB send were sorry mail
		// remvoe from waitlist
		// remove from sent
	}
	
	//input: date sent and date recived
	//output: true if less the one hour has passed flase if not
	private static boolean lessThanOneHour(Date sent, Date recived) {
		Date limit = new Date(sent.getTime() + 60*60*1000);
		if (limit.compareTo(recived) >= 0)
			return true;
		return false;
	}

	//input: Email messege replay
	//output: none
	public static void replayFromMail(EmailMessege msg) {
		Date sent = msg.getRepliedTo().getSentTime();
		Date recived = msg.getSentTime();
		if (lessThanOneHour(sent,recived)) {
			//ok
		}
		else {
			//not ok
		}
	}
	
	//input: Sms messege replay
	//output: none
	public static void replayFromSms(SmsMessege msg) {
		Date sent = msg.getRepliedTo().getSentTime();
		Date recived = msg.getSentTime();
		if (lessThanOneHour(sent,recived)) {
			//ok
		}
		else {
			//not ok
		}
	}
	
	private static void confirmWaitlistNotification(Order order) {
		//at that point we know than less than one hour has passed
		//
		//
			System.out.println("you snooze you lose!");
		
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
