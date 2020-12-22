package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import orderData.OrderType;
import server.WaitingList;
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
		if ((!a.checkForAvailableSpots(recived, client))) {
			answer.add("Failed");// need to enter waiting list
			EchoServer.sendToMyClient(answer, client);
		}

		else {

			data = totalPrice(data, memb, data.isOccasional());// updating the prices in the order
			//////////////
			///// Roi//////
			//////////////
			data.setOrderNumber(Counter.getCounter().orderNum()); // get an order number
			//////////////
			//////////////
//			ArrayList<String> query = new ArrayList<String>();
//			query.add("insert"); // command
//			query.add("orders"); // table name
//			query.add(data.toStringForDB()); // values in query format

			answer.add(data);

			EchoServer.sendToMyClient(answer, client);
		}

	}

	// input: ArrayList<Object>: cell[0] function name
	// cell[1] order object
	// cell[2] credit card object/null
	// ConnectionToClient
	// output:
	public static void queInsert(ArrayList<Object> recived, ConnectionToClient client) {
		if (recived.get(2) != null)
			creditCardSave((CreditCard)recived.get(2));
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // order object received
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("orders"); // table name
		query.add(data.toStringForDB()); // values in query format
		if (MySQLConnection.insert(query)) {
			answer.add(data);
		} else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
	}

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

	// input: Order with empty totalPrice & price & orderType, a Member
	//
	// output: Order with updated totalPrice and price and orderType
	public static Order totalPrice(Order ord, Member memb, Boolean occasional) {
		int parkEnteryPrice = CurrentPriceInPark(ord);
		int numberOfPeople;
		if (occasional) {
			numberOfPeople = ord.getAmountArrived();
			ord.setPrice(parkEnteryPrice * numberOfPeople);// full price for all

			if (memb == null) { // if the order is not for a member
				ord.setOrderType(OrderType.REGULAR);
				ord.setTotalPrice(ord.getPrice());
			} else { // if the order is for occasional member
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

	// input: order
	//
	// output: checks if u a member and return the member from DB
	public static Member MemerCheck(Order ord) {
		ArrayList<String> query = new ArrayList<String>();
		System.out.println("memberCheck start");
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
		else
			return new Member(queryData.get(0));

	}

	// input: order
	//
	// output: returns the current price of entry in the park with manger discount
	// calculated
	// selected in the order
	public static int CurrentPriceInPark(Order ord) {

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("entryPrice,mangerDiscount"); // columns to select from
		query.add("WHERE parkName='" + ord.getParkName() + "'"); // condition
		query.add("2"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		// returns the price of entry to the park with a manager discount
		return Integer.parseInt(queryData.get(0).get(0)) * Integer.parseInt(queryData.get(0).get(1));
	}

	// input: ArrayList<Object>, ConnectionToClient
	//
	// output: returns to client side with a list of parks names from DB
	public static void ParksNames(ArrayList<Object> recived, ConnectionToClient client) {

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
		if (queryData.get(0).isEmpty()) {
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
		query.add(data.get(0)); // parkName value

		if (MySQLConnection.update(query))
			answer.add(true);
		else
			answer.add(false);

		EchoServer.sendToMyClient(answer, client);
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