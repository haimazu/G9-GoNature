package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import orderData.OrderType;
import userData.Member;

/**
 * The NewOrder program places a new order into the DB
 *
 * @author Anastasia Kokin
 */

public class NewOrder {

	/**
	 * inserting a new reservation in order table in DB
	 * 
	 * @param ArrayList of Object: cell[0] function name, cell[1] order object
	 * @param client             ConnectionToClient
	 * @return none. sends to client :ArrayList of Object => cell[0] function name,
	 *         cell[1] Order object with updated cells: price ,totalPrice
	 * 
	 **/
	public static void NewReservation(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList a = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		Member memb = MemerCheck(data); // to check the member type by order
		if (checkFake(data)) {
			String fakeOrdNum = checkFakeExist(data);
			if (fakeOrdNum != null) {
				answer.add(fakeOrdNum);
				EchoServer.sendToMyClient(answer, client);
			}
		}

		// check if the capacity of orders is full
		if ((!a.checkForAvailableSpots(recived))) {
			answer.add("Failed");// need to enter waiting list
			System.out.println("failed");
			EchoServer.sendToMyClient(answer, client);
		}

		else {
			System.out.println("check1");
			data = totalPrice(data, memb, data.isOccasional());// updating the prices in the order
			System.out.println(data);
			data.setOrderNumber(Counter.getCounter().orderNum()); // get an order number
			System.out.println(data);
			System.out.println("check2");
			answer.add(data);
			System.out.println(answer);
			EchoServer.sendToMyClient(answer, client);
		}

	}

	// input: order Class
	// NOTE: check if allready in the park by ID/MemberID and arriveTime
	// output: True if allready in exist flase if not exist

