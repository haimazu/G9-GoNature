package server;

import java.util.ArrayList;
import java.util.Arrays;

import ocsf.server.ConnectionToClient;

public class CreditCardSave {

	public static void creditCardSave(ArrayList<Object> recived, ConnectionToClient client) {
		
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ArrayList<String> data = (ArrayList<String>) recived.get(1);
		ArrayList<String> query = new ArrayList<String>();
		query.add("insert"); // command
		query.add("creditcard"); // table name
		query.add("cardNumber,cardHolderName,expiredDate,cvc"); // columns names

		StringBuilder values = new StringBuilder();
		for (String p : data) {
			values.append(p);
			values.append(", ");
		}
		//deleting the last " , " & " "
		if (values.length() > 0) {
			values.setLength(values.length()-2);
		}
		
		query.add(values.toString());//adding the values to insert
		
		Boolean queryData = MySQLConnection.insert(query);
		if (queryData==false) {
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			answer.add(true);
		}
		EchoServer.sendToMyClient(answer,client);
	}

}
