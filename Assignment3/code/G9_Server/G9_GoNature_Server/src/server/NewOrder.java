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

//executed by Nastya
public class NewOrder {

	// input: ArrayList<Object>: cell[0] function name
	// cell[1] order object ,ConnectionToClient
	// inserting a new reservation in order table in DB
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] Order object with updated cells: price ,totalPrice
	public static void NewReservation(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList a = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		Member memb = MemerCheck(data); // to check the member type by order

		// check if the capacity of orders is full
		if ((!a.checkForAvailableSpots(recived))) {
			answer.add("Failed");// need to enter waiting list
			EchoServer.sendToMyClient(answer, client);
		}

		else {

			data = totalPrice(data, memb, data.isOccasional());// updating the prices in the order
			///// Roi//////
			data.setOrderNumber(Counter.getCounter().orderNum()); // get an order number

			answer.add(data);

			EchoServer.sendToMyClient(answer, client);
		}

	}

	// input: ArrayList<Object>: cell[0] function name
	// cell[1] order object
	// cell[2] credit card object/null
	// ConnectionToClient
	// output:T/F
	public static void queInsert(ArrayList<Object> recived, ConnectionToClient client) {
		System.out.println("queInsert start");
		if (recived.get(2) != null)
			creditCardSave((CreditCard) recived.get(2));
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		answer.add(insertNewOrder(data)); //////////////////////////////////////////// ROI
		EchoServer.sendToMyClient(answer, client);
	}

	// input: order object
	//
	// output: checks if u a member and return the member from DB
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
	// input: Order with empty totalPrice & price & orderType, a Member
	//
	// output: Order with updated totalPrice and price and orderType
	public static Order totalPrice(Order ord, Member memb, Boolean occasional) {
		double parkEnteryPrice = CurrentPriceInPark(ord);
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
								+ Integer.parseInt(memb.getMemberAmount()) * parkEnteryPrice * 0.8);
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
				ord.setOrderType(OrderType.REGULAR);
				ord.setTotalPrice(ord.getPrice() * 0.85);
			} else {// if the order is for non-occasional member
				ord.setMemberId(memb.getMemberNumber());
				ord.setID(memb.getMemberID());
				switch (memb.getMemberOrderType()) {
				case MEMBER:
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
					ord.setOrderType(OrderType.GROUP);
					ord.setTotalPrice(ord.getPrice() * 0.75);
					break;
				default:
					break;
				}
			}
		}

		return ord;
	}

	// input: order object
	//
	// output: returns the current price of entry in the park with manger discount
	// calculated
	public static double CurrentPriceInPark(Order ord) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));

		// new park discount
		ArrayList<String> query1 = new ArrayList<String>();
		query1.add("select"); // command
		query1.add("discounts"); // table name
		query1.add("parkName,discountscol"); // columns to select from
		query1.add("WHERE parkName='" + ord.getParkName() + "' AND discounts.from <= '" + now
				+ "' AND discounts.to >= '" + now + "'"); // condition
		query1.add("2"); // how many columns returned
		ArrayList<ArrayList<String>> newDiscountsQ = MySQLConnection.select(query1);

		// current park discount
		ArrayList<String> query2 = new ArrayList<String>();
		query2.add("select"); // command
		query2.add("park"); // table name
		query2.add("entryPrice,mangerDiscount"); // columns to select from
		query2.add("WHERE parkName='" + ord.getParkName() + "'"); // condition
		query2.add("2"); // how many columns returned
		ArrayList<ArrayList<String>> currentParkDiscountQ = MySQLConnection.select(query2);

		// returns the price of entry to the park with a manager discount
		System.out.println("current dis " + currentParkDiscountQ.get(0).get(1));
		double discount = Double.parseDouble(currentParkDiscountQ.get(0).get(1));
		if (!newDiscountsQ.isEmpty()) {
			discount = Double.parseDouble(newDiscountsQ.get(0).get(1));
			// if there is no change in discount
			if (Double.parseDouble(currentParkDiscountQ.get(0).get(1)) == discount)
				return Double.parseDouble(currentParkDiscountQ.get(0).get(0))
						* Double.parseDouble(currentParkDiscountQ.get(0).get(1));

			// update the park table in DB with current discount
			ArrayList<String> query3 = new ArrayList<String>();
			query3.add("update");
			query3.add("park");
			System.out.println(discount);
			query3.add("mangerDiscount= '" + discount + "'");
			query3.add("parkName");
			query3.add(ord.getParkName());
			System.out.println(query3);
			boolean a;
			a = MySQLConnection.update(query3);
			System.out.println(a);
		}

		return Double.parseDouble(currentParkDiscountQ.get(0).get(0)) * discount;
	}

	////////////// ********************* Park **************************************
	// input: ArrayList<Object>, ConnectionToClient
	//
	// output: returns to client side with a list of parks names from DB
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

	// input: ArrayList<Object>,ConnectionToClient
	// pulling details of a selected park from DB
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] ArrayList<String> [0] orderNumber
	// [1] number of visitors to add
	@SuppressWarnings("unchecked")
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
	// input: object credit card
	//
	// output: T\F if success
	public static Boolean creditCardSave(CreditCard cc) {

		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add(toStringForCreditCardSave(cc)); // values in query format

		return MySQLConnection.insert(query); // returns T\F
	}

	// in use for CreditCardSave query
	public static String toStringForCreditCardSave(CreditCard data) {
		return "'" + data.getCardNumber() + "','" + data.getCardHolderName() + "','" + data.getExpirationDate() + "','"
				+ data.getCvc() + "','" + data.getOrderNumber() + "'";
	}

	// input: Order to insert into the DB
	// output: true if sussesful false if not
	// send to DB: new order to list in
	public static boolean insertNewOrder(Order order) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("orders"); // table name
		query.add(order.toStringForDB()); // values in query format
		return MySQLConnection.insert(query);
	}

}

