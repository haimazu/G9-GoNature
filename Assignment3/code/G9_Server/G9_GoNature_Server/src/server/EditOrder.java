package server;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import orderData.Order;
import userData.Member;

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
		Order stubOrder = new Order(newOrder);
		ArrayList<Object> objectArrayStub = new ArrayList<Object>();
		ArrayList<Object> stubForAvilableSpots = new ArrayList<Object>();
		objectArrayStub.add(recived.get(0));
		stubForAvilableSpots.add(recived.get(0));
		newOrder = NewOrder.totalPrice(newOrder, NewOrder.MemerCheck(newOrder), false);
		if (newOrder.getArrivedTime().equals(originalOrder.getArrivedTime())){ //if same time
			if (newOrder.getVisitorsNumber()<originalOrder.getVisitorsNumber()) { //if less than original
				updateVisitorsNumber(originalOrder.getOrderNumber(),newOrder.getVisitorsNumber());
				stubOrder.setVisitorsNumber(originalOrder.getVisitorsNumber()-newOrder.getVisitorsNumber()); //will stay positive cause of the if
				objectArrayStub.add(stubOrder);
				WaitingList.enterTheWaitList(objectArrayStub, client);
			} else { //if more than original
				stubOrder.setVisitorsNumber(newOrder.getVisitorsNumber()-originalOrder.getVisitorsNumber()); //will stay positive cause of the if
				stubForAvilableSpots.add(stubOrder);
				if (waitlist.checkForAvailableSpots(stubForAvilableSpots,client)) {
					updateVisitorsNumber(originalOrder.getOrderNumber(),newOrder.getVisitorsNumber());
				} else { //nospots to expand
					answer.add("no spots avilable");
					try {
						client.sendToClient(answer);
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				}
			}
			updatePrice(newOrder);
			answer.add("edit OK");
		} else {
			stubForAvilableSpots.add(newOrder);
			if (waitlist.checkForAvailableSpots(stubForAvilableSpots,client)) {
				updatePrice(newOrder);
				objectArrayStub.add(originalOrder);
				CancelOrder.cancel(objectArrayStub,client);
				
				//use CancelOrder to cancel old order
				//if there is new spot use insertNewOrder method
				//if no spots avilable at the date send no spots avilable
			
			
			}
		}
		//send to client answer what you did
	}
	
	//input: int order number, int visitors number
	//output: true if sussesful false if not
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
	
	//input: Order to update price to
	//output: true if sussesful false if not
	//send to DB: update the price of a given order
	private static boolean updatePrice(Order order) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("update"); // command
		query.add("orders"); // table name
		query.add("beforeDiscountPrice='"+ order.getPrice() + "AfterDiscountPrice='"+ order.getTotalPrice() + "'"); // values
		query.add("orderNumber"); // primaryKey
		query.add(""+order.getOrderNumber()); // pkValue
		return MySQLConnection.update(query);
	}
	
	//input: Order to insert into the DB
	//output: true if sussesful false if not
	//send to DB: new order to list in
	public static boolean insertNewOrder(Order order) {
	ArrayList<String> query = new ArrayList<String>();
	query.add("insert"); // command
	query.add("orders"); // table name
	query.add(order.toStringForDB()); // values in query format
	return MySQLConnection.insert(query);
	}
}
