package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dataLayer.EmailMessege;
import dataLayer.SmsMessege;
import ocsf.server.ConnectionToClient;
import orderData.Order;

public class Comunication {
	public static void sendNotification(String subject ,String messege, Order order) {
		SmsMessege sms = new SmsMessege(order.getOrderPhone(),subject+"\n\n "+messege,order);
		EmailMessege email = new EmailMessege(order.getOrderEmail(),subject,messege,order);
		//SIMULATION ONLY
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new Runnable() {
			@Override
			public void run() {
				SendMail.simulateMail(email);
				SendMail.simulateSms(sms);
			}
		});	
	}
	
	
	
	//input: array list of objects that contains:
	// [0] -> string "waitlistReplay"
	// [1] -> String answer (NOTE: have to be "yes" or "no")
	// [2] -> Order class of the order that initiated this method
	//output:none
	//sendToClient: array list of object that contains:
	// [0] -> string "waitlistReplay"
	// [1] -> Boolean true if the order is now in place false if something went wrong
	// NOTE: if the client say "no" an empty message will be sent to the client - ergo: nothing in cell [1].
	public static void waitlistReplay(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		String decide = (String)recived.get(1);
		Order order = (Order)recived.get(2);
		if (decide.equals("yes")) {
			answer.add(WaitListSingelton.getWaitlist().orderConfirm(order)); //if true than less than limit was not reached
		}
		else if (decide.equals("no")) {
			WaitListSingelton.getWaitlist().removePending(order);
			CancelOrder.deleteOrder(order.getOrderNumber());
			ArrayList<Object> stubForPullFromWaitList = new ArrayList<Object>();
			stubForPullFromWaitList.add("removeIt");
			stubForPullFromWaitList.add(order);
			WaitingList.pullFromWaitList(stubForPullFromWaitList);
		}
		try {
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
