package server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dataLayer.EmailMessege;
import dataLayer.Messege;
import dataLayer.SmsMessege;
import orderData.Order;

public class WaitListSingelton {

	// singleton WaitListSingelton
	private static WaitListSingelton obj;
	private static Date timeLastChecked;
	private static Date LastNightlyCheck;
	private static Date LastMorningCheck;
	private static boolean nightCheck;
	private static boolean morningCheck;
	
	//SingletonBuilder
	private WaitListSingelton() {
		timeLastChecked = new Date();
		LastNightlyCheck = new Date();
		LastMorningCheck = new Date();
		nightCheck = true;
		morningCheck = true;
		// on start up do this
	}

	//Singleton, create one instance only
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
		String subject = "Waitlist Notification";
		String body = "GoNature WaitList Notification";
		EmailMessege waitlistMail = new EmailMessege(order.getOrderEmail(), subject, body, order);
		insertPending(waitlistMail);
		Comunication.sendNotification(subject, body, order);
		// send mail and sms notification
	}
	
	//input: order that is pulled from the waitlist
	//output: none
	//send to client: SMS and Mail notification
	public static void send24HoursNotification(Order order) {
		String subject = "24 Hours Notification";
		String body = "GoNature 24 Hours Notification";
		EmailMessege waitlistMail = new EmailMessege(order.getOrderEmail(), subject, body, order);
		insertPending(waitlistMail);
		Comunication.sendNotification(subject, body, order);
		// send mail and sms notification
	}
	
	
	//input: none
	//output: none
	//DB: 	delete (if found) expierd orders listed in pending (more than 1 hour)
	//		delete (if found) expierd orders listed in waitlist (more than 24 hours)
	//NOTE: the timeLastChecked mechanizem make sure that this function run not more than once per minute (to not overload the server and DB each time)
	//		the LastDailyCheck mechanizem make sure that the function of deleting from the waitlist will not run more than once per day
	public static void CheckTheWaitList() {
		Date timeNow = new Date();
		Date LastPlusMinute = new Date(timeLastChecked.getTime()+60*1000);
		if ((LastPlusMinute.compareTo(timeNow)<=0) || nightCheck || morningCheck){
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
				query.add("pendingwaitlist");
				query.add("timeLimit <= NOW()");
				MySQLConnection.deleteCond(query);
			}
			
			//send were sorry mail
			timeLastChecked= new Date();
		} //if last check was less than minute ago dont check
		Date LastPlusDay = new Date(LastNightlyCheck.getTime()+24*60*60*1000);
		if (LastPlusDay.compareTo(timeNow)<=0 || ((Calendar.HOUR_OF_DAY>22) && !isSameDay(LastNightlyCheck,timeNow))) {
			nightCheck=true;
		}
		if (nightCheck) { //TODO: think if we want to send a messege to the deleted or not.
			ArrayList<String> query = new ArrayList<String>();
			query.add("deleteOver24");
			query.add("waitinglist");
			query.add("arrivedTime <= DATE_SUB(NOW(), INTERVAL 1 DAY)");
			MySQLConnection.deleteCond(query);
			//delete wait list of today
			nightCheck=false;
			LastNightlyCheck= new Date();
		}
		LastPlusDay = new Date(LastMorningCheck.getTime()+24*60*60*1000);
		if (LastPlusDay.compareTo(timeNow)<=0 || ((Calendar.HOUR_OF_DAY>8) && (Calendar.HOUR_OF_DAY<10) && !isSameDay(LastMorningCheck,timeNow))) {
			morningCheck=true;
		}
		if (morningCheck) { //TODO: think if we want to send a messege to the deleted or not.
			notificationsForTommorowTravelers();
			morningCheck=false;
			LastMorningCheck= new Date();
		}
	}
	
	//
	//
	//
	private static void notificationsForTommorowTravelers() {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("orders"); // table name
		query.add("*"); // col name
		query.add("WHERE arrivedTime > CURDATE() + 1 AND arrivedTime < CURDATE() + 2"); // condition
		query.add("12"); // col num
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return;
		else {
			for (ArrayList<String> row : queryData) {
				send24HoursNotification(new Order(row));
			}
		}
	}
	
	//input: two dates
	//output: true if the two dates are on the same day, false if not
	private static boolean isSameDay(Date date1, Date date2) {
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(date1);
	    Calendar calendar2 = Calendar.getInstance();
	    calendar2.setTime(date2);
	    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
	      && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
	      && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}
	
	//input: order calss of order to check
	//output: true if limit has passed false if not
	private static boolean limitReach(Order order) {
		Date now = new Date();
		Date limit = selectDateForPending(order);
		if(limit.equals(null) || limit.compareTo(now) >= 0)
			return false;
		return true;
	}
	
	
	public static boolean replay(Order order) {
		//if order not exist in pending send denied
		//else if exist check if one hour gone by
		if (limitReach(order)) {
			removePending(order);
			return true;
		}
		return false;
	}
	
	

	// input: order class of the order to cancel
	// output: true if found the order on the waitlist and removed it, false if not.
	// DB: delete the waitlist entry of that order if found
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
	// send to DB: order to remove from pending
	public static boolean removePending(Order order) {
		int orderNum = order.getOrderNumber();
		ArrayList<String> query = new ArrayList<String>();
		query.add("delete");
		query.add("pendingwaitlist");
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
	query.add("pendingwaitlist"); // table name
	if(messege.getMessage().contains("GoNature 24 Hours Notification"))
		query.add("'"+messege.getOrder().getOrderNumber()+"','"+messege.getLimitFor24()+"'"); // values in query format
	else if(messege.getMessage().contains("GoNature WaitList Notification"))
		query.add("'"+messege.getOrder().getOrderNumber()+"','"+messege.getLimitFor24()+"'"); // values in query format
	return MySQLConnection.insert(query);
	}
	
	
	//input: Order to insert into the DB
	//output: Array list of array list of strings that contains the expired entrys in pending
	private static ArrayList<ArrayList<String>> selectExpiredPending() {
	ArrayList<String> query = new ArrayList<String>();
	query.add("select"); // command
	query.add("pendingwaitlist"); // table name
	query.add("orderNumber"); // col name
	query.add("WHERE timeLimit<NOW()"); // condition
	query.add("1"); // col num
	return MySQLConnection.select(query);
	}
	
	private static Date selectDateForPending(Order order) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("pendingwaitlist"); // table name
		query.add("timeLimit"); // col name
		query.add("WHERE orderNumber='"+ order.getOrderNumber() +"'"); // condition
		query.add("1"); // col num
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return null;
		return java.sql.Timestamp.valueOf(queryData.get(0).get(0)); ////////////check this closely
	}

}


