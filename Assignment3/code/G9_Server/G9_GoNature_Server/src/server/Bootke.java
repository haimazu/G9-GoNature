package server;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dataLayer.Park;
import ocsf.server.ConnectionToClient;
import orderData.Order;
import userData.Member;

public class Bootke {
	// Price
	// parkName + String ID/MEMBERID/ORDERNUMBER + value of ID/MEMBERID/ORDERNUMBER
	// + HOWMANY
	// return price
	// ArrayList <object> -> cell[0] -> string methodcalled
	// -> cell[1] -> price before discount as string
	// -> cell[2] -> price after discount as string
	// -> cell[3] -> discount
	public static void getPriceForBootke(ArrayList<Object> recived, ConnectionToClient client) {
		boolean moreThanOrdered = false;
		ArrayList<Object> answer = new ArrayList<Object>();
		Double priceBeforeDiscount = 0.0;
		Double priceAfterDiscount = 0.0;
		Double disount = 0.0;
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
		String howMany = (String) recived.get(4);
		ArrayList<Object> objForFech = new ArrayList<Object>();
		ArrayList<String> orderNumArr = new ArrayList<String>();
		orderNumArr.add(orderNumber);
		if (orderNumber != null) {
			objForFech.add("ordersByOrderNumber");
			objForFech.add(orderNumArr);
		} else {
			objForFech.add("ordersByIdOrMemberId");
			if (memberId != null)
				objForFech.add(memberId);
			else if (id != null)
				objForFech.add(id);
		}
		ArrayList<ArrayList<String>> orderWrapped = ExistingOrderCheck.fechOrder(objForFech, "orders", "orderNumber");
		if (!orderWrapped.isEmpty()) {//order was found
			Order order = new Order(orderWrapped.get(1));
			priceBeforeDiscount += order.getPrice();
			priceAfterDiscount += order.getTotalPrice();
			disount = (1-(priceBeforeDiscount-priceAfterDiscount)/priceBeforeDiscount)*100; //discount in precent
			// suck the order price
			if (order.getVisitorsNumber() < Integer.parseInt(howMany)) //if more than pepole on reservation
				moreThanOrdered = true;
		}
		if (orderWrapped.isEmpty() || moreThanOrdered) { // if order not exist
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			Order stubOrder = new Order(park.getName(), now.format(dtf).toString(), memberId, id, Integer.parseInt(howMany));
			Member member = NewOrder.MemerCheck(stubOrder);
			stubOrder = NewOrder.totalPrice(stubOrder, member, true);
			//dont forget to insert the + thig
			priceBeforeDiscount += stubOrder.getPrice();
			priceAfterDiscount += stubOrder.getTotalPrice();
			disount = (1-(priceBeforeDiscount-priceAfterDiscount)/priceBeforeDiscount)*100; //discount in precent
		}
		answer.add(priceBeforeDiscount);
		answer.add(priceAfterDiscount);
		answer.add(disount);
		try {
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// insertToPark
	// parkName + ID/MEMBERID/ORDERNUMBER + HOWMANY
	// enter, parkfull, allreadyInPark, notGoodTime
	public static void insertToParkForBootke(ArrayList<Object> recived, ConnectionToClient client) {
		
	}

	// exitFromPark
	// parkName + ID/MEMBERID/ORDERNUMBER + HOWMANY
	// exited, allreadyexited(today), neverwashere(today)
}
