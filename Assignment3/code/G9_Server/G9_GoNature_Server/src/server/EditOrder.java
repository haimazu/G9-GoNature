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
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order newOrder = (Order)recived.get(1);
		Order originalOrder = (Order)recived.get(2);
		if (newOrder.getArrivedTime().equals(originalOrder.getArrivedTime())){
			if (newOrder.getVisitorsNumber()<originalOrder.getVisitorsNumber()) {
				//only update and start the waitlist with no delete
			} else {
				//only update and start the waitlist with no delete
				//check if have avilable spots only for the hefresh
			}
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
}
