package simulations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.mysql.cj.protocol.Message;

import orderData.Order;

public class Simulations {
	private static ArrayList<MessageSimulation> recivedMails = new ArrayList<MessageSimulation>();
	private static ArrayList<MessageSimulation> recivedSms = new ArrayList<MessageSimulation>();
	private static int choice;
	private static int orderNum;
	
	public static void sendMailSimulation(int orderNum,String message,String to) {
		recivedMails.add(new MessageSimulation(orderNum,message,to));
	}
	
	public static MessageSimulation CheckMailSimulation(int orderNum) {
		for (MessageSimulation message : recivedMails) {
			if (message.getOrderNum()==orderNum)
				return message;
		}
		return null;
	}
	
	public static void sendSmsSimulation(int orderNum,String message,String to) {
		recivedMails.add(new MessageSimulation(orderNum,message,to));
	}
	
	public static MessageSimulation CheckSmsSimulation(int orderNum) {
		for (MessageSimulation message : recivedSms) {
			if (message.getOrderNum()==orderNum)
				return message;
		}
		return null;
	}
	
	private static void reader(MessageSimulation message) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nTo: " + message.getTo());
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
				System.out.println("im in!");
				//replay - activate the function in the server
			else if (choice==2)
				System.out.println("im out");
				//replay give out spot
			} while (!(choice == 1 || choice == 2));
		} else {
			System.out.println(message.getMessage());
			System.out.println("\n");
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		//tests only
		recivedMails.add(new MessageSimulation(555, "messege!", "demo@simulation.com"));
		recivedSms.add(new MessageSimulation(555, "waitingList notification", "0501234567"));
		//remove when done playing
		
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
				MessageSimulation message = CheckMailSimulation(orderNum);
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
				MessageSimulation message = CheckSmsSimulation(orderNum);
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
