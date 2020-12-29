package server;

import dataLayer.EmailMessege;
import dataLayer.Messege;
import dataLayer.SmsMessege;
import orderData.Order;
import simulations.Simulations;

public class Comunication {
	public static void sendNotification(String to, String messege, Order order) {
		SmsMessege sms = new SmsMessege(to,messege,order);
		EmailMessege email = new EmailMessege(to,messege,order);
		//SIMULATION ONLY
		Simulations.sendMailSimulation(email);
		Simulations.sendSmsSimulation(sms);
	}
	
	public static void replayCatcher(Messege msg) {
		String replayMsg = msg.getMessage();
		if (replayMsg.equals("yes"))
			replayForker(msg);
		else if (replayMsg.equals("no"))
			WaitListSingelton.getWaitlist().removePending(msg.getOrder());
	}
	
	private static void replayForker(Messege message) {
		if (message instanceof SmsMessege)
			WaitListSingelton.getWaitlist().replayFromSms((SmsMessege)message);
		else
			WaitListSingelton.getWaitlist().replayFromMail((EmailMessege)message);
	}
}
