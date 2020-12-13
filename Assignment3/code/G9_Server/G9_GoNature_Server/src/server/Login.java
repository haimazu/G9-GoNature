package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import ocsf.server.ConnectionToClient;

//input: ArrayList of objects ->
//			in cell 0: the client function who called for service (in that case "login")
//			in cell 1: ArrayList of String ->
//							in cell 0: user name
//							in cell 1: password
//the function add the relevant data for the query and call MySQLConnection
//output: none (send to client with ServerController)
//send to client: ArrayList of objects ->
//					in cell 0: the client function who called for service (in that case "login")
//					in cell 1: ArrayList of String ->
//									{if failed:} in cell 0: the string "failed"
//									{if success:} in cell 0: the employee role as string
public class Login {
	public static void login(ArrayList<Object> recived, ConnectionToClient client) {
		//add if db up later
		ArrayList<Object> answer = new ArrayList<Object>();
		answer.add(recived.get(0));
		ArrayList<String> data = (ArrayList<String>)recived.get(1);
		ArrayList<String> query = new ArrayList<String>();
		query.add("select");
		query.add("employee");
		query.add("role");
		query.add("WHERE username='" + data.get(0) + "' AND password='" + data.get(1) + "'" );
		query.add("1");
		ArrayList<ArrayList<String>> queryData = MySQLConnection.select(query);
		if (queryData.get(0).isEmpty()) {
			answer.add(new ArrayList<String>(Arrays.asList("Failed")));
		} else {
			answer.add(queryData.get(0));
		}
		EchoServer.sendToMyClient(answer,client);
	}
}
