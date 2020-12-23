package server;

import java.util.ArrayList;

import ocsf.server.ConnectionToClient;
import userData.Member;
//executed by Nastya
public class NewMember {

	// input: ArrayList<Object>: cell[0] function name
	// cell[1] member object ,ConnectionToClient
	// inserting a new member:MEMBER\GUIDE in members table in DB
	// output: ArrayList<Object>=> cell[0] function name
	// cell[1] T/F
	public static void NewMemberInsert(ArrayList<Object> recived, ConnectionToClient client) {
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		Member memb=(Member)recived.get(1);
		//if member is already exists in DB
		if(MemerCheck(memb.getMemberID())) {
			answer.add(false); 
			EchoServer.sendToMyClient(answer, client);
			return;
		}
		else {
			//adding a member to DB
			ArrayList<String> query = new ArrayList<String>();
			query.add("insert"); // command
			query.add("orders"); // table name
			query.add(queryToString(memb)); // values in query format
			MySQLConnection.insert(query);
			EchoServer.sendToMyClient(answer, client);
			
		}
	}

	// input: memberID
	//checks if the member exists in DB with his ID 
	// output: T/F
	public static Boolean MemerCheck(String memberID) {
		ArrayList<String> query = new ArrayList<String>();
		query.add("select"); // command
		query.add("member"); // table name
		query.add("*"); // columns to select from
		query.add("WHERE ID='" + memberID + "'");

		query.add("1"); // how many columns returned

		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.isEmpty())
			return false;
		else
			return true;
	}
	
	public static String queryToString(Member memb) {
		return "'" + memb.getMemberID() + "','" 
				+ memb.getMemberFirstName() + "','"
				+ memb.getMemberLastName() + "','" 
				+ memb.getMemberNumber() + "','" 
				+ memb.getMemberPhoneNumber()+ "','" 
				+ memb.getMemberEmail() + "','" 
				+ memb.getMemberPaymentType() + "','"
				+ memb.getMemberOrderType() + "','" 
				+ memb.getMemberAmount() + "'";
	}
}
