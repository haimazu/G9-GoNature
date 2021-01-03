package client;

import java.io.IOException;
import java.util.ArrayList;

import controllers.*;
import ocsf.client.*;
import orderData.Order;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	private boolean awaitResponse = false;
	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public ChatClient(String host, int port, WelcomeController control2) throws IOException {
		super(host, port); // Call the superclass constructor

		System.out.println("the Client is connected to server " + host + " over port " + port);
		connect();

	}

	public void connect() {
		try {
			openConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // in order to send more than one message
	}

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */

	@SuppressWarnings("unchecked")
	public void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		ArrayList<Object> received = (ArrayList<Object>) msg;
		switch ((String)received.get(0)) {
			case "login":
				LoginController.receivedFromServerUserStatus((Object)received.get(1));
				break;
			case "updateLoggedIn":
				LoginController.receivedFromServerLoggedInStatus((boolean)received.get(1));
				break;
			case "orderParksNameList":
				OrderController.recivedFromServerParksNames(((ArrayList<String>)received.get(1)));
				break;
			case "ordersByIdOrMemberId":
				ParkEmployeeController.receivedFromServerOrderDetails((Object)received.get(1));
				break;
			case "ordersByOrderNumber":
				ParkEmployeeController.receivedFromServerOrderDetails((Object)received.get(1));
				break;
			case "getParkDetails":
				ParkEmployeeController.receivedFromServerParkDetails((Object)received.get(1));
				break;
			case "updateCurrentVisitors":
				ParkEmployeeController.receivedFromServerUpdateStatus((boolean)received.get(1));
				break;
			case "updateAmountArrived":
				ParkEmployeeController.receivedFromServerUpdateStatus((boolean)received.get(1));
				break;
			case "randomVisitorFakeOrder":
				ParkEmployeeController.receivedFromServerVisitorsPrice((Object)received.get(1));
				break;
			case "addFakeOrder":
				ParkEmployeeController.receivedFromServerUpdateStatus((boolean)received.get(1));
				break;
			case "order":
				OrderController.recivedFromServer((Object)received.get(1));
				break;
			case "checkOrderForGo":
				WelcomeController.recievedFromServerValidOrderAndPending((ArrayList<Object>)received);
				break;
			case "confirmOrder":
				OrderController.recivedFromServerConfirmOrder((boolean)received.get(1));
				break;
			case "editOrder":
				ManageOrderController.updatedOrderFromServer((Object)received.get(1));
				break;
			case "cancelOrder":
				ManageOrderController.canceledOrderFromServer((boolean)received.get(1));
				break;
			case "enterTheWaitList":
				WaitingListConfirmContoller.recivedfromWaitListServer((Object)received.get(1));
				break;
			case "newMembershipInsert":
				ServiceRepresentativeController.receivedFromServerAddMemberStatus((boolean)received.get(1),(String)received.get(2));
				break;
			case "checkFullDays":
				WaitingListController.getListDatesServer(((ArrayList<String>)received.get(1)));
				break;
			case "parkManagerRequest":
				ParkManagerController.recivedFromserver((boolean)received.get(1));
				break;
			case "requestForEmployeeID":
				ParkManagerController.recivedFromserverEmployeeID((String)received.get(1));
				break;
			case "getCancellationReports":
				DepartmentManagerController.receivedFromServerCancelReportsData(((ArrayList<ArrayList<String>>)received.get(1)));
				break;
			case "PendingManagerRequests":
				DepartmentManagerController.setDBList((ArrayList<ArrayList<String>>)received.get(1));
				break;
			case "updateAccessControl":
				ParkEmployeeController.receivedFromServerUpdateStatus((boolean)received.get(1));
				break;
			case "requestForParkDetails" :
				ParkManagerController.recivedFromserverParkDetails((Object)received.get(1));
				break;
			case "overallVisitorsReport" :
				ParkManagerController.recivedFromserverVisitorsReport((ArrayList<ArrayList<String>>)received.get(1));
				break;
			case "UsageReport":
				ParkManagerController.recivedFromserverUsageReport((ArrayList<ArrayList<String>>)received.get(1));
				break;
			case "waitlistReplay" :
				ManageOrderController.receviedFromserverArrivalConfirmation((ArrayList<Object>)received);
				break;
			case "removePendingsManagerReq":
				System.out.println((boolean)received.get(1));
				DepartmentManagerController.setData((boolean)received.get(1));
				break;
			case "getRegularsVisitorsData":
				DepartmentManagerController.receivedFromServerRegularsVisitorsData((double)received.get(1), (double)received.get(2),
																				   (double)received.get(3), (double)received.get(4));
				break;
			case "getMembersVisitorsData":
				DepartmentManagerController.receivedFromServerMembersVisitorsData(double)received.get(1), (double)received.get(2),
				   																 (double)received.get(3), (double)received.get(4));
				break;
			case "getGroupsVisitorsData":
				DepartmentManagerController.receivedFromServerGroupsVisitorsData(double)received.get(1), (double)received.get(2),
				   														        (double)received.get(3), (double)received.get(4));
				break;
			default:
			break;
		}
//		feedback = "";
//		awaitResponse = false;
//		data.clear();
//		if (msg instanceof ArrayList<?>) {
//			@SuppressWarnings("unchecked")
//			ArrayList<ArrayList<String>> got = (ArrayList<ArrayList<String>>) msg;
//			// data.addAll(got);
//			setData(got);
//			System.out.println("got data");
//			feedback = "positive";
//		} else {
//			String answer = (String) msg;
//			System.out.println("--> handleMessageFromServer");
//			System.out.println(answer);
//			if (!answer.contains("error"))
//				feedback = "positive";
//		}
//		/// add send to GUI

	}

	/**
	 * This method handles all data coming from the UI.
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(ArrayList<Object> message) {

		try {
			sendToServer(message);
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Could not send message to server.  Terminating client.");
			quit();
		}
		awaitResponse = true;
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		if (!feedback.equals("positive")) {
//			System.out.println("something went wrong");
//		}

	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
			System.out.println("did not closed connection");
		}
		System.exit(0);
	}

}
