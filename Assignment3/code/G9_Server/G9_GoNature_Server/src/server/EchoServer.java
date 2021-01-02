package server;

import java.io.IOException;
//import java.sql.Connection;
import java.util.ArrayList;

import gui.ServerController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class EchoServer extends AbstractServer {
	private static ServerController control;

	public EchoServer(int port, ServerController control) {
		super(port);
		EchoServer.control = control;
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		@SuppressWarnings("unchecked")
		ArrayList<Object> recived = (ArrayList<Object>) msg;
		WaitListSingelton.getWaitlist().CheckTheWaitList();
		switch ((String) recived.get(0)) {
		case "login":
			Login.login(recived, client);
			break;
		case "updateLoggedIn":
			Login.updateLoggedInStatus(recived, client);
			break;
		case "orderParksNameList":
			NewOrder.parksNames(recived, client);
			break;
		case "ordersByIdOrMemberId":	
			ExistingOrderCheck.getOrderDetailsByOrderNumber(recived, client);
			break;
		case "ordersByOrderNumber":	
			ExistingOrderCheck.getOrderDetailsByOrderNumber(recived, client);
			break;
		case "getParkDetails":
			UpdateVisitorsNumber.getParkDetails(recived, client);
			break;
		case "updateCurrentVisitors":
			UpdateVisitorsNumber.updateParkCurrentVisitors(recived, client);
			break;
		case "updateAmountArrived":
			NewOrder.updateOrderAmountArrived(recived, client);
			break;
		case "randomVisitorFakeOrder":
			NewOrder.NewReservation(recived, client);
			break;
		case "addFakeOrder":
			NewOrder.queInsert(recived, client);
			break;
		case "order":
			NewOrder.NewReservation(recived, client);
			break;
		case "checkValidOrderNum":
			ExistingOrderCheck.getOrderDetailsByOrderNumber(recived, client);
			break;
		case "confirmOrder":
			NewOrder.queInsert(recived, client);
			break;
		case "editOrder":
			EditOrder.edit(recived, client);
			break;
		case "enterTheWaitList":
			WaitingList.enterTheWaitList(recived, client);
			break;
		case "cancelOrder":
			CancelOrder.cancel(recived, client);
			break;
		case "newMembershipInsert":
			NewMember.NewMemberInsert(recived, client);
			break;
		case "checkFullDays":
			EditOrder.checkFullDays(recived, client);
			break;
		case "parkManagerRequest":
			PendingMenagerRequest.InsertToPending(recived, client);
			break;
		case "getCancellationReports":
			Reports.CancellationReport(recived, client);
			break;
		case "requestForEmployeeID" : 
			PendingMenagerRequest.employeeNumberSet(recived, client);
			break;
		case "PendingManagerRequests":
			PendingMenagerRequest.pendingManagerRequestAllItems(recived, client);
			break;
		case "updateAccessControl":
			UpdateVisitorsNumber.updateAccessControl(recived, client);
			break;
		case "requestForParkDetails" :
			PendingMenagerRequest.getParkDetails(recived, client);
			break;
		case "overallVisitorsReport" :
			Reports.OverallVisitorsReport(recived, client);
			break;
		case "checkIfPending" : 
			ExistingOrderCheck.checkIfPending(recived, client);
		case "UsageReport" :
			Reports.UsageReport(recived, client);
		case "waitlistReplay":
			Comunication.waitlistReplay(recived, client);
		case "removePendingsManagerReq":
			PendingMenagerRequest.deleteFromPending(recived, client);
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
	}

	public static void sendToMyClient(ArrayList<Object> msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void clientConnected(ConnectionToClient client) {
		control.logIt("New Client conneted: "+ client.toString() +" "+ this.getPort());
	}

}