// for backup
////input: order
//	//
//	// output: to string for a query
//	public static String toStringForReservation(Order data) {
//
//		String afterDiscount = Double.toString(data.getTotalPrice());
//		String beforDiscount = Double.toString(data.getPrice());
//		return "'" + data.getVisitorsNumber() + "','" + data.getOrderEmail() + "','" + data.getOrderPhone() + "','"
//				+ data.getOrderType().toString() + "','" + afterDiscount + "','" + beforDiscount + "','"
//				+ data.getParkName() + "','" + data.getArrivedTime() + "','" + data.getMemberId() + "','" + data.getID()
//				+ "','" + data.getAmountArrived() + "','" + data.getOrderNumber() + "'";
//	}
// input: Order with empty totalPrice & price & orderType, a Member
//
// output: Order with updated totalPrice and price and orderType
//public static Order totalPrice(Order ord, Member memb, Boolean occasional) {
//
//	int parkEnteryPrice = CurrentPriceInPark(ord);
//	if (!occasional)
//		ord.setPrice(parkEnteryPrice * ord.getVisitorsNumber());// full price
//	else
//		ord.setPrice(parkEnteryPrice * ord.getAmountArrived());// full price
//
//	if (memb == null) {// if the order is not for a member
//		ord.setOrderType(OrderType.REGULAR);
//		ord.setID(null);
//		ord.setMemberId(null);
//		if (!occasional)
//			ord.setTotalPrice(parkEnteryPrice * ord.getVisitorsNumber() * 0.85);
//		else
//			ord.setTotalPrice(parkEnteryPrice * ord.getAmountArrived() * 0.85);
//	} else {// if the order is for members/group
//
//		switch (memb.getMemberOrderType()) {
//		case MEMBER:
//			ord.setOrderType(OrderType.MEMBER);
//			int familymembers = Integer.parseInt(memb.getMemberAmount());
//			int notFamilyMembers;
//			if (occasional)
//				notFamilyMembers = ord.getAmountArrived() - familymembers;
//			else
//				notFamilyMembers = ord.getVisitorsNumber() - familymembers;
//			if (notFamilyMembers < 0)
//				notFamilyMembers = 0;
//			ord.setTotalPrice(familymembers * parkEnteryPrice * 0.80);
//			if (occasional)
//				ord.setTotalPrice(ord.getTotalPrice() + notFamilyMembers * parkEnteryPrice * 0.85);
//			else
//				ord.setTotalPrice(ord.getTotalPrice() + notFamilyMembers * parkEnteryPrice);
//			break;
//		case GROUP:
//			ord.setOrderType(OrderType.GROUP);
//			int groupAmount = Integer.parseInt(memb.getMemberAmount());
//			if (!occasional) {
//				groupAmount = Integer.parseInt(memb.getMemberAmount());
//				ord.setTotalPrice(groupAmount * parkEnteryPrice * 0.75);
//			} else {
//				groupAmount = ord.getAmountArrived();
//				ord.setTotalPrice(groupAmount * parkEnteryPrice * 0.90);
//			}
//			break;
//		default:
//			break;
//		}
//	}
//	return ord;
//}