//
////input: Email messege replay
////output: none
////DB: if replay was made less than 1 hour from when sent remove from pending list
//public static void replayFromMail(EmailMessege msg) {
//	Date sent = msg.getRepliedTo().getSentTime();
//	Date recived = msg.getSentTime();
//	if (lessThanOneHour(sent,recived)) {
//		removePending(msg.getOrder());
//		//notify ok
//	}
//	//else part taking care by CheckTheWaitList()
//}
//
////input: Sms messege replay
////output: none
////DB: if replay was made less than 1 hour from when sent remove from pending list
//public static void replayFromSms(SmsMessege msg) {
//	Date sent = msg.getRepliedTo().getSentTime();
//	Date recived = msg.getSentTime();
//	if (lessThanOneHour(sent,recived)) {
//		removePending(msg.getOrder());
//		//notify ok
//	}
//	//else part taking care by CheckTheWaitList()
//}

////input:
////output:
////sendToClient: true if less than hour has passed and all good false if not
//public static boolean oldReplay(Order order) {
//	//if order not exist in pending send denied
//	//else if exist check if one hour gone by
//	ArrayList<String> query = new ArrayList<String>();
//	query.add("select");
//	query.add("pendingwaitlist");
//	query.add("timeSent");
//	query.add("WHERE orderNumber='"+order.getOrderNumber()+"'");
//	query.add("1");
//	ArrayList<ArrayList<String>> answer = MySQLConnection.select(query);
//	if (!answer.isEmpty()) {
//		Date sent = java.sql.Timestamp.valueOf(answer.get(0).get(0)); ////////////check this closely
//		Date recived = new Date();
//		if (lessThanOneHour(sent,recived)) {
//			removePending(order);
//			return true;
//		}
//	}
//	return false;
//}

////input: date sent and date recived
////output: true if less the one hour has passed flase if not
//private static boolean lessThanOneHour(Date sent, Date recived) {
//	Date limit = new Date(sent.getTime() + 60*60*1000);
//	if (limit.compareTo(recived) >= 0)
//		return true;
//	return false;
//}