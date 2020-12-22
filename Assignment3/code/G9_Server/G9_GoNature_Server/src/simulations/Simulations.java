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
				MessageSimulation message = CheckMailSimulation(orderNum);
				if (message!=null) {
					System.out.println("new mail for order " + orderNum );
					if(message.getMessage().equals(""))
					System.out.print("====  ==== \n");
					System.out.print(" 1. Check Mail   \n");
				}
				else
					System.out.println("no new mail! for order " + orderNum );
				}
				break;
			case 2: {
				MessageSimulation message = CheckSmsSimulation(orderNum);
				if (message!=null) {
					System.out.println("new sms for order " + orderNum );
					
				}
				else
					System.out.println("no sms mail! for order " + orderNum );
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
