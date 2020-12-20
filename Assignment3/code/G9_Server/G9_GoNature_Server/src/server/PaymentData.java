package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;

public class PaymentData {
	
	
	//input: ArrayList<Object>=> cell[0] function name
	//                           cell[1] order object
	//                           cell[2] payment type in string format
	//                           cell[3] credit card object if payment type is credit card
	//
	//output: ArrayList<Object>=> cell[0] function name
	//                           cell[1] T\F if input to DB was success
	public static void PaymentInsertData(ArrayList<Object> recived, ConnectionToClient client) {
		
		WaitingList a = new WaitingList();
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Order data=(Order)recived.get(1);
		if((String)recived.get(2)=="CreditCard") {
			CreditCard cc=(CreditCard)recived.get(3);
			if(creditCardSave(cc,data))
				answer.add(true);
			else
				answer.add(false);
		}
		else {
			answer.add(true);
			EchoServer.sendToMyClient(answer, client);
		}
	}
	
	
	// input: object credit card, object order
	//
	//output: T\F if success
	public static Boolean creditCardSave(CreditCard cc, Order data) {
		
		cc.setOrderNumber(data.getOrderNumber());
		
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add(toStringForCreditCardSave(cc)); // values in query format

		if (MySQLConnection.insert(query)) {
			return true;
		} else {
			return false;
		}
		
	}

	
	//in use for CreditCardSave query
		public static String toStringForCreditCardSave(CreditCard data) {
			return "'" + data.getCardNumber() + "','" 
					+ data.getCardHolderName() + "','"
					+ data.getExpirationDate() + "','" 
					+ data.getCvc() +"','"
					+data.getOrderNumber()+ "'";
		}
	
}
