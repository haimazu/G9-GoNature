package server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import userData.Member;

public class ParkGate {

	//input: ArrayList of object
	//						cell[0] contains "getPrice" String
	//						cell[1] contains ParkName
	//						cell[2] contains String "ID" or "MEMBERID" or "ORDERNUMBER" (depends on data in cell3)
	//						cell[3] contains value detorment by cell2 as String 
	//						cell[4] how many people wants to enter 
	//output: none
	//send to client: ArrayList of object
	//						cell[0] contains "enterThePark" String
	//						cell[1] contains priceBeforeDiscount as string
	//						cell[2] contains priceAfterDiscount as string
	//						cell[3] contains discount as string
	public static void getPrice(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		String parkName = (String)recived.get(1);
		String switchKey = (String)recived.get(2);
		String id = null, memberId = null, orderNumber = null;
		switch (switchKey) {
		case "ID":
			id = (String)recived.get(3);
			break;
		case "MEMBERID":
			memberId = (String)recived.get(3);
			break;
		case "ORDERNUMBER":
			orderNumber = (String)recived.get(3);
			break;
		default:
			System.out.println("bootkeError");
			break;
		}
		String howMany = (String)recived.get(4);
		ArrayList<Double> prices = priceingForEntry(orderNumber, memberId, id, howMany, parkName);
		answer.add(prices.get(0).toString()); //priceBeforeDiscount
		answer.add(prices.get(1).toString()); //priceAfterDiscount
		answer.add(prices.get(2).toString()); //discount
		try {
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//input: ArrayList of object
	//						cell[0] contains "enterThePark" String
	//						cell[1] contains Park Class
	//						cell[2] contains String "ID" or "MEMBERID" or "ORDERNUMBER" (depends on data in cell3)
	//						cell[3] contains value determent by cell2 as String 
	//						cell[4] how many people wants to enter 
	//output: none
	//send to client: ArrayList of object
	//						cell[0] contains "enterThePark" String
	//						cell[1] contains String "enter" upon success "notGoodTime" / "allreadyInPark" / "parkfull" if not
	public static void enterThePark(ArrayList<Object> recived, ConnectionToClient client) throws IOException {
		String extras = null;
		boolean moreThanOrdered = false;
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Park park = refreshPark(((Park)recived.get(1)).getName());
		String switchKey = (String) recived.get(2);
		String id = null, memberId = null, orderNumber = null;
		switch (switchKey) {
		case "ID":
			id = (String) recived.get(3);
			break;
		case "MEMBERID":
			memberId = (String) recived.get(3);
			break;
		case "ORDERNUMBER":
			orderNumber = (String) recived.get(3);
			break;
		default:
			System.out.println("bootkeError");
			break;
		}
		String howMany = (String) recived.get(4);
		if (park.getMaximumCapacityInPark()<park.getCurrentAmount()+Integer.parseInt(howMany)) {
			answer.add("parkFull");
			client.sendToClient(answer);
			return;
		}
		ArrayList<Object> objForFech = new ArrayList<Object>();
		ArrayList<String> orderNumArr = new ArrayList<String>();
		orderNumArr.add(orderNumber);
//		if (orderNumber != null) {
//			objForFech.add("ordersByOrderNumber");
//			objForFech.add(orderNumArr);
//		} else {
//			objForFech.add("ordersByIdOrMemberId");
//			if (memberId != null)
//				objForFech.add(memberId);
//			else if (id != null)
//				objForFech.add(id);
//		}
		ArrayList<String> stringArr = new ArrayList<String>();
		if (orderNumber != null) {
			stringArr.add(orderNumber);
			objForFech.add("ordersByOrderNumber");
		} else {
			objForFech.add("ordersByIdOrMemberId");
			if (memberId != null)
				stringArr.add(memberId);
			else if (id != null)
				stringArr.add(id);			
		}
		objForFech.add(stringArr);
		Order order=null;
		ArrayList<ArrayList<String>> orderWrapped = ExistingOrderCheck.fechOrder(objForFech, "orders", "orderNumber");
		if (!orderWrapped.isEmpty()) {//order was found
			order = new Order(orderWrapped.get(1));
			if (!orderOnTime(order)) {
				answer.add("notGoodTime");
				client.sendToClient(answer);
				return;
			}
			if(orderWasUsed(order)) {
				answer.add("allreadyInPark");
				client.sendToClient(answer);
				return;
			}
			if (order.getVisitorsNumber() < Integer.parseInt(howMany)) { //if more than pepole on reservation
				moreThanOrdered = true;
				extras = ""+(Integer.parseInt(howMany) - order.getVisitorsNumber());
				updateArrived(order,order.getVisitorsNumber());//insert arrived to order
				insertEnteryExit(order,order.getVisitorsNumber(),"enter");
				//dont return yet
			}		
		} if (orderWrapped.isEmpty() || moreThanOrdered) { //create an order for random visits
			if (moreThanOrdered)
				howMany = ""+(Integer.parseInt(howMany) - Integer.parseInt(extras));
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			order = new Order(park.getName(), now.format(formatter).toString(), memberId, id, Integer.parseInt(howMany));
			Member member = NewOrder.MemerCheck(order);
			order = NewOrder.totalPrice(order, member, true);// updating the prices in the order
			order.setOrderNumber(Counter.getCounter().orderNum()); // get an order number
			NewOrder.insertNewOrder(order);
		}
		updateArrived(order,order.getVisitorsNumber());//insert arrived to order
		insertEnteryExit(order,order.getVisitorsNumber(),"enter");
		answer.add("enter");
		client.sendToClient(answer);
		
	}
	
	//input: ArrayList of object
	//						cell[0] contains "exitThePark" String
	//						cell[1] contains Park Class
	//						cell[2] contains String "ID" or "MEMBERID" or "ORDERNUMBER" (depends on data in cell3)
	//						cell[3] contains value determent by cell2 as String 
	//output: none
	//send to client: ArrayList of object
	//						cell[0] contains "exitThePark" String
	//						cell[1] contains String "exited" if exited "allreadyExited" or "neverWasHere" if not
	public static void exitThePark(ArrayList<Object> recived, ConnectionToClient client) throws IOException {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Park park = (Park) recived.get(1);
		String switchKey = (String) recived.get(2);
		String id = null, memberId = null, orderNumber = null;
		switch (switchKey) {
		case "ID":
			id = (String) recived.get(3);
			break;
		case "MEMBERID":
			memberId = (String) recived.get(3);
			break;
		case "ORDERNUMBER":
			orderNumber = (String) recived.get(3);
			break;
		default:
			System.out.println("bootkeError");
			break;
		}
		ArrayList<Object> objForFech = new ArrayList<Object>();
		ArrayList<String> orderNumArr = new ArrayList<String>();
		orderNumArr.add(orderNumber);
//		if (orderNumber != null) {
//			objForFech.add("ordersByOrderNumber");
//			objForFech.add(orderNumArr);
//		} else {
//			objForFech.add("ordersByIdOrMemberId");
//			if (memberId != null)
//				objForFech.add(memberId);
//			else if (id != null)
//				objForFech.add(id);
//		}
		ArrayList<String> stringArr = new ArrayList<String>();
		if (orderNumber != null) {
			stringArr.add(orderNumber);
			objForFech.add("ordersByOrderNumber");
		} else {
			objForFech.add("ordersByIdOrMemberId");
			if (memberId != null)
				stringArr.add(memberId);
			else if (id != null)
				stringArr.add(id);			
		}
		objForFech.add(stringArr);
		Order order=null;
		ArrayList<ArrayList<String>> orderWrapped = ExistingOrderCheck.fechOrder(objForFech, "orders", "orderNumber");
		if (orderWrapped.isEmpty()) {
			answer.add("neverWasHere");
			client.sendToClient(answer);
			return;
		}
		order = new Order(orderWrapped.get(1));
		//select entry and exit time from exitentry
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("entryandexit"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE orderNumber='"+ order.getOrderNumber() +"'"); // condition
		query.add("6"); // how many columns returned
		ArrayList<ArrayList<String>> entryExitArray = MySQLConnection.select(query);
		if (entryExitArray.isEmpty()) { //if not exist never was here
			answer.add("neverWasHere");
			client.sendToClient(answer);
			return;
		} else if (entryExitArray.get(0).get(2)!=null) { //if exittime not null than allready exited
			answer.add("allreadyExited");
			client.sendToClient(answer);
			return;
		}
		//if made it here than all good and can exit
		insertEnteryExit(order,order.getAmountArrived(),"exit"); //update exit time to now entryexit
		answer.add("exited");
		client.sendToClient(answer);
		return;
	}

	//input: order , amount of visitors , "enter" or "exit" as string
	//output: none
	//DB: 	if enter than insert a new line to entryandexit table with time enterd
	//		if exit than update the time exited
	private static void insertEnteryExit(Order order, int visitorsNumber, String enterOrExit) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		ArrayList<String> query = new ArrayList<String>();
		if (enterOrExit.equals("enter")) {
			query.add("insert");
			query.add("entryandexit");
			query.add("'"+ order.getOrderNumber() +"', '"+ now.format(formatter) +"', "+ null +", '"+ order.getParkName() +"', '"+ order.getOrderType() +"', '"+ visitorsNumber +"'");
			MySQLConnection.insert(query);
			updateParkCapacity(order.getParkName(),visitorsNumber);
		}
		else if (enterOrExit.equals("exit")) {
			query.add("update");
			query.add("entryandexit");
			query.add("timeExit='"+ now.format(formatter) +"'");
			query.add("orderNumber");
			query.add(""+order.getOrderNumber());
			MySQLConnection.update(query);
			updateParkCapacity(order.getParkName(),-1*visitorsNumber);
		}		
	}
	
	//input: park name and number to update
	//output: none
	//DB: updates the park capacity
	private static void updateParkCapacity(String parkName, int num) {
		Park park=refreshPark(parkName);
		int newAmount=park.getCurrentAmount()+num;
		ArrayList<String> query = new ArrayList<String>();
		query.add("update");
		query.add("park");
		query.add("currentVisitoreAmount='"+ newAmount +"'");
		query.add("parkName");
		query.add(parkName);
		MySQLConnection.update(query);
	}

	//input: order class and amount arrived
	//output: none
	//DB: update amount arrive in order table
	private static void updateArrived(Order order, int visitorsNumber) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("update");
		query.add("orders");
		query.add("amountArrived='"+ visitorsNumber +"'");
		query.add("orderNumber");
		query.add(""+order.getOrderNumber());
		MySQLConnection.update(query);
	}

	//input: park name
	//output: park class fresh from the db
	private static Park refreshPark(String parkName) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("park"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE parkName='"+ parkName +"'"); // condition
		query.add("6"); // how many columns returned
		ArrayList<ArrayList<String>> parkArray = MySQLConnection.select(query);
		if (parkArray.isEmpty())
			return null;
		return new Park(parkArray.get(0));		
	}

	//input: order
	//note: check if order is on time (not before capsule start and not after capsule ends)
	//output: true if on time, false if not
	private static boolean orderOnTime(Order order) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime capsuleStart = LocalDateTime.parse(order.getArrivedTime(), formatter);
		LocalDateTime capsuleEnd = LocalDateTime.parse(order.getArrivedTime(), formatter).plusHours(4);
		if (now.isBefore(capsuleStart) || now.isAfter(capsuleEnd))
			return false;
		return true;
	}

	
	//input: order class
	//note: check if the order exist in entry and exit table
	//output: true if exist false if not
	private static boolean orderWasUsed(Order order) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("enteryandexit"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE orderNumber='"+ order.getOrderNumber() +"'"); // condition
		query.add("6"); // how many columns returned
		ArrayList<ArrayList<String>> usedOrder = MySQLConnection.select(query);
		if (usedOrder.isEmpty())
			return false;
		return true;		
	}
	