	private static String checkFakeExist(Order data) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("orders"); // table name
		query.add("orderNumber"); // columns to select from
		String cond = "";
		if (data.getID() != null) {
			cond += "WHERE ID='" + data.getID() + "' ";
		} else if (data.getMemberId() != null) {
			cond += "WHERE memberNumber='" + data.getMemberId() + "' ";
		}
		query.add(cond + "AND arrivedTime='" + data.getArrivedTime() + "'");
		query.add("1"); // how many columns returned
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return null;
		return queryData.get(0).get(0); // return original order number
	}

	// input: order class
	// note: check if the order is "fake order" for random visitions
	// output: true if fake false if not
	private static boolean checkFake(Order data) {
		if (data.getOrderEmail() == null)
			return true;
		return false;
	}

	/**
	 * function that called when we want to insert a new order and credit card into
	 * the DB
	 * 
	 * @param cell[0] function name cell[1] order object cell[2] credit card
	 *                object/null
	 * @param client  ConnectionToClient
	 * @return T/F
	 * 
	 **/
	public static void queInsert(ArrayList<Object> recived, ConnectionToClient client) {
		System.out.println("queInsert start");

		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order order = (Order) recived.get(1);
		answer.add(insertNewOrder(order));

		if (recived.get(2) != null) {
			CreditCard cc = (CreditCard) recived.get(2);
			cc.setOrderNumber(order.getOrderNumber());
			creditCardSave(cc);
		}

		EchoServer.sendToMyClient(answer, client);
		String subject = "GoNature Order Confirmation";
		String messege = "order completed sucssesfuly!\n "
				+ "dont forget to confirm you arrival 24 hours before the visit"
				+ "\ndont worry, we will send you a reminder!" + "\nwe hope to see you soon!\n\n\n"
				+ order.messegeString();
		Comunication.sendNotification(subject, messege, order);
	}

	/**
	 * sending to DB: new order to list in
	 * 
	 * @param order Order Object to insert into the DB
	 * @return true if successful false if not
	 **/

	public static boolean insertNewOrder(Order order) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("orders"); // table name
		query.add(order.toStringForDB()); // values in query format
		return MySQLConnection.insert(query);
	}

	/**
	 * Checks if you are a member
	 * 
	 * @param ord Order object
	 * @return member/null
	 * 
	 **/

	public static Member MemerCheck(Order ord) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("member"); // table name
		query.add("*"); // columns to select from

		if (ord.getID() != null) {
			query.add("WHERE ID='" + ord.getID() + "'");
		} else if (ord.getMemberId() != null) {
			query.add("WHERE memberNumber='" + ord.getMemberId() + "'");
		}

		query.add("9"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return null;
		else {
			Member m = new Member(queryData.get(0));
			return m;
		}

	}

	///////////// ************************* price *****************************
	/**
	 * function that calculates the price for an order and puts it into Order object
	 * 
	 * @param ord        Order with empty totalPrice
	 * @param memb       Member object
	 * @param occasional T/F
	 * @return Order with updated totalPrice and price and orderType
	 */
	public static Order totalPrice(Order ord, Member memb, Boolean occasional) {
		System.out.println("total price enter");
		double parkEnteryPrice = CurrentPriceInPark(ord);
		System.out.println("CurrentPriceInPark exit");
		System.out.println(parkEnteryPrice);
		int numberOfPeople;
		if (occasional) {
			numberOfPeople = ord.getAmountArrived();
			ord.setPrice(parkEnteryPrice * numberOfPeople);// full price for all

			if (memb == null) { // if the order is not for a member
				ord.setOrderType(OrderType.REGULAR);
				ord.setTotalPrice(ord.getPrice());
			} else { // if the order is for occasional member
				ord.setMemberId(memb.getMemberNumber());
				ord.setID(memb.getMemberID());
				switch (memb.getMemberOrderType()) {
				case MEMBER:
					ord.setOrderType(OrderType.MEMBER);
					int nonFamily = numberOfPeople - Integer.parseInt(memb.getMemberAmount());
					if (nonFamily > 0)
						ord.setTotalPrice(nonFamily * parkEnteryPrice
								+ Double.parseDouble(memb.getMemberAmount()) * parkEnteryPrice * 0.8);
					else
						ord.setTotalPrice(numberOfPeople * parkEnteryPrice * 0.8);
					break;
				case GROUP:
					ord.setOrderType(OrderType.GROUP);
					ord.setTotalPrice(ord.getPrice() * 0.9);
					break;
				default:
					break;
				}
			}
		} else { // pre-order
			numberOfPeople = ord.getVisitorsNumber();
			ord.setPrice(parkEnteryPrice * numberOfPeople);// full price for all
			if (memb == null) { // if the order is not for a member
				System.out.println("pre order regular type enter");
				ord.setOrderType(OrderType.REGULAR);
				ord.setTotalPrice(ord.getPrice() * 0.85);
			} else {// if the order is for non-occasional member
				ord.setMemberId(memb.getMemberNumber());
				ord.setID(memb.getMemberID());
				switch (memb.getMemberOrderType()) {
				case MEMBER:
					System.out.println("pre order member type enter");
					ord.setOrderType(OrderType.MEMBER);
					int nonFamily = numberOfPeople - Integer.parseInt(memb.getMemberAmount());
					if (nonFamily > 0) {
						ord.setTotalPrice(ord.getPrice() * 0.85);
						ord.setTotalPrice(ord.getTotalPrice() - (ord.getTotalPrice() / numberOfPeople)
								* Integer.parseInt(memb.getMemberAmount()) * 0.2);
					} else {
						ord.setTotalPrice(numberOfPeople * parkEnteryPrice * 0.85);
						ord.setTotalPrice(ord.getTotalPrice() * 0.8);
					}
					break;
				case GROUP:
					System.out.println("pre order group type enter");
					System.out.println(ord.getPrice());
					ord.setOrderType(OrderType.GROUP);
					ord.setTotalPrice(ord.getPrice() * 0.75);
					System.out.println(ord.getTotalPrice());
					break;
				default:
					break;
				}
			}
		}
		System.out.println("finish price");
		return ord;
	}

	/**
	 * calculates and return current price of entry in the park with manger discount
	 * calculated
	 * 
	 * @param ord Order object
	 * @return entry price
	 **/
	public static double CurrentPriceInPark(Order ord) {

		updatingParkCurrentPrice(ord.getParkName());
		// current park discount
		ArrayList<String> query2 = new ArrayList<String>();
		query2.add("select"); // command
		query2.add("park"); // table name
		query2.add("entryPrice, mangerDiscount"); // columns to select from
		query2.add("WHERE parkName='" + ord.getParkName() + "'"); // condition
		query2.add("2"); // how many columns returned
		ArrayList<ArrayList<String>> parkDetails = MySQLConnection.select(query2);
		Double priceWithDiscount = Double.parseDouble(parkDetails.get(0).get(0))
				* Double.parseDouble(parkDetails.get(0).get(1));
		System.out.println(parkDetails);

		// returns full price in park if no discount
		if (priceWithDiscount <= 0.0)
			return Double.parseDouble(parkDetails.get(0).get(0));
		else
			return priceWithDiscount;
	}

	/**
	 * updates the discount in the park table in DB by the right dates
	 * 
	 * @param parkName
	 */
	public static void updatingParkCurrentPrice(String parkName) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		// new park discount
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("discounts"); // table name
		query1.add("parkName, discountscol"); // columns to select from
		query1.add("WHERE parkName='" + parkName + "' AND discounts.from <= '" + now + "' AND discounts.to >= '" + now
				+ "'"); // condition
		query1.add("2"); // how many columns returned
		ArrayList<ArrayList<String>> newDiscountsQ = MySQLConnection.select(query1);
		System.out.println(newDiscountsQ);

		// current park discount
		ArrayList<String> query2 = new ArrayList<String>();
		query2.add("select"); // command
		query2.add("park"); // table name
		query2.add("entryPrice, mangerDiscount"); // columns to select from
		query2.add("WHERE parkName='" + parkName + "'"); // condition
		query2.add("2"); // how many columns returned
		ArrayList<ArrayList<String>> currentParkDiscountQ = MySQLConnection.select(query2);
		System.out.println(currentParkDiscountQ);
		// returns the price of entry to the park with a manager discount

		double discount = Double.parseDouble(currentParkDiscountQ.get(0).get(1));
		System.out.println("current dis " + discount);
		if (!newDiscountsQ.isEmpty())
			discount = Double.parseDouble(newDiscountsQ.get(0).get(1));
		else
			discount = 1;
		// if there is no change in discount
		if (Double.parseDouble(currentParkDiscountQ.get(0).get(1)) == discount) {
			System.out.println("current dis2 " + discount);
			return;
		}

		// update the park table in DB with current discount
		System.out.println("update start");
		ArrayList<String> query3 = new ArrayList<String>();
		query3.add("update");
		query3.add("park");
		System.out.println(discount);
		query3.add("mangerDiscount= '" + discount + "'");
		query3.add("parkName");
		query3.add(parkName);
		System.out.println(query3);
		boolean a;
		a = MySQLConnection.update(query3);
		System.out.println(a);
	}

	////////////// ********************* Park **************************************

	/**
	 * function to pull parks names from DB
	 * 
	 * @param recived ArrayList<Object> cell [0]: calling function name
	 * @param client  ConnectionToClient
	 * @return a list of parks names from DB
	 **/
	public static void parksNames(ArrayList<Object> recived, ConnectionToClient client) {

		// the returned values stored here
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ParksNames
		answer.add(recived.get(0));
		// cell 0: recivedFromServerParksNames

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("parkName"); // columns to select from
		query.add(""); // condition - non -> all parks names required
		query.add("1"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			// no parks in DB
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			ArrayList<String> parkNames = new ArrayList<String>();
			for (ArrayList<String> a : queryData)
				for (String b : a)
					parkNames.add(b);
			answer.add(parkNames);
		}
		EchoServer.sendToMyClient(answer, client);

	}

	/**
	 * updating column amountArrived in order table for an order
	 * 
	 * @param recived ArrayList<Object> cell[0]: calling function name
	 * @param client  ConnectionToClient
	 * @return ArrayList<Object>=> cell[0] function name, cell[1]: ArrayList<String>
	 *         [0] orderNumber, [1] number of visitors to add
	 **/

	public static void updateOrderAmountArrived(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : updateAmountArrived
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: orderNumber
		// cell 1: amountArrived
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("orders"); // table name
		query.add("amountArrived = '" + data.get(1) + "'"); // columns to update
		query.add("orderNumber"); // condition
		query.add(data.get(0)); // orderNumber value

		if (MySQLConnection.update(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
	}

	////// ************credit card****************************************

	/**
	 * inserts a new credit card in DB
	 * 
	 * @param cc CreditCard object
	 * @return T\F
	 **/
	public static Boolean creditCardSave(CreditCard cc) {

		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add(toStringForCreditCardSave(cc)); // values in query format

		return MySQLConnection.insert(query);
	}

	/**
	 * toString in use for CreditCardSave query
	 * 
	 * @param data CreditCard object
	 * @return String
	 **/
	public static String toStringForCreditCardSave(CreditCard data) {
		return "'" + data.getCardNumber() + "','" + data.getCardHolderName() + "','" + data.getExpirationDate() + "','"
				+ data.getCvc() + "','" + data.getOrderNumber() + "'";
	}

}
