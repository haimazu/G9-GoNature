package simulations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import dataLayer.EmailMessege;
import dataLayer.Messege;
import dataLayer.SmsMessege;
import orderData.Order;
import server.Comunication;
import server.WaitListSingelton;

//import com.mysql.cj.protocol.Message;

//import orderData.Order;

public class Simulations {
	private static Simulations obj;
	private static ArrayList<EmailMessege> recivedMails = new ArrayList<EmailMessege>();
	private static ArrayList<SmsMessege> recivedSms = new ArrayList<SmsMessege>();
	private static int choice;
	private static int orderNum;
	
	//SingletonBuilder
	private Simulations() {
		// on start up do this
	}

	//Singleton, create one instance only
	public static Simulations getSim() {
		if (obj == null) {
			if (obj == null) {
				obj = new Simulations();// instance will be created at request time
			}
		}
		return obj;
	}
	
	public static void sendMailSimulation(EmailMessege email) {
		recivedMails.add(email);
	}
	
	public static EmailMessege CheckMailSimulation(int orderNum) {
		for (EmailMessege message : recivedMails) {
			if (message.getOrder().getOrderNumber()==orderNum)
				return message;
		}
		return null;
	}
	
	public static void sendSmsSimulation(SmsMessege sms) {
		recivedSms.add(sms);
	}
	
	public static SmsMessege CheckSmsSimulation(int orderNum) {
		for (SmsMessege message : recivedSms) {
			if (message.getOrder().getOrderNumber()==orderNum)
				return message;
		}
		return null;
	}
	
	private static void reader(Messege message) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Messege replay = null;
		readForker(message);
		if(message.getMessage().equals("waitingList notification")) {
			do {	
				System.out.print("== waitlist notification == \n");
				System.out.print("== please choose one option == \n");
				System.out.print(" 1. ill make it   \n");
				System.out.print(" 2. give up your spot   \n");
				System.out.print("Enter your choice: ");
				br = new BufferedReader(new InputStreamReader(System.in));
				choice = Integer.parseInt(br.readLine());
				if (choice==1)
					replay = replayForker(message,"yes");
					//replay - activate the function in the server
				else if (choice==2)
					replay = replayForker(message,"no");
					//replay give out spot
			} while (!(choice == 1 || choice == 2));
			Comunication.replayCatcher(replay); //send to the server the replay
		} else {
			System.out.println(message.getMessage());
			System.out.println("\n");
		}
	}
	
	public static void readForker(Messege message) {
		if (message instanceof SmsMessege)
			((SmsMessege)message).read();
		else
			((EmailMessege)message).read();
	}
	
	public static Messege replayForker(Messege message, String replayMsg) {
		if (message instanceof SmsMessege)
			return ((SmsMessege)message).replay("SERVER", replayMsg);
		else
			return ((EmailMessege)message).replay("SERVER",replayMsg);
	}
	
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.print("========= Simulation ============ \n");
		System.out.print("Enter orderNumber: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		orderNum = Integer.parseInt(br.readLine());
		do {
			System.out.print("========= Simulation ============ \n");
			System.out.print(" 1. Check Mail   \n");
			System.out.print(" 2. Check SMS \n");
			System.out.print(" 0. Exit \n");
			System.out.print("Enter your choice: ");
			br = new BufferedReader(new InputStreamReader(System.in));
			choice = Integer.parseInt(br.readLine());
			switch (choice) {
				case 1: {
					EmailMessege message = CheckMailSimulation(orderNum);
					if (message!=null) {
						System.out.println("new mail for order " + orderNum );
						reader(message);
						recivedMails.remove(message);
					}
					else
						System.out.println("no new mail for order " + orderNum +"\n");
					}
					break;
				case 2: {
					SmsMessege message = CheckSmsSimulation(orderNum);
					if (message!=null) {
						System.out.println("new sms for order " + orderNum );
						reader(message);
						recivedSms.remove(message);
					}
					else
						System.out.println("no new sms for order " + orderNum +"\n");
					}
					break;
				case 0: {
					System.out.println("see you soon!");
				}
					break;
				default: {
					System.out.println("\nbad choice, not available\n");
					break;
				}
			}// end of switch

		} while (choice != 0);

	}
}
