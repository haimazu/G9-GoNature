package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;

//input: arraylist of objects that contains:
//			[0] -> String "EditOrder"
//			[1] -> Order with changes
//			[2] -> original Order
//NOTE: changes can be made only for ArivelTime and VisitorsNumber parameters
//output:NONE
//send to client:
public class EditOrder {
	public static void edit(ArrayList<Object> recived, ConnectionToClient client) {
		WaitingList waitlist = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order newOrder = (Order)recived.get(1);
		Order originalOrder = (Order)recived.get(2);
		if (newOrder.getArrivedTime().equals(originalOrder.getArrivedTime())){
			if (newOrder.getVisitorsNumber()<originalOrder.getVisitorsNumber()) {
				//only update and start the waitlist with no delete
				updateVisitorsNumber(originalOrder.getOrderNumber(),newOrder.getVisitorsNumber());
			} else {
				Order stubOrder = new Order(newOrder);
				stubOrder.setVisitorsNumber(newOrder.getVisitorsNumber()-originalOrder.getVisitorsNumber()); //will stay positive cause of the if
				ArrayList<Object> stubForAvilableSpots = new ArrayList<Object>();
				stubForAvilableSpots.add("");
				stubForAvilableSpots.add(stubOrder);
				if (waitlist.checkForAvailableSpots(stubForAvilableSpots,client)) {
					updateVisitorsNumber(originalOrder.getOrderNumber(),newOrder.getVisitorsNumber());
					
				} else { //nospots to expand
					//nofity the user
					return;
				}
			}
			//only update and start the waitlist with no delete
			//update price
		} else {
			//checkForAvailableSpots for new order
			//if there is new spot use queInsert method
			//if no spots avilable at the date send no spots avilable
			//update price
			//use CancelOrder to cancel old order
		}
		//send to client answer what you did
	}
	
	//input: int order number, int visitors number
	//output: NONE
	//send to DB: update the visitors number in that given order
	private static boolean updateVisitorsNumber(int orderNum, int visitorsNum) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("orders"); // table name
		query.add("visitorsNumber='"+ visitorsNum + "'"); // values
		query.add("orderNumber"); // primaryKey
		query.add(""+orderNum); // pkValue
		return MySQLConnection.update(query);
	}
}
