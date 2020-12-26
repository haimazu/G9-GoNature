package server;

import java.util.ArrayList;
import java.util.Arrays;

import dataLayer.CreditCard;
import ocsf.server.ConnectionToClient;
//executed by Nastya
//inserting new credit card in creditcard table in DB
//returns "Failed" in 'answer' arrayList if not succeed
//returns true in 'answer' arrayList if succeed
public class CreditCardSave {

	
	
	public static void creditCardSave(ArrayList<Object> recived, ConnectionToClient client) {
		// received object credit card
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		CreditCard data = (CreditCard) recived.get(1); // credit card object received
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add(toStringForCreditCardSave(data)); // values in query format

		Boolean queryData = MySQLConnection.insert(query);
		if (queryData == false) {
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			answer.add(true);
		}
		EchoServer.sendToMyClient(answer, client);
	}

	
	//in use for CreditCardSave query
		public static String toStringForCreditCardSave(CreditCard data) {
			return "'" + data.getCardNumber() + "','" + data.getCardHolderName() + "','"
					+ data.getExpirationDate() + "','" + data.getCvc() + "'";
		}
}
