package server;

import java.util.ArrayList;
import java.util.Arrays;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import orderData.OrderType;
import server.WaitingList;
import userData.Member;

//executed by Nastya
public class NewOrder {
	
	public static void NewReservation(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList a = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received
		Member memb = MemerCheck(data); // to check the member type by order
		// check if the capacity of orders is full
		if ((!a.checkForAvailableSpots(recived, client))) {
			answer.add(false);
			EchoServer.sendToMyClient(answer, client);
		}

		else {

			data = totalPrice(data, memb);// updating the prices in the order
			//////////////
			/////Roi//////
			//////////////
			data.setOrderNumber(Counter.getCounter().orderNum()); //get an order number
			//////////////
			//////////////
			ArrayList<String> query = new ArrayList<String>();
			query.add("insert"); // command
			query.add("orders"); // table name
			query.add(toStringForReservation(data)); // values in query format
			
			if (MySQLConnection.insert(query)) {
				//answer.add(true);
				answer.add(data);
			}
			else
				answer.add("Failed");

			EchoServer.sendToMyClient(answer, client);
		}

	}

	// input: Order with empty totalPrice & price & orderType, a Member
	//
	// output: Order with updated totalPrice and price and orderType
	public static Order totalPrice(Order ord, Member memb) {

		int parkEnteryPrice = CurrentPriceInPark(ord);
		ord.setPrice(parkEnteryPrice * ord.getVisitorsNumber());
		if (memb == null) {// if the order is not 4 a member
			ord.setOrderType(OrderType.REGULAR);
			ord.setTotalPrice(parkEnteryPrice * ord.getVisitorsNumber() * 0.85);
		} else {// if the order is for some members

			switch (memb.getMemberOrderType()) {
			case MEMBER:
				ord.setOrderType(OrderType.MEMBER);
				int familymembers = Integer.parseInt(memb.getAmount());
				int notFamilyMembers = ord.getVisitorsNumber() - familymembers;
				if (notFamilyMembers < 0)
					notFamilyMembers = 0;
				ord.setTotalPrice(familymembers * parkEnteryPrice * 0.85);
				ord.setTotalPrice(ord.getPrice() * 0.75 + notFamilyMembers * parkEnteryPrice * 0.85);
				break;
			case GROUP:
				ord.setOrderType(OrderType.GROUP);
				int groupAmount = Integer.parseInt(memb.getAmount());
				ord.setTotalPrice(groupAmount * parkEnteryPrice * 0.75);
				break;
			default:
				break;
			}
		}
		return ord;
	}

	//input: order
	//
	// output: to string for a query
	public static String toStringForReservation(Order data) {
		
		String afterDiscount=Double. toString(data.getTotalPrice());
		String beforDiscount=Double. toString(data.getPrice());
		return "'" + data.getVisitorsNumber() + "','" 
					+ data.getOrderEmail() + "','" 
					+ data.getOrderPhone()+ "','"
					+ data.getOrderType().toString() + "','" 
					+ afterDiscount + "','"
					+ beforDiscount + "','" 
					+ data.getParkName() + "','" 
					+ data.getArrivedTime() + "','" 
					+ data.getMemberId() + "','"
					+ data.getID() + "','"
					+ data.getAmountArrived()+"','"
					+ data.getOrderNumber() +"'";
	}

	//input: order
	//
	//output:  checks if u a member and return the member from DB
	public static Member MemerCheck(Order ord) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("member"); // table name
		query.add("*"); // columns to select from
//		if (ord.getMemberId() != null && ord.getID() != null)
//			query.add("WHERE memberNumber='" + ord.getMemberId() + "'" + " OR ID='=" + ord.getID() + "'");
		if (ord.getMemberId() != null)
			query.add("WHERE memberNumber='" + ord.getMemberId() + "'");
		else if (ord.getID() != null)
			query.add("WHERE ID='" + ord.getID() + "'");
		query.add("9"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return null;
		else
			return new Member(queryData.get(0));

	}

	//input: order
	//
	// output: returns the current price of entry in the park with manger discount calculated
	//selected in the order
	public static int CurrentPriceInPark(Order ord) {

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("entryPrice,mangerDiscount"); // columns to select from
		query.add("WHERE parkName='" + ord.getParkName() + "'"); // condition 
		query.add("2"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		//returns the price of entry to the park with a manager discount
		return Integer.parseInt(queryData.get(0).get(0))*Integer.parseInt(queryData.get(0).get(1));
	}

	//input: ArrayList<Object>, ConnectionToClient
	//
	//output: returns to client side with a list of parks names from DB
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
		query.add("order"); // table name
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
