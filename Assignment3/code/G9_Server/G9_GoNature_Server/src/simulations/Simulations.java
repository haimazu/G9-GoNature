package simulations;

import java.util.ArrayList;

import orderData.Order;

public class Simulations {
	private static ArrayList<Integer> recivedMails;
	private static ArrayList<Integer> recivedSms;
	
	public static void sendMailSimulation(Order order) {
		recivedMails.add(order.getOrderNumber());
	}
	
	public static boolean CheckMailSimulation(int orderNum) {
		return recivedMails.contains(orderNum);
	}
	
	public static void sendSmsSimulation(Order order) {
		recivedMails.add(order.getOrderNumber());
	}
	
	public static boolean CheckSmsSimulation(int orderNum) {
		return recivedSms.contains(orderNum);
	}
	
	public static void main(String[] args) {
		
	}
}
