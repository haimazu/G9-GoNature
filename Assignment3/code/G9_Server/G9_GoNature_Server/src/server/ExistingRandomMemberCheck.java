package server;

import java.util.ArrayList;
import java.util.Arrays;

import ocsf.server.ConnectionToClient;

public class ExistingRandomMemberCheck {
	// input: ArrayList<Object> received,
	//					cell [0]: memberByIdOrMemberId
	// 					cell [1]: Id / MemberId ,ConnectionToClient
	// output: ArrayList<Object> answer
	// 				    cell[0] function name
	// 					cell[1] ArrayList<Object> answer,
	//                                            member details -> if success 
	//                                            Not a member -> if failed
	public static void getMemberByIdOrMemberId(ArrayList<Object> recived, ConnectionToClient client) {
		// query
		ArrayList<Object> answer = new ArrayList<Object>();
		// the service name : membersByIdOrMemberId
		answer.add(recived.get(0));
		// the data that sent from the client
		// cell 0: ID / memberId
		ArrayList<String> data = (ArrayList<String>) recived.get(1);

		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("member"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE ID = '" + data.get(0) + "' OR memberId = '" + data.get(0) + "'"); // condition
		query.add("9"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty()) {
			answer.add(new ArrayList<String>(Arrays.asList("Not a member")));
		} else {
			answer.add(queryData.get(0));
		}

		EchoServer.sendToMyClient(answer, client);

	}
}
