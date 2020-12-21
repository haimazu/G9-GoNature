package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
import orderData.Order;

public class PaymentData {
	//executed by Nastya
	// input: ArrayList<Object>=> cell[0] function name
	// cell[1] credit card object
	//
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T\F if input to DB was success
	public static void PaymentInsertData(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		CreditCard cc = (CreditCard) recived.get(1);
		answer.add(creditCardSave(cc)); //T\F
		EchoServer.sendToMyClient(answer, client);
	}

	// input: object credit card
	//
	// output: T\F if success
	public static Boolean creditCardSave(CreditCard cc) {

		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add(toStringForCreditCardSave(cc)); // values in query format

		return MySQLConnection.insert(query); //returns T\F
	}

	// in use for CreditCardSave query
	public static String toStringForCreditCardSave(CreditCard data) {
		return "'" + data.getCardNumber() + "','" + data.getCardHolderName() + "','" + data.getExpirationDate() + "','"
				+ data.getCvc() + "','" + data.getOrderNumber() + "'";
	}

}
