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
	private static Date timeLastChecked;
	
	private WaitListSingelton() {
		timeLastChecked = new Date();
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
		insertPending(waitlistMail);
		// send mail and sms notification
	}
	
	public static void CheckTheWaitList() {
		Date timeNow = new Date();
		Date LastPlusMinute = new Date(timeLastChecked.getTime()+60*1000);
		if (LastPlusMinute.compareTo(timeNow)>=0){
			ArrayList<ArrayList<String>> expired = selectExpiredPending(); //select all expired in pending
			if (!expired.isEmpty()) { //if not empty
				for (ArrayList<String> row : expired) { //for each expired order
					ArrayList<Object> stubForFetchOrder = new ArrayList<Object>();
					stubForFetchOrder.add("orderByID");
					stubForFetchOrder.add(row);
					Order exp = new Order((ExistingOrderCheck.fechOrder(stubForFetchOrder,"orders","orderNumber")).get(0));
					CancelOrder.deleteOrder(exp.getOrderNumber());
					ArrayList<Object> stubForpullFromWaitList = new ArrayList<Object>();
					stubForpullFromWaitList.add("waitlist");
					stubForpullFromWaitList.add(exp);
					WaitingList.pullFromWaitList(stubForpullFromWaitList);
				}
				ArrayList<String> query = new ArrayList<String>();
				query.add("deleteCond");
				query.add("pending");
				query.add("timeSent >= DATE_SUB(NOW(), INTERVAL 1 HOUR)");
				MySQLConnection.deleteCond(query);
			}
			
			//send were sorry mail
			
		}else //if last check was less than minute ago dont check
			return;
		

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
			removePending(msg.getOrder());
			//notify ok
		}
		else {
			//notify not ok
		}
	}
	
	//input: Sms messege replay
	//output: none
	public static void replayFromSms(SmsMessege msg) {
		Date sent = msg.getRepliedTo().getSentTime();
		Date recived = msg.getSentTime();
		if (lessThanOneHour(sent,recived)) {
			removePending(msg.getOrder());
			//notify ok
		}
		else {
			//notify not ok
		}
	}

	// input: order class of the order to cancel
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
	
	// input: Order to delete from the DB
	// output: true if found the order on the waitlist and removed it, false if not.
	// send to DB: order to remove
	private static boolean removePending(Order order) {
		int orderNum = order.getOrderNumber();
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("pending");
		query.add("orderNumber");
		query.add(String.valueOf(orderNum));
		if (!MySQLConnection.delete(query))
			return false; // error in delete
		return true;
	}
	
	//input: Order to insert into the DB
	//output: true if sussesful false if not
	//send to DB: new order to list in pending
	private static boolean insertPending(EmailMessege messege) {
	ArrayList<String> query = new ArrayList<String>();
	query.add("insert"); // command
	query.add("pending"); // table name
	query.add(messege.getOrder().getOrderNumber() + ","+ messege.getSentTime()); // values in query format
	return MySQLConnection.insert(query);
	}
	
	
	//input: Order to insert into the DB
	//output: true if sussesful false if not
	//send to DB: new order to list in pending
	private static ArrayList<ArrayList<String>> selectExpiredPending() {
	ArrayList<String> query = new ArrayList<String>();
	query.add("select"); // command
	query.add("pending"); // table name
	query.add("orderNumber"); // col name
	query.add("timeSent >= DATE_SUB(NOW(), INTERVAL 1 HOUR)"); // condition
	query.add("1"); // col num
	return MySQLConnection.select(query);
	}

}
