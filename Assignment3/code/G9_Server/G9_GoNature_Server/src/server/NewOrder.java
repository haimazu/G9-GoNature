package server;

import java.util.ArrayList;
import java.util.Arrays;

//import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import orderData.OrderType;
import server.WaitingList;
import userData.Member;

//executed by Nastya
public class NewOrder {

	public static void NewReservation(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList a = null;
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data = (Order) recived.get(1); // credit card object received

		// check if the capacity of orders is full
		if ((!a.checkForAvailableSpots(recived, client)) || (data.getMemberId() == null && data.getID() == null)) {
			answer.add(false);
			EchoServer.sendToMyClient(answer, client);
		}

		else {

			if (data.getMemberId() != null) {

			}

			// לבדוק ID אן MEMBERID,
			// אם לא ריקיםן. מושכת את הממבר ובודקת את הסוג שלו
			// לבדוק אם קיים. אם לא אז לא חבר ולא מקבל את ההנחה

			Member memb; // to check the member type by order

			ArrayList<String> query = new ArrayList<String>();
			query.add("insert"); // command
			query.add("orders"); // table name
			query.add(toStringForReservation(data)); // values in query format

			if (MySQLConnection.insert(query))
				answer.add(true);
			else
				answer.add(false);

			EchoServer.sendToMyClient(answer, client);
		}

	}

	public static double totalPrice(int visitorsAmount, OrderType ot) {

		return 10;
	}

	// check if needed to be sent null or empty in order number
	public static String toStringForReservation(Order data) {
		return "'" + "" + "','" + data.getVisitorsNumber() + "','" + data.getOrderEmail() + "','" + data.getOrderType()
				+ "','" + data.getPrice() + "','" + data.getParkName() + "','" + data.getArrivedTime() + "','"
				+ data.getMemberId() + "','" + data.getID() + "'";
	}

	public static Member MemerCheck(Order ord) {

		// the returned values stored here
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : ParksNames

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("member"); // table name
		query.add("*"); // columns to select from
		if (ord.getMemberId() != null && ord.getID() != null)
			query.add("WHERE memberNumber='" + ord.getMemberId() + "'" + " OR ID='=" + ord.getID() + "'");
		else if (ord.getMemberId() != null)
			query.add("WHERE memberNumber='" + ord.getMemberId() + "'"); // condition - non -> all parks names required
		else if (ord.getID() != null)
			query.add("WHERE ID='" + ord.getID() + "'");
		query.add("9"); // how many columns returned

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
		return null;

	}

	// func that returns all parks names
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

}