	//input: order number, member id, id, how many, park name
	//note: calculate the price the visitor need to pay
	//output: array list of doubles [0]=priceBeforeDiscount, [1]=priceBeforeDiscount, [0]=discount
	private static ArrayList<Double> priceingForEntry(String orderNumber, String memberId, String id, String howMany, String parkName) {
		ArrayList<Double> ret = new ArrayList<Double>();
		boolean moreThanOrdered = false;
		Double priceBeforeDiscount = 0.0;
		Double priceAfterDiscount = 0.0;
		Double discount = 0.0;
		ArrayList<Object> objForFech = new ArrayList<Object>();
		ArrayList<String> stringArr = new ArrayList<String>();
		if (orderNumber != null) {
			stringArr.add(orderNumber);
			objForFech.add("ordersByOrderNumber");
		} else {
			objForFech.add("ordersByIdOrMemberId");
			if (memberId != null)
				stringArr.add(memberId);
			else if (id != null)
				stringArr.add(id);			
		}
		objForFech.add(stringArr);
		ArrayList<ArrayList<String>> orderWrapped = ExistingOrderCheck.fechOrder(objForFech, "orders", "orderNumber");
		if (!orderWrapped.isEmpty()) {//order was found
			Order order = new Order(orderWrapped.get(0));
			priceBeforeDiscount += order.getPrice();
			priceAfterDiscount += order.getTotalPrice();
			discount = ((priceBeforeDiscount-priceAfterDiscount)/priceBeforeDiscount)*100; //discount in precent
			// suck the order price
			if (order.getVisitorsNumber() < Integer.parseInt(howMany)) { //if more than pepole on reservation
				moreThanOrdered = true;
				howMany = ""+(Integer.parseInt(howMany) - order.getVisitorsNumber());
			}
		}
		if (orderWrapped.isEmpty() || moreThanOrdered) { // if order not exist 
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Order stubOrder = new Order(parkName, now.format(dtf).toString(), memberId, id, Integer.parseInt(howMany));
			Member member = NewOrder.MemerCheck(stubOrder);
			stubOrder = NewOrder.totalPrice(stubOrder, member, true);
			//dont forget to insert the + thig
			priceBeforeDiscount += stubOrder.getPrice();
			priceAfterDiscount += stubOrder.getTotalPrice();
			discount = ((priceBeforeDiscount-priceAfterDiscount)/priceBeforeDiscount)*100; //discount in precent
		}
		ret.add(priceBeforeDiscount);
		ret.add(discount);
		ret.add(priceAfterDiscount);
		
		return ret;
	}
	
	
	
	private static int enteryAmountLeft(String patkName, String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		
		int amount=0;
		
		
		
		return amount;
	}
	
}